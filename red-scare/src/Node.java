import java.util.ArrayList;
import java.util.List;

class Node {
	private int id;
	private String name;
	private boolean isRed;
	private List<Edge> edges = new ArrayList<>();

	public Node(int id, String name, boolean isRed){
		this.id = id;
		this.name = name;
		this.isRed = isRed;
	}

	public Node(String name) {
		this.name = name;
		this.isRed = false;
	}

	public int getId() {
		return id;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void addEdge(Edge e) {
		this.edges.add(e);
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Node)) {
			return false;
		}
		Node node = (Node) o;
		return node.getName().equals(this.getName());
	}

	@Override
	public String toString(){
		return "{" + this.name + "}";
	}
}