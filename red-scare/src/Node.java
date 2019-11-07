import java.util.List;

public class Node {
	int id;
	boolean isRed;
	List<Edge> edges; // Edges going out of Node

	public Node(int id, boolean isRed, List<Edge> edges) {
		this.id = id;
		this.isRed = isRed;
		this.edges = edges;
	}
}