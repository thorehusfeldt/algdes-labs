import java.util.ArrayList;

public class Receiver {
    Integer id;
    String name;
    Proposer currentMatch;
    ArrayList<Receiver> preferences;

    public Receiver(Integer id, String name, Integer numPairs) {
        this.id  = id;
        this.name = name;
        this.currentMatch = null; // No match when starting
        preferences = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public Proposer getCurrentMatch() {
        return currentMatch;
    }

    public void setCurrentMatch(Proposer object) {
        currentMatch = object;
    }

    public void addPreference(Receiver object) {
        preferences.add(object);
    }

    public ArrayList<Receiver> getPreferences() {
        return preferences;
    }

    public void tryMatch(Proposer object) {
        if(currentMatch == null) {
            // Update current match if no current match
            setCurrentMatch(object);
        }
        else {
            // Find current match in preferences
            Integer currentMatchIndex = preferences.indexOf(currentMatch);
            // Find input object in preferences
            Integer inputMatchIndex = preferences.indexOf(object);
            // Compare the two indexes in our preference list
            if(currentMatchIndex > inputMatchIndex) {
                // Update current match if preferential
                setCurrentMatch(object);
            }
        }

    }

    @Override
    public String toString() {
        return "id: " + this.id + " name: " + this.name;
    }
}
