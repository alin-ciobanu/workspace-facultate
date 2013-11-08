package graphics;

import enums.DIFFICULTY;
import enums.PLAYER;
import gamelogic.Board;
import gamelogic.Bot;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

import javax.swing.JFrame;

import structs.Pair;

public class GameplayPanel extends EmptyBoardPanel {

	private static final long serialVersionUID = 1L;

	LinkedList<PiecePoint> points;
	Bot bot;

	private final GameplayPanelMouseListeners mouseListeners;

	public GameplayPanel (Bot bot, PLAYER player) {

		initPoints();

		System.out.println(points);

		this.bot = bot;

		mouseListeners = new GameplayPanelMouseListeners (this, points, player, bot);

		this.setBackground(Color.WHITE);

	}

	@Override
	public void paintComponent (Graphics g) {

		g.clearRect(0, 0, super.getWidth(), super.getHeight());

		super.paintComponent(g);

		for (PiecePoint p: points) {

			p.paintComponent(g);

		}

	}

	public void addMouseListeners () {

		this.addMouseListener(mouseListeners);
		this.addMouseMotionListener(mouseListeners);

	}

	public void removeMouseListeners () {

		this.removeMouseMotionListener(mouseListeners);
		this.removeMouseListener(mouseListeners);

	}

	private void initPoints () {

		points = new LinkedList<PiecePoint>();
		Board board = new Board();

		TreeSet<Pair<Integer, Integer>> ptsCoord = super.getPointsCoordinates();
		TreeSet<Pair<Integer, Integer>> ptsMatrx = board.getAllPointsOfBoard();

		Iterator<Pair<Integer, Integer>> itCoord = ptsCoord.iterator();
		Iterator<Pair<Integer, Integer>> itMatrx = ptsMatrx.iterator();

		while (itCoord.hasNext() && itMatrx.hasNext()) {

			PiecePoint point = new PiecePoint();

			Pair<Integer, Integer> coord = itCoord.next();
			Pair<Integer, Integer> matrx = itMatrx.next();

			int x, y;
			x = coord.getFirst();
			y = coord.getSecond();

			int line, col;
			line = matrx.getFirst();
			col = matrx.getSecond();

			point.setCoordinates(x, y);
			point.setMatrix2Did(line, col);

			// TODO Hardcodate
			point.setRadiusEmpty(this.getPieceRadius());
			point.setRadiusHovering( (int) (this.getPieceRadius() * 1.8));
			point.setRadiusPiece( (int) (this.getPieceRadius() * 1.5));
			point.setColors(Color.BLUE, Color.ORANGE);

			points.add(point);

		}

	}

	public void play () {

		PLAYER human, computer;
		human = PLAYER.BLACK;
		computer = PLAYER.WHITE;

		int blacksLeft, whitesLeft;
		blacksLeft = whitesLeft = 9;

		this.addMouseListeners();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("GameplayPanel.main()");

		JFrame f = new JFrame();

		GameplayPanel p = new GameplayPanel(new Bot(PLAYER.WHITE, DIFFICULTY.EASY), PLAYER.BLACK);

		f.setLayout(new FlowLayout());
		f.add(p);

		p.play();

		f.setBackground(Color.WHITE);

		f.setSize(700, 730);

		f.setVisible(true);



	}

}
