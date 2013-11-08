package graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

import structs.Pair;
import enums.MARK;
import enums.PLAYER;
import gamelogic.Board;
import gamelogic.Bot;

public class GameplayPanelMouseListeners implements MouseListener, MouseMotionListener {

	LinkedList<PiecePoint> points;
	GameplayPanel panel;
	PLAYER player; // player-ul controlat de la calculatorul curent (human)
	PLAYER opponent; // player-ul controlat de oponent
	PLAYER next; // player-ul care este la mutare
	GameFlowManager game;

	public GameplayPanelMouseListeners (GameplayPanel panel,
			LinkedList<PiecePoint> points, PLAYER player, Bot bot) {

		this.points = points;
		this.panel = panel;
		this.player = player;

		if (player == PLAYER.BLACK) {
			opponent = PLAYER.WHITE;
		} else {
			opponent = PLAYER.BLACK;
		}

		game = new GameFlowManager(9, 9, points, new Board(), bot);
		next = player;

	}

	@Override
	public void mouseClicked(MouseEvent e) {

		MARK markPlayer, markOpponent;
		if (player == PLAYER.BLACK) {
			markPlayer = MARK.BLACK;
			markOpponent = MARK.WHITE;
		}
		else {
			markPlayer = MARK.WHITE;
			markOpponent = MARK.BLACK;
		}

		int x, y;
		x = e.getX();
		y = e.getY();

		for (PiecePoint pt: points) {

			int px, py;
			px = pt.x();
			py = pt.y();

			if (px - 30 <= x && px + 30 >= x && py - 30 <= y && py + 30 >= y) {

				if (game.checkLineCompletion() && next == player) {
					pt.unsetMark();
					game.removePiece(pt.getLine(), pt.getColumn());
					game.resetLineCompleted();
					panel.repaint();
					panel.removeMouseListeners();
					next = opponent;
				}

				if (next == player) {

					if (game.getPlayerPiecesLeft(player) > 0) {
						pt.setMark(markPlayer);
						game.putPiece(pt.getLine(), pt.getColumn(), player);
					}

					game.printBoard();

					if (game.checkLineCompletion()) {
						next = player;
					} else {
						next = opponent;
						panel.removeMouseListeners();
					}

					for (PiecePoint p: points) {
						p.setHovering(false);
					}

					panel.repaint();
				}

				if (next == opponent) {

					if (game.getOpponentPiecesLeft(opponent) > 0) {
						Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> move = game.doBotMove();
						Pair<Integer, Integer> to = move.getSecond();

						for (PiecePoint p: points) {
							if (p.getLine() == to.getFirst() && p.getColumn() == to.getSecond()) {
								p.setMark(markOpponent);
								panel.repaint();
							}
						}

						if (game.checkLineCompletion()) {
							Pair<Integer, Integer> removed =
									game.removePointAfterCompletingLine(player);
							game.resetLineCompleted();
							for (PiecePoint p: points) {
								if (p.getLine() == removed.getFirst() &&
										p.getColumn() == removed.getSecond()) {
									p.unsetMark();
									panel.repaint();
								}
							}
						}

						next = player;
						panel.addMouseListeners();

					}
				}

			}

		}

		panel.repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

		int x, y;
		x = arg0.getX();
		y = arg0.getY();

		for (PiecePoint pt: points) {

			int px, py;
			px = pt.x();
			py = pt.y();

			if (px - 30 <= x && px + 30 >= x && py - 30 <= y && py + 30 >= y) {
				pt.setHovering(true);
			}
			else {
				pt.setHovering(false);
			}

		}

		panel.repaint();

	}




}
