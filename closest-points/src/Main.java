import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Point2D.Double> points = doParse(args[0]);
		int numPoints = points.size();
		double minDistance = findClosestPairQuadratic(points);
		prettyPrint(args[0], numPoints, minDistance);
	}

	public static void prettyPrint(String filename, int numPoints, double minDistance) {
		System.out.println(filename.replace(".txt", "").replace("-tsp", ".tsp") + ": " + numPoints + " " + minDistance);
	}

	public static double findClosestPairQuadratic(List<Point2D.Double> points) {
		double minDistance = Double.MAX_VALUE;
		// Eww, quadratic
		for(Point2D.Double pt : points) {
			for(Point2D.Double p : points) {
				// Yay object comparison!
				if(pt.equals(p)) {
					continue;
				}
				double distance = pt.distance(p);
				if(distance < minDistance) {
					minDistance = distance;
				}
			}
		}
		return minDistance;
	}

	public static List<Point2D.Double> doParse(String filename) {
		List<Point2D.Double> points = new ArrayList<>();
		try {
			String tempPath = filename;
			//String tempPath = "data/a280-tsp.txt";
			File file = new File(tempPath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			String st;

			boolean isTsp = file.getName().contains("-tsp.txt");
			boolean foundNodeCoord = !isTsp;
			// PARSING THE INPUT:
			while ((st = br.readLine()) != null) {
				if (st.isEmpty() || st.equals(" ")) {
					continue;
				}
				if(st.contains("EOF")) {
					break;
				}
				if(!foundNodeCoord && !st.equals("NODE_COORD_SECTION")) {
					continue;
				} else if(!foundNodeCoord) {
					foundNodeCoord = true;
					continue;
				}
				// Remove trailing/leading spaces, split on 1 or more spaces
				String[] lineParts = st.trim().split("\\s+");
				Point2D.Double pt = new Point2D.Double(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]));
				points.add(pt);
			}
			br.close();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return points;
	}
}
