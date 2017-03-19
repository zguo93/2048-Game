//This app is developed by Zichang Guo
//It is a practice project (^_^) not for commercial use.
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;
import java.awt.GridLayout;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.GridBagLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.JTextField;

public class MainFunction {
	JFrame mainFrame;
	private JTable table;
	MainFunction(){
		//initialize the application
		mainFrame=new JFrame("Welcom to 2048 Game world");
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500, 500);
		
		
		//set up the panel
		JPanel panel = new JPanel();
		panel.setBackground(Color.PINK);
		panel.setForeground(Color.ORANGE);
		mainFrame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblWelcomeTo = new JLabel("Welcome to 2048 Game");
		lblWelcomeTo.setFont(new Font("ËÎÌו", Font.BOLD, 41));
		lblWelcomeTo.setForeground(new Color(139, 0, 0));
		panel.add(lblWelcomeTo,BorderLayout.NORTH);
		
		//set up the rules
		JTextArea txtrRules = new JTextArea("Rules" 
				+ ": \n Use up down right and left to control\n"
				+ "1. The number will move to the direction you pressed, if two neighbor numbers is the same, they will add up and merge\n"
				+ "2. If one of grid reach 2048, then you win it\n"
				+ "3. If all the grid is filled and unable to solve, then you lose\n");
		txtrRules.setBackground(new Color(219, 112, 147));
		txtrRules.setFont(new Font("Serif", Font.ITALIC, 16));
		txtrRules.setLineWrap(true);
		panel.add(txtrRules, BorderLayout.CENTER);
		
		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
			  {
			    // display/center the jdialog when the button is pressed
					Game game=new Game();
					Thread t=new Thread(game);
					t.start();
			  }
		});
		panel.add(startButton, BorderLayout.SOUTH);

		//startButton.addActionListener(l);
		
	}
	public static void main(String[] args){
		MainFunction m=new MainFunction();
		//m.init();
	}
}
