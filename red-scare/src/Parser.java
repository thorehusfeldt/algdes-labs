import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Parser {
	public static Graph doParse(String input_path){
		HashMap<String, Node> nodes = new HashMap<>();
		List<Edge> edges = new ArrayList<>();
		int numVertices = 0;
		int numEdges = 0;
		int cardinality = 0;
		Node start;
		Node end;
		boolean foundDirectedEdge = false;
		try {
			List<String> lines = Files.readAllLines(Paths.get(input_path),
					Charset.defaultCharset());

			String[] nums = lines.get(0).split("\\s+");
			numVertices = Integer.parseInt(nums[0]);
			numEdges = Integer.parseInt(nums[1]);
			cardinality = Integer.parseInt(nums[2]);

			String[] verticeNames = lines.get(1).split("\\s+");
			start = new Node(verticeNames[0]);
			end = new Node(verticeNames[1]);
			nodes.put(verticeNames[0], start);
			nodes.put(verticeNames[1], end);

			// Parse Vertices
			for (int i = 2; i < 2 + numVertices; i++) {
				String line = lines.get(i);
				String nodeName = line.replace("*", "").strip();
				nodes.put(nodeName, new Node(i, nodeName, line.contains("*")));
			}
			// Parse edges
			for(int i = numVertices + 3; i < 2 + numVertices + numEdges; i++) {
				// U -- V or U -> V
				String line = lines.get(i);
				String[] lineParts = line.split("\\s+");
				// U -- V
				boolean isDirected = line.contains(">");
				Node from = nodes.get(lineParts[0]);
				Node to = nodes.get(lineParts[2]);

				Edge e = new Edge(from, to);
				edges.add(e);
				from.addEdge(e);
				if(isDirected) {
					foundDirectedEdge = true;
				} else {
					// Add reverse edge if we are not directed
					Edge eRev = new Edge(to, from);
					edges.add(eRev);
					to.addEdge(eRev);
				}
			}
			return new Graph(nodes, edges, foundDirectedEdge, cardinality, start, end);

		} catch (IOException ex) {
			// fuck
			ex.printStackTrace();
		}
		return null;
	}
}
