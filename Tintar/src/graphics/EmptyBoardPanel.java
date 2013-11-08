package graphics;

import gamelogic.PointComparator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import structs.Pair;

public class EmptyBoardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final static int LITTLE_WIDTH = 150;
	private final static int LITTLE_HEIGHT = 300;

	private final static int MIDDLE_WIDTH = 324;
	private final static int MIDDLE_HEIGHT = 450;

	private final static int BIG_WIDTH = 500;
	private final static int BIG_HEIGHT = 620;

	private final static int BIG_BORDER_X = 34;
	private final static int BIG_BORDER_Y = 34;

	private final static int MIDDLE_BORDER_X =
			BIG_BORDER_X + (BIG_WIDTH - MIDDLE_WIDTH) / 2;
	private final static int MIDDLE_BORDER_Y =
			BIG_BORDER_Y + (BIG_HEIGHT - MIDDLE_HEIGHT) / 2;

	private final static int LITTLE_BORDER_X =
			MIDDLE_BORDER_X + (MIDDLE_WIDTH - LITTLE_WIDTH) / 2;
	private final static int LITTLE_BORDER_Y =
			MIDDLE_BORDER_Y + (MIDDLE_HEIGHT - LITTLE_HEIGHT) / 2;

	private final static int VERT_LINE_UP_POINT_UP_X = BIG_BORDER_X + BIG_WIDTH / 2;
	private final static int VERT_LINE_UP_POINT_UP_Y = BIG_BORDER_Y;
	private final static int VERT_LINE_UP_POINT_DOWN_X = BIG_BORDER_X + BIG_WIDTH / 2;
	private final static int VERT_LINE_UP_POINT_DOWN_Y = LITTLE_BORDER_Y;

	private final static int VERT_LINE_DOWN_POINT_UP_X = BIG_BORDER_X + BIG_WIDTH / 2;
	private final static int VERT_LINE_DOWN_POINT_UP_Y = LITTLE_BORDER_Y + LITTLE_HEIGHT;
	private final static int VERT_LINE_DOWN_POINT_DOWN_X = BIG_BORDER_X + BIG_WIDTH / 2;
	private final static int VERT_LINE_DOWN_POINT_DOWN_Y = BIG_BORDER_Y + BIG_HEIGHT;

	private final static int ORIZ_LINE_LEFT_POINT_LEFT_X = BIG_BORDER_X;
	private final static int ORIZ_LINE_LEFT_POINT_LEFT_Y = LITTLE_BORDER_Y + LITTLE_HEIGHT / 2;
	private final static int ORIZ_LINE_LEFT_POINT_RIGHT_X = LITTLE_BORDER_X;
	private final static int ORIZ_LINE_LEFT_POINT_RIGHT_Y = LITTLE_BORDER_Y + LITTLE_HEIGHT / 2;

	private final static int ORIZ_LINE_RIGHT_POINT_LEFT_X = LITTLE_BORDER_X + LITTLE_WIDTH;
	private final static int ORIZ_LINE_RIGHT_POINT_LEFT_Y = LITTLE_BORDER_Y + LITTLE_HEIGHT / 2;
	private final static int ORIZ_LINE_RIGHT_POINT_RIGHT_X = BIG_BORDER_X + BIG_WIDTH;
	private final static int ORIZ_LINE_RIGHT_POINT_RIGHT_Y = BIG_BORDER_Y + BIG_HEIGHT / 2;

	TreeSet<Pair<Integer, Integer>> points;

	public EmptyBoardPanel() {

		this.setPointsCoordinates();

		this.setPreferredSize(new Dimension(2 * BIG_BORDER_X + BIG_WIDTH, 2 * BIG_BORDER_Y + BIG_HEIGHT));
		this.setBackground(Color.WHITE);

		this.setVisible(true);

	}

	/**
	 * Picteaza board-ul gol
	 */
	@Override
	public void paintComponent (Graphics g) {

		g.drawRect(BIG_BORDER_X, BIG_BORDER_Y, BIG_WIDTH, BIG_HEIGHT);
		g.drawRect(BIG_BORDER_X - 1, BIG_BORDER_Y - 1, BIG_WIDTH, BIG_HEIGHT);
		g.drawRect(BIG_BORDER_X + 1, BIG_BORDER_Y + 1, BIG_WIDTH, BIG_HEIGHT);
		g.drawRect(BIG_BORDER_X + 2, BIG_BORDER_Y + 2, BIG_WIDTH, BIG_HEIGHT);

		g.drawRect(MIDDLE_BORDER_X, MIDDLE_BORDER_Y, MIDDLE_WIDTH, MIDDLE_HEIGHT);
		g.drawRect(MIDDLE_BORDER_X - 1, MIDDLE_BORDER_Y - 1, MIDDLE_WIDTH, MIDDLE_HEIGHT);
		g.drawRect(MIDDLE_BORDER_X + 1, MIDDLE_BORDER_Y + 1, MIDDLE_WIDTH, MIDDLE_HEIGHT);
		g.drawRect(MIDDLE_BORDER_X + 2, MIDDLE_BORDER_Y + 2, MIDDLE_WIDTH, MIDDLE_HEIGHT);

		g.drawRect(LITTLE_BORDER_X, LITTLE_BORDER_Y, LITTLE_WIDTH, LITTLE_HEIGHT);
		g.drawRect(LITTLE_BORDER_X - 1, LITTLE_BORDER_Y - 1, LITTLE_WIDTH, LITTLE_HEIGHT);
		g.drawRect(LITTLE_BORDER_X + 1, LITTLE_BORDER_Y + 1, LITTLE_WIDTH, LITTLE_HEIGHT);
		g.drawRect(LITTLE_BORDER_X + 2, LITTLE_BORDER_Y + 2, LITTLE_WIDTH, LITTLE_HEIGHT);

		/*
		 * Linia verticala de sus
		 */
		g.drawLine(VERT_LINE_UP_POINT_UP_X, VERT_LINE_UP_POINT_UP_Y, VERT_LINE_UP_POINT_DOWN_X,
				VERT_LINE_UP_POINT_DOWN_Y);
		g.drawLine(VERT_LINE_UP_POINT_UP_X - 1, VERT_LINE_UP_POINT_UP_Y,
				VERT_LINE_UP_POINT_DOWN_X - 1, VERT_LINE_UP_POINT_DOWN_Y);
		g.drawLine(VERT_LINE_UP_POINT_UP_X + 1, VERT_LINE_UP_POINT_UP_Y,
				VERT_LINE_UP_POINT_DOWN_X + 1, VERT_LINE_UP_POINT_DOWN_Y);
		g.drawLine(VERT_LINE_UP_POINT_UP_X + 2, VERT_LINE_UP_POINT_UP_Y,
				VERT_LINE_UP_POINT_DOWN_X + 2, VERT_LINE_UP_POINT_DOWN_Y);

		/*
		 * Linia verticala de jos
		 */
		g.drawLine(VERT_LINE_DOWN_POINT_UP_X, VERT_LINE_DOWN_POINT_UP_Y,
				VERT_LINE_DOWN_POINT_DOWN_X, VERT_LINE_DOWN_POINT_DOWN_Y);
		g.drawLine(VERT_LINE_DOWN_POINT_UP_X - 1, VERT_LINE_DOWN_POINT_UP_Y,
				VERT_LINE_DOWN_POINT_DOWN_X - 1, VERT_LINE_DOWN_POINT_DOWN_Y);
		g.drawLine(VERT_LINE_DOWN_POINT_UP_X + 1, VERT_LINE_DOWN_POINT_UP_Y,
				VERT_LINE_DOWN_POINT_DOWN_X + 1, VERT_LINE_DOWN_POINT_DOWN_Y);
		g.drawLine(VERT_LINE_DOWN_POINT_UP_X + 2, VERT_LINE_DOWN_POINT_UP_Y,
				VERT_LINE_DOWN_POINT_DOWN_X + 2, VERT_LINE_DOWN_POINT_DOWN_Y);

		/*
		 * Linia orizontala din stanga
		 */
		g.drawLine(ORIZ_LINE_LEFT_POINT_LEFT_X, ORIZ_LINE_LEFT_POINT_LEFT_Y,
				ORIZ_LINE_LEFT_POINT_RIGHT_X, ORIZ_LINE_LEFT_POINT_RIGHT_Y);
		g.drawLine(ORIZ_LINE_LEFT_POINT_LEFT_X, ORIZ_LINE_LEFT_POINT_LEFT_Y + 1,
				ORIZ_LINE_LEFT_POINT_RIGHT_X, ORIZ_LINE_LEFT_POINT_RIGHT_Y + 1);
		g.drawLine(ORIZ_LINE_LEFT_POINT_LEFT_X, ORIZ_LINE_LEFT_POINT_LEFT_Y - 1,
				ORIZ_LINE_LEFT_POINT_RIGHT_X, ORIZ_LINE_LEFT_POINT_RIGHT_Y - 1);
		g.drawLine(ORIZ_LINE_LEFT_POINT_LEFT_X, ORIZ_LINE_LEFT_POINT_LEFT_Y + 2,
				ORIZ_LINE_LEFT_POINT_RIGHT_X, ORIZ_LINE_LEFT_POINT_RIGHT_Y + 2);

		/*
		 * Linia orizontala din dreapta
		 */
		g.drawLine(ORIZ_LINE_RIGHT_POINT_LEFT_X, ORIZ_LINE_RIGHT_POINT_LEFT_Y,
				ORIZ_LINE_RIGHT_POINT_RIGHT_X, ORIZ_LINE_RIGHT_POINT_RIGHT_Y);
		g.drawLine(ORIZ_LINE_RIGHT_POINT_LEFT_X, ORIZ_LINE_RIGHT_POINT_LEFT_Y - 1,
				ORIZ_LINE_RIGHT_POINT_RIGHT_X, ORIZ_LINE_RIGHT_POINT_RIGHT_Y - 1);
		g.drawLine(ORIZ_LINE_RIGHT_POINT_LEFT_X, ORIZ_LINE_RIGHT_POINT_LEFT_Y + 1,
				ORIZ_LINE_RIGHT_POINT_RIGHT_X, ORIZ_LINE_RIGHT_POINT_RIGHT_Y + 1);
		g.drawLine(ORIZ_LINE_RIGHT_POINT_LEFT_X, ORIZ_LINE_RIGHT_POINT_LEFT_Y + 2,
				ORIZ_LINE_RIGHT_POINT_RIGHT_X, ORIZ_LINE_RIGHT_POINT_RIGHT_Y + 2);

	}

	/**
	 * Apelat din constructor (sau din getPointsCoordinates)
	 * Seteaza coordonatele punctelor
	 * 24 de puncte in total
	 */
	private void setPointsCoordinates() {

		points = new TreeSet<Pair<Integer, Integer>>(new PointComparator());

		int i, j;

		for (i = BIG_BORDER_X; i <= BIG_BORDER_X + BIG_WIDTH; i = i + BIG_WIDTH / 2) {
			for (j = BIG_BORDER_Y; j <= BIG_BORDER_Y + BIG_HEIGHT; j = j + BIG_HEIGHT) {
				points.add(new Pair<Integer, Integer>(i, j));
			}
		}

		for (i = MIDDLE_BORDER_X; i <= MIDDLE_BORDER_X + MIDDLE_WIDTH; i = i + MIDDLE_WIDTH / 2) {
			for (j = MIDDLE_BORDER_Y; j <= MIDDLE_BORDER_Y + MIDDLE_HEIGHT; j = j + MIDDLE_HEIGHT) {
				points.add(new Pair<Integer, Integer>(i, j));
			}
		}

		for (i = LITTLE_BORDER_X; i <= LITTLE_BORDER_X + LITTLE_WIDTH; i = i + LITTLE_WIDTH / 2) {
			for (j = LITTLE_BORDER_Y; j <= LITTLE_BORDER_Y + LITTLE_HEIGHT; j = j + LITTLE_HEIGHT) {
				points.add(new Pair<Integer, Integer>(i, j));
			}
		}

		points.add(new Pair<Integer, Integer>(BIG_BORDER_X, BIG_BORDER_Y + BIG_HEIGHT / 2));
		points.add(new Pair<Integer, Integer>(MIDDLE_BORDER_X, BIG_BORDER_Y + BIG_HEIGHT / 2));
		points.add(new Pair<Integer, Integer>(LITTLE_BORDER_X, BIG_BORDER_Y + BIG_HEIGHT / 2));
		points.add(new Pair<Integer, Integer>(LITTLE_BORDER_X + LITTLE_WIDTH,
				BIG_BORDER_Y + BIG_HEIGHT / 2));
		points.add(new Pair<Integer, Integer>(MIDDLE_BORDER_X + MIDDLE_WIDTH,
				BIG_BORDER_Y + BIG_HEIGHT / 2));
		points.add(new Pair<Integer, Integer>(BIG_BORDER_X + BIG_WIDTH,
				BIG_BORDER_Y + BIG_HEIGHT / 2));

	}

	/**
	 *
	 * @return coordonatele punctelor
	 */
	public TreeSet<Pair<Integer, Integer>> getPointsCoordinates() {

		return points;

	}

	public int getPieceRadius () {

		return LITTLE_WIDTH / 9;

	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame f = new JFrame();
		f.setBackground(Color.WHITE);

		f.add(new EmptyBoardPanel());
		f.setSize(800, 730);
		f.setVisible(true);

	}

}
