package main.java.networking;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos; 
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.util.Pair;
import main.java.characters.Characters;
import main.java.characters.DetermineMasons;
import main.java.characters.DetermineWerewolves;
import main.java.controller.MVCController;
import main.java.frontend.Card;
import main.java.frontend.SwitchPlayerDialog;

public class GameClient {

	private Map<String, Card> playerCards;
	private Card[] middleCards;

	private static int PORT = 8901;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	private Stage myStage;
	private Scene myScene;
	private Group myRoot;
	private GridPane myGridPane;
	private Label myWerewolfLabel, myMasonsLabel, myMessageLabel;
	private Button mySwitchBtn, myFinishBtn;
	private int myWindowWidth, myWindowHeight;

	private String currPlayer;

	/**
	 * Constructs the client by connecting to a server, laying out the
	 * GUI and registering GUI listeners.
	 */
	public GameClient(String serverAddress, Stage stage, MVCController controller) throws Exception {
		Map<String, Characters> initAssignments = controller.getInitAssignments();
		for (String player : initAssignments.keySet()) {
			Card card = new Card(player, initAssignments.get(player));
			playerCards.put(player, card);
		}

		Characters[] initMiddleCards = controller.getMiddleCards();
		middleCards = new Card[3];
		for(int j=0; j<3; j++) {
			Card card = new Card(null, initMiddleCards[j]);
			middleCards[j] = card;
		}

		// Setup networking
		socket = new Socket(serverAddress, PORT);
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

		// Layout GUI
		Scene scene = init((int)stage.getWidth(), (int)stage.getHeight());
		stage.setScene(scene);

		setUpCards(initAssignments);
	}

