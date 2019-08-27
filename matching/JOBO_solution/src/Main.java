import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        try {
            String tempPath = System.getProperty("user.dir") + "/src/sm-friends-in.txt";
            File file = new File(tempPath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
//            int lineCount = 0;

            HashMap<Integer, Proposer> proposers = new HashMap<>();
            HashSet<Proposer> freeProposers;
            HashMap<Integer, Receiver> receivers = new HashMap<>();
//            ArrayList<MatchingObject> matchingObjects = new ArrayList<>();

            int totalPairs = -1;

            int index = -1;

            // PARSING THE INPUT:
            while ((st = br.readLine()) != null) {
//                lineCount++;
//                System.out.println("line: " + lineCount);
                if (st.startsWith("#")) {
                    continue;
                }
                if (totalPairs == -1 && st.startsWith("n=")) {
                    totalPairs = Integer.parseInt(st.split("=")[1]);
                    // System.out.println("n = " + totalPairs);
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
//                    if (index % 2 == 1) {
//                        matchingObjects.add(new Proposer(index, name));
//                    }
//                    else {
//                        matchingObjects.add(new Receiver(index, name));
//                    }
                } else {
                    index = Integer.parseInt(lineParts[0].split(":")[0]);
                    int[] preferenceIds = new int[lineParts.length - 1];
                    for (int i = 1; i < lineParts.length; i++) {
                        preferenceIds[i - 1] = Integer.parseInt(lineParts[i]);
                    }
//                    matchingObjects.get(index - 1).addPreference(preferenceIds);
                    if (index % 2 == 1) {
                        proposers.get(index).addPreference(preferenceIds);
                    } else {
                        receivers.get(index).addPreference(preferenceIds);
                    }
                }
            }

            freeProposers = new HashSet<>(proposers.values());

          while (!freeProposers.isEmpty()) { // && !noPreferences) {
              Iterator it = freeProposers.iterator();
              while (it.hasNext()) {
                  // Get Proposer object, find next preferred Receiver id, retrieve Receiver object, try matching
                  Proposer currentProposer = (Proposer) it.next();
                  int preferenceId = currentProposer.getNextPreferenceId();
                  Receiver currentReceiver = receivers.get(preferenceId);
                  if (!currentReceiver.hasMatch()) {
                      // TODO implement tryMatch functionality and next steps (see pseudo code in book)
                      (currentProposer, currentReceiver);
                  }
              }
          }





        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
}
