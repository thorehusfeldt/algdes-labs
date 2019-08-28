import java.util.ArrayList;

public class Receiver implements MatchingObject {

    private int id;
    private String name;
    private ArrayList<Integer> preferredProposers;
    private Proposer currentMatch;
    private boolean hasMatch;

    Receiver(int id, String name) {
        this.id =id;
        this.name = name;
        preferredProposers = new ArrayList<>();
        currentMatch = null;
        hasMatch = false;
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

    Proposer getCurrentMatch() {
        return currentMatch;
    }

    boolean isFree() {
        return !hasMatch;
    }

    boolean tryMatch(int id) {
        return preferredProposers.indexOf(id) < preferredProposers.indexOf(currentMatch.getId());
    }

    void setNewMatch(Proposer newMatch) {
        hasMatch = true;
        currentMatch = newMatch;
    }
}
