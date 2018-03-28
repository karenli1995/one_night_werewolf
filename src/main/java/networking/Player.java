package main.java.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;

import main.java.characters.Characters;
import main.java.characters.Doppelganger;
import main.java.frontend.Card;

public class Player extends Thread {
	String myName;
	Socket socket;
	BufferedReader input;
	PrintWriter output;

	Map<String, Characters> initAssignments;
	Characters[] middlecards;
	Characters role;

	/**
	 * Constructs a handler thread for a given socket and mark
	 * initializes the stream fields, displays the first two
	 * welcoming messages.
	 */
	public Player(Socket socket, String mark, Map<String, Characters> assignedRoles, Characters[] middlecards, Characters role) {
		this.socket = socket;
		this.myName = mark;

		this.initAssignments = assignedRoles;
		this.middlecards = middlecards;
		this.role = role;
		try {
			input = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			output = new PrintWriter(socket.getOutputStream(), true);
			output.println("WELCOME " + role);
			output.println("MESSAGE Waiting for other players to connect");
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		}
	}

	/**
	 * The run method of this thread.
	 */
	public void run() {
		try {
			// The thread is only started after everyone connects.
			output.println("MESSAGE All players connected");

			// Repeatedly get commands from the client and process them.
			while (true) {
				String command = input.readLine();
				if (command.startsWith("SWITCH_MIDDLE")) {
					String[] cmd = command.split(" ");
					int middleCardIndex = Integer.parseInt(cmd[1]);
					String playerName = cmd[2];
					switchMiddleCard(middleCardIndex, playerName);

					if (myName.equals(playerName)) {
						this.role = initAssignments.get(myName);
					}

					output.println("MESSAGE " + this.role.toString());
				} else if (command.startsWith("SWITCH_PLAYERS")) {
					String[] cmd = command.split(" ");
					String player1Name = cmd[1];
					String player2Name = cmd[2];
					switchPlayerCard(player1Name, player2Name);

					if (myName.equals(player1Name) || myName.equals(player2Name)) {
						this.role = initAssignments.get(myName);
					}

					output.println("MESSAGE " + this.role.toString());
				} else if (command.startsWith("END_GAME")) {
					return;
				}
			}
		} catch (IOException e) {
			System.out.println("Player died: " + e);
		} finally {
			try {socket.close();} catch (IOException e) {}
		}
	}

	private void switchPlayerCard(String player1Name, String player2Name) {
		Characters player1Card = initAssignments.get(player1Name);
		Characters player2Card = initAssignments.get(player2Name);

		initAssignments.put(player1Name, player2Card);
		initAssignments.put(player2Name, player1Card);
	}

	private void switchMiddleCard(int middleCardIndex, String playerName) {
		Characters middleCard = middlecards[middleCardIndex];
		Characters playerCard = initAssignments.get(playerName);

		middlecards[middleCardIndex] = playerCard;
		initAssignments.put(playerName, middleCard);
	}

}
