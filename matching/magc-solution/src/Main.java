import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String line = "", error = "";
        Integer n = null;
        List<Proposer> proposerList = new ArrayList<>();
        List<Receiver> receiverList = new ArrayList<>();

        try (BufferedReader stdoutReader = new BufferedReader(new FileReader(System.getProperty(("user.dir")) + "/src/sm-random-50-in.txt"))) {
            // Parsing into matchObject
            while ((line = stdoutReader.readLine()) != null) {
                if(line.equals("")) {
                    break;
                }
                if(line.startsWith("#")) {
                    continue;
                }
                if(n == null) {
                    n = Integer.parseInt(line.split("=")[1]);
                    continue;
                }
                // Do actual parsing
                else {
                    String[] lineParts = line.split(" ");
                    Integer objectIndex = Integer.parseInt(lineParts[0]);
                    // Odd indices (half of the people) are proposers
                    if(objectIndex % 2 == 1) {
                        Proposer object = new Proposer(objectIndex, lineParts[1], n);
                        proposerList.add(object);

                    } else {
                        Receiver object = new Receiver(objectIndex, lineParts[1], n);
                        receiverList.add(object);
                    }
                }
            }
            // Parse preferences
            while ((line = stdoutReader.readLine()) != null) {
                String[] lineParts = line.split(": ");
                Integer id = Integer.parseInt(lineParts[0]);
                // Odd for proposer
                if(id % 2 == 1) {
                    Proposer referenceHolderObject = proposerList.stream().filter(proposer -> proposer.getId().equals(id)).findFirst().orElse(null);
                    String[] preferences = lineParts[1].split(" ");
                    for(String s : preferences) {
                        Integer foundId = Integer.parseInt(s);
                        Receiver foundObject = receiverList.stream().filter(rec -> rec.getId().equals(foundId)).findFirst().orElse(null);
                        if(foundObject != null) {
                            referenceHolderObject.addPreference(foundObject);
                        }
                    }
                } else {
                    // Even for receivers
                    Receiver referenceHolderObject = receiverList.stream().filter(receiver -> receiver.getId().equals(id)).findFirst().orElse(null);
                    String[] preferences = lineParts[1].split(" ");
                    for(String s : preferences) {
                        Integer foundId = Integer.parseInt(s);
                        Proposer foundObject = proposerList.stream().filter(rec -> rec.getId().equals(foundId)).findFirst().orElse(null);
                        if(foundObject != null) {
                            referenceHolderObject.addPreference(foundObject);
                        }
                    }
                }
            }
            // Do matching
            List<Proposer> availableProposers = proposerList;
            HashMap<Receiver, Proposer> matchedPairs = new HashMap<>();
            while(availableProposers.size() > 0) {
                Proposer currentProposer = availableProposers.get(0);
                if(currentProposer.canPropose()) {
                    List<Receiver> currentReceivers = currentProposer.getPreferences();
                    for(Receiver r : currentReceivers) {
                        boolean success = r.tryMatch(currentProposer);
                        if(success) {
                            // Check if the receiver was already in a pair
                            if(matchedPairs.containsKey(r)) {
                                Proposer previousProposer = matchedPairs.get(r);
                                // Remove whatever pair the new receiver was in before
                                matchedPairs.remove(r);
                                if(!availableProposers.contains(previousProposer)) {
                                    // If we previously removed the currentProposer, re-add it
                                    availableProposers.add(previousProposer);
                                }
                            }
                            // Add the new pair to matchings
                            matchedPairs.put(r, currentProposer);
                            // Remove the current proposer from list of available proposers
                            availableProposers.remove(currentProposer);
                            break; // Stop when receiver is found
                        }
                    }
                } else {

                }
            }
            matchedPairs.keySet().stream().sorted((a, b) -> a.getId() - b.getId()).forEach(receiverKey -> {
                System.out.println(receiverKey + " -- " + matchedPairs.get(receiverKey));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Receiver> doGS(List<Receiver> inputList) {
        return inputList;
    }
}


