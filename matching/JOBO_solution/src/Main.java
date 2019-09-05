import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        try {
            String tempPath = args[0];
            File file = new File(tempPath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            HashMap<Integer, Proposer> proposers = new HashMap<>();
            HashMap<Integer, Receiver> receivers = new HashMap<>();

            int index = -1;

            // PARSING THE INPUT:
            while ((st = br.readLine()) != null) {
                if (st.startsWith("#") || st.startsWith("n=") || st.isEmpty()) {
                    continue;
                }
                String[] lineParts = st.split(" ");
                if (!lineParts[0].contains(":")) {
                    index = Integer.parseInt(lineParts[0]);
                    String name = lineParts[1];
                    if (index % 2 == 1) {
                        proposers.put(index, new Proposer(index, name));
                    } else {
                        receivers.put(index, new Receiver(index, name));
                    }
                } else {
                    index = Integer.parseInt(lineParts[0].split(":")[0]);
                    int[] preferenceIds = new int[lineParts.length - 1];
                    for (int i = 1; i < lineParts.length; i++) {
                        preferenceIds[i - 1] = Integer.parseInt(lineParts[i]);
                    }
                    if (index % 2 == 1) {
                        proposers.get(index).addPreference(preferenceIds);
                    } else {
                        receivers.get(index).addPreference(preferenceIds);
                    }
                }
            }

            HashMap<Receiver, Proposer> result = galeShapley(proposers.values(), receivers);
            for (Proposer p : proposers.values()) {
                Receiver r = getCurrentMatch(p, result);
                System.out.println("" + p.getName() + " -- " + r.getName());
            }

        } catch(Exception ex) {
        	ex.printStackTrace();
        }
    }

    private static HashMap<Receiver, Proposer> galeShapley(Collection<Proposer> proposers, HashMap<Integer, Receiver> receivers) {
        Stack<Proposer> availableProposers = new Stack();
        // Fill available proposers stack with every proposer at start
        for(Proposer p : proposers) {
            availableProposers.push(p);
        }
        HashMap<Receiver, Proposer> matches = new HashMap<>();
        Proposer p;
            while(!availableProposers.empty()) {
                p = availableProposers.pop();
                // Pop next preference off of preference stack
                int receiverId = p.getNextPreferenceId();
                if (receiverId == -1) {
                    // Current proposer has no preferences left, continue to next available proposer
                    // Current proposer will not be put back on the stack.
                    continue;
                }
                Receiver r = receivers.get(receiverId);
                // Does r already have a match?
                if (matches.get(r) == null) {
                    matches.put(r, p);
                } else {
                    Proposer currentlyMatchedProposer = matches.get(r);
                    // Does r prefer p to the currently matched proposer?
                    if (r.tryMatch(p.getId(), currentlyMatchedProposer.getId())) {
                        // Add the (now available) proposer back to the stack of available proposers
                        availableProposers.push(currentlyMatchedProposer);
                        // Add the match to list of matches - previous pair is overwritten
                        matches.put(r, p);
                    } else {
                        // Nothing happened, re-add proposer as available
                        availableProposers.push(p);
                    }
                }
            }
        return matches;
    }
    // Get the proposer p's matched receiver
    // Both receivers and proposers are unique, so doing a reverse (value -> key) lookup in a map is safe
    public static Receiver getCurrentMatch(Proposer p, HashMap<Receiver, Proposer> matches) {
        Receiver r = null;
        for (Map.Entry<Receiver, Proposer> entry : matches.entrySet()) {
            if(entry.getValue().getId() == p.getId()) {
                r = entry.getKey();
            }
        }
        return r;
    }
}
