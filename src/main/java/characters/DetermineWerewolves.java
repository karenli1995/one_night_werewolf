package main.java.characters;

import java.util.Map;

public class DetermineWerewolves {
	private boolean isDoppelganger = false;
	
	public String seeWerewolves(Map<String, Characters> roles) {
		String werewolves = "";
		if (!isDoppelganger()) {
			for (String name : roles.keySet()) {
				if (roles.get(name).equals(Characters.WEREWOLF1) || roles.get(name).equals(Characters.WEREWOLF2)) {
					werewolves += name + " ";
				}
			}
		} else {
			for (String name : roles.keySet()) {
				if (roles.get(name).equals(Characters.WEREWOLF1) || roles.get(name).equals(Characters.WEREWOLF2) || roles.get(name).equals(Characters.DOPPELGANGER)) {
					werewolves += name + " ";
				}
			}
		}
		return werewolves;
	}

	public boolean isDoppelganger() {
		return isDoppelganger;
	}

	public void setDoppelganger(boolean isDoppelganger) {
		this.isDoppelganger = isDoppelganger;
	}
}
