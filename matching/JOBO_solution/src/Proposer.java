import java.util.Stack;

public class Proposer implements MatchingObject {
    private int id;
    private String name;
    private Stack<Integer> preferredReceivers;

    Proposer(int id, String name) {
        this.id = id;
        this.name = name;
        preferredReceivers = new Stack<>();
    }

    int getId() {
        return this.id;
    }

    String getName() {
        return this.name;
    }

    public void addPreference(int[] ids) {
        for (int i = ids.length - 1; i >= 0; i--) {
            preferredReceivers.push(ids[i]);
        }
    }

    int getNextPreferenceId() {
        if (preferredReceivers.isEmpty()) {
            return -1;
        }
        else {
            return preferredReceivers.pop();
        }
    }
}
