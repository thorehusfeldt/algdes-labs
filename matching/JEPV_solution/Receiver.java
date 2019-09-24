package stable_matching;

public class Receiver extends MatchObject {

    public Receiver(int id, String name) {
        super(id, name);
    }


    public boolean prefersMatchedProposer(Proposer matchedProposer, Proposer newProposer) {
        return preferenceIDStack.indexOf(matchedProposer.id) <= preferenceIDStack.indexOf(newProposer.id);
    }


}
