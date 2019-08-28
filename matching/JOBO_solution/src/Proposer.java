import java.util.Stack;

public class Proposer implements MatchingObject {
    private int id;
    private String name;
    private Stack<Integer> preferredReceivers;
    private Receiver currentMatch;
    private boolean hasMatch;

    Proposer(int id, String name) {
        this.id = id;
        this.name = name;
        preferredReceivers = new Stack<>();
        currentMatch = null;
        hasMatch = false;
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
//        int until = preferredReceivers.size();
//        for (int i = 0; i < until; i++)
//            System.out.println(preferredReceivers.pop());
//        System.out.println("Stack is empty? " + preferredReceivers.isEmpty());
    }

    int getNextPreferenceId() {
        if (preferredReceivers.isEmpty()) {
            return -1;
        }
        else {
            return preferredReceivers.pop();
        }
    }

    void setNewMatch(Receiver newMatch) {
        currentMatch = newMatch;
        hasMatch = true;
    }

}
