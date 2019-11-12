
import java.util.HashMap;
import java.util.List;

public class Graph {
	Node start;
	Node end;
	HashMap<String, Node> nodes;
	List<Edge> edges;
	boolean isDirected;
	int numRedNodes;

	public Graph(HashMap<String, Node> n, List<Edge> e, boolean isDirected, int numRedNodes, Node start, Node end) {
		nodes = n;
		edges = e;
		this.isDirected = isDirected;
		this.numRedNodes = numRedNodes;
		this.start = start;
		this.end = end;
	}

	public HashMap<String, Node> getNodes() {
		return nodes;
	}

	public int getNumRedNodes() {
		return numRedNodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public Node getEnd() {
		return end;
	}

	public Node getStart() {
		return start;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(Edge e : edges) {
			builder.append(e.toString());
			builder.append("\n");
		}
		return builder.toString();
	}
}

enum GRAPH_PROPERTIES {
	CYCLICAL,
	ACYCLICAL,
	DIRECTED,
	UNDIRECTED,
	CONNECTED,
	UNCONNECTED,
	BIPARTITE
}
