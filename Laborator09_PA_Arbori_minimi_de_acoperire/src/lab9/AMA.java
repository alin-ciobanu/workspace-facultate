/**
 * Proiectarea Algoritmilor, 2013
 * Lab 9: Arbori minimi de acoperire
 *
 * @author 	Alexandru Tudorica
 * @email	tudalex@gmail.com
 */

package lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Scanner;

import graph.Graph;
import graph.Node;
import graph.Pair;

public class AMA {

	final static int MAX_VALUE = 9999;

	/*
	 * Prim
	 * */
	public static ArrayList< Pair< Integer,Integer> > prim(Graph g) {

		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Integer> cost = new ArrayList<Integer>(Collections.nCopies(nodes.size(), MAX_VALUE));
		ArrayList<Integer> parent = new ArrayList<Integer>(Collections.nCopies(nodes.size(), -1));
		PriorityQueue<Pair<Node, Integer>> queue = new PriorityQueue<Pair<Node, Integer>>(nodes.size(), new GraphComparator());
		ArrayList < Pair <Integer, Integer> > ama =
				new ArrayList < Pair <Integer, Integer> >();
		int source = 0;
		/*
		 * TODO
		 * initializare cost si parent
		 *
		 * Hint:
		 * boolean g.existEdgeBetween(Node, Node)
		 * int g.getCostBetween(Node, Node)
		 * */
		
		for (Node u : g.getNodes()) {
			cost.set(u.getId(), Integer.MAX_VALUE);
			parent.set(u.getId(), -1);
		}

		for (int i = 0; i < nodes.size(); i++) {
			queue.add(new Pair<Node, Integer>(nodes.get(i), cost.get(nodes.get(i).getId())));
		}
		cost.set(source, 0);
		

		/*
		 * TODO
		 * aflati costul minim al transportului retinut in coada
		 * incercati sa relaxati muchiile
		 * daca o muchie poate fi relaxata si nodul nu este deja in AMA => ea va fi updatata
		 */

		while (!queue.isEmpty()) {
			
			Pair<Node, Integer> p = queue.poll();
			Node u = p.getFirst();
			int w = p.getSecond();
			ama.add(new Pair<Integer, Integer>(u.getId(), parent.get(u.getId())));

			for (Pair<Node, Integer> pair : g.getEdges(u)) {
				Node v = pair.getFirst();

				if (pair.getSecond() < cost.get(v.getId())) {
					
					cost.set(v.getId(), g.getCostBetween(u, v));
					parent.set(v.getId(), u.getId());				
					
					Iterator<Pair<Node, Integer>> it = queue.iterator();
					while (it.hasNext()) {
						
						Pair<Node, Integer> current = it.next();
						
						if (current.getFirst().getId() == v.getId()) {
							queue.remove(current);
							current.setSecond(cost.get(v.getId()));
							queue.add(current);
							break;
						}
						
					}
					
				}
			}
		}	

		ama.remove(new Pair<Integer, Integer>(source, -1));

		return ama;
	}

//	static int get_mul(int mul[], int x) {
//		
//	}
//	static void join_mul(int mul[], int x, int y) {
//		for (int i = 0; i < mul.length; i++) {
//			if (mul[i] == x) {
//				mul[i] = y;
//			}
//		}
//	}
	
	static ArrayList < Pair < Integer, Integer > > kruskal(Graph g) {
		ArrayList <Node> nodes = g.getNodes();
		ArrayList < Pair <Integer, Integer> > edges = g.getAllEdges();
		ArrayList < Pair <Integer, Integer> > ama =
				new ArrayList < Pair <Integer, Integer> >();
		int multimi[] = new int[g.nodeCount()];
		/*
		 * TODO
		 * Construiti o lista de muchii cu tot cu cost
		 * Sortati lista de muchii dupa costul lor
		 *
		 * Initializati vectorul multimi
		 *
		 * HINT: Collections.sort
		 */
		
		for (Node v : nodes) {
		}

		

		/*
		 * TODO
		 * Parcurgeti muchiile in ordine
		 * Pe cele care intra in AMA le bagati in vectorul ama
		 */

		
		return ama;
	}
	
	public static void main( String... args ) throws FileNotFoundException
	{
		
		final String fisiere[] = {"date1", "date2"};
		for (int test = 0; test < 2; ++test) {
			Scanner scanner = new Scanner(new File(fisiere[test]));
	
			Graph g = new Graph();
			g.readData(scanner);
			System.out.print(g);
	
			ArrayList<Node> nodes = g.getNodes();
			int sum = 0;
			ArrayList<Pair<Integer, Integer>> edges = kruskal(g);
			for (Pair<Integer, Integer> edge: edges) {
				sum+=g.getCostBetween(nodes.get(edge.getFirst()), nodes.get(edge.getSecond()));
				edge.sort();
			}
			Collections.sort(edges);
			System.out.println("Kruskal:");
			System.out.println(edges);
			System.out.println("Cost total: "+sum);
			
			edges = prim(g);
			sum = 0;
			for (Pair<Integer, Integer> edge: edges) {
				sum+=g.getCostBetween(nodes.get(edge.getFirst()), nodes.get(edge.getSecond()));
				edge.sort();
			}
			Collections.sort(edges);
			System.out.println("Prim:");
			System.out.println(edges);
			System.out.println("Cost total: "+sum+"\n");
			
			scanner.close();
		}
	
	}

	/*
	 * TODO
	 * comparator pentru coada cu prioritati
	 */
	private static class GraphComparator implements Comparator<Pair<Node, Integer>> {

		@Override
		public int compare(Pair<Node, Integer> arg0, Pair<Node, Integer> arg1) {

			/*
			 * TODO
			 * */
			
			return arg0.getSecond() - arg0.getSecond();
			
		}
	}

}

