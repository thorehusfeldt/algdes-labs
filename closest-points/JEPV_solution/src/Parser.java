import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Parser {


    public static List<DoublePoint> parse(String filename) {

        System.out.println("Parsing file " + filename);

        List<DoublePoint> result = new LinkedList<>();

        File file = new File(filename);
        BufferedReader br = null;
        String input;

        try {
            br = new BufferedReader(new FileReader(file));

            if(filename.contains("-tsp.txt")) {
                do {
                    input = br.readLine();
                } while(!input.contains("NODE_COORD_SECTION"));
            }

            String[] pointInfo;
            while((input = br.readLine()) != null && !input.equals("EOF")) {
                pointInfo = input.trim().split("\\s+");
                result.add(new DoublePoint(pointInfo[0], Double.parseDouble(pointInfo[1]), Double.parseDouble(pointInfo[2])));
            }

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(br == null) return null;
        }

        return result;

    }




}



