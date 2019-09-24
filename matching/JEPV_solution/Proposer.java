package stable_matching;

public class Proposer extends MatchObject {

    public Proposer(int id, String name) {
        super(id, name);
    }

    public boolean hasProposedToEveryReceiver() {
        return preferenceIDStack.isEmpty();
    }

    public int getPreferredReceiverID() {
        return preferenceIDStack.pop();
    }
}
