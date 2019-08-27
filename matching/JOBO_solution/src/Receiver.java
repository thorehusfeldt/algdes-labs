import java.util.ArrayList;
import java.util.List;

public class Receiver implements MatchingObject {

    private int id;
    private String name;
    private List<Integer> preferredProposers;
    private int matchId;
    private boolean hasMatch;

    public Receiver(int id, String name) {
        this.id =id;
        this.name = name;
        preferredProposers = new ArrayList<>();
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

    public void setNewMatch(int newMatchId) {
        matchId = newMatchId;
    }
}
