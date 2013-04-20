package viewBook;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import savedBooks.BookXMLParserWriter;

import element.*;

public class AuthorPanel extends AbstractViewMode implements ActionListener {

	Book book;

	JPanel authorPanel;
	JScrollPane bookScrollPanel;
	JPanel bookPanel;
	JPanel optionsPanel;
	JPanel ISBNPanel;

	JPanel bookTitlePanel;
	JPanel bookAuthorPanel;

	JTextField bookTitle;
	JTextField ISBN;
	JTextField author;
	JButton changeBookTitle;
	JButton changeISBN;
	JButton changeAuthor;

	ArrayList<Chapter> chapters;
	ArrayList<JPanel> chapterPanels;

	public AuthorPanel (Book book) {

		drawBook(book);

	}

	@Override
	JPanel authorPanel() {
		return authorPanel;
	}

	@Override
	JPanel publishPanel() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void drawBook (Book book) {

		this.book = book;
		
		chapters = new ArrayList<Chapter>();
		chapterPanels = new ArrayList<JPanel>();
		int i = 0;
		while (i < book.size()) {
			chapters.add(book.getChapter(i));
			chapterPanels.add(this.drawChapter(book.getChapter(i), i));
			i++;
		}
		
		authorPanel = new JPanel();
		optionsPanel = new JPanel();
		bookPanel = new JPanel();
		
		authorPanel.setBackground(Color.white);
		optionsPanel.setBackground(Color.white);
		bookPanel.setBackground(Color.white);

		bookTitle = new JTextField(book.getTitle());
		ISBN = new JTextField(book.getISBN());
		author = new JTextField(book.getAuthor());
		changeBookTitle = new JButton("Set book title");
		changeAuthor = new JButton("Set author name");
		changeISBN = new JButton("Set ISBN");
		changeBookTitle.addActionListener(this);
		changeAuthor.addActionListener(this);
		changeISBN.addActionListener(this);

		bookTitlePanel = new JPanel();
		bookTitlePanel.add(new JLabel("Book title: "));
		bookTitlePanel.add(bookTitle);
		bookTitle.setPreferredSize(new Dimension (200, 20));
		bookTitlePanel.add(changeBookTitle);
		bookPanel.add(bookTitlePanel);

		bookAuthorPanel = new JPanel();
		bookAuthorPanel.add(new JLabel("Author: "));
		bookAuthorPanel.add(author);
		author.setPreferredSize(new Dimension(200, 20));
		bookAuthorPanel.add(changeAuthor);
		bookPanel.add(bookAuthorPanel);

		ISBNPanel = new JPanel();
		ISBNPanel.add(new JLabel("ISBN: "));
		ISBNPanel.add(ISBN);
		ISBN.setPreferredSize(new Dimension (200, 20));
		ISBNPanel.add(changeISBN);
		bookPanel.add(ISBNPanel);
		
		bookPanel.setBackground(Color.white);
		
		for (int j=0; j<chapterPanels.size(); j++)
			bookPanel.add(chapterPanels.get(j));

		bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
		bookScrollPanel = new JScrollPane(bookPanel);
		bookScrollPanel.setPreferredSize(new Dimension(1200, 600));
		bookScrollPanel.setBackground(Color.white);

		authorPanel.add(bookScrollPanel);
		
	}

	private JPanel drawChapter (Chapter ch, int index) {
		JPanel chapterPanel = new JPanel();
		JPanel chapterTitle = new JPanel();

		JTextField title = new JTextField(ch.getChapterName());
		title.setPreferredSize(new Dimension(200, 20));
		JButton changeTitle = new JButton("Change");
		changeTitle.addActionListener(this);

		JButton addChapterBefore = new JButton("+ before");
		JButton addChapterAfter = new JButton("+ after");
		JButton deleteChapter = new JButton("- delete current");

		addChapterBefore.addActionListener(this);
		addChapterAfter.addActionListener(this);
		deleteChapter.addActionListener(this);
		
		int realIndex = index + 1;
		chapterTitle.add(new JLabel("Chapter " + realIndex));
		chapterTitle.add(title);
		chapterTitle.add(changeTitle);
		
		chapterTitle.add(addChapterBefore);
		chapterTitle.add(addChapterAfter);
		chapterTitle.add(deleteChapter);
		
		JButton firstParagraph = new JButton("Add first paragraph");
		firstParagraph.addActionListener(this);		

		chapterPanel.add(new JLabel("  "));
		chapterPanel.add(chapterTitle);
		chapterPanel.add(firstParagraph);

		int i = 0;
		while (i < ch.size()) {
			JPanel paragraph = new JPanel();
			paragraph = this.drawParagraph(ch.getParagraph(i));
			chapterPanel.add(paragraph);
			i++;
		}

		chapterPanel.setLayout(new BoxLayout(chapterPanel, BoxLayout.Y_AXIS));
		return chapterPanel;
	}
	
	private JPanel drawParagraph (Paragraph p) {
		JPanel paragraphPanel = new JPanel();
		JButton plus = new JButton("+");
		JButton minus = new JButton("-");
		JTextArea text = new JTextArea();

		plus.addActionListener(this);
		minus.addActionListener(this);
		
		String content = "\t"; // sentences
		int i = 0;
		while (i < p.size()) {
			content = content + p.getStringSentence(i) + " ";
			i++;
		}
		text.setEditable(false);
		text.setText(content);
		text.setLineWrap(true);

		JPanel paragraphContent = new JPanel();
		JPanel paragraphSpace = new JPanel();
	
		paragraphContent.add(minus);
		paragraphContent.add(text);
		paragraphContent.add(plus);
		paragraphContent.setLayout(new BoxLayout(paragraphContent, BoxLayout.X_AXIS));
		
		JLabel space = new JLabel("  ");
		space.setBackground(Color.gray);
		paragraphSpace.add(space);

		paragraphPanel.add(paragraphContent);
		paragraphPanel.add(paragraphSpace);
		
		paragraphPanel.setLayout(new BoxLayout(paragraphPanel, BoxLayout.Y_AXIS));
		return paragraphPanel;
	}

	public Book getBook () {
		return book;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String s = arg0.getActionCommand();
		
		if (s.equals("-")) {
			JButton but = (JButton) arg0.getSource();
			but.setText("delete");
			JPanel pan = (JPanel) but.getParent(); // panelul pe care e desenat contentul paragrafului
			JPanel panP = (JPanel) pan.getParent(); // panelul pe care e desenat paragraful
			JPanel panCh = (JPanel) panP.getParent(); // panelul pe care e desenat chapterul
			JPanel panAll = (JPanel) panCh.getParent(); // panelul pe care sunt desenate toate chapter-urile
// chapter-urile incep de la indexul cu numarul 3 - pe pozitiile 0, 1, 2 sunt bookName, author si ISBN
// putem sa gasim panelurile pe care sunt desenate chapter-urile cu un panAll.getComponent(x), cu x = 3..panAll.getComponentCount ()

			boolean deleted = false;
			for (int i=0; i<chapters.size(); i++) {
				try {
					for (int j=0; j<chapters.get(i).size(); j++) {
						Paragraph p = chapters.get(i).getParagraph(j);
						JPanel paragraphPanel = new JPanel();
						paragraphPanel = this.drawParagraph(p);
						JPanel paragraphContent = (JPanel) paragraphPanel.getComponent(0);
						JTextArea pText = (JTextArea) paragraphContent.getComponent(1);
						JTextArea panText = (JTextArea) pan.getComponent(1);
						if (pText.getText().equals(panText.getText()) && !deleted) {
							deleted = true;
							chapters.get(i).remove(j);
							panP.removeAll();
							if (chapters.get(i).size() == 0) {
								chapters.remove(i); // daca nu mai exista paragrafe in capitol, capitolul se sterge
								panCh.removeAll();
							}
						}
					}
				} catch (IndexOutOfBoundsException e) {
					System.out.println("I got this.");
				}
			}

			// mai jos vom modifica de pe fiecare capitol JLabel-ul pe care e scris titlul capitolelor
			int chapterIndexHuman = 1;
			for (int x=3; x < panAll.getComponentCount(); x++) {
				JPanel chapter = (JPanel) panAll.getComponent(x);
				if (chapter.getComponentCount() != 0) {
					JPanel chapterTitlePanel = (JPanel) chapter.getComponent(1);
					JLabel chapterTitleLabel = (JLabel) chapterTitlePanel.getComponent(0);
					chapterTitleLabel.setText("Chapter " + chapterIndexHuman);
					chapterIndexHuman++;
				}
			}

			String bTitle = book.getTitle();
			String ISBN = book.getISBN();
			String auth = book.getAuthor();
			this.book.clear();
			this.book = new Book(bTitle, auth, ISBN);
			for (int i=0; i<chapters.size(); i++)
				this.book.add(chapters.get(i));
			BookXMLParserWriter write = new BookXMLParserWriter();
			try {
				write.deleteBook(book);
				write.writeBookToFile(book);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (s.equals("+")) {
			JButton but = (JButton) arg0.getSource();
			JPanel pan = (JPanel) but.getParent(); // panelul pe care e desenat contentul paragraful
			JPanel panP = (JPanel) pan.getParent(); // panelul pe care e desenat paragraful (content + spatiu)

			AddNewParagraph newParagraph = new AddNewParagraph();
			Paragraph pNew = new Paragraph();
			pNew = newParagraph.getParagraph();

			if (newParagraph.hasText()) {
				boolean added = false;
				for (int i=0; i<chapters.size(); i++) {
					for (int j=0; j<chapters.get(i).size(); j++) {
						Paragraph p = chapters.get(i).getParagraph(j);
						JPanel paragraphPanel = this.drawParagraph(p);
						JPanel paragraphContents = (JPanel) paragraphPanel.getComponent(0);
						JTextArea pText = (JTextArea) paragraphContents.getComponent(1);
						JTextArea panText = (JTextArea) pan.getComponent(1);
						if (pText.getText().equals(panText.getText()) && !added) {
							chapters.get(i).add(j+1, pNew);
							panP.getParent().add(this.drawParagraph(pNew), j + 4);
							((JDialog)(panP.getParent().getParent().getParent().getParent().getParent().getParent().
				getParent().getParent().getParent().getParent())).pack();
							// da, stiu, nici mie nu imi vine sa cred ce am facut mai sus
							// am scris getParent pana am dat de fereastra. daca nu dadeam pack nu se updata panelul
							added = true;
							String bTitle = book.getTitle();
							String ISBN = book.getISBN();
							String auth = book.getAuthor();
							this.book.clear();
							this.book = new Book(bTitle, auth, ISBN);
							for (int k=0; k<chapters.size(); k++)
								this.book.add(chapters.get(k));
							BookXMLParserWriter write = new BookXMLParserWriter();
							try {
								write.deleteBook(book);
								write.writeBookToFile(book);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}

		}
		
		if (s.equals("Add first paragraph")) {
			
			JButton but = (JButton) arg0.getSource();
			JPanel pan = (JPanel) but.getParent(); // panelul pe care e desenat chapterul
			JPanel chTitlePan = (JPanel) pan.getComponent(1);
			JLabel chNumberLabel = (JLabel) chTitlePan.getComponent(0);
			String chNumber = chNumberLabel.getText();

			AddNewParagraph newParagraph = new AddNewParagraph();
			Paragraph pNew = new Paragraph();
			pNew = newParagraph.getParagraph();

			if (newParagraph.hasText()) {
				JPanel paragraphPanel = drawParagraph(pNew);
				pan.add(paragraphPanel, 3);
				((JDialog)(pan.getParent().getParent().getParent().getParent().getParent().
						getParent().getParent().getParent().getParent())).pack();
				
				int chapterIndex = 1;
				while (chapterIndex <= chapters.size()) {
					if (chNumber.equals("Chapter " + chapterIndex)) {
						chapters.get(chapterIndex-1).add(0, pNew);
					}
					chapterIndex++;
				}
				
				String bTitle = book.getTitle();
				String ISBN = book.getISBN();
				String auth = book.getAuthor();
				this.book.clear();
				this.book = new Book(bTitle, auth, ISBN);
				for (int k=0; k<chapters.size(); k++)
					this.book.add(chapters.get(k));
				BookXMLParserWriter write = new BookXMLParserWriter();
				try {
					write.deleteBook(book);
					write.writeBookToFile(book);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

		if (s.equals("Change")) {
			
			JPanel pan = (JPanel)((JButton) (arg0.getSource())).getParent(); // panelul pe care e desenat titlul capitolului
			String chapterIndex = ((JLabel)(pan.getComponent(0))).getText();
			String chapterName = ((JTextField) (pan.getComponent(1))).getText();
			
			for (int i=0; i<chapters.size(); i++) {
				int humanIndexForChapter = i+1;
				if (("Chapter " + humanIndexForChapter).equals(chapterIndex))
					chapters.get(i).changeChapterName(chapterName);
			}

			String bTitle = book.getTitle();
			String ISBN = book.getISBN();
			String auth = book.getAuthor();
			this.book.clear();
			this.book = new Book(bTitle, auth, ISBN);
			for (int i=0; i<chapters.size(); i++)
				this.book.add(chapters.get(i));
			BookXMLParserWriter write = new BookXMLParserWriter();
			try {
				write.deleteBook(book);
				write.writeBookToFile(book);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		if (s.equals("Set book title")) {
			
			BookXMLParserWriter write = new BookXMLParserWriter();
			write.deleteBook(this.book);
			String title = bookTitle.getText();
			this.book.renameBook(title);
			try {
				write.writeBookToFile(this.book);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		if (s.equals("Set author name")) {
			
			BookXMLParserWriter write = new BookXMLParserWriter();
			write.deleteBook(this.book);
			String authorName = author.getText();
			this.book.renameAuthor(authorName);
			try {
				write.writeBookToFile(this.book);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		if (s.equals("Set ISBN")) {
			
			BookXMLParserWriter write = new BookXMLParserWriter();
			write.deleteBook(this.book);
			String ISBNString = ISBN.getText();
			this.book.changeISBN(ISBNString);
			try {
				write.writeBookToFile(this.book);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (s.equals("+ before")) { // adaugare capitol inainte de capitolul curent
			
			int index = 1;
			JPanel pan = (JPanel) ((JButton) arg0.getSource()).getParent();
			JLabel chapterNumberLabel = (JLabel) pan.getComponent(0);
			String chapterNumber = chapterNumberLabel.getText();

			Chapter ch = new Chapter();
			ch.changeChapterName("Chapter name");
			JPanel newChPanel = new JPanel();
			
// capitolele incep de la indicele 3 pe panelul unde sunt asezate
			JPanel panCh = (JPanel) pan.getParent();
			JPanel panAll = (JPanel) panCh.getParent(); // panelul pe care sunt asezate capitolele

			while (index <= chapters.size()) {
				if (chapterNumber.equals("Chapter " + index)) {
					newChPanel = drawChapter(ch, index - 1);
					chapters.add(index - 1, ch);
					// pentru a adauga un capitol pe panel inaintea capitolului curent, va trebui sa il punem la indexul index+2 
					panAll.add(newChPanel, index+2);
					((JDialog) panAll.getParent().getParent().getParent().getParent().getParent().getParent().getParent()
							.getParent()).pack();
				}
 				index++;
			}

			// mai jos vom modifica de pe fiecare capitol JLabel-ul pe care e scris titlul capitolelor
			int chapterIndexHuman = 1;
			for (int x=3; x < panAll.getComponentCount(); x++) {
				JPanel chapter = (JPanel) panAll.getComponent(x);
				if (chapter.getComponentCount() != 0) {
					JPanel chapterTitlePanel = (JPanel) chapter.getComponent(1);
					JLabel chapterTitleLabel = (JLabel) chapterTitlePanel.getComponent(0);
					chapterTitleLabel.setText("Chapter " + chapterIndexHuman);
					chapterIndexHuman++;
				}
			}

			String bTitle = book.getTitle();
			String ISBN = book.getISBN();
			String auth = book.getAuthor();
			this.book.clear();
			this.book = new Book(bTitle, auth, ISBN);
			for (int i=0; i<chapters.size(); i++)
				this.book.add(chapters.get(i));
			BookXMLParserWriter write = new BookXMLParserWriter();
			try {
				write.deleteBook(book);
				write.writeBookToFile(book);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		if (s.equals("+ after")) { // adaugare capitol nou dupa capitolul curent
			
			int index = 1;
			JPanel pan = (JPanel) ((JButton) arg0.getSource()).getParent();
			JLabel chapterNumberLabel = (JLabel) pan.getComponent(0);
			String chapterNumber = chapterNumberLabel.getText();

			Chapter ch = new Chapter();
			ch.changeChapterName("Chapter name");
			JPanel newChPanel = new JPanel();
			
// capitolele incep de la indicele 3 pe panelul unde sunt asezate
			JPanel panCh = (JPanel) pan.getParent();
			JPanel panAll = (JPanel) panCh.getParent(); // panelul pe care sunt asezate capitolele

			while (index <= chapters.size()) {
				if (chapterNumber.equals("Chapter " + index)) {
					newChPanel = drawChapter(ch, index);
					chapters.add(index, ch);
					// pentru a adauga un capitol pe panel dupa capitolul curent, va trebui sa il punem la indexul index+3 
					panAll.add(newChPanel, index+3);
					((JDialog) panAll.getParent().getParent().getParent().getParent().getParent().getParent().getParent()
							.getParent()).pack();
				}
 				index++;
			}

			// mai jos vom modifica de pe fiecare capitol JLabel-ul pe care e scris titlul capitolelor
			int chapterIndexHuman = 1;
			for (int x=3; x < panAll.getComponentCount(); x++) {
				JPanel chapter = (JPanel) panAll.getComponent(x);
				if (chapter.getComponentCount() != 0) {
					JPanel chapterTitlePanel = (JPanel) chapter.getComponent(1);
					JLabel chapterTitleLabel = (JLabel) chapterTitlePanel.getComponent(0);
					chapterTitleLabel.setText("Chapter " + chapterIndexHuman);
					chapterIndexHuman++;
				}
			}

			String bTitle = book.getTitle();
			String ISBN = book.getISBN();
			String auth = book.getAuthor();
			this.book.clear();
			this.book = new Book(bTitle, auth, ISBN);
			for (int i=0; i<chapters.size(); i++)
				this.book.add(chapters.get(i));
			BookXMLParserWriter write = new BookXMLParserWriter();
			try {
				write.deleteBook(book);
				write.writeBookToFile(book);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		if (s.equals("- delete current")) { // adaugare capitol nou dupa capitolul curent

			int index = 1;
			JPanel pan = (JPanel) ((JButton) arg0.getSource()).getParent();
			JLabel chapterNumberLabel = (JLabel) pan.getComponent(0);
			String chapterNumber = chapterNumberLabel.getText();
			
// capitolele incep de la indicele 3 pe panelul unde sunt asezate
			JPanel panCh = (JPanel) pan.getParent();
			JPanel panAll = (JPanel) panCh.getParent(); // panelul pe care sunt asezate capitolele

			while (index <= chapters.size()) {
				if (chapterNumber.equals("Chapter " + index)) {
					chapters.remove(index-1);					
					panCh.removeAll();
				}
 				index++;
			}

			// mai jos vom modifica de pe fiecare capitol JLabel-ul pe care e scris titlul capitolelor
			int chapterIndexHuman = 1;
			for (int x=3; x < panAll.getComponentCount(); x++) {
				JPanel chapter = (JPanel) panAll.getComponent(x);
				if (chapter.getComponentCount() != 0) {
					JPanel chapterTitlePanel = (JPanel) chapter.getComponent(1);
					JLabel chapterTitleLabel = (JLabel) chapterTitlePanel.getComponent(0);
					chapterTitleLabel.setText("Chapter " + chapterIndexHuman);
					chapterIndexHuman++;
				}
			}

			String bTitle = book.getTitle();
			String ISBN = book.getISBN();
			String auth = book.getAuthor();
			this.book.clear();
			this.book = new Book(bTitle, auth, ISBN);
			for (int i=0; i<chapters.size(); i++)
				this.book.add(chapters.get(i));
			BookXMLParserWriter write = new BookXMLParserWriter();
			try {
				write.deleteBook(book);
				write.writeBookToFile(book);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
