package topologicalSorting;

import java.util.ArrayList;
import java.util.Collections;

import graph.Graph;
import graph.Node;

public class DFS {

	private Graph graph;
	private int time = 0;
	public DFS(Graph graph) {
		this.graph = graph;
	}

	/*
	TODO: 
	Luati toate nodurile care nu au intrari ( root nodes ),
		pentru fiecare: parcurgeti subarborele asociat folosind DFS
		si adaugati in order in ordine inversa terminarii
	Hint: puteti folosii:
		- Graph.getAllRootNodes 
		- Node.getOutNodes
		- Node.getInitTime
		- Node.setInitTime
	 */
	public ArrayList<Node> setOrdering() {
		
		ArrayList<Node> order = new ArrayList<Node>();
		
		
		
		return order;
	}

	
}
