import java.util.Scanner;
public class ClosestPoints{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		while(!input.hasNextInt()){
			System.out.println(input.nextLine());
		}
		while(input.hasNextDouble()){
			System.out.println(input.next());
		}
	}
	private class Point{
		final double xCoordinate;
		final double yCoordinate;

		Point(double x, double y){
			xCoordinate = x;
			yCoordinate = y;
		}
		double d(Point p){
			return Math.sqrt(Math.pow((this.getX() - p.getX()), 2) - Math.pow((this.getY() - p.getY()), 2));
		}
		public double getX(){
			return this.xCoordinate;
		}
		public double getY(){
			return this.yCoordinate;
		}
	}
}
