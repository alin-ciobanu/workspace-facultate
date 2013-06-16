import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;


public class Directions {

	/*
	 * A NU SE CONFUNDA CU ENUMERAREA DIRECTION!
	 * Clasa pentru scrierea metodelor statice
	 */
	
	static int TIME_FOR_DFS;
	static int[][] IDX;
	static Pair<Integer, Integer>[][] parent;
	static ArrayList<Pair<Integer, Integer>> articulationPoints;
	
	private static int INF = Integer.MAX_VALUE;
	
	/**
	 * Functie pentru calculul distantei dintre doua puncte
	 * @param p1 - Prima pereche de pozitii
	 * @param p2 - A doua pereche de pozitii
	 * @return - Distanta
	 */
	public static int distanceBetween(Pair<Integer,Integer> p1, Pair<Integer,Integer> p2){
		/**
		 * Variabila folosita pentru a nu avea un carnat de expresii
		 */

		return Math.abs ((p1.getFirst() - p2.getFirst()) + (p1.getSecond() - p2.getSecond()));

		}
	
	/**
	 * 
	 * @param dir - o directie (UP, DOWN, LEFT, RIGHT)
	 * @return - directia opusa
	 */
	public static DIRECTION complementDirection (DIRECTION dir) {

		switch (dir) {

		case UP:
			return DIRECTION.DOWN;
		case DOWN:
			return DIRECTION.UP;
		case RIGHT:
			return DIRECTION.LEFT;
		case LEFT:
			return DIRECTION.RIGHT;
		default:
			System.out.println("Directie gresita!\n");
			// nu prea ai cum sa ajungi pe ramura asta
			return null;
		
		}

	}
	
	/**
	 * 
	 * @param b -- board-ul
	 * @param play_as -- jucatorul pentru care se face BFS
	 * 
	 * @return -- distantele de la pozitia curenta a jucatorului
	 * la fiecare din toate celelalte puncte de pe board 
	 * (INF pentru punctele la care nu poate ajunge)
	 * 
	 * Nu-s foarte mandru pentru modul cum arata metoda asta, dar face ce trebuie.
	 * 
	 */
	public static int[][] bfs (Board b, PLAYER play_as) {
		
		int[][] distance = new int[b.lines][b.cols];
		Color[][] nodeColor = new Color[b.lines][b.cols];
		// Cool Color class in Java is cool. :)

		int currentPosL = -1, currentPosC = -1; // pozitia curenta a PLAYER-ului
		Pair<Integer, Integer> pos = new Pair<Integer, Integer>();

		if (play_as == PLAYER.G) {
			pos = b.getCurrentPositionForG();
		}
		else {
			pos = b.getCurrentPositionForR();
		}
		currentPosL = pos.getFirst();
		currentPosC = pos.getSecond();

		for (int i = 0; i < b.lines; i++) {
			for (int j = 0; j < b.cols; j++) {
				// Initializare distanta si culoare pentru fiecare nod
				distance[i][j] = INF;
				nodeColor[i][j] = Color.WHITE;
			}
		}
		
		distance[currentPosL][currentPosC] = 0; // distanta pana la sursa
		nodeColor[currentPosL][currentPosC] = Color.BLACK; // marcat
		
		LinkedList<Pair<Integer, Integer>> queue = new LinkedList<Pair<Integer,Integer>>();
		// folosim LinkedList pentru ca toate Queue-urile din Java au O(N) complexitate pentru size()
		queue.add(new Pair<Integer, Integer>(currentPosL, currentPosC));
		
		while (!queue.isEmpty()) {
			
			Pair<Integer, Integer> u = queue.removeFirst();
			int ui = -1, uj = -1; // coordonatele punctului u
			ui = u.getFirst();
			uj = u.getSecond();
			
			ArrayList<Pair<Integer, Integer>> neighbours = new ArrayList<Pair<Integer,Integer>>();
			// vecinii nodului u
			
			if (b.board[ui + 1][uj] == MARK.SPACE) {
				neighbours.add(new Pair<Integer, Integer>(ui + 1, uj));
			}
			if (b.board[ui - 1][uj] == MARK.SPACE) {
				neighbours.add(new Pair<Integer, Integer>(ui - 1, uj));
			}
			if (b.board[ui][uj + 1] == MARK.SPACE) {
				neighbours.add(new Pair<Integer, Integer>(ui, uj + 1));
			}
			if (b.board[ui][uj - 1] == MARK.SPACE) {
				neighbours.add(new Pair<Integer, Integer>(ui, uj - 1));
			}
			
			for (Pair<Integer, Integer> v : neighbours) {
				
				int vi = -1, vj = -1; // coordonatele lui v
				vi = v.getFirst();
				vj = v.getSecond();
				
				if (nodeColor[vi][vj] == Color.WHITE) {
					
					distance[vi][vj] = distance[ui][uj] + 1;
					nodeColor[vi][vj] = Color.BLACK;
					queue.add(new Pair<Integer, Integer>(vi, vj));

				}
				
			}
			
			nodeColor[ui][uj] = Color.BLACK;

		}
		
		return distance;

	}
	
