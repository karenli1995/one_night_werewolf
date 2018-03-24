package main.java.characters;

import java.util.Map;

public class Insomniac {
    public void wakeUp(Map<String, Characters> initroles, Map<String, Characters> finalroles, boolean isDoppelganger) {
        String myName = null;
        if (!isDoppelganger) {
            for (String name : initroles.keySet()) {
                if (initroles.get(name).equals(Characters.INSOMNIAC)) {
                    myName = name;
                }
            }
        } else {
            for (String name : initroles.keySet()) {
                if (initroles.get(name).equals(Characters.DOPPELGANGER)) {
                    myName = name;
                }
            }
        }
        
        Characters role = finalroles.get(myName);
        System.out.println("Hello " + myName + ". You are now: " + role.toString());
    }
}
