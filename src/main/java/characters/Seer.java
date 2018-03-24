package main.java.characters;

import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Seer {
    public void look(Map<String, Characters> updatedRoles, Characters[] middlecards, Scanner scanner) {
        System.out.println("Hello Seer. Choose a player's card to look at. Type their name. Type NO if you want to look at 2 center cards instead: ");
        String playerName = scanner.next();

        Characters role;
        if (!playerName.equals("NO")) {
            role = updatedRoles.get(playerName);
            System.out.println("You viewed " + playerName + "'s card. He/she is " + role.toString());
        } else {
            System.out.println("Here are 2 random center cards, and the indices of where they exist currently: ");
            Random rand = new Random();
            int n = rand.nextInt(2);
            System.out.println(middlecards[n] + " at Index " + n);
            int o = n;
            while (o == n) {
                o = rand.nextInt(2);
            }
            System.out.println(middlecards[o] + " at Index " + o);
        }
    }
}