	/**
	 *
	 * @return punctele de articulatie
	 */
	public static void getArticulationPoints (Board b, PLAYER play_as) {

		articulationPoints = new ArrayList<Pair<Integer,Integer>>();
		
		IDX = new int[b.lines][b.cols];
		parent = new Pair[b.lines][b.cols];
		for (int i = 0; i < b.lines; i++) {
			for (int j = 0; j < b.cols; j++) {
				IDX[i][j] = -1; // IDX e nedefinit pt nodul [i,j]
				parent[i][j] = new Pair<Integer, Integer>(-1, -1);
			}
		}
		

		TIME_FOR_DFS = 0;
		for (int i = 0; i < b.lines; i++) {
			for (int j = 0; j < b.lines; j++) {
				if (IDX[i][j] == -1 && b.board[i][j] == MARK.SPACE) {
					isArticulationPoint(b, new Pair<Integer, Integer>(i, j));
				}
			}
		}
		

	}
	
	private static void isArticulationPoint (Board b, Pair<Integer, Integer> v) {
		
		int i = v.getFirst();
		int j = v.getSecond();
		
		int[][] low;
		low = new int[b.lines][b.cols];
		
		IDX[i][j] = TIME_FOR_DFS;
		low[i][j] = TIME_FOR_DFS;
		
		TIME_FOR_DFS++;	
		HashSet<Pair<Integer, Integer>> copii = new HashSet<Pair<Integer,Integer>>();
		
		ArrayList<Pair<Integer, Integer>> neighbours = new ArrayList<Pair<Integer,Integer>>();
		// vecinii nodului v
		
		if (b.board[i + 1][j] == MARK.SPACE) {
			neighbours.add(new Pair<Integer, Integer>(i + 1, j));
		}
		if (b.board[i - 1][j] == MARK.SPACE) {
			neighbours.add(new Pair<Integer, Integer>(i - 1, j));
		}
		if (b.board[i][j + 1] == MARK.SPACE) {
			neighbours.add(new Pair<Integer, Integer>(i, j + 1));
		}
		if (b.board[i][j - 1] == MARK.SPACE) {
			neighbours.add(new Pair<Integer, Integer>(i, j - 1));
		}
		
		for (Pair<Integer, Integer> u : neighbours) {
			int ui, uj;
			ui = u.getFirst();
			uj = u.getSecond();
			
			if (IDX[ui][uj] == -1) {
				parent[ui][uj] = new Pair<Integer, Integer>(i, j);
				copii.add(u);
				isArticulationPoint(b, u);
				low[i][j] = Math.min(low[i][j], low[ui][uj]);
			}
			else {
				low[i][j] = Math.min(low[i][j], IDX[ui][uj]);
			}
		}
		
		if (parent[i][j].getFirst() == -1 && copii.size() >= 2) {
			articulationPoints.add(new Pair<Integer, Integer>(i, j));
		}
		else {
			if (!(parent[i][j].getFirst() == -1)) {
				for (Pair<Integer, Integer> p : copii) {
					if (low[p.getFirst()][p.getSecond()] >= IDX[i][j]) {
						articulationPoints.add(new Pair<Integer, Integer>(i, j));
					}
				}
			}
		}
		
	}

	
	public static void main (String[] args) {
		
		Board b = new Board(15, 15);
		Scanner in = new Scanner(System.in);
		
		in.useDelimiter("\n");
		String[] map = new String[15];
		for (int i = 0; i < 15; i++) {
			map[i] = in.next();
		}
		
		b.updateMap(map, 15);
		b.setCurrentPositionForR(7, 1);

		int[][] dist = new int[15][15];
		
		ArrayList<Pair<Integer, Integer>> pct = new ArrayList<Pair<Integer,Integer>>();
		
		getArticulationPoints(b, PLAYER.R);
		pct = Directions.articulationPoints;
		System.out.println(pct);
		
	}
	
	
}
