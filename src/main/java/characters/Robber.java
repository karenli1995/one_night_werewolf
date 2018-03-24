package main.java.characters;

import java.util.Map;
import java.util.Scanner;

public class Robber {
    public Map<String, Characters> exchangeWithAnotherPlayer(Map<String, Characters> initRoles, Map<String, Characters> finalRoles,
            boolean isDoppelganger, Scanner reader) {
        String myName = null;
        if (!isDoppelganger) {
            for (String playerName : initRoles.keySet()) {
                if (initRoles.get(playerName).equals(Characters.ROBBER)) {
                    myName = playerName;
                }
            }
        } else {

            for (String playerName : initRoles.keySet()) {
                if (initRoles.get(playerName).equals(Characters.DOPPELGANGER)) {
                    myName = playerName;
                }
            }
        }

        System.out.println("Hello Robber. Type the first name of the player you want to exchange cards with: ");
        String player = reader.next();
        Characters role = finalRoles.get(player);

        Characters myRobberRole = finalRoles.get(myName);
        finalRoles.put(player, myRobberRole);
        finalRoles.put(myName, role);
        System.out.println("Player " + player + " was the " + role.toString() + ". Your new role: " + role.toString());
        return finalRoles;
    }
}
