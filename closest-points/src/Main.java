import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		List<Point2D.Double> points = doParse(args[0]);

	}

	public static double findClosestPair(List<Point2D.Double> points) {
		double distance = -1.0;
		// Do work
		return distance;
	}

	public static List<Point2D.Double> doParse(String filename) {
		List<Point2D.Double> points = new ArrayList<>();
		try {
			// String tempPath = args[0];
			String tempPath = "data/a280-tsp.txt";
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
				// Split on 1 or more spaces
				String[] lineParts = st.split("\\s+");
				Point2D.Double pt = new Point2D.Double(Double.parseDouble(lineParts[1]), Double.parseDouble(lineParts[2]));
				points.add(pt);
			}
			br.close();
			System.out.println(points);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return points;
	}
}
/*
class Point {
	String id;
	Double x;
	Double y;
	public Point(String id, Double x, Double y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public String getId() {
		return id;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public String toString() {
		return String.format("{id: id=%s, x: x=%.2f, y: y=%.2f}", this.id, this.x, this.y);
	}
}*/
