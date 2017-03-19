//This app is developed by Zichang Guo
//It is a practice project (^_^) not for commercial use.
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

public class Game implements Runnable, KeyListener{

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	private JLabel[][] labels;
	//the Jlabel corresponding to num array
	private int[][] num;
	boolean success=false;
	private Lock lock;
	JLabel step;
	int steps;
	//grid to record num
	
	/**
	 * Create the application.
	 */
	public Game() {
		lock=new ReentrantLock();
		lock.lock();
		//Constructor
		labels=new JLabel[4][4];
		num=new int[4][4];
		steps=0;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.addKeyListener(this);
		panel.setFocusable(true);
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(5, 4, 0, 0));
		panel.setBackground(Color.CYAN);

		JLabel lblNewLabel = new JLabel("Steps:");
		panel.add(lblNewLabel);
		
		step = new JLabel(""+steps);
		panel.add(step);
		
		panel.add(new JLabel());
		panel.add(new JLabel());
		
		initBoard();
		
		for(int i =0;i<4;i++){
			for(int j=0;j<4;j++){
				labels[i][j]=new JLabel();
				labels[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				labels[i][j].setText(num[i][j]==0? "":""+String.valueOf(num[i][j]));
				labels[i][j].setHorizontalAlignment(0);
				labels[i][j].setForeground(Color.RED);
				panel.add(labels[i][j]);
			}
		}
		
		frame.setVisible(true);
		System.out.println("initialized!");
	}
	private void paint(){
		//decides the label based on the num array
		JPanel panel=(JPanel) frame.getContentPane();
		step.setText(""+steps);
		for(int i =0;i<4;i++){
			for(int j=0;j<4;j++){
				labels[i][j].setText(num[i][j]==0? "":""+String.valueOf(num[i][j]));
			}
		}
	}
	private void initBoard(){
		//put a '2' and a '4' randomly at the board
		Random r=new Random();
		int x=r.nextInt(4);
		int y=r.nextInt(4);
		num[x][y]=2;
		do{
			//put 4 not located at position holding '2'
			x=r.nextInt(4);
			y=r.nextInt(4);
		}while(num[x][y]==2);
		num[x][y]=4;
	}
	private void shift(int key){
		//shift the elements in the array based on the key pressed by user
		if(success) {lock.unlock();return;}
		steps++;
		if(key == KeyEvent.VK_LEFT){
			//left
			for(int j =1;j<4;j++){
				for(int i =0;i<4;i++){
					if(num[i][j]!=0){
						int col=j;
						while(col>0&&num[i][col-1]==0){
							num[i][col-1]=num[i][col];
							num[i][col]=0;
							col--;
						}
						if(col>0&&num[i][col-1]==num[i][col]){
							num[i][col]=0;
							num[i][col-1]=2*num[i][col-1];
							if(num[i][col-1]==2048) success=true;
						}
					}
				}
			}
		}
		else if(key == KeyEvent.VK_RIGHT){
			//right
			for(int j =2;j>=0;j--){
				for(int i =0;i<4;i++){
					if(num[i][j]!=0){
						int col=j;
						while(col<3&&num[i][col+1]==0){
							num[i][col+1]=num[i][col];
							num[i][col]=0;
							col++;
						}
						if(col<3&&num[i][col+1]==num[i][col]){
							num[i][col]=0;
							num[i][col+1]=2*num[i][col+1];
							if(num[i][col+1]==2048) success=true;
						}
					}
				}
			}
		}
		else if(key == KeyEvent.VK_DOWN){
			//down
			for(int i =2;i>=0;i--){
				for(int j =0;j<4;j++){
					if(num[i][j]!=0){
						int row=i;
						while(row<3&&num[row+1][j]==0){
							num[row+1][j]=num[row][j];
							num[row][j]=0;
							row++;
						}
						if(row<3&&num[row+1][j]==num[row][j]){
							num[row][j]=0;
							num[row+1][j]=2*num[row+1][j];
							if(num[row+1][j]==2048) success=true;
						}
					}
				}
			}
		}
		else if(key == KeyEvent.VK_UP){
			//up
			for(int i =1;i<4;i++){
				for(int j =0;j<4;j++){
					if(num[i][j]!=0){
						int row=i;
						while(row>0&&num[row-1][j]==0){
							num[row-1][j]=num[row][j];
							num[row][j]=0;
							row--;
						}
						if(row<3&&num[row+1][j]==num[row][j]){
							num[row+1][j]=0;
							num[row][j]=2*num[row][j];
							if(num[row][j]==2048) success=true;
						}
					}
				}
			}
		}
		put2randomly();
		paint();
		frame.validate();
		frame.repaint();
	}
	private void put2randomly(){
		//put a new 2 into the board empty spots
		if(success) return;
		ArrayList<int[]> emptyspot=new ArrayList<int[]>();
		//record the remaining empty spot
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				if(num[i][j]==0){
					emptyspot.add(new int[]{i,j});
				}
			}
		}
		int size=emptyspot.size();
		if(size==0) success=true;
		else{
			Random r=new Random();
			int index=r.nextInt(size);
			int[] temp=emptyspot.get(index);
			num[temp[0]][temp[1]]=2;
		}
	}
	@Override
	public void keyReleased(KeyEvent e){
		//System.out.println("Released Key");
		if(!success) shift(e.getKeyCode());
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game window = new Game();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}
