import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;


public class TextFieldP2 extends JFrame implements ActionListener, KeyListener{

	TextField tf = new TextField();
	String tasteApasate = "";
	
	public TextFieldP2 () {
		
		this.setSize(600, 400);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		tf.setText("black");
		tf.addKeyListener(this);
		this.add(tf);
		tf.addActionListener(this);
		tf.setForeground(Color.BLACK);
//		this.pack();
		this.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String s = arg0.getActionCommand(); 
		s = s.toUpperCase();
		if (s.equals("RED"))
			tf.setForeground(Color.RED);
		if (s.equals("BLUE"))
			tf.setForeground(Color.BLUE);
		if (s.equals("ORANGE"))
			tf.setForeground(Color.ORANGE);
		else
			tf.setForeground(Color.BLACK);
	}
	
	public static void main(String[] args) {
		TextFieldP2 t = new TextFieldP2();		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		char ch = arg0.getKeyChar();
		if (Character.isAlphabetic(ch))
			tasteApasate = tasteApasate + Character.toUpperCase(ch);
		System.out.println(ch);
		System.out.println(tasteApasate);
		if (tasteApasate.contains("RED")) {
			tf.setForeground(Color.RED);
			tasteApasate = "";
		}
		if (tasteApasate.contains("BLUE")) {
			tf.setForeground(Color.BLUE);
			tasteApasate = "";
		}
		if (tasteApasate.contains("ORANGE")) {
			tf.setForeground(Color.ORANGE);
			tasteApasate = "";
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
