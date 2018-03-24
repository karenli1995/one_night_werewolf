package main.java;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import main.java.characters.Characters;
import main.java.players.MixUpCards;
import main.java.players.SetUpPlayers;

@SpringBootApplication
public class Game {

    public static void main(String[] args) {
        
        SetUpPlayers setup = new SetUpPlayers();
        
        setup.chooseCharacters();
        Characters[] middlecards = setup.chooseMiddleCards();
        Map<String, Characters> assignments = setup.assignPlayers();
        
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
