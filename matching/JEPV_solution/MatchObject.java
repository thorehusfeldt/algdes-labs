package stable_matching;

import java.util.LinkedList;
import java.util.Stack;

public abstract class MatchObject {

    public int id;
    public String name;
    public LinkedList<Integer> preferenceIDStack;

    public MatchObject(int id, String name) {
        this.id = id;
        this.name = name;
        this.preferenceIDStack = new LinkedList<>();
    }

    public void addPreferenceID(int preferenceID) {
        preferenceIDStack.push(preferenceID);
    }

}
