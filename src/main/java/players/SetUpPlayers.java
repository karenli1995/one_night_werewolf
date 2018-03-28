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
    
    public SetUpPlayers(String[] playerNames, List<Integer> chars) {
        
    }

    public void chooseCharacters(String[] playerNames, List<Integer> chars) {
        myNumPlayers = playerNames.length;

        System.out.println("Enter the numbers of players you wish to use separated by a single space between each. \n"
                + "(1) Werewolf1 (2) Werewolf2 (3) Seer (4) Robber (5) Troublemaker (6) Tanner \n"
                + "(7) Drunk (8) Hunter (9) Mason1 (10) Mason2 (11) Insomniac (12) Minion \n"
                + "(13) Doppelganger (14) Villager1 (15) Villager2 (16) Villager3 \n"
                + "(For example, enter 1 5 8 9 2): ");

        for (int i=0; i < chars.size(); i++){
            int n = chars.get(i);
            //check that it is num between 1-size(allCharacters)
            chosenCharacters.add(allCharacters.get(n));
            charactersInGame.add(allCharacters.get(n));
            i++;
        }
        System.out.println(chosenCharacters);
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
    
    public Set<Characters> getAllCharactersInGame() {
        return charactersInGame;
    }
}
