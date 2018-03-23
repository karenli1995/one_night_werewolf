package players;

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

import characters.Characters;

public class SetUpPlayers {
    private Scanner myScanner = new Scanner(System.in);
    
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

    public int chooseCharacters() {
        //set num players
        System.out.println("Enter number of players: ");
        int numPlayers = myScanner.nextInt();

        System.out.println("Enter the numbers of players you wish to use separated by a single space between each. "
                + "(1) Werewolf1 (2) Werewolf2 (3) Seer (4) Robber (5) Troublemaker (6) Tanner (7) Drunk (8) Hunter (9) Mason1 (10) Mason2"
                + "(11) Insomniac (12) Minion (13) Doppelganger (14) Villager1 (15) Villager2 (16) Villager3 "
                + "(For example, enter 1 5 8 9 2): ");
        int i = 0;
        while (i < numPlayers + 3) {
            int n = myScanner.nextInt(); // Scans the next token of the input as an int.
            //check that it is num between 1-size(allCharacters)
            chosenCharacters.add(allCharacters.get(n));
            charactersInGame.add(allCharacters.get(n));
            i++;
        }
        System.out.println(chosenCharacters);
        return numPlayers;
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

    public Map<String, Characters> assignPlayers(int numPlayers) {
        Collections.shuffle(chosenCharacters);
        int j = 0;
        while (j < numPlayers) {
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
