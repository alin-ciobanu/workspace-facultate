
package viewBook;

import element.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import savedBooks.BookXMLParserReader;
import savedBooks.BookXMLParserWriter;


public class ViewLibrary extends JFrame implements ActionListener {

	JPanel mainPanel;
	
	JLabel greetingText;
	JList<Book> books;
	DefaultListModel<Book> booksListModel;
	JTextField longestBook;
	JTextArea longestParagraph;

	JButton addNewLibrary;
	JButton addNewBook;
	JButton renameBook;
	JButton deleteBook;
	JButton deleteLibrary;
	JButton viewBook;

	JPanel booksListPanel;
	JScrollPane booksListScrollPane;

	JScrollPane longestBookScrollPane;
	JScrollPane longestParagraphScrollPane;
	JPanel firstPanelWithButtons; // 3 butoane din cele 6
	JPanel secondPanelWithButtons; // celelalte 3 butoane din cele 6
	JPanel contentAndButtonsPanel; // cu BoxLayout
	JPanel buttonsPanel;
	
	File folder; // folderul unde se salveaza fisierele de care avem nevoie pentru a retine cartile

	BookXMLParserWriter bookWriter;
	BookXMLParserReader bookReader;

	public ViewLibrary () {
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());

		
		/*
		 *  initializare elemente grafice
		 */
		mainPanel = new JPanel(new GridLayout (1, 2));
		
		greetingText = new JLabel("Welcome to your virtual library.");
		greetingText.setForeground(Color.DARK_GRAY);

		addNewLibrary = new JButton("Add library");
		addNewBook = new JButton("Add book");
		renameBook = new JButton("Rename book");
		viewBook = new JButton("View book");
		deleteLibrary = new JButton("Delete library");
		deleteBook = new JButton("Delete book");

		booksListModel = new DefaultListModel<Book>();
		books = new JList<Book>(booksListModel);
		
		longestBook = new JTextField();
		longestBook.setEditable(false);
		longestBook.setBackground(Color.white);
		
		longestParagraph = new JTextArea();
		longestParagraph.setEditable(false);
		longestParagraph.setBackground(Color.white);

		String folderName = "./books/";
		folder = new File(folderName);
		bookWriter = new BookXMLParserWriter(folderName);
		bookReader = new BookXMLParserReader(folderName);
		
