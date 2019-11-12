
public class Main {
	public static void main(String[] args) {
		Graph g = Parser.doParse(System.getProperty("user.dir") + "/data/G-ex.txt");
		System.out.println(g);
	}
}