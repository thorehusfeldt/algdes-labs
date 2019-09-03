import java.util.ArrayList;

public class Receiver implements MatchingObject {

    private int id;
    private String name;
    private ArrayList<Integer> preferredProposers;

    Receiver(int id, String name) {
        this.id =id;
        this.name = name;
        preferredProposers = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    public void addPreference(int[] ids) {
        for (int value : ids) {
            preferredProposers.add(value);
        }
    }

    boolean tryMatch(int id, int curMatchId) {
        return preferredProposers.indexOf(id) < preferredProposers.indexOf(curMatchId);
    }


}
