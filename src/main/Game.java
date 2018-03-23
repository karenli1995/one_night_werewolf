package main;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import characters.Characters;
import players.MixUpCards;
import players.SetUpPlayers;

public class Game {

    public static void main(String[] args) {
        
        SetUpPlayers setup = new SetUpPlayers();
        
        int numPlayers = setup.chooseCharacters();
        Characters[] middlecards = setup.chooseMiddleCards();
        Map<String, Characters> assignments = setup.assignPlayers(numPlayers);
        
        //TODO: delete
        System.out.println("Initial Assignments: " + assignments.toString());
        
        System.out.println("_____________________________________________________");
        System.out.println("Starting game...");
        
        MixUpCards game = new MixUpCards(assignments, middlecards);
        game.wakeUp(setup.getScanner());
        
        //TODO: delete
        System.out.println("Final Assignments: " + game.getFinalAssignments().toString());
    }
}
