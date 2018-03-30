package main.java.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.java.characters.Characters;
import main.java.networking.GameServer;
import main.java.players.SetUpPlayers;

@Component
public class BackendController {
	
    @Autowired
	private SetUpPlayers mySetup;
    
    @Autowired
    private GameServer myGameServer;

	public void passDataForSetUp(String[] names, String[] intRoles) {
		mySetup.chooseCharacters(names, intRoles);
		mySetup.chooseMiddleCards();
		mySetup.assignPlayers(names);		
	}
	
	public Map<String, Characters> getInitAssignments() {
		return mySetup.getInitAssignments();
	}
	
	public Characters[] getMiddleCards() {
		return mySetup.getMiddleCards();
	}

	public void startGameServer() throws IOException {
	    myGameServer.startServer();
	}
	
}
