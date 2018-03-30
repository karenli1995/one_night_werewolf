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

import org.springframework.stereotype.Component;

import main.java.characters.Characters;

@Component
public class SetUpPlayers {    
    private int myNumPlayers;
    
    private List<Characters> allCharacters;
    private List<Characters> chosenCharacters;

    private Map<String, Characters> roleAssignments;
    
    private Characters[] middleCards;
        
    public SetUpPlayers() {
        myNumPlayers = 0;
        
        allCharacters = Arrays.asList(Characters.WEREWOLF1, Characters.WEREWOLF2, Characters.SEER,
                Characters.ROBBER, Characters.TROUBLEMAKER, Characters.TANNER, Characters.DRUNK, Characters.HUNTER,
                Characters.MASON1, Characters.MASON2, Characters.INSOMNIAC, Characters.MINION, Characters.DOPPELGANGER,
                Characters.VILLAGER1, Characters.VILLAGER2, Characters.VILLAGER3);
        
        chosenCharacters = new ArrayList<Characters>();
        roleAssignments = new HashMap<String, Characters>();
    }
    
    public SetUpPlayers(String[] playerNames, List<Integer> chars) {
        
    }

    public void chooseCharacters(String[] playerNames, String[] chars) {
        int[] initRoles = new int[chars.length];
        for(int j=0; j<initRoles.length; j++) {
            initRoles[j] = Integer.parseInt(chars[j]);
        }
        System.out.println("YO" + initRoles);
        
        myNumPlayers = playerNames.length;

        System.out.println("Enter the numbers of players you wish to use separated by a single space between each. \n"
                + "(1) Werewolf1 (2) Werewolf2 (3) Seer (4) Robber (5) Troublemaker (6) Tanner \n"
                + "(7) Drunk (8) Hunter (9) Mason1 (10) Mason2 (11) Insomniac (12) Minion \n"
                + "(13) Doppelganger (14) Villager1 (15) Villager2 (16) Villager3 \n"
                + "(For example, enter 1 5 8 9 2): ");

        int i = 0;
        while (i < myNumPlayers + 3) {
            int n = initRoles[i] - 1;
            //check that it is num between 1-size(allCharacters)
            chosenCharacters.add(allCharacters.get(n));
            i++;
        }
        
//        for (int i=0; i < initRoles.length; i++){
//            int n = initRoles[i] - 1;
//            //check that it is num between 1-size(allCharacters)
//            chosenCharacters.add(allCharacters.get(n));
//        }
        System.out.println("HI" + chosenCharacters);
    }
    
    public void chooseMiddleCards() {
        middleCards = new Characters[3];
        int i = 0;
        while (i < 3) {
            Random rand = new Random();
            int n = rand.nextInt(chosenCharacters.size()-1);
            middleCards[i] = chosenCharacters.get(n);
            chosenCharacters.remove(n);
            i++;
        }        
    }

    public void assignPlayers(String[] playerNames) {
        Collections.shuffle(chosenCharacters);
        int j = 0;
        while (j < myNumPlayers) {
        	String player = playerNames[j];
            roleAssignments.put(player, chosenCharacters.get(j));
            j++;
        }
    }
    
    public Characters[] getMiddleCards() {
    	return middleCards;
    }
    
    public Map<String, Characters> getInitAssignments() {
    	return roleAssignments;
    }
    
//    public Set<Characters> getAllCharactersInGame() {
//        return charactersInGame;
//    }
}
