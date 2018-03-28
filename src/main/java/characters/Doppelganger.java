package main.java.characters;

import java.util.Map;
import java.util.Scanner;

public class Doppelganger {
    private boolean isWerewolf;
    private boolean isMason;
    private boolean isInsomniac;

    private Map<String, Characters> assignedRoles;
    private Map<String, Characters> updatedRoles;
    private Characters[] myInitMiddleCards;
    private Characters[] myUpdatedMiddleCards; //set by Drunk
    
    private Scanner myScanner;
    
    public Doppelganger(Map<String, Characters> assignedRoles, Characters[] middlecards, Scanner scanner) {
        this.assignedRoles = assignedRoles;
        this.updatedRoles = assignedRoles;
        this.myInitMiddleCards = middlecards;
        this.myUpdatedMiddleCards = middlecards;
        System.out.println("Doppelganger, wake up and look at another players card. You are now that role. "
                + "If your new role has a night action, do it now.");
        
        myScanner = scanner;
    }
    
    public Map<String, Characters> chooseRole() {
        System.out.println("Choose a player to Doppelgang. Enter the player's first name: ");
        String playerName = myScanner.next();
        Characters role = assignedRoles.get(playerName);
        System.out.println("You are now doppelganging " + playerName + ". You are the " + role);
        
        Map<String, Characters> newRoles = performRole(myInitMiddleCards, role);
        return newRoles;
    }

    private Map<String, Characters> performRole(Characters[] middlecards, Characters role) {
        if (role.equals(Characters.SEER)) {
            Seer seer = new Seer();
            seer.look(assignedRoles, middlecards, myScanner);
        } else if (role.equals(Characters.ROBBER)) {
            Robber robber = new Robber();
            updatedRoles = robber.exchangeWithAnotherPlayer(assignedRoles, assignedRoles, true, myScanner);
        } else if (role.equals(Characters.DRUNK)) {
            Drunk drunk = new Drunk();
            myUpdatedMiddleCards = drunk.exchange(middlecards, assignedRoles, assignedRoles, true, myScanner);
        } else if (role.equals(Characters.TROUBLEMAKER)) {
            Troublemaker trouble = new Troublemaker();
            updatedRoles = trouble.exchangeCards(assignedRoles, myScanner);
        } else if (role.equals(Characters.WEREWOLF1) || role.equals(Characters.WEREWOLF2)) {
            isWerewolf = true;
        } else if (role.equals(Characters.MASON1) || role.equals(Characters.MASON2)) {
            isMason = true;
        } else if (role.equals(Characters.MINION)) {
            System.out.println("You are a Minion. The Werewolves present in the game are: " );
            Minion m = new Minion();
            m.getWerewolves(assignedRoles);
        } else if (role.equals(Characters.INSOMNIAC)) {
            isInsomniac = true;
        }
        
        return updatedRoles;
    }
    
    public boolean isWerewolf() {
        return isWerewolf;
    }
    
    public boolean isMason() {
        return isMason;
    }
    
    public boolean isInsomniac() {
        return isInsomniac;
    }
    
    public Characters[] getUpdatedMiddleCards() {
        return myUpdatedMiddleCards;
    }

    public void performInsomniac(Map<String, Characters> finalroles) {
        Insomniac i = new Insomniac();
        i.wakeUp(assignedRoles, finalroles, true);
    }
}
