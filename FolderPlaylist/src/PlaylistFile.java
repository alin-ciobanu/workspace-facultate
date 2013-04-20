import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class PlaylistFile extends JFrame implements ActionListener {

	JFileChooser browse;
	
	public PlaylistFile () {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		browse = new JFileChooser("D:");
		browse.setAcceptAllFileFilterUsed(false);
		browse.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		browse.addActionListener(this);
		browse.setApproveButtonText("Create m3u playlist for this folder");
		
		this.add(browse);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new PlaylistFile();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selection = e.getActionCommand();
		if (selection.equals("ApproveSelection")) {
			File folder;
			folder = browse.getSelectedFile();
			String[] list;
			
			list = folder.list(new FilenameFilter() {
				
				@Override
				public boolean accept(File arg0, String arg1) {
					return (arg1.endsWith(".flac") || arg1.endsWith(".FLAC") || arg1.endsWith(".mp3")
							|| arg1.endsWith(".MP3") || arg1.endsWith(".WAV") || arg1.endsWith(".wav")
							|| arg1.endsWith(".APE") || arg1.endsWith(".ape"));
				}
			});

			RandomAccessFile out = null;
			try {
				out = new RandomAccessFile(folder.getAbsolutePath() + "\\playlist.m3u", "rw");
			} catch (FileNotFoundException e1) {
				JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
				System.exit(-1);
			}
			for (int i=0; i<list.length; i++) {
				try {
					out.writeBytes(list[i] + '\n');
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
					System.exit(-1);
				}
			}
			try {
				out.close();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(this, "An error occured. Please try again.");
				System.exit(-1);
			}
			JOptionPane.showMessageDialog(this, "m3u playlist was successfully created.");
			System.exit(0);
		}
		if (selection.equals("CancelSelection")) {
			System.exit(1);
		}
	}

}
