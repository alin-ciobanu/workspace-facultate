package maze;

import java.util.ArrayList;

import ui.UI;
import mapGenerator.Coord;
import mapGenerator.Map;
import mapGenerator.MapPosition;
import mapGenerator.Coord.Direction;

public class MazeResolver {
	public enum Method {
		DFS, BFS
	}
	
	private UI ui;
	private Method searchingMethod;
	private Map map;
	private ArrayList<Coord> frontier;
	
	public MazeResolver(Map map, UI ui, Method searchingMethod) {
		this.searchingMethod = searchingMethod;
		this.map = map;
		this.ui = ui;
		this.frontier = new ArrayList<Coord>();
	}

	/*
	 * TODO:
	 * 	- explore the map
	 * 	- at each step call the function makeMove 
	 */
	public void resolve(){
		Coord coordStart = map.getStartCoord();
		Coord coordFin = map.getFinishCoord();
		
		frontier.add(coordStart);
		
		while (frontier.size() != 0) {
			Coord current = getNextNode();
			frontier.remove(0);
			makeMove(current);
			if (current.equals(coordFin))
				return;
			addNeighbours(current);
		}
		
	}
	
	private void makeMove(Coord myNewPosition) {
		map.setCell(myNewPosition, MapPosition.ME);	
		ui.paint(myNewPosition);
	}

	/*
	 * TODO: Add neighbours to the frontier list. 
	 * Take a look at the variable searchingMethod;
	 * 	Hint: use:
	 * 	- getPossibleMoves
	 * 	- insert differently depending on the searchingMethod
	 */
	public void addNeighbours(Coord myPosition){
		ArrayList<Coord> a = getPossibleMoves(myPosition);
		if (searchingMethod == Method.BFS) {
			frontier.addAll(a);
		}
		else {
			frontier.addAll(0, a);
		}
	}

	
	/*
	 * TODO: Extract the next neighbour from frontier;
	 */		
	public Coord getNextNode(){
		return frontier.get(0);
	}
	
	
	/*
	 * TODO:
	 * 	Return an array with all possible moves
	 * 	Hint: use
	 * 	- Coord.getNeighbour
	 * 	- MapPosition.EMPTY
	 */
	public ArrayList<Coord> getPossibleMoves(Coord myPosition){
		ArrayList<Coord> rez = new ArrayList<Coord>();
		
		for (int i = 0; i < 4; i++) {
			Coord currentCoord = Coord.getNeighbour(myPosition, Direction.fromInt(i));
			if (map.getCell(currentCoord.getX(), currentCoord.getY()) == MapPosition.EMPTY)
				rez.add(currentCoord);
		}

		return rez;
	}

	public void setSearchingMethod(Method searchingMethod) {
		this.searchingMethod = searchingMethod;
	}	
	
}
