package main.java.characters;

import java.util.Map;
import java.util.Random;

public class Werewolf {
    public void seeWerewolves(Map<String, Characters> roles, Characters[] middlecards, boolean isDoppelganger) {
        System.out.println("Hello Werewolves. Here are the other Werewolf Players: ");
        int numWerewolvesAssigned = 0;
        if (!isDoppelganger) {
        for (String name : roles.keySet()) {
            if (roles.get(name).equals(Characters.WEREWOLF1) || roles.get(name).equals(Characters.WEREWOLF2)) {
                System.out.println(name);
                numWerewolvesAssigned++;
            }
        }
        } else {
            for (String name : roles.keySet()) {
                if (roles.get(name).equals(Characters.WEREWOLF1) || roles.get(name).equals(Characters.WEREWOLF2) || roles.get(name).equals(Characters.DOPPELGANGER)) {
                    System.out.println(name);
                    numWerewolvesAssigned++;
                }
            }
        }
        
        if (numWerewolvesAssigned < 2) {
            Random rand = new Random();
            int n = rand.nextInt(2);
            System.out.println("One card in the middle is: " + middlecards[n].toString() + " at index " + n);
        }
    }
}
