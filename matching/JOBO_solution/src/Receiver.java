import java.util.ArrayList;

public class Receiver implements MatchingObject {

    private int id;
    private String name;
    private ArrayList<Integer> preferredProposers;
    private int matchId;
    private boolean hasMatch;

    public Receiver(int id, String name) {
        this.id =id;
        this.name = name;
        preferredProposers = new ArrayList<>();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void addPreference(int[] ids) {
        for (int i = 0; i < ids.length; i++) {
            preferredProposers.add(ids[i]);
        }
    }

    public int getMatchId() {
        return matchId;
    }

    public boolean hasMatch() {
        return hasMatch;
    }

    public void tryMatch(int id) {
        if (preferredProposers.indexOf(id) < matchId) {
            setNewMatch(id);
        }
    }

    public void setNewMatch(int newMatchId) {
        hasMatch = true;
        matchId = newMatchId;
    }
}
