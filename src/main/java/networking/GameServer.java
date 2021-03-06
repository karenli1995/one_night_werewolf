package main.java.networking;
import java.io.IOException;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import main.java.characters.Characters;
import main.java.controller.MVCController;

public class GameServer {
	private static int PORT = 8901;
//	private static int PORT = Integer.valueOf(System.getenv("PORT"));

	private MVCController myController;
	
	private Map<String, Characters> myInitAssignments;
	private Characters[] myInitMiddleCards;
	
	public GameServer(MVCController controller) throws IOException {
		myController = controller;
		
		myInitAssignments = myController.getInitAssignments();
		myInitMiddleCards = myController.getMiddleCards();
		
        System.out.println("Starting game...");
        
        List<Player> allPlayers = new ArrayList<Player>();
        
    	ServerSocket listener = new ServerSocket(PORT);
        System.out.println("Werewolf Server is Running");
        try {
            while (true) {                
                for (String name : myInitAssignments.keySet()) {
                	Player player = new Player(listener.accept(), name, myInitAssignments, myInitMiddleCards, myInitAssignments.get(name));
                	allPlayers.add(player);
                	player.start();
                }
            }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            listener.close();
        }
        
        //TODO: delete
//        System.out.println("Final Assignments: " + game.getFinalAssignments().toString());
    }
}
