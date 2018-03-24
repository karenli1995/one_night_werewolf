package main.java.characters;

import java.util.Map;

public class Minion {

    public void getWerewolves(Map<String, Characters> initRoles) {
        System.out.println("Hello Minion. The werewolves you will be aiding are: ");
        for (String name : initRoles.keySet()) {
            if (initRoles.get(name).equals(Characters.WEREWOLF1) || initRoles.get(name).equals(Characters.WEREWOLF2)) {
                System.out.println(name);
            }
        }
    }
}