	private void setUpCards(Map<String, Characters> initAssignments) {
		myGridPane = new GridPane();    

		myGridPane.setMinSize(400, 200);  
		myGridPane.setPadding(new Insets(10, 10, 10, 10)); 
		myGridPane.setVgap(5); 
		myGridPane.setHgap(5);       

		//Setting the Grid alignment 
		myGridPane.setAlignment(Pos.CENTER); 

		myMessageLabel = new Label();
		myGridPane.add(myMessageLabel, 0, 0);

		addSwitchCardFunctionality();

		for (int i=0; i<3; i++) {
			Card c = middleCards[i];
			myGridPane.add(c, i, 1);
		}

		int j = 0;
		for (String player : playerCards.keySet()) {
			Card c = playerCards.get(player);
			myGridPane.add(new Label(c.getPlayer()), j, 2);
			myGridPane.add(c, j, 3);
			j++;
		}

		DetermineWerewolves werewolves = setUpWereWolvesLabel(initAssignments);
		DetermineMasons masons = setUpMasonsLabel(initAssignments);

		addDoppelgangerFunctionalities(werewolves, masons);

		//TODO: implement
		myFinishBtn = new Button("Finish Game");
		myFinishBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				myMessageLabel.setVisible(true);
				out.println("END_GAME");
			}
		});

		myRoot.getChildren().addAll(myGridPane);
	}

	private void addSwitchCardFunctionality() {
		mySwitchBtn = new Button("Switch 2 Cards");
		myGridPane.add(mySwitchBtn, 1, 0);
		
		mySwitchBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				SwitchPlayerDialog dialog = new SwitchPlayerDialog();
				Optional<Pair<String, String>> result = dialog.showAndWait();
				
				result.ifPresent(twoCards -> {
				    String card1 = twoCards.getKey();
				    String card2 = twoCards.getValue();
				    performSwitch(card1, card2);
				});
			}
		});
	}
	
	//TODO: implement
	private void performSwitch(String card1, String card2) {
		int middleCardIndex = -1;
		String player1Name = null;
		String player2Name = null;
		try {
			middleCardIndex = Integer.parseInt(card1);
		} catch (NumberFormatException n) {
			player1Name = card1;
		}
		
		try {
			middleCardIndex = Integer.parseInt(card2);
		} catch (NumberFormatException n) {
			player2Name = card2;
		}
		
		if (middleCardIndex == -1) {
			switchPlayers(player1Name, player2Name);
		} else {
			String playerName = player1Name != null ? player1Name : player2Name;
			switchMiddleCard(middleCardIndex - 1, playerName);
		}

	}

	private void switchMiddleCard(int middleCardIndex, String playerName) {
		Card middleCard = middleCards[middleCardIndex];
		Card playerCard = playerCards.get(playerName);
		
		Characters middleRole = middleCard.getMyRole();
		Characters playerRole = playerCard.getMyRole();
		
		middleCard.setPlayer(playerName);
		middleCard.setMyRole(playerRole);
		playerCard.setPlayer(null);
		playerCard.setMyRole(middleRole);
		
		out.println("SWITCH_MIDDLE " + middleCardIndex + " " + playerName);
	}

	private void switchPlayers(String player1Name, String player2Name) {
		Card player1Card = playerCards.get(player1Name);
		Card player2Card = playerCards.get(player2Name);
		
		Characters player1Role = player1Card.getMyRole();
		Characters player2Role = player2Card.getMyRole();
		
		player1Card.setPlayer(player2Name);
		player1Card.setMyRole(player2Role);
		player2Card.setPlayer(player1Name);
		player2Card.setMyRole(player1Role);
		
		out.println("SWITCH_PLAYERS " + player1Name + " " + player2Name);
	}

	private DetermineWerewolves setUpWereWolvesLabel(Map<String, Characters> initAssignments) {
		DetermineWerewolves werewolves = new DetermineWerewolves();
		myWerewolfLabel = new Label("Reveal Werewolves");
		myWerewolfLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				myWerewolfLabel.setText("The Werewolves are: " + werewolves.seeWerewolves(initAssignments));
			}
		});
		myGridPane.add(myWerewolfLabel, 0, 7);
		return werewolves;
	}

	private DetermineMasons setUpMasonsLabel(Map<String, Characters> initAssignments) {
		DetermineMasons masons = new DetermineMasons();
		myMasonsLabel = new Label("Reveal Masons");
		myMasonsLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				myWerewolfLabel.setText("The Masons are: " + masons.seeOtherMasons(initAssignments));
			}
		});
		myGridPane.add(myMasonsLabel, 1, 7);
		return masons;
	}

	private void addDoppelgangerFunctionalities(DetermineWerewolves werewolves,
			DetermineMasons masons) {
		myGridPane.add(new Label("Doppelganger Buttons"), 0, 5);
		Button doppelGangWerewolfBtn = new Button("Doppelganging WereWolf");
		Button doppelGangMasonBtn = new Button("Doppelganging Mason");
		myGridPane.add(doppelGangWerewolfBtn, 1, 5);
		myGridPane.add(doppelGangMasonBtn, 2, 5);

		doppelGangWerewolfBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				werewolves.setDoppelganger(true);
			}
		});

		doppelGangMasonBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				masons.setDoppelganger(true);
			}
		});
	}

	/**
	 * The main thread of the client will listen for messages
	 * from the server.  The first message will be a "WELCOME"
	 * message in which we receive our mark.  Then we go into a
	 * loop listening for "VALID_MOVE", "OPPONENT_MOVED", "VICTORY",
	 * "DEFEAT", "TIE", "OPPONENT_QUIT or "MESSAGE" messages,
	 * and handling each message appropriately.  The "VICTORY",
	 * "DEFEAT" and "TIE" ask the user whether or not to play
	 * another game.  If the answer is no, the loop is exited and
	 * the server is sent a "QUIT" message.  If an OPPONENT_QUIT
	 * message is recevied then the loop will exit and the server
	 * will be sent a "QUIT" message also.
	 */
	public void play() throws Exception {
		String response;
		try {
			response = in.readLine();
			if (response.startsWith("WELCOME")) {
				String role = response.substring(8);
				myMessageLabel.setText("Hello, you are the " + role.toString());
			}
			while (true) {
				response = in.readLine();
				if (response.startsWith("MESSAGE")) {
					String role = response.split(" ")[1];
					myMessageLabel.setVisible(false);
					myMessageLabel.setText("You are now the " + role);
				}
			}
//			out.println("END_GAME");
		}
		finally {
			socket.close();
		}
	}

	/**
	 * Initialize the window
	 * @param width The width of the window
	 * @param height The height of the window
	 * @return the Scene that was initialized
	 */
	private Scene init(int width, int height) {
		myWindowWidth = width;
		myWindowHeight = height;
		myRoot = new Group();
		myScene = new Scene(myRoot,width,height, Color.AZURE);
		return myScene;
	}
}
