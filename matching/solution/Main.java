import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String line = "", error = "";
        Integer n = null;
        List<Proposer> proposerList = new ArrayList<>();
        List<Receiver> receiverList = new ArrayList<>();

        try (BufferedReader stdoutReader = new BufferedReader(new FileReader("/Users/freak/Desktop/ITU/FIRST/src/sm-random-50-in.txt"))) {
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
                    // First N (half of the people) are proposers
                    if(objectIndex <= n) {
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
                Receiver referenceHolderObject = receiverList.get(id - 1);
                String[] preferences = lineParts[1].split(" ");
                for(String s : preferences) {
                    Integer foundId = Integer.parseInt(s);
                    Receiver foundObject = receiverList.get(foundId - 1);
                    referenceHolderObject.addPreference(foundObject);
                }
            }

            // Do matching
            while ()

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Receiver> doGS(List<Receiver> inputList) {

        return inputList;
    }
}


