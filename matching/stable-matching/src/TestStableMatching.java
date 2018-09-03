import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class TestStableMatching {

    public static void main(String[] args) {
        boolean commentLine = true;
        Integer n;
        List<String> names = new ArrayList<>();
        List<List<Integer>> preferences = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()).startsWith("#")) {
                // we have skipped comments
            }

            n = Integer.parseInt(line.substring(2));

            //read the names
            for (int i = 0; i < 2 * n; i++) {
                line = br.readLine();
                names.add(line.substring(line.indexOf(" ")));
                System.out.println(names.get(i));
            }

            //empty line
            line = br.readLine();

            //read the preferences
            for (int i = 0; i < 2 * n; i++) {
                line = br.readLine();
                List<Integer> preference = Arrays.stream(line.substring(line.indexOf(":") + 2).split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                preferences.add(preference);

                System.out.print(i + 1);
                System.out.print(" ");
                System.out.println(preference);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class StableMatching {
    //key = women, value = men
    Map<Integer, Integer> M;
    Integer n;
    List<String> names;
    List<List<Integer>> preferences;

    public StableMatching(Integer n, List<String> names, List<List<Integer>> preferences) {
        this.n = n;
        this.names = names;
        this.preferences = preferences;
    }

    public Map<Integer, Integer> gs() {
        while (M.size() < n) {
            for (int i = 0; i < 2 * n; i += 2) {
                int s = preferences.get(i).get(0);
                preferences.get(i).remove(0);

                if (!M.containsValue(s)) {
                    M.put(s, i);
                } else if (/*s prefers h to current partner hʹ*/) {
                    //...
                }
            }
        }
    }


}