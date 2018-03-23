package characters;

import java.util.Map;
import java.util.Scanner;

public class Drunk {
    public Characters[] exchange(Characters[] middlecards, Map<String, Characters> initRoles, Map<String, Characters> finalRoles,
            boolean isDoppelganger, Scanner reader) {
        String myName = null;
        if (!isDoppelganger) {
            for (String player : initRoles.keySet()) {
                Characters role = initRoles.get(player);
                if(role.equals(Characters.DRUNK)) {
                    myName = player;
                }
            }
        } else {
            for (String player : initRoles.keySet()) {
                Characters role = initRoles.get(player);
                if(role.equals(Characters.DOPPELGANGER)) {
                    myName = player;
                }
            }
        }
        System.out.println(initRoles);
        System.out.println(myName);

        System.out.println("Hello Drunk. Enter the index of one of the middle cards you are switching with (0-2): ");
        int n = reader.nextInt();
        Characters prevRole = finalRoles.get(myName);
        Characters role = middlecards[n];
        middlecards[n] = prevRole;
        finalRoles.put(myName, role);
        
        System.out.println(middlecards[0].toString() + middlecards[1].toString() + middlecards[2].toString());
        return middlecards;
    }
}
