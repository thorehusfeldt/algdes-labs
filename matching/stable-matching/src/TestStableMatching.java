import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

class TestStableMatching {

    private static Integer n;

    private static ArrayList<String> men = new ArrayList<>();
    private static ArrayList<String> women = new ArrayList<>();

    private static List<List<Integer>> menPref = new ArrayList<>();
    private static List<List<Integer>> womenPref = new ArrayList<>();

    private static Integer[][] ranking;

    public static void main(String[] args) {
        //fileReader("../data/sm-bbt-in.txt");
        //fileReader("../data/sm-friends-in.txt");
        fileReader("../data/sm-illiad-in.txt");
        //fileReader("../data/sm-kt-p-4-in.txt");
        //fileReader("../data/sm-kt-p-5-in.txt");
        //fileReader("../data/sm-random-5-in.txt");
        //fileReader("../data/sm-random-50-in.txt");
        //fileReader("../data/sm-random-500-in.txt");
        //fileReader("../data/sm-worst-5-in.txt");
        //fileReader("../data/sm-worst-50-in.txt");
        //fileReader("../data/sm-worst-500-in.txt");

        StableMatching matcher = new StableMatching(n, menPref, ranking);

        Integer[] engagements = matcher.galeShapley();

        System.out.println("List of pairs");
        for (int i = 0; i < n; i++) {
            System.out.println(men.get(engagements[i]) + " -- " + women.get(i));
        }
    }

    private static void fileReader(String file){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()).startsWith("#")) {
                // we have skipped comments
            }

            n = (Integer) Integer.parseInt(line.substring(2));

            //read the names
            for (int i = 0; i < 2 * n; i++) {
                line = br.readLine();
                String name = line.substring(line.indexOf(" "));
                if (i % 2 == 0) {
                    men.add(name);
                } else {
                    women.add(name);
                }
            }

            System.out.println("List of Men");
            for (int i = 0; i < n; i++) {
                System.out.print(i);
                System.out.print(" ");
                System.out.println(men.get(i));
            }
            System.out.println();

            System.out.println("List of Women");
            for (int i = 0; i < n; i++) {
                System.out.print(i);
                System.out.print(" ");
                System.out.println(women.get(i));
            }
            System.out.println();

            //empty line
            line = br.readLine();

            //read the preferences
            for (int i = 0; i < 2 * n; i++) {
                line = br.readLine();
                List<Integer> preference = Arrays.stream(line.substring(line.indexOf(":") + 2).split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                // Split preferences into men, women and update their refrences to names.
                ArrayList<Integer> tmpPref = new ArrayList<>();
                if ( i % 2 == 0){
                    for (Integer pref : preference) {
                        tmpPref.add(pref/2 - 1);
                    }
                    menPref.add(tmpPref);
                } else {
                    for (Integer pref : preference) {
                        tmpPref.add((pref - 1) / 2);
                    }
                    womenPref.add(tmpPref);
                }
            }

            System.out.println("List of Men Preferences");
            for (int i = 0; i < n; i++) {
                System.out.print(i);
                System.out.print(" ");
                System.out.println(menPref.get(i));
            }
            System.out.println();

            System.out.println("List of Women Preferences");
            for (int i = 0; i < n; i++) {
                System.out.print(i);
                System.out.print(" ");
                System.out.println(womenPref.get(i));
            }
            System.out.println();

            // Creates constant lookup time for women ranking of men.
            ranking = new Integer[n][n];
            for (int w = 0; w < n; w++) {
                List<Integer> wPrefs = womenPref.get(w);
                for (Integer rank = 0; rank < n; rank++) {
                    Integer m = wPrefs.get(rank);
                    ranking[w][m] = rank;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class StableMatching {
    //key = women, value = men // legacy code
    Map<Integer, Integer> M;
    Integer n;
    List<String> names;
    List<List<Integer>> preferences;

    // thtj implementation
    ArrayList<Integer> bachelors;
    Stack<Integer> stackBachelors;
    List<List<Integer>> menPref;
    Integer[] crush;
    Integer[] current;
    Integer[][] ranking;

    public StableMatching(Integer n, List<String> names, List<List<Integer>> preferences) {
        this.n = n;
        this.names = names;
        this.preferences = preferences;
    }

    public StableMatching(Integer n,
                          List<List<Integer>> menPref,
                          Integer[][] ranking) {
        this.n = n;
        this.bachelors = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            this.bachelors.add(i);
        }
        this.stackBachelors = new Stack<>();
        for (int i = n-1; i >= 0; i--) {
            stackBachelors.push(i);
        }
        this.menPref = menPref;
        this.crush = new Integer[n];
        Arrays.fill(crush, 0);
        this.current = new Integer[n];
        Arrays.fill(current, Integer.MIN_VALUE);
        this.ranking = ranking;
    }

    public Integer[] galeShapley() {
        //return galeShapleyQueue();
        return galeShapleyStack();
    }

    public Integer[] galeShapleyStack() {
        while (!stackBachelors.isEmpty()) {
            Integer m = stackBachelors.pop();
            Integer w = menPref.get(m).get(crush[m]);
            crush[m]++;
            if (current[w] < 0) {
                current[w] = m;
            } else {
                Integer currentMan = current[w];
                if (ranking[w][currentMan] < ranking[w][m]) {
                    stackBachelors.push(m);
                } else {
                    current[w] = m;
                    stackBachelors.push(currentMan);
                }
            }
        }
        return current;
    }

    public Integer[] galeShapleyQueue() {
        while (!bachelors.isEmpty()) {
            Integer m = bachelors.get(0);
            bachelors.remove(0);
            Integer w = menPref.get(m).get(crush[m]);
            crush[m]++;
            if (current[w] < 0) {
                current[w] = m;
            } else {
                Integer currentMan = current[w];
                if (ranking[w][currentMan] < ranking[w][m]) {
                    bachelors.add(m);
                } else {
                    current[w] = m;
                    bachelors.add(currentMan);
                }
            }
        }
        return current;
    }
}