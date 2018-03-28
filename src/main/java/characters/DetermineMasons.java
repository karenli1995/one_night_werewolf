package main.java.characters;

import java.util.Map;

public class DetermineMasons {
	private boolean isDoppelganger;
	
	public String seeOtherMasons(Map<String, Characters> assignedRoles){
		String names = "";
        if (!isDoppelganger) {
            for (String name : assignedRoles.keySet()) {
                if (assignedRoles.get(name).equals(Characters.MASON1) || assignedRoles.get(name).equals(Characters.MASON2)) {
                    names += name + " ";
                }
            } 
        } else {
            for (String name : assignedRoles.keySet()) {
                if (assignedRoles.get(name).equals(Characters.MASON1) || assignedRoles.get(name).equals(Characters.MASON2)
                        || assignedRoles.get(name).equals(Characters.DOPPELGANGER)) {
                    names += name + " ";
                }
            } 
        }
        return names;
    }
	
	public boolean isDoppelganger() {
		return isDoppelganger;
	}

	public void setDoppelganger(boolean isDoppelganger) {
		this.isDoppelganger = isDoppelganger;
	}
}
