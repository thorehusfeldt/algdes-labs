import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;

public class ClosestPoints{
    private Point[] points;
    private static Comparator<ClosestPoints.Point> xComparator = new Comparator<ClosestPoints.Point>(){
		public int compare(ClosestPoints.Point p1, ClosestPoints.Point p2){
		    Double x1 = p1.getX(),
				   x2 = p2.getX();
		    return x1.compareTo(x2); 
		}
    };
    private static Comparator<ClosestPoints.Point> yComparator = new Comparator<ClosestPoints.Point>(){
		public int compare(ClosestPoints.Point p1, ClosestPoints.Point p2){
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
		ClosestPoints cp = 
				new ClosestPoints((ClosestPoints.Point[]) list.toArray());
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
		
	private static class Point{
		final double xCoordinate;
		final double yCoordinate;
		final int id;

		Point(int id, double x, double y ){
			xCoordinate = x;
			yCoordinate = y;
			this.id = id;
		}
		double d(Point p){
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
}
