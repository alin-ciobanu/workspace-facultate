package viewBook;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import element.Paragraph;
import element.Sentence;

public class AddNewParagraph extends JDialog implements ActionListener {

	JTextArea textArea;
	JScrollPane scrollPane;
	JButton okButton;
	JPanel panel;
	String pText = "";

	public AddNewParagraph () {
		
		textArea = new JTextArea("Enter you text here.");
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		panel = new JPanel();
		textArea.setLineWrap(true);
		scrollPane = new JScrollPane (textArea);
		panel.add(scrollPane);
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setPreferredSize(new Dimension(600, 300));
		panel.add(okButton);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		this.add(panel);
		this.setLayout(new FlowLayout());
		this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setVisible(true);
		
	}
	
	public boolean hasText () {
		if (pText.equals(""))
			return false;
		else
			return true;
	}
	
	public Paragraph getParagraph () {
		Paragraph p = new Paragraph();
		StringTokenizer tok = new StringTokenizer(pText, ".\n\r\n");
		String word = "";
		while (tok.hasMoreTokens()) {
			word = tok.nextToken();
			Sentence s = new Sentence();
			while (word.startsWith(" "))
				word = word.substring(1, word.length());
			if (word.endsWith("?") || word.endsWith("!") || word.endsWith(" "))
				s.setSentence(word);
			else
				s.setSentence(word + ".");
			p.add(s);
		}
		return p;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if (s.equals("OK")) {
			pText = textArea.getText();
			this.dispose();
		}
	}
	
}
