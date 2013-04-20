import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.*;

import javax.swing.*;


public class NewGame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	JPanel desenHangman;
	JPanel litere;
	ArrayList<JButton> butoaneLitere;
	WordDatabase db;
	Color bg; // background default pentru butoane
	boolean youCanTry;
	
	JLabel timeLabel;
	JLabel[] timeRemaining;

	ArrayList<Integer> listOfUsedWords; // lista indecsilor cuvintelor folosite - // vezi clasa RandomizedWord
	
	char ch; // litera apasata prin mouse

	JLabel wordToBeGuessedPanel; // labelul pe care punem cuvantul in curs de ghicire	
	JLabel omSpanzurat; // labelul in care se va spanzura omul (desen)

	public NewGame () { // constructor
		
		db = new WordDatabase(); // baza de date care contine cuvintele
		listOfUsedWords = new ArrayList<Integer>();

		this.setSize(600, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(1, 2));
		this.setTitle("You are playing Hangman.");
		this.setResizable(false);
		
		desenHangman = new JPanel(); // panel unde vom spanzura omul :) sau nu
		litere = new JPanel(); // panel pentru butoanele cu litere
							// eventual si pentru "Exit to main menu"

		desenHangman.setLayout(new BorderLayout()); // pe panelul asta vom desena omul spanzurat
		litere.setLayout(new GridLayout(2, 1)); // pe panelul asta se vor pune butoanele cu litere, iar sub ele
						// vom pune cuvantul care trebuie ghicit

		JPanel butoane = new JPanel(); // panelul de butoane
		JPanel cuvant = new JPanel(new GridLayout(5,1)); // panelul unde se va pune cuvantul care trebuie ghicit
		
		butoane.setBackground(Color.WHITE);
		
		litere.add(cuvant, BorderLayout.WEST);
		litere.add(butoane, BorderLayout.SOUTH);
		
		wordToBeGuessedPanel = new JLabel("");
//		wordToBeGuessedPanel.setLayout();
		wordToBeGuessedPanel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
		
		
		cuvant.add(wordToBeGuessedPanel);
		timeLabel = new JLabel();
		timeRemaining = new JLabel[2];
		for (int i=0; i<=1; i++)
			timeRemaining[i] = new JLabel();
		timeRemaining[1].setForeground(Color.RED);
		cuvant.add(timeLabel);
		cuvant.add(timeRemaining[0]); // Aici scrie "Remaining seconds: " sau ceva de genul
		cuvant.add(timeRemaining[1]); // Aici sunt scrise secundele ramase
		timeRemaining[1].setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
		cuvant.setBackground(Color.WHITE);
		
		desenHangman.setBackground(Color.WHITE);
		litere.setBackground(Color.WHITE);
		
		butoaneLitere = new ArrayList<JButton>();
		int j = 0;
		for (char i='A'; i<='Z'; i++) {
			butoaneLitere.add(new JButton(""+i));  // adaugare buton la vector
			butoane.add(butoaneLitere.get(j)); // adaugam butoanele litere pe panel-ul de litere
			j++;
		}
		bg = butoaneLitere.get(0).getBackground(); // background-ul unui buton neapasat
		
		this.add(desenHangman);
		this.add(litere);

		omSpanzurat = new JLabel (new ImageIcon(getClass().getResource("/hangman0.png")));
		desenHangman.add(omSpanzurat);
		
		this.setVisible(true);
		
		newGame();
		
	} // inchidere constructor


	public void newGame () {
		
		while (true) {
			
			timeLabel.setText("");
			youCanTry = false;
			ch = '@';

			for (int i=0; i<butoaneLitere.size(); i++) {
				butoaneLitere.get(i).setBackground(bg);
				butoaneLitere.get(i).addActionListener(this);
			}
			
			RandomizedWord newWord = new RandomizedWord();
			if (listOfUsedWords.size() == db.size())
				listOfUsedWords.clear();
			boolean searchSuitableWord = true;
			boolean selectRandomWord = true;
			int k = 0;
			while (searchSuitableWord) {
				if (selectRandomWord)
					newWord = db.getRandomWord(-1); // vezi WordDatabase.getRandomWord(int) si RandomizedWord
				else {
					newWord = db.getRandomWord(k++); // aici e neterminat
				}
				if (listOfUsedWords.contains(newWord.getIndex())) {
					selectRandomWord = false;
				}
				else {
					listOfUsedWords.add(newWord.getIndex());
//					Collections.sort(listOfUsedWords);
					searchSuitableWord = false;
				}
			}
			Cuvant word = new Cuvant(newWord.getWord());
			
			wordToBeGuessedPanel.setText(word.toString());

			boolean guessed = false;
			
			timeRemaining[0].setText("Remainging seconds: ");
			timeRemaining[1].setText("60");
			long startTime = System.currentTimeMillis();

			while (!guessed) {
				
				if (youCanTry)
					if (ch >= 'A' && ch <= 'Z')
						word.tryCharacter(ch);

				omSpanzurat.setIcon(new ImageIcon(getClass().getResource("/hangman" + word.getFaultsNo() + ".png")));

				wordToBeGuessedPanel.setText(word.toString());

				timeRemaining[1].setText("" + (int)(60 - ((System.currentTimeMillis() - startTime)/1000)));

				if (word.win()) {
									
					timeRemaining[0].setText("");
					timeRemaining[1].setText("");
					
					long time = System.currentTimeMillis() - startTime;
					float fTime = (float) time;
					wordToBeGuessedPanel.setText(word.toString());
					timeLabel.setText("You win. The time you needed was " + fTime/1000 + " seconds.");
					
					for (int i=0; i<butoaneLitere.size(); i++)
						butoaneLitere.get(i).removeActionListener(this);

					int selection = JOptionPane.showConfirmDialog(new JFrame(), "Would you like to play again?", "You win.", JOptionPane.YES_NO_OPTION);
					if (selection == JOptionPane.YES_OPTION)
						guessed = true;
					if (selection == JOptionPane.NO_OPTION) {
						JOptionPane.showMessageDialog(new JFrame(), "Thanks for playing.", "Game ended.", JOptionPane.PLAIN_MESSAGE);
						System.exit(0);
					}
				}

				else {
					if (word.gameOver(System.currentTimeMillis() - startTime)) {
	
						timeRemaining[0].setText("");
						timeRemaining[1].setText("");
						
						if (word.getFaultsNo() < 7)
							timeLabel.setText("You lose. Your time has expired.");

						for (int i=0; i<butoaneLitere.size(); i++)
							butoaneLitere.get(i).removeActionListener(this);

						wordToBeGuessedPanel.setText(word.getFullWord());
						wordToBeGuessedPanel.setForeground(Color.RED);

						int selection = JOptionPane.showConfirmDialog(new JFrame(), "Would you like to play again?", "You lose.", JOptionPane.YES_NO_OPTION);
						if (selection == JOptionPane.YES_OPTION) {
							guessed = true;
							wordToBeGuessedPanel.setForeground(Color.BLACK);
						}
						if (selection == JOptionPane.NO_OPTION) {
							JOptionPane.showMessageDialog(new JFrame(), "Thanks for playing.", "Game ended.", JOptionPane.PLAIN_MESSAGE);
							System.exit(0);
						}
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		String s = arg0.getActionCommand();
		
		if (s.length() == 1) {
			youCanTry = true;
			ch = s.charAt(0);
			((JButton) arg0.getSource()).setBackground(Color.LIGHT_GRAY);
		}
		
	}


	public static void main (String[] args) {

		new NewGame();

	}

}