package main.java.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import main.java.characters.Characters;
import main.java.networking.GameServer;
import main.java.players.SetUpPlayers;

public class MVCController {
	
	private SetUpPlayers mySetup;

	public void passDataForSetUp(String[] names, List<Integer> intRoles) {
		mySetup = new SetUpPlayers();
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
		new GameServer(this);		
	}
	
}
