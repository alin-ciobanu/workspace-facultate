package lab9;

import java.util.ArrayList;
import java.util.Random;

import graph.*;

public class Maze {

	final static int MAX_SIZE = 5;
	static char[][] maze = new char[ 2*MAX_SIZE+1 ][2*MAX_SIZE+1 ];
	
	final int dx[] = {  0, 0, -1, 1 };
	final int dy[] = { -1, 1,  0, 0 };
	
	Graph g = new Graph();
	
	public Maze(){
		
		Random rand = new Random();
		
		for( int i = 0; i < MAX_SIZE * MAX_SIZE; ++i ){
			g.insertNode( new Node(i) );
		}
		/*
		 * TODO
		 * Construieste legaturile intre nodurile din labirint
		 * Nodul de la pozitia (i,j) va avea id-ul i*maze_size+j
		 *
		 * ATENTIE SA NU IESITI DIN LABIRINT
		 */
		
	}
	
	public void printMaze( ArrayList< Pair<Integer, Integer> > edges )
	{
		for( int i = 0; i < maze.length; ++i )
			for( int j = 0; j < maze.length; ++j )
			{
				maze[i][j] = '#';
			}
		
		for( int i = 0; i < MAX_SIZE ; ++i )
			for( int j = 0; j < MAX_SIZE; ++j )
			{
				maze[2 * i + 1][ 2 * j + 1] = '_';
				
				for( int k = 0; k < 4; ++k )
				{
					int x = i + dx[k];
					int y = j + dy[k];
					
					if( edges.contains( new Pair<Integer,Integer>( i * MAX_SIZE + j, x * MAX_SIZE + y ) ) ||
						edges.contains( new Pair<Integer,Integer>( x * MAX_SIZE + y, i * MAX_SIZE + j ) ) )
						maze[ 2 * i + 1 + dx[k] ][ 2 * j + 1 + dy[k] ] = '_';
				}
			}
		
		System.out.println(this);
	}
	
	void makeMaze()
	{
		ArrayList< Pair< Integer,Integer > > edges = new ArrayList< Pair< Integer,Integer > >();
		/*
		 * TODO
		 * Aplica un algorithm de AMA cu alegere randomizata
		 */
		
		printMaze( edges );
	}
	
	public String toString()
	{
		String str = "";
		for( int i = 0; i < maze.length; ++i ){
			for( int j = 0; j < maze.length; ++j ){
				str += maze[i][j];
			}
			str += "\n";
		}
		return str;
	}
	
	public static void main( String... args )
	{
		Maze maze = new Maze();
		maze.makeMaze();
	}
	
}