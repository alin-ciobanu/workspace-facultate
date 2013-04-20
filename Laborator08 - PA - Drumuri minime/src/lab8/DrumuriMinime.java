/**
 * Proiectarea Algoritmilor, 2013
 * Lab 8: Drumuri minime
 *
 * @author 	Emma Sevastian
 * @email	emma.sevastian@gmail.com
 */

package lab8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import graph.Graph;
import graph.Node;
import graph.Pair;

public class DrumuriMinime {

	final static int MAX_VALUE = 9999;
	static int costFloydWarshall[][];
	static int detourFloydWarshall[][];

	/*
	 * Dijkstra
	 * */
	public static ArrayList<Integer> dijkstra(Graph g, int source, int destination) {

		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Integer> cost = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		ArrayList<Integer> parent = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		PriorityQueue<Pair<Node, Integer>> queue = new PriorityQueue<Pair<Node, Integer>>(nodes.size(), new GraphComparator());

		/*
		 * TODO
		 * initializare cost si parent
		 *
		 * Hint:
		 * boolean g.existEdgeBetween(Node, Node)
		 * int g.getCostBetween(Node, Node)
		 * */

		nodes.get(source).visit();

		for (int i = 0; i < g.getNodes().size(); i++) {
			if (g.existsEdgeBetween(nodes.get(source), nodes.get(i))) {
				cost.set(i, g.getCostBetween(nodes.get(i), nodes.get(source)));
				queue.add(new Pair<Node, Integer> (nodes.get(i), cost.get(i)));
				parent.set(nodes.get(i).getId(), source);
			}
			else {
				cost.set(i, MAX_VALUE);
				parent.set(i, -1);
			}
		}
		
		/*
		 * TODO
		 * scoateti costul minim din coada
		 * relaxati muchiile
		 * daca o muchie poate fi relaxata => trebuie updatata in coada
		 * */

		while (!queue.isEmpty()) {
			
			Pair<Node, Integer> u = queue.poll();
			u.getFirst().visit();

			ArrayList<Pair<Node, Integer>> vecini = g.getEdges(u.getFirst());

			for (int i = 0; i < vecini.size(); i++) {
				
				Node v = vecini.get(i).getFirst();
				int w = vecini.get(i).getSecond();

				if (!nodes.get(i).isVisited() && cost.get(v.getId()) > cost.get(u.getFirst().getId()) + w) {
					cost.set(u.getFirst().getId(), cost.get(u.getFirst().getId()) + w);
					parent.set(v.getId(), u.getFirst().getId());
				}
				
			}
			
		}
		

		ArrayList<Integer> path = new ArrayList<Integer>();

		/*
		 * TODO
		 * aflati drumul de la sursa la destinatie (plecand din destinatie)
		 * */
		System.out.println(cost.get(destination));
		
		int nod = parent.get(destination);
		path.add(destination);
		
		while (nod != -1) {
			path.add(nod);
			nod = parent.get(nod);
		}
		

		return path;
	}

	/*
	 * TODO
	 * afiseaza drumul intre 2 orase
	 * */
	public static void printPath(Graph g, int i, int j)
	{
		dijkstra (g, i, j);
	}

	/*
	 * Floyd-Warshall
	 * */
	public static void FloydWarshall(Graph g)
	{

		ArrayList<Node> nodes = g.getNodes();
		int n = nodes.size();

		/*
		 * TODO
		 * initializati costFloydWarshall si detourFloydWarshall
		 * */
		
		int d[][] = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (g.existsEdgeBetween(nodes.get(i), nodes.get(j))) {
					d[i][j] = g.getCostBetween(nodes.get(i), nodes.get(j));
				}
				else {
					d[i][j] = MAX_VALUE;
				}
			}
		}

		

		/*
		 * TODO
		 * incercati sa relaxati muchiile
		 * tineti cont de faptul ca cel mai lung drum de cost minim intre 2 orase
		 * poate avea maxim n-1 muchii
		 * */

		for (int k = 1; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
				}
			}
		}
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}
		

	}

	/*
	 * Bellman-Ford
	 * */
	public static void BellmanFord(Graph g, int source)
	{
		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Integer> cost = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		ArrayList<Integer> parent = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));

		/*
		 * TODO
		 * initializare cost si parent
		 * */

		

		/*
		 * TODO
		 * relaxare noduri
		 * */

		

		/*
		 * TODO
		 * daca mai exista muchii ce pot fi relaxate => exista un ciclu de cost negativ
		 * */

		

	}

	public static void main( String... args ) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("date1"));

		Graph g = new Graph();
		g.readData(scanner);
		System.out.print(g);

		ArrayList<Node> nodes = g.getNodes();

		/*
		 * aflare drum de cost minim intre Bucuresti si Paris
		 * */
		System.out.println("Drumul de cost minim intre Bucuresti - Paris :");
		ArrayList<Integer> path = dijkstra(g, g.getNode("Bucuresti"), g.getNode("Paris"));

		for (int i = path.size()-1; i >= 0; i--)
			System.out.print(nodes.get(path.get(i)).getCity() + " ");
		System.out.println("\n");

		/*
		 * Gasiti drumul de cost minim intre oricare 2 orase
		 * */
		System.out.println("Drumul minim de la Paris la Viena");
		costFloydWarshall = new int[nodes.size()][nodes.size()];
		detourFloydWarshall = new int[nodes.size()][nodes.size()];

		FloydWarshall(g);
		
		/*
		 * Aflati daca exista drumuri de cost negativ
		 * */
		Scanner scannerNew = new Scanner(new File("date2"));

		Graph gNew = new Graph();
		gNew.readData(scannerNew);
		System.out.print(gNew);

		System.out.println("Exista drumuri de cost negativ?");
		BellmanFord(gNew, 0);

		scanner.close();
		scannerNew.close();
	}

	/*
	 * TODO
	 * comparator pentru coada cu prioritati
	 */
	private static class GraphComparator implements Comparator<Pair<Node, Integer>> {

		@Override
		public int compare(Pair<Node, Integer> arg0, Pair<Node, Integer> arg1) {

			return (arg0.getSecond() - arg1.getSecond());
			
		}
	}

}

