package main.java.characters;

import java.util.Map;
import java.util.Scanner;

public class Troublemaker {
    public Map<String, Characters> exchangeCards(Map<String, Characters> assignedRoles, Scanner reader) {
        System.out.println("Hello Troublemaker. Enter the names of the two other players you hope to switch, separated by a space: ");
        String player1 = reader.next();
        String player2 = reader.next();
        
        Characters character1 = assignedRoles.get(player1);
        Characters character2 = assignedRoles.get(player2);
        
        assignedRoles.put(player1, character2);
        assignedRoles.put(player2, character1);
        
        return assignedRoles;
    }
}
