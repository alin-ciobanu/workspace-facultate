package topologicalSorting;

import java.util.ArrayList;
import java.util.Random;

import graph.Graph;
import graph.Node;
import graph.Node.Type;

public class Kahn {

	private Graph graph;
	private Random random;
	public Kahn(Graph graph) {
		this.graph = graph;
		random = new Random();
	}

	/*
	TODO:
	Folositi algorintmul lui kahn ca sa scoateti o sortare topologica
		Hint: puteti folosii:
			- Graph.getAllRootNodes
			- Node.getOutNodes
			- Node.removeNeighbour
	 */
	public ArrayList<Node> setOrdering() {
		ArrayList<Node> order = new ArrayList<Node>();

		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes = graph.getAllNodes();
		
		ArrayList<Node> noInNodes = new ArrayList<Node>();
		
		for (int i = 0; i < nodes.size(); i++) {
			Node currentNode = nodes.get(i);
			if (currentNode.getInNodes().size() == 0) {
				noInNodes.add(currentNode);
			}
		}
		
		while (noInNodes.size() > 0) {
			Random r = new Random();
			Node u = noInNodes.get(r.nextInt(noInNodes.size()));
			order.add(u);
			ArrayList<Node> uSuccs = new ArrayList<Node>();
			for (int i = 0; i < uSuccs.size(); i++) {
				u.removeNeighbour(uSuccs.get(i), Node.Type.IN);
				if (uSuccs.get(i).getInNodes().size() == 0)
					noInNodes.add(uSuccs.get(i));
			}
		}
		
		return order;
	}

	

}
