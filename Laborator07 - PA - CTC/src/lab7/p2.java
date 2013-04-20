/**
 * Proiectarea Algoritmilor, 2013
 * Lab 7: Aplicatii DFS
 * 
 * @author 	Radu Iacob
 * @email	radu.iacob23@gmail.com
 */

package lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import graph.Graph;
import graph.Node;
import graph.Pair;

public class p2 {

	/*
	 * Afiseaza componentele biconexe, salvate in stiva
	 */
	
	static void print_biconex_component( Graph g, Node n1, Node n2 )
	{
		System.out.println("Componenta biconexa: ");
		
		HashSet< Node > comp = new HashSet< Node >();
		
		Pair< Node, Node > aux;
		
		do
		{
			aux = g.edge_stack.pop();
			
			comp.add( aux.getFirst() );
			comp.add( aux.getSecond() );
			
		}while( aux.getFirst() != n1 || aux.getSecond() != n2 );
		
		System.out.println( comp );
	}
	
	/*
	 * Useful API:
	 * 
	 * graph.edge_stack
	 * graph.muchii_critice
	 * graph.puncte_de_articulatie
	 * graph.get_edges
	 * 
	 * node.level
	 * node.lowlink
	 * node.in_stack
	 * 
	 */

	static void dfs_biconex( Graph g, Node node, int current_level, int father_id )
	{
		ArrayList< Node > childs = new ArrayList< Node >();
		
		/*
		 * TODO: Initializati level si lowlink
		 */
		node.level = node.lowlink = current_level;
		
		/*
		 * TODO: Parcurgere recursiva a copiilor nevizitati
		 */
		g.stack.push(node);
		node.in_stack = true;
		
		for (Node v : g.get_edges(node)) {
			if (! v.visited()) {
				childs.add(v);
				dfs_biconex(g, v, current_level + 1, father_id);
				node.lowlink = Math.min(node.lowlink, v.lowlink);
				if (v.lowlink >= node.level) {
					g.muchii_critice.add(new Pair<Node, Node>(v, node));
				}
			}
			else {
				node.lowlink = Math.min (node.lowlink, v.level);
			}
		}

		/*
		 * TODO: Identifica daca nodul este punct de articulatie
		 */
		if (node.get_id() == father_id && childs.size() > 2) {
			g.puncte_de_articulatie.add(node);
		}
		else {
			if (node.get_id() != father_id) {
				for (Node u : childs) {
					if (u.lowlink > node.lowlink) {
						g.puncte_de_articulatie.add(node);
					}
				}
			}
		}
		
	}
	
	/*
	 * Descompune graful in componente biconexe
	 *
	 * Useful API:
	 * 
	 * g.get_nodes()
	 * node.visited()
	 */

	static void ComponenteBiconexe( Graph g )
	{
		g.reset();
		
		/*
		 * TODO: Parcurgere df pentru identificarea componentelor biconexe
		 */
		int index = 0;
		for (Node v : g.get_nodes()) {
			dfs_biconex(g, v, index, v.get_id());
		}

		System.out.println("\nPuncte. de articulatie: \n" + g.puncte_de_articulatie );
		System.out.println("\nMuchii critice: \n" + g.muchii_critice );
	}
	
	final static String PATH = "./res/test02";
	
	public static void main( String... args ) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(PATH));
		int test_count = scanner.nextInt();
		
		while( test_count-- > 0 )
		{
			Graph g = new Graph( Graph.GraphType.UNDIRECTED );	
			g.readData( scanner );
			System.out.println(g);
			ComponenteBiconexe(g);
		}
		
		scanner.close();
	}
	
	
	
}

