package players;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import characters.Characters;
import characters.Doppelganger;
import characters.Drunk;
import characters.Insomniac;
import characters.Mason;
import characters.Minion;
import characters.Robber;
import characters.Seer;
import characters.Troublemaker;
import characters.Werewolf;

public class MixUpCards {
    
    private Set<Characters> charsInPlay;
    
    private Map<String, Characters> initAssignments;
    private Map<String, Characters> finalAssignments = new HashMap<String, Characters>();
    
    private Characters[] middlecards;
    private Characters[] updatedMiddleCards;
    
    public MixUpCards(Map<String, Characters> assignments, Characters[] middlecards) {
        initAssignments = assignments;
        charsInPlay = new HashSet<Characters>();
        for (Characters c : assignments.values()) {
            charsInPlay.add(c);
        }
        this.middlecards = middlecards;
        
        for (String player : initAssignments.keySet()) {
            finalAssignments.put(player, initAssignments.get(player));
        }
        
        this.updatedMiddleCards = this.middlecards;
    }
    
    public void wakeUp(Scanner scanner) {
        Optional<Doppelganger> d = Optional.empty();
        if (charsInPlay.contains(Characters.DOPPELGANGER)) {
            d = Optional.of(new Doppelganger(initAssignments, middlecards, scanner));
            finalAssignments = d.get().chooseRole();
            updatedMiddleCards = d.get().getUpdatedMiddleCards();
            System.out.println(initAssignments);

        }
        if (charsInPlay.contains(Characters.WEREWOLF1) || charsInPlay.contains(Characters.WEREWOLF2)) {
            Werewolf w = new Werewolf();
            boolean doppelgangerWolf = false;
            if (d.isPresent()) {
                doppelgangerWolf = d.get().isWerewolf();
            }
            w.seeWerewolves(initAssignments, updatedMiddleCards, doppelgangerWolf);
            System.out.println(initAssignments);
        } 
        if (charsInPlay.contains(Characters.MINION)) {
            Minion m = new Minion();
            m.getWerewolves(initAssignments);
            System.out.println(initAssignments);

        }
        if (charsInPlay.contains(Characters.MASON1) || charsInPlay.contains(Characters.MASON2)) {
            Mason m = new Mason();
            boolean doppelgangerMason = false;
            if (d.isPresent()) {
                doppelgangerMason = d.get().isMason();
            }
            m.seeOtherMasons(initAssignments, doppelgangerMason);
            System.out.println(initAssignments);

        }
        if (charsInPlay.contains(Characters.SEER)) {
            Seer s = new Seer();
            s.look(finalAssignments, updatedMiddleCards, scanner);
            System.out.println(initAssignments);

        } 
        if (charsInPlay.contains(Characters.ROBBER)) {
            Robber r = new Robber();
            finalAssignments = r.exchangeWithAnotherPlayer(initAssignments, finalAssignments, false, scanner); //update roles TODO
            System.out.println(initAssignments);

        } 
        if (charsInPlay.contains(Characters.TROUBLEMAKER)) {
            Troublemaker t = new Troublemaker();
            finalAssignments = t.exchangeCards(finalAssignments, scanner); //update roles TODO
            System.out.println("Troublemaker: " + finalAssignments);
            System.out.println(initAssignments);

        } 
        if (charsInPlay.contains(Characters.DRUNK)) {
            Drunk drunk = new Drunk();
            updatedMiddleCards = drunk.exchange(updatedMiddleCards, initAssignments, finalAssignments, false, scanner);
        } 
        if (charsInPlay.contains(Characters.INSOMNIAC)) {
            Insomniac i = new Insomniac();
            i.wakeUp(initAssignments, finalAssignments, false);
        } 
        if (d.isPresent() && d.get().isInsomniac()) {
            d.get().performInsomniac(finalAssignments);
        }
    }
    
    public Map<String, Characters> getFinalAssignments() {
        return finalAssignments;
    }
}