		if (folder.exists()) { // daca exista o biblioteca
			addNewLibrary.setEnabled(false);
			String[] bookFiles = folder.list();
			for (int i=0; i<bookFiles.length; i++) {
				Book newBook = new Book();
				try {
					newBook = bookReader.readBookFromFile(bookFiles[i]);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showConfirmDialog(this, "Error reading from file.");
				}
				booksListModel.addElement(newBook);
			}
		}
		else {
			addNewLibrary.setEnabled(true);
			deleteLibrary.setEnabled(false);
			addNewBook.setEnabled(false);
			renameBook.setEnabled(false);
			deleteBook.setEnabled(false);
			viewBook.setEnabled(false);
		}
		Book longBook = this.getLongestBook(booksListModel);
		longestBook.setText(longBook.getTitle());
		longestBook.setForeground(Color.blue);
		longestBook.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));

		Paragraph longParagraph = this.getLongestParagraph(booksListModel);
		if (longParagraph != null) {
			for (int i=0; i < longParagraph.size(); i++)
				longestParagraph.append(longParagraph.getStringSentence(i) + " ");
		}

		booksListPanel = new JPanel();
		booksListScrollPane = new JScrollPane(books);
		booksListScrollPane.setPreferredSize(new Dimension(200, 500));
		booksListPanel.add(greetingText);
		booksListPanel.add(new JLabel("  "));
		booksListPanel.add(new JLabel("  "));
		booksListPanel.add(new JLabel("List of books"));
		booksListPanel.add(booksListScrollPane);
		booksListPanel.setLayout(new BoxLayout(booksListPanel, BoxLayout.Y_AXIS));
		mainPanel.add(booksListPanel);

		contentAndButtonsPanel = new JPanel();

		longestBookScrollPane = new JScrollPane(longestBook);
		longestBookScrollPane.setPreferredSize(new Dimension(200, 40));
		contentAndButtonsPanel.add(new JLabel("The longest book of the library is"));
		contentAndButtonsPanel.add(longestBookScrollPane);

		contentAndButtonsPanel.add(new JLabel("  "));
		contentAndButtonsPanel.add(new JLabel("  "));

		longestParagraphScrollPane = new JScrollPane(longestParagraph);
		longestParagraph.setLineWrap(true);
		longestParagraphScrollPane.setPreferredSize(new Dimension(400, 40));
		contentAndButtonsPanel.add(new JLabel("The longest paragraph of the library is"));
		contentAndButtonsPanel.add(longestParagraphScrollPane);

		contentAndButtonsPanel.add(new JLabel("  "));
		contentAndButtonsPanel.add(new JLabel("  "));

		firstPanelWithButtons = new JPanel();
		secondPanelWithButtons = new JPanel();
		buttonsPanel = new JPanel();
		firstPanelWithButtons.add(addNewBook);
		firstPanelWithButtons.add(renameBook);
		firstPanelWithButtons.add(deleteBook);
		secondPanelWithButtons.add(viewBook);
		secondPanelWithButtons.add(deleteLibrary);
		secondPanelWithButtons.add(addNewLibrary);
		
		buttonsPanel.add(firstPanelWithButtons);
		buttonsPanel.add(secondPanelWithButtons);
		contentAndButtonsPanel.add(buttonsPanel);
		
		contentAndButtonsPanel.setLayout(new BoxLayout(contentAndButtonsPanel, BoxLayout.Y_AXIS));

		mainPanel.add(contentAndButtonsPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		/*
		 * adaugare listeneri
		 */
		addNewBook.addActionListener(this);
		renameBook.addActionListener(this);
		addNewLibrary.addActionListener(this);
		deleteBook.addActionListener(this);
		deleteLibrary.addActionListener(this);
		viewBook.addActionListener(this);


		this.add(mainPanel); // adaugare panel principal la fereastra

		this.pack();
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String s = arg0.getActionCommand();

		if (s.equals("Add library")) {
			addNewLibrary.setEnabled(false);
			folder.mkdir();
			addNewBook.setEnabled(true);
			deleteBook.setEnabled(true);
			viewBook.setEnabled(true);
			renameBook.setEnabled(true);
			deleteLibrary.setEnabled(true);
		}
		
		if (s.equals("Add book")) {
			String title = JOptionPane.showInputDialog("You are about to add a new book", 
					"Enter the title");
			if (title != null) { // se adauga doar daca nu s-a apasat cancel
				String author = JOptionPane.showInputDialog("You are about to add a new book", 
						"Enter the author");
				String ISBN = JOptionPane.showInputDialog("You are about to add a new book", 
						"Enter the ISBN");
				Book newBook = new Book(title, author, ISBN);
				try {
					bookWriter.writeBookToFile(newBook);
				} catch (IOException e) {
					// mesaj eroare
					e.printStackTrace();
				}
				booksListModel.addElement(newBook);
			}
		}

		if (s.equals("Rename book")) {
			Book selectedBook = books.getSelectedValue();
			int index = booksListModel.indexOf(selectedBook);
			if (selectedBook != null) {
				String newName = JOptionPane.showInputDialog("Change the name", "Enter the new title");
				if (newName != null) {
					Book oldBook = new Book();
					oldBook = selectedBook;
					bookWriter.deleteBook(oldBook);
					selectedBook.renameBook(newName);
					try {
						bookWriter.writeBookToFile(selectedBook);
						booksListModel.setElementAt(selectedBook, index);
					} catch (IOException e) {
						// afisare mesaj eroare
						e.printStackTrace();
					}
				}
			}
			longestBook.setText(this.getLongestBook(booksListModel).getTitle());
			Paragraph longParagraph = this.getLongestParagraph(booksListModel);
			longestParagraph.setText("");
			if (longParagraph != null) {
				for (int i=0; i < longParagraph.size(); i++)
					longestParagraph.append(longParagraph.getStringSentence(i) + " ");
			}
		}
		
		if (s.equals("Delete book")) {
			Book selectedBook = books.getSelectedValue();
			int index = booksListModel.indexOf(selectedBook);
			if (selectedBook != null) {
				bookWriter.deleteBook(selectedBook);
				booksListModel.remove(index);
			}
			longestBook.setText(this.getLongestBook(booksListModel).getTitle());
			Paragraph longParagraph = this.getLongestParagraph(booksListModel);
			longestParagraph.setText("");
			if (longParagraph != null) {
				for (int i=0; i < longParagraph.size(); i++)
					longestParagraph.append(longParagraph.getStringSentence(i) + " ");
			}
		}
		
		if (s.equals("View book")) {
			Book selectedBook = books.getSelectedValue();
			int index = booksListModel.indexOf(selectedBook);
			if (selectedBook != null) {
				if (selectedBook.size() == 0) {
					Chapter firstChapter = new Chapter();
					firstChapter.changeChapterName("Automatically added chapter");
					selectedBook.add(firstChapter);
				}
				ViewMode bookView = new ViewMode(selectedBook);
				booksListModel.setElementAt(bookView.getBook(), index);
			}
			longestBook.setText(this.getLongestBook(booksListModel).getTitle());
			Paragraph longParagraph = this.getLongestParagraph(booksListModel);
			longestParagraph.setText("");
			if (longParagraph != null) {
				for (int i=0; i < longParagraph.size(); i++)
					longestParagraph.append(longParagraph.getStringSentence(i) + " ");
			}
		}
		
		if (s.equals("Delete library")) {
			if (folder.exists()) {
				File[] files = folder.listFiles();
				for (int i=0; i<files.length; i++)
					files[i].delete();
				folder.delete();
			}
			addNewLibrary.setEnabled(true);
			deleteLibrary.setEnabled(false);
			addNewBook.setEnabled(false);
			viewBook.setEnabled(false);
			renameBook.setEnabled(false);
			deleteBook.setEnabled(false);
	
			booksListModel.removeAllElements();

		}
		
	}
	
	public Book getLongestBook (Library l) {
		int index = 0;
		for (int i=0; i < l.size(); i++) {
			if (l.get(i).size() > l.get(index).size())
				index = i;
		}
		return l.get(index);
	}

	public Book getLongestBook (DefaultListModel<Book> l) {
		int index = 0;
		for (int i=0; i < l.getSize(); i++) {
			if (l.get(i).size() > l.get(index).size()) {
				index = i;
			}
		}
		if (l.size() == 0)
			return new Book();
		return l.get(index);
	}
	
	public Paragraph getLongestParagraph (DefaultListModel<Book> l) {
		int indexBook = 0;
		int indexChapter = 0;
		int indexParagraph = 0;
		if (l.size() == 0)
			return null;
		for (int i=0; i < l.getSize(); i++) {
			for (int j=0; j < l.get(i).size(); j++) {
				for (int k = 0; k < l.get(i).getChapter(j).size(); k++) {
					Paragraph p = new Paragraph();
					p = l.get(i).getChapter(j).getParagraph(k);
					Paragraph oldP = new Paragraph();
					try {
						oldP = l.get(indexBook).getChapter(indexChapter).getParagraph(indexParagraph);
					} catch (IndexOutOfBoundsException e) {
						System.out.println("I got this.");
					}
					if (p.getTotalWordsCount() > oldP.getTotalWordsCount()) {
							indexBook = i;
							indexChapter = j;
							indexParagraph = k;
					}
				}
			}
		}
		if (l.get(indexBook).size() == 0)
			return null;
		else if (l.get(indexBook).getChapter(indexChapter).size() == 0)
			return null;
		return l.get(indexBook).getChapter(indexChapter).getParagraph(indexParagraph);
	}
	
	public static void main (String[] args) {
		
		new ViewLibrary();
		
	}

	
}
