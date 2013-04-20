import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;


import javax.swing.Timer;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.SpringLayout;
import javax.swing.border.Border;




class Fereastra extends JFrame{
        /**
         * 
         */
        public static Vector puncte = new Vector();
        //static LinkedList lst = new LinkedList();
        int[] last = {0,0};
        int k = 0;
        public static Vector bonus = new Vector();
        
        private static final long serialVersionUID = 1L;


        public Fereastra(){
                Desen des = new Desen();
                final JLabel label = new JLabel("");
                Timer time;
                JPanel panou = new JPanel();
                panou.setPreferredSize(new Dimension(100, 100));
                panou.add(label);
                
                add(des);
                add(panou);
                
                time = new Timer(500, new ActionListener() {
                        
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // TODO Auto-generated method stub
                                
                                        /*Iterator it = puncte.iterator();
                                        while(it.hasNext()){
                                                Link lk = ((Link)it.next());
                                                if(lk.next == null)
                                                {
                                                        lk.x +=last[1];
                                                        lk.y +=last[0];
                                                
                                                }
                                                else{
                                                        lk.x = lk.next.old_x;
                                                        lk.y = lk.next.old_y;
                                                }
                                        
                                        }*/
                                        
                                
                                        ((Link)puncte.get(0)).old_x = ((Link)puncte.get(0)).x;
                                        ((Link)puncte.get(0)).old_y = ((Link)puncte.get(0)).y;
                                        ((Link)puncte.get(0)).x += last[1];
                                        ((Link)puncte.get(0)).y += last[0];
                                        int x = ((Link)puncte.get(0)).x;
                                        int y = ((Link)puncte.get(0)).y;
                                        int max = puncte.size()-1;
                                        label.setText("" + max);
                                        for(int i = 1 ; i < puncte.size();i++){
                                                ((Link)puncte.get(i)).old_x = ((Link)puncte.get(i)).x;
                                                ((Link)puncte.get(i)).old_y = ((Link)puncte.get(i)).y;
                                                ((Link)puncte.get(i)).x = ((Link)puncte.get(i-1)).old_x;
                                                ((Link)puncte.get(i)).y = ((Link)puncte.get(i-1)).old_y;
                                                if(new Rectangle(x,y,10,10).intersects(new Rectangle(((Link)puncte.get(i)).x,((Link)puncte.get(i)).y,10,10))){
                                                        label.setText("Ai piredut");
                                                        ((Timer) e.getSource()).stop();
                                                }
                                                
                                                if(x < 10){
                                                        label.setText("Ai piredut");
                                                        ((Timer) e.getSource()).stop();
                                                }
                                                


                                                
                                        }
                                        for(int i = 0; i < bonus.size();i++){
                                                if(new Rectangle(x,y,10,10).intersects((Rectangle)(bonus.elementAt(i)))){
                                                        bonus.removeElementAt(i);
                                                        puncte.add(new Link(((Link)puncte.get(max)).old_x,((Link)puncte.get(max)).old_y));
                                                        ((Timer) e.getSource()).setDelay(((Timer) e.getSource()).getDelay()-50);
                                                }
                                                
                                        }
                                        repaint();
                                
                        }
                });
                time.start();
                
                Timer time2 = new Timer(500, new ActionListener() {
                        
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // TODO Auto-generated method stub
                                bonus.add(new Rectangle((int)((Math.random()*500)/10)*10,(int)((Math.random()*400)/10)*10,10,10));
                                repaint();
                        }
                });
                time2.start();


                
                addKeyListener(new KeyListener() {
                        
                        @Override
                        public void keyTyped(KeyEvent e) {
                                // TODO Auto-generated method stub
                                
                        }
                        
                        @Override
                        public void keyReleased(KeyEvent e) {
                                // TODO Auto-generated method stub
                                if(e.getKeyText(e.getKeyCode())=="Up"){
                                 //       ((Link)puncte.get(0)).y-=10;
                                        last[0] = -10;
                                        
                                        last[1] = 0;
                                        
                                        repaint();
                                }
                                if(e.getKeyText(e.getKeyCode())=="Down"){
                                //        ((Link)puncte.get(0)).y+=10;
                                        last[0] = 10;
                                        last[1] = 0;
                                        
                                        repaint();
                                }
                                if(e.getKeyText(e.getKeyCode())=="Left"){
                                 //       ((Link)puncte.get(0)).x-=10;
                                        last[0] = 0;
                                        last[1] = -10;
                                        
                                        
                                        repaint();
                                }
                                if(e.getKeyText(e.getKeyCode())=="Right"){
                                   //     ((Link)puncte.get(0)).x+=10;
                                        last[0] = 0;
                                        last[1] = 10;
                                        repaint();
                                        
                                }
                        }
                        
                        @Override
                        public void keyPressed(KeyEvent e) {
                                // TODO Auto-generated method stub
                                //label.setText(e.getKeyText(e.getKeyCode()));
                                
                                        
                        }
                });
                
                puncte.add(new Link(0,0));
                
                setLayout(new FlowLayout());
                
                
                setSize(600,400);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
}


class Desen extends JPanel{
        
        /**
         * 
         */
        private static final long serialVersionUID = 1L;


        Desen(){
                //setForeground(Color.white);
                setBackground(Color.white);
                setForeground(Color.blue);
                setBorder(BorderFactory.createLoweredBevelBorder());
                setSize(500,300);
                setPreferredSize(getSize());
        }
        
        
        
        protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Iterator it = Fereastra.puncte.iterator();
                for(int i = 0; i< Fereastra.bonus.size();i++){
                        Rectangle r = (Rectangle) Fereastra.bonus.elementAt(i);
                        
                         
                        g.drawRect((int)r.getX(), (int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
                }
                while(it.hasNext()){
                        Link li = (Link) it.next();
                        g.drawRect(li.x, li.y, 10, 10);
                        
                }
        }
}

class Main {
	
	public static void main (String[] args) {
		Fereastra fer = new Fereastra(); 
		fer.setVisible(true);
	}
	
}
