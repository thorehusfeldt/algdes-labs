import java.util.ArrayList;
import java.util.HashMap;

public class Receiver implements MatchingObject {

    private int id;
    private String name;
    private HashMap<Integer,Integer> proposerPreferenceValues;

    Receiver(int id, String name) {
        this.id =id;
        this.name = name;
        proposerPreferenceValues = new HashMap<>();
    }

    String getName() {
        return this.name;
    }

    public void addPreference(int[] ids) {
        for(int idx = 0; idx < ids.length; idx++) {
            proposerPreferenceValues.put(ids[idx], ids.length - idx);
        }
    }

    boolean tryMatch(int id, int curMatchId) {
        return proposerPreferenceValues.get(id) > proposerPreferenceValues.get(curMatchId);
    }


}
