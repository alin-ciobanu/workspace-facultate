import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Fereastra extends JFrame implements ActionListener {

	JTextField ecran;
	JButton[] butoanePanel1;
	JButton[] butoaneOperatii;
	JButton[] butoaneM;
	JPanel[] panels;
	
	int op1, op2;
	int op2Buf;
	String operator;
	
	public Fereastra () {
		
		op1 = 0;
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(0, 1));
		this.setSize(new Dimension(280, 280));

		panels = new JPanel[4];
		for (int i=0; i<panels.length; i++) {
			panels[i] = new JPanel();
			if (i != 0)
				panels[i].setLayout(new GridLayout(1, 4));
		}

		ecran = new JTextField("");
		ecran.setPreferredSize(new Dimension(200,50));
		panels[0].add(ecran);

		butoanePanel1 = new JButton[3];
		butoaneOperatii = new JButton[4];
		butoaneM = new JButton[4];
		
		for (int i=0; i<butoanePanel1.length; i++)
			butoanePanel1[i] = new JButton();
		butoanePanel1[0].setText("M");
		butoanePanel1[0].setEnabled(false);
		butoanePanel1[1].setText("C");
		butoanePanel1[2].setText("=");

		for (int i=0; i<butoanePanel1.length; i++)
			panels[1].add(butoanePanel1[i]);
		panels[1].add(new JLabel());
		
		for (int i=0; i<butoaneOperatii.length; i++)
			butoaneOperatii[i] = new JButton();
		butoaneOperatii[0].setText("/");
		butoaneOperatii[1].setText("*");
		butoaneOperatii[2].setText("-");
		butoaneOperatii[3].setText("+");
		for (int i=0; i<butoaneOperatii.length; i++)
			panels[2].add(butoaneOperatii[i]);
		
		for (int i=0; i<butoaneM.length; i++)
			butoaneM[i] = new JButton();
		butoaneM[0].setText("MC");
		butoaneM[1].setText("MR");
		butoaneM[2].setText("MS");
		butoaneM[3].setText("M+");
		for (int i=0; i<butoaneM.length; i++)
			panels[3].add(butoaneM[i]);

		
		for (int i=0; i<panels.length; i++)
			this.add(panels[i]);
		
		for (int i=0; i<butoanePanel1.length; i++)
			butoanePanel1[i].addActionListener(this);
		
		for (int i=0; i<butoaneOperatii.length; i++)
			butoaneOperatii[i].addActionListener(this);
		
		for (int i=0; i<butoaneM.length; i++)
			butoaneM[i].addActionListener(this);

//		this.pack();
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Fereastra();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		if (s.equals("+")) {
			operator = "";
			operator = "+";
			op1 = Integer.parseInt(ecran.getText());
			ecran.setText("");
			ecran.requestFocus();
		}

		if (s.equals("*")) {
			operator = "";
			operator = "*";
			op1 = Integer.parseInt(ecran.getText());
			ecran.setText("");
			ecran.requestFocus();
		}

		if (s.equals("=")) {
			if (operator.equals("+")) {
				op2 = Integer.parseInt(ecran.getText());
				op1 = op1 + op2;
				ecran.setText("" + op1);
			}

			if (operator.equals("*")) {
				op2 = Integer.parseInt(ecran.getText());
				op1 = op1 * op2;
				ecran.setText("" + op1);
			}
		}
		

	}

}
