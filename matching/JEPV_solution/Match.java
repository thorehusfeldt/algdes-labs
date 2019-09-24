package stable_matching;

public class Match {

    public Proposer proposer;
    public Receiver receiver;

    public Match(Proposer proposer, Receiver receiver) {
        this.proposer = proposer;
        this.receiver = receiver;
    }

}
