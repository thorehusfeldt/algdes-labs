class Edge implements Comparable<Edge> {
	private Node from;
	private Node to;

	public Edge(Node from, Node to){
		this.from = from;
		this.to = to;
	}

	@Override
	public int compareTo(Edge that) {
		return this.getFrom().getName().compareTo(that.getFrom().getName());
	}


	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	@Override
	public String toString() {
		return "(" + this.from.getName() + " -> " + this.to.getName() + ")";
	}

}