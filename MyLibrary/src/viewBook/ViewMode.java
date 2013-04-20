package viewBook;

import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import savedBooks.BookXMLParserReader;

import element.Book;

public class ViewMode extends JDialog implements ChangeListener {

	JTabbedPane tabbedPanelView;
	JPanel publishPanel;
	JPanel authorPanel;
	AuthorPanel authPanel;
	PublishPanel publPanel;
	
	Book book;
	
	public ViewMode (Book book) {
		
		this.book = book;

		authPanel = new AuthorPanel(book);
		publPanel = new PublishPanel(book);
		
		authorPanel = authPanel.authorPanel();
		publishPanel = publPanel.publishPanel();
		
		tabbedPanelView = new JTabbedPane();
		
		tabbedPanelView.addTab("Publish mode", publishPanel);
		tabbedPanelView.addTab("Author mode", authorPanel);
		
		tabbedPanelView.addChangeListener(this);

		this.add(tabbedPanelView);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setModal(true);
		this.setVisible(true);
		
	}
	
	public Book getBook () {
		book = authPanel.getBook();
		return book;
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		
		book = authPanel.getBook();
		publishPanel = new PublishPanel(book).publishPanel();
		JTabbedPane pane = (JTabbedPane) arg0.getSource();
		pane.setComponentAt(0, publishPanel);
		
	}
	
}
