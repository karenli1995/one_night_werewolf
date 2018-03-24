package main.java.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import main.java.characters.Characters;

public class SetUpPlayers {
    private Scanner myScanner = new Scanner(System.in);
    
    private int myNumPlayers = 0;
    
    private List<Characters> allCharacters = Arrays.asList(Characters.WEREWOLF1, Characters.WEREWOLF2, Characters.SEER,
            Characters.ROBBER, Characters.TROUBLEMAKER, Characters.TANNER, Characters.DRUNK, Characters.HUNTER,
            Characters.MASON1, Characters.MASON2, Characters.INSOMNIAC, Characters.MINION, Characters.DOPPELGANGER,
            Characters.VILLAGER1, Characters.VILLAGER2, Characters.VILLAGER3);
    private List<Characters> chosenCharacters = new ArrayList<Characters>();

    private Map<String, Characters> roleAssignments = new HashMap<String, Characters>();
    
    private Characters[] middleCards;
    
    private Set<Characters> charactersInGame = new HashSet<Characters>();
    
    public SetUpPlayers() {
        
    }

    public void chooseCharacters() {
        //set num players
        System.out.println("Enter number of players: ");
        myNumPlayers = myScanner.nextInt();

        System.out.println("Enter the numbers of players you wish to use separated by a single space between each. \n"
                + "(1) Werewolf1 (2) Werewolf2 (3) Seer (4) Robber (5) Troublemaker (6) Tanner \n"
                + "(7) Drunk (8) Hunter (9) Mason1 (10) Mason2 (11) Insomniac (12) Minion \n"
                + "(13) Doppelganger (14) Villager1 (15) Villager2 (16) Villager3 \n"
                + "(For example, enter 1 5 8 9 2): ");
        int i = 0;
        while (i < myNumPlayers + 3) {
            int n = myScanner.nextInt() - 1;
            //check that it is num between 1-size(allCharacters)
            chosenCharacters.add(allCharacters.get(n));
            charactersInGame.add(allCharacters.get(n));
            i++;
        }
        System.out.println(chosenCharacters);
    }
    
    public Characters[] chooseMiddleCards() {
        middleCards = new Characters[3];
        int i = 0;
        while (i < 3) {
            Random rand = new Random();
            int n = rand.nextInt(chosenCharacters.size()-1);
            middleCards[i] = chosenCharacters.get(n);
            chosenCharacters.remove(n);
            i++;
        }
        return middleCards;
        
    }

    public Map<String, Characters> assignPlayers() {
        Collections.shuffle(chosenCharacters);
        int j = 0;
        while (j < myNumPlayers) {
            System.out.println("Enter first name of Player " + j + ": ");
            String player = myScanner.next();
            roleAssignments.put(player, chosenCharacters.get(j));
            j++;
        }
        return roleAssignments;
    }
    
    public Set<Characters> getAllCharactersInGame() {
        return charactersInGame;
    }
    
    public Scanner getScanner() {
        return myScanner;
    }
}
