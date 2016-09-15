import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;

public class ClosestPoints{
    private Point[] points;
    private static Comparator<Point> xComparator = new Comparator<Point>(){
		public int compare(Point p1, Point p2){
		    Double x1 = p1.getX(),
				   x2 = p2.getX();
		    return x1.compareTo(x2); 
		}
    };
    private static Comparator<Point> yComparator = new Comparator<Point>(){
		public int compare(Point p1, Point p2){
		    Double y1 = p1.getY(),
				   y2 = p2.getY();
				return y1.compareTo(y2); 
		    }
    };

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		while(!input.hasNextInt()){
			System.out.println(input.nextLine());
		}
		ArrayList<Point> list = new ArrayList<>();
		while(input.hasNextDouble()){
			Point p = 
				new Point(input.nextInt(),input.nextDouble(), input.nextDouble());
			list.add(p);
		}
		int count = 0;
		Point[] points = new Point[list.size()];
		for(Point p : list)
		    points[count++] = p;
		ClosestPoints cp = 
				new ClosestPoints(points);
		cp.closestPair();
		
	}

    public ClosestPoints(Point[] points){
			this.points = points;
	}

    private Point[] sortByX(){
		Point[] px = this.points;
		Arrays.sort(px, xComparator);
		return px;
	}
    private Point[] sortByY(){
		Point[] py = this.points;
		Arrays.sort(py, yComparator);
		return py;
	}
    public Point[] closestPair(){
				
	    return null;
	}
		
}
class Point{
    final double xCoordinate;
	final double yCoordinate;
	final int id;

	Point(int id, double x, double y ){
		xCoordinate = x;
		yCoordinate = y;
		this.id = id;
	}
	double d(Point p){
		if(p == this)
		    return Double.MAX_VALUE;
		return Math.sqrt(Math.pow((this.getX() - p.getX()), 2) + Math.pow((this.getY() - p.getY()), 2));
	}
	public double getX(){
		return this.xCoordinate;
	}
	public double getY(){
		return this.yCoordinate;
	}
	public String toString(){
			String str =   "id: "+this.id + " x: " + this.getX() + " y: " + this.getY();
			return str;
	}		
}
