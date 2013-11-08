package graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import enums.MARK;


public class PiecePoint extends JPanel {


	private static final long serialVersionUID = 1L;

	private int x;
	private int y;

	private int line;
	private int column;

	private int radius_empty;
	private int radius_piece;
	private int radius_hovering;

	private boolean hovering = false;
	private MARK mark;

	private Color playerBlackColor;
	private Color playerWhiteColor;

	public PiecePoint () {

		mark = MARK.EMPTY;

	}

	@Override
	public void paintComponent (Graphics g) {

		if (!hovering) {
			g.setColor(Color.BLACK);
			g.fillOval(x - radius_empty, y - radius_empty, radius_empty * 2, radius_empty * 2);
		}
		else {
			g.setColor(Color.GREEN);
			g.fillOval(x - radius_hovering, y - radius_hovering,
					radius_hovering * 2, radius_hovering * 2);
		}

		if (mark == MARK.BLACK) {
			g.setColor(playerBlackColor);
			g.fillOval(x - radius_piece, y - radius_piece, radius_piece * 2, radius_piece * 2);
		}
		else if (mark == MARK.WHITE) {
			g.setColor(playerWhiteColor);
			g.fillOval(x - radius_piece, y - radius_piece, radius_piece * 2, radius_piece * 2);
		}

	}

	/**
	 * Seteaza coordonatele punctului de pe board-ul grafic (de pe JPanel)
	 *
	 * @param x - abscisa
	 * @param y - ordonata
	 */
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Seteaza coordonatele punctului de pe board-ul logic / de pe matrice (din clasa Board)
	 *
	 * @param line - linia
	 * @param column - coloana
	 */
	public void setMatrix2Did (int line, int column) {
		this.line = line;
		this.column = column;
	}

	/**
	 * 
	 * @return abscisa de pe board-ul grafic
	 */
	public int x() {
		return x;
	}

	/**
	 * 
	 * @return ordonata de pe board-ul grafic
	 */
	public int y() {
		return y;
	}

	/**
	 * 
	 * @return linia de pe board-ul logic
	 */
	public int getLine() {
		return line;
	}

	/**
	 * 
	 * @return coloana de pe board-ul logic
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * 
	 * @param flag valoare pe care o va lua hovering
	 */
	public void setHovering (boolean flag) {
		hovering = flag;
	}

	/**
	 * 
	 * @return
	 * true daca mouse-ul este deasupra punctului
	 * false daca mouse-ul nu este deasupra punctului
	 * (se seteaza de mana daca punctul este hovered - prin setHovering)
	 */
	public boolean getHovering () {
		return hovering;
	}

	/**
	 * Seteaza raza cercului care va fi desenat ca pozitie libera
	 * 
	 * @param radius - raza
	 */
	public void setRadiusEmpty (int radius) {
		this.radius_empty = radius;
	}

	/**
	 * 
	 * @return - raza cercului desenat ca pozitie libera
	 */
	public int getRadiusEmpty () {
		return radius_empty;
	}

	/**
	 * Seteaza raza cercului care semnaleaza ca mouse-ul este deaspura
	 * unui punct.
	 * 
	 * @param radius - raza
	 */
	public void setRadiusHovering (int radius) {
		this.radius_hovering = radius;
	}

	/**
	 * 
	 * @return - raza cercului care semnaleaza hover-ul cu mouse-ul
	 */
	public int getRadiusHovering () {
		return radius_hovering;
	}

	/**
	 * Seteaza raza cercului care este desenat ca piesa
	 * 
	 * @param radius - raza
	 */
	public void setRadiusPiece (int radius) {
		this.radius_piece = radius;
	}

	/**
	 * 
	 * @return - raza cercului care este desenat ca piesa
	 */
	public int getRadiusPiece () {
		return radius_piece;
	}

	/**
	 * Seteaza MARK-ul pentru a sti ce piesa va fi desenata (de paintComponent)
	 * 
	 * @param piece - piesa (MARK-ul)
	 */
	public void setMark (MARK piece) {
		mark = piece;
	}

	/**
	 * Seteaza MARK.EMPTY pentru punctul curent.
	 * 
	 */
	public void unsetMark () {
		mark = MARK.EMPTY;
	}

	/**
	 * 
	 * @return - piesa (MARK-ul) care este desenat
	 */
	public MARK getMark () {
		return mark;
	}

	/**
	 * Seteaza culorile pieselor jucatorilor.
	 * 
	 * @param playerBlackColor - culoarea pentru PLAYER.BLACK
	 * @param playerWhiteColor - culoarea pentru PLAYER.WHITE
	 */
	public void setColors (Color playerBlackColor, Color playerWhiteColor) {

		this.playerBlackColor = playerBlackColor;
		this.playerWhiteColor = playerWhiteColor;

	}


	@Override
	public String toString () {

		String s = "";
		s += "Coord: (" + x + ", " + y + "), ";
		s += "Board: (" + line + ", " + column + ")";
		return s;

	}

}
