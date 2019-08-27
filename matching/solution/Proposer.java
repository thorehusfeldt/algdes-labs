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

    public boolean canPropose() {
        return preferences.size() > 0;
    }

    public void doPropose() {
        if(!canPropose()) {
            return;
        }
        Receiver receiver = this.preferences.get(0);
        receiver.tryMatch(this);
    }

    @Override
    public String toString() {
        return "id: " + this.id + " name: " + this.name;
    }
}
