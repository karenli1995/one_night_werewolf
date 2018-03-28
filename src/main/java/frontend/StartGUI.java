package main.java.frontend;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import javafx.util.Pair;
import main.java.characters.Characters;
import main.java.controller.MVCController;
import main.java.networking.GameClient;
import main.java.networking.GameServer;
import main.java.players.SetUpPlayers;

public class StartGUI {
	private static final String TITLE = "Werewolf";
	private static final String myServerAddress = "localhost";

	private Stage myStage;
	private Scene myScene;
	private Group myRoot;
	private int myWindowWidth, myWindowHeight;
	
	private MenuBar myMenuBar;
	private MenuItem myConnectMenuItem;
	private VBox myVBox;
	
	private TextField myNamesTf, myRolesTf;
	
	private MVCController myController;

	public StartGUI(Stage stage) throws Exception {
		myStage = stage;
		myController = new MVCController();
		
		Scene scene = init((int)stage.getWidth(), (int)stage.getHeight());
		stage.setScene(scene);
		stage.setTitle(TITLE);
		
		MenuBar menuBar = setMenuBar(stage);
		myRoot.getChildren().add(menuBar);
		setUpGui();
		stage.show();
	}


	private MenuBar setMenuBar(Stage stage) throws Exception {
		myMenuBar = new MenuBar();
		Menu menu = new Menu("File");
		myConnectMenuItem = new MenuItem("Connect to Game");
		myConnectMenuItem.setDisable(true);
		connectGame();
		
		menu.getItems().add(myConnectMenuItem);
		myMenuBar.prefWidthProperty().bind(stage.widthProperty());
		
		myMenuBar.getMenus().add(menu);
		return myMenuBar;
	}
	
	private void addPlayers() {
		Label label1 = new Label("Enter Player First Names (separated by space):");
		myNamesTf = new TextField();
		HBox hb = new HBox();
		hb.getChildren().addAll(label1, myNamesTf);
		hb.setSpacing(10);
		
		myVBox.getChildren().add(hb);
	}
	
	private void addRoles() {
		Label label1 = new Label("Enter Roles to Play with (separated by space):");
		myRolesTf = new TextField();
		HBox hb = new HBox();
		hb.getChildren().addAll(label1, myRolesTf);
		hb.setSpacing(10);
		
		myVBox.getChildren().add(hb);
	}
	
	
	private void connectGame() {
		myConnectMenuItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				while (true) {
		            GameClient client = null;
					try {
						client = new GameClient(myServerAddress, myStage, myController);
					} catch (Exception e) {
						e.printStackTrace();
					}
		            try {
						client.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
		        }
			}
		});
	}


	private void setUpGui() {
		myVBox = new VBox();
		myVBox.setSpacing(10);
		myRoot.getChildren().add(myVBox);
		
		addPlayers();
		addRoles(); 
		
		Button startGameBtn = new Button("Start Game");
		myRoot.getChildren().add(startGameBtn);

		startGameBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	String[] names = myNamesTf.getText().split(" ");
            	String[] roles = myRolesTf.getText().split(" ");
            	List<Integer> intRoles= new ArrayList<Integer>();
            	for(String num : roles) {
            		intRoles.add(Integer.parseInt(num));
            	}
            	
            	myController.passDataForSetUp(names, intRoles);
            	try {
					myController.startGameServer();
				} catch (IOException e) {
					e.printStackTrace();
				}
            	
            	myConnectMenuItem.setDisable(false);
            }
        });
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
