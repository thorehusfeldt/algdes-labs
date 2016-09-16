import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;

public class ClosestPoints{
    private static Comparator<Point> xComparator = 
			(p1, p2) -> new Double(p1.getX()).compareTo(new Double(p2.getX()));
    private static Comparator<Point> yComparator = 
			(p1, p2) -> new Double(p1.getY()).compareTo(new Double(p2.getY()));
	private Scanner input;
	Point[] points, pointsX, pointsY;
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		ClosestPoints cp = new ClosestPoints(input);
		try{ cp.build();
		}catch(InterruptedException e){System.exit(42);}

	}
	public ClosestPoints(Scanner input){
		this.input = input;
	}
	public void build() throws InterruptedException{
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
		 points = new Point[list.size()];
		for(Point p : list)
		    points[count++] = p;
		sort();
	}
	private void  sort() throws InterruptedException {
		Thread t1 = new Thread(() -> sortByX());
		Thread t2 = new Thread(() -> sortByY());
		t1.start(); t2.start();
		t1.join();t2.join();
		closestPair();
	}

    private void sortByX(){
		int count = 0;
		pointsX = new Point[points.length];
		for(Point p: this.points)
			pointsX[count++] = p;
		Arrays.sort(pointsX, xComparator);
	}
    private void sortByY(){
		int count = 0;
		pointsY = new Point[points.length];
		for(Point p : this.points)
			pointsY[count++] = p;
		Arrays.sort(pointsY, yComparator);
	}
    private Point[] closestPair(){
		return	ClosestPairRec(pointsX, pointsY ,0, points.length);
	}
	private Point[] ClosestPairRec(Point[] px, Point[] py, int from, int to){
		//System.out.println("from: "+ from + ", to: " + to );
		if(to - from <= 3){
			return computeMinDistance(px, py);
		}
		final int from1= from, to1 = Math.round(Math.round(Math.ceil(from1 + to)/2)) ;
		final int from2 = to1, to2 = to;
		Point[][] result = constructQR(from, to);
		Point[] qX = result[0];
		Point[] qY = result[1];
		Point[] rX = result[2];
		Point[] rY = result[3];
		Point[] left = ClosestPairRec(qX, qY, from1, to1);
		Point[] right = ClosestPairRec(rX, rY, from2, to2);
		double d = Math.min(left[0].distanceTo(left[1]), right[0].distanceTo(right[1]));
		System.out.println(d);
		Point x = qX[qX.length - 1];
		Point[] S = constructS(d, x);
		Point[] Sy = S;
		Arrays.sort(Sy, yComparator);
		double min = Double.MAX_VALUE;
		Point[] center = new Point[2];
		int countj;
		System.out.println("length: "+ Sy.length);
		System.out.println(min);
		for(int i = 0; i < Sy.length; i++){
			countj =0;
			for(int j = i + 1; j < S.length; j++){
				if(countj++ >  16)
					break;
				System.out.println(Sy[i] +" "+Sy[j]);
				if(Sy[i].distanceTo(Sy[j]) < min){
					min = Sy[i].distanceTo(Sy[j]);
					System.out.print("Change min to: " +min); 
					center[0] = Sy[i];
					center[1] = Sy[j];
				}
			}
		}
		double ds;
		try{
			ds = center[0].distanceTo(center[1]);
		}catch(NullPointerException e){
			ds = d + 1000.0;
		}
		if(ds < d)
			return center;
		else if(left[0].distanceTo(left[1]) < right[0].distanceTo(right[1]))
			return left;
		else 
			return right;
	}
	private Point[] constructS(double d, Point x){
		ArrayList<Point> l = new ArrayList<>();
		for(Point p: pointsX){
			if(p.distanceTo(x) < d)
				l.add(p);
		}
		Point[] res = new Point[l.size()];
		int count = 0;
		for(Point p : l)
			res[count++] = p;

		return  res;
	}
	private static void print(Point[] parr){
		for( Point p : parr)
			System.out.println(p);
	}
	private Point[][] constructQR(int from, int to){
		final int from1= from, to1 = Math.round(Math.round(Math.ceil(from1 + to)/2)) ;
		final int from2 = to1, to2 = to;
		final Point[] qY = new Point[to1 - from1];
		final Point[] qX = new Point[to1 - from1];
		int count = 0;
		//Already Sort
		Thread t1 = new Thread(() ->{
			int count1 = from1;
			int  size1 = to1 - from1;
			for(int p = 0; p < size1; p++){
				qX[p] = pointsX[count1++];
				qY[p] = qX[p];
			}
			Arrays.sort(qY, yComparator);
		});t1.start();
		final Point[]rY = new Point[to2 - from2],
				rX = new Point[to2 - from2];
		Thread t2 = new Thread(() -> {
			int size2 = to2 - from2;
			int count2 = from2;
			for(int p = 0 ; p < size2; p++){
				rX[p] = pointsY[count2++];
				rY[p] = pointsX[p];
			}
			Arrays.sort(rY, yComparator);
		});t2.start();
		try{
			t1.join();t2.join();
		}catch(InterruptedException e) {
			input.close();
			throw new RuntimeException(e);
		}
		return new Point[][]{qX, qY, rX, rY}; 
	}
	private Point[] computeMinDistance(Point[] psX, Point[] psY){
		Point[] res = new Point[2];
		double min = Double.MAX_VALUE;
		for(Point px : psX){
			for(Point py : psY){
				if(px.distanceTo(py) < min){
					min = px.distanceTo(px);
					res[0] = px; res[1] = py;
				}
			}
		}
		return res;
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
		double distanceTo(Point p){
			if(p.equals(this))
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
		
}


