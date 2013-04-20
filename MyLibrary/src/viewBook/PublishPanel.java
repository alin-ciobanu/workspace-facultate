package viewBook;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import element.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.StyledEditorKit.FontSizeAction;

import savedBooks.BookXMLParserReader;


public class PublishPanel extends AbstractViewMode implements ActionListener {

	JPanel publishPanel;
	JScrollPane readingPanel;
	JPanel optionsPanel;

	JTextArea readingArea; // aici se scrie cartea
	
	JTextField findText;
	JButton find;
	JComboBox<String> fontsMenu;
	ArrayList<Font> fonts;
	JComboBox<Integer> dimensionsMenu;
	int textDimension;

	public PublishPanel (Book book) {

		publishPanel = new JPanel();
		optionsPanel = new JPanel();
		
		textDimension = 15;
		
		readingArea = new JTextArea("");
		readingArea.setFont(new Font (Font.MONOSPACED, Font.PLAIN, textDimension));
		readingArea.setLineWrap(true);
		readingPanel = new JScrollPane(readingArea);

		findText = new JTextField();
		findText.setPreferredSize(new Dimension (200, 20));
		find = new JButton("Find");

		fonts = new ArrayList<Font>();
		fontsMenu = new JComboBox<String>();
		fonts.add(new Font(Font.MONOSPACED, Font.PLAIN, textDimension));
		fontsMenu.addItem("Monospaced");
		fonts.add(new Font(Font.SANS_SERIF, Font.PLAIN, textDimension));
		fontsMenu.addItem("Sans Serif");
		fonts.add(new Font(Font.SERIF, Font.PLAIN, textDimension));
		fontsMenu.addItem("Serif");
		
		dimensionsMenu = new JComboBox<Integer>();
		dimensionsMenu.addItem(10);
		dimensionsMenu.addItem(12);
		dimensionsMenu.addItem(15);
		dimensionsMenu.addItem(18);
		dimensionsMenu.addItem(20);
		dimensionsMenu.addItem(22);
		dimensionsMenu.addItem(28);
		dimensionsMenu.addItem(30);
		dimensionsMenu.setSelectedIndex(2);
		
		fontsMenu.addActionListener(this);
		dimensionsMenu.addActionListener(this);
		
		optionsPanel.setLayout(new FlowLayout());
		optionsPanel.setBackground(Color.white);
		optionsPanel.add(findText);
		optionsPanel.add(find);
		optionsPanel.add(new JLabel("  "));
		optionsPanel.add(new JLabel("Font"));
		optionsPanel.add(fontsMenu);
		optionsPanel.add(new JLabel("  "));
		optionsPanel.add(dimensionsMenu);
		
		find.addActionListener(this);
		
		/*
		 * scriere carte
		 */
		readingArea.setEditable(false);
		readingArea.setText(book.getTitle() + "\n" + "Written by " + book.getAuthor() + "\nISBN: " + book.getISBN() + "\n\n\n");
		int i = 0;
		while (i < book.size()) {
			Chapter currentChapter = book.getChapter(i);
			int chapterNumberHumanReadingFormat = i + 1;
			readingArea.append("Chapter " + chapterNumberHumanReadingFormat + " - " + currentChapter.getTitle() + '\n' + '\n');
			int indexParagraph = 0;
			while (indexParagraph < currentChapter.size()) {
				readingArea.append("\t");
				Paragraph currentParagraph = currentChapter.getParagraph(indexParagraph);
				int indexSentence = 0;
				while (indexSentence < currentParagraph.size()) {
					Sentence currentSentence = currentParagraph.get(indexSentence);
					readingArea.append(currentSentence.getSentence() + " ");
					indexSentence++;
				}
				readingArea.append("\n");
				indexParagraph++;
			}
			readingArea.append("\n\n");
			i++;
		}
		
		
		readingPanel.setPreferredSize(new Dimension(1200, 600));

		publishPanel.setLayout(new BoxLayout(publishPanel, BoxLayout.Y_AXIS));
		publishPanel.add(readingPanel);
		publishPanel.add(optionsPanel);

		
//		// de sters
//		JFrame fer = new JFrame();
//		fer.setLayout(new FlowLayout());
//		fer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		fer.add(publishPanel);
//		fer.pack();
//		fer.setVisible(true);
	
	}
	
	@Override
	JPanel authorPanel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	JPanel publishPanel() {
		return publishPanel;
	}
	
	public static void main (String[] args) {
		
		Book book = new Book();
		BookXMLParserReader read = new BookXMLParserReader();
		try {
			book = read.readBookFromFile("stele");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new PublishPanel(book);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		
		if (s.equals("Find")) {
			String textToFind = "";
			textToFind = findText.getText();
			Highlighter h = readingArea.getHighlighter();
			h.removeAllHighlights();
			String text = readingArea.getText();
			int position;
			int lookFrom = 0; // pozitia de pe care se cauta textul, argument pentru String.indexOf
			while ((position = text.toLowerCase().indexOf(textToFind.toLowerCase(), lookFrom)) >= 0) {
				lookFrom = position + 1;
				try {
					h.addHighlight(position, position + textToFind.length(), DefaultHighlighter.DefaultPainter);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		if (e.getSource().equals(fontsMenu)) {
			int i = fontsMenu.getSelectedIndex();
			readingArea.setFont(fonts.get(i).deriveFont((float) textDimension));
		}
		
		if (e.getSource().equals(dimensionsMenu)) {
			textDimension = (int) dimensionsMenu.getSelectedItem();
			System.out.println(textDimension);
			readingArea.setFont(fonts.get(fontsMenu.getSelectedIndex()).deriveFont( (float) textDimension));
		}
		
	}

}
