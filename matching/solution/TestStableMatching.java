import java.collections.*;

class TestStableMatching {

    public static void main(String[] args) {
        boolean commentLine = true;
        Integer n;
        List<String> names = new ArrayList<>();
        List<List<Integer>> preferences = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(args[1]))) {
            String line;
            while ((line = br.readLine()).startsWith("#")) {
                // we have skipped comments
            }

            line = br.readLine();
            n = line.substring(2);

            //read the names
            for (int i = 0; i < 2 * n; i++) {
                line = br.readLine();
                names.push(line.substring(line.indexOf(" ")));
                System.out.println(names.get(i));
            }

            //empty line
            line = br.readLine();

            //read the preferences
            for (int i = 0; i < 2 * n; i++) {
                line = br.readLine();
                List preference = Arrays.asList(line.substring(line.indexOf(":")).split(" "));

                preferences.push(preference);

                System.out.print(i);
                System.out.print(" ");
                System.out.println(preference);
            }
        }
    }

}