import java.util.Stack;

public class Proposer implements MatchingObject {
    private int id;
    private String name;
    private Stack<Integer> preferredReceivers;
    private int matchId;
    private boolean hasMatch;

    public Proposer(int id, String name) {
        this.id = id;
        this.name = name;
        preferredReceivers = new Stack<>();
        matchId = -1;
        hasMatch = false;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void addPreference(int[] ids) {
        for (int i = ids.length - 1; i >= 0; i--) {
            preferredReceivers.push(ids[i]);
        }
//        int until = preferredReceivers.size();
//        for (int i = 0; i < until; i++)
//            System.out.println(preferredReceivers.pop());
//        System.out.println("Stack is empty? " + preferredReceivers.isEmpty());
    }

    public int getNextPreferenceId() {
        return preferredReceivers.pop();
    }

    public int getMatchId() {
        return matchId;
    }

    public boolean hasMatch() {
        return hasMatch;
    }

    public void setNewMatch(int newMatchId) {
        matchId = newMatchId;
        hasMatch = true;
    }

    public void removeMatch() {
        matchId = -1;
        hasMatch = false;
    }
}
