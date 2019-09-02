package stable_matching;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class StableMatching {

    public static void main(String[] args) {

        Pair<HashMap<Integer,Proposer>,HashMap<Integer,Receiver>> parseResult = parseInput("C:\\Users\\Jeppe\\GitHub\\AlgorithmDesign\\src\\stable_matching\\test_input\\sm-random-5-in.txt");

        HashMap<Integer,Match> matchMap = new HashMap<>();

        HashMap<Integer,Proposer> proposerMap = parseResult.getKey();
        HashMap<Integer,Receiver> receiverMap = parseResult.getValue();

        Stack<Proposer> freeProposerStack = new Stack<>();

        for(Proposer freeProposer : proposerMap.values()) { //at first all proposers are assumed to be free
            freeProposerStack.push(freeProposer);
        }

        Proposer currentProposer, matchedProposer;
        Receiver currentReceiver;
        int currentReceiverID;

        while(!freeProposerStack.empty() && !(currentProposer = freeProposerStack.pop()).hasProposedToEveryReceiver()) {

            currentReceiver = receiverMap.get(currentProposer.getPreferredReceiverID());
            currentReceiverID = currentReceiver.id;
            Match match = matchMap.get(currentReceiverID);

            if(match == null) { //if match does not exist, then currentReceiver is free
                matchMap.put(currentReceiverID, new Match(currentProposer, currentReceiver));
            } else { //else match exists and currentReceiver is not free
                matchedProposer = match.proposer;

                if(currentReceiver.prefersMatchedProposer(matchedProposer, currentProposer)) { // currentReceiver prefers current match
                    freeProposerStack.push(currentProposer); // currentProposer becomes free again
                } else { // currentReceiver prefers the new proposer
                    match.proposer = currentProposer;
                    freeProposerStack.push(matchedProposer); // previously matchedProposer becomes free
                }
            }
        }

        for(Match match : matchMap.values()) {
            System.out.println(match.proposer.name + " -- " + match.receiver.name);
        }

    }




    private static Pair<HashMap<Integer,Proposer>,HashMap<Integer,Receiver>> parseInput(String inputFilename) {

        HashMap<Integer,Proposer> proposerMap = new HashMap<>();
        HashMap<Integer,Receiver> receiverMap = new HashMap<>();

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFilename));
            String inputLine;

            do {
                inputLine = bufferedReader.readLine();
            } while (inputLine.substring(0, 1).equals("#"));

            int n = Integer.parseInt(inputLine.split("=")[1]);


            int id;
            String name;
            String[] tokens;

            for(int i = 0; i < n*2; i++) {
                inputLine = bufferedReader.readLine();
                tokens = inputLine.split(" ");
                id = Integer.parseInt(tokens[0]);
                name = tokens[1];

                if(i % 2 == 0) {
                    proposerMap.put(id, new Proposer(id, name));
                } else {
                    receiverMap.put(id, new Receiver(id, name));
                }
            }

            bufferedReader.readLine(); //empty line

            String[] preferenceList;


            MatchObject currentMatchObject;

            for(int i = 0; i < n*2; i++) {

                inputLine = bufferedReader.readLine();
                tokens = inputLine.split(": ");
                id = Integer.parseInt(tokens[0]);
                preferenceList = tokens[1].split(" ");

                if(i % 2 == 0) {
                    currentMatchObject = proposerMap.get(id);
                } else {
                    currentMatchObject = receiverMap.get(id);
                }

                for(int j = preferenceList.length - 1; j >= 0; j--) {
                    currentMatchObject.addPreferenceID(Integer.parseInt(preferenceList[j]));
                }

            }




        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(proposerMap.toString());

        return new Pair<>(proposerMap, receiverMap);
    }

}
