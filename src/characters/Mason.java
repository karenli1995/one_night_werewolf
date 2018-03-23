package characters;

import java.util.Map;

public class Mason {
    public void seeOtherMasons(Map<String, Characters> assignedRoles, boolean isDoppelganger){
        System.out.println("The Masons are: ");
        if (!isDoppelganger) {
            for (String name : assignedRoles.keySet()) {
                if (assignedRoles.get(name).equals(Characters.MASON1) || assignedRoles.get(name).equals(Characters.MASON2)) {
                    System.out.println(name);
                }
            } 
        } else {
            for (String name : assignedRoles.keySet()) {
                if (assignedRoles.get(name).equals(Characters.MASON1) || assignedRoles.get(name).equals(Characters.MASON2)
                        || assignedRoles.get(name).equals(Characters.DOPPELGANGER)) {
                    System.out.println(name);
                }
            } 
        }
    }
}
