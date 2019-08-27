import java.util.ArrayList;

public class Proposer {
    Integer id;
    String name;
    Receiver currentMatch;
    ArrayList<Receiver> preferences;

    public Proposer(Integer id, String name, Integer numPairs) {
        this.id  = id;
        this.name = name;
        preferences = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void addPreference(Receiver object) {
        preferences.add(object);
    }

    public ArrayList<Receiver> getPreferences() {
        return preferences;
    }

    public Receiver getCurrentMatch() {
        return currentMatch;
    }

    public boolean canPropose() {
        return preferences.size() > 0;
    }

    public boolean doPropose() {
        if(!canPropose()) {
            return false;
        }
        Receiver receiver = this.preferences.get(0);
        // Only propose once
        this.preferences.remove(receiver);
        boolean success = receiver.tryMatch(this);
        if(success) {
            currentMatch = receiver;
        }
        return success;
    }

    @Override
    public String toString() {
        return "{Proposer: " + this.name + "}";
    }
}
