import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        try {
//            String tempPath = System.getProperty("user.dir") + "/src/sm-friends-in.txt";
            String tempPath = "/Users/joakim/Development/itu/algorithm-design/algdes-labs/matching/data/sm-bbt-in.txt";
            File file = new File(tempPath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            HashMap<Integer, Proposer> proposers = new HashMap<>();
            HashMap<Integer, Receiver> receivers = new HashMap<>();

            int totalPairs = -1;

            int index = -1;

            // PARSING THE INPUT:
            while ((st = br.readLine()) != null) {
                if (st.startsWith("#")) {
                    continue;
                }
                if (totalPairs == -1 && st.startsWith("n=")) {
                    totalPairs = Integer.parseInt(st.split("=")[1]);
                    continue;
                }
                if (st.isEmpty()) {
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

            HashMap<Proposer, Receiver> result = galeShapley(proposers.values(), receivers);

            for (Proposer p : proposers.values()) {
                Receiver r = result.get(p);
                System.out.println("" + p.getName() + " -- " + r.getName());
            }

        } catch(Exception ex) {
            System.out.println(ex);
        }
    }

    private static HashMap<Proposer, Receiver> galeShapley(Collection<Proposer> proposers, HashMap<Integer, Receiver> receivers) {

        LinkedList<Proposer> proposersList = new LinkedList<>(proposers);
        HashMap<Proposer, Receiver> matches = new HashMap<>();

        while (!proposersList.isEmpty()) {
            for (Proposer p : proposersList) {
                int receiverId = p.getNextPreferenceId();
                if (receiverId == -1) {
                    matches.put(p, null);
                    proposersList.remove(p);
                    continue;
                }
                Receiver r = receivers.get(receiverId);
                if (r.isFree()) {
                    p.setNewMatch(r);
                    r.setNewMatch(p);
                    matches.put(p, r);
                    proposersList.remove(p);
                    break;
                } else {
                    if (r.tryMatch(p.getId())) {
                        matches.remove(r.getCurrentMatch());
                        proposersList.add(r.getCurrentMatch());
                        p.setNewMatch(r);
                        r.setNewMatch(p);
                        matches.put(p, r);
                        proposersList.remove(p);
                        break;
                    }
                }
            }
        }
        return matches;
    }
}
