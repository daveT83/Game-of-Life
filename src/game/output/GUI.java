package game.output;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import game.cell.CellColors;
import game.storage.Board;
import game.utils.Utils;

public class GUI {
	private Board board;
	private JFrame window;
	private int speed;
	private JButton run;
	private JLabel generation;
	private JPanel[][] boardGUI;
	
	public GUI(Board board, int speed) {
		this.board = board;
		this.speed = speed;
		initWindow();
	}
	
	private void initWindow() {
		this.window = new JFrame();
		JPanel backPane = new JPanel();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screenSize.width*2)/3;
		int y = (screenSize.height*2)/3;
		
		backPane.setLayout(new BorderLayout());
		populateBottom(backPane);
		populateBoard(backPane);
		
		this.window.setTitle("Game of Life");
		this.window.setSize(new Dimension(x,y));
		this.window.setPreferredSize(new Dimension(x,y));
		this.window.setLocationRelativeTo(null);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.add(backPane);
		window.setAlwaysOnTop(true);
		this.window.setVisible(true);
	}
	
	private void populateBottom(JPanel pane) {
		JPanel bottom = new JPanel();
		JPanel key = new JPanel();
		JPanel alivePane = new JPanel();
		JPanel deadPane = new JPanel();
		JLabel alive = new JLabel();
		JLabel dead = new JLabel();
		JPanel aliveColor = new JPanel();
		JPanel deadColor = new JPanel();
		this.generation = new JLabel();
		this.run = new JButton();
		
		
		this.run.setText("Run");
		this.generation.setText("Generation: ");
		
		alivePane.setLayout(new FlowLayout());
		alive.setText("Alive ");
		addBorder(aliveColor);
		aliveColor.setBackground(CellColors.ALIVE);
		alivePane.add(alive);
		alivePane.add(aliveColor);
		
		deadPane.setLayout(new FlowLayout());
		dead.setText("Dead ");
		addBorder(deadColor);
		deadColor.setBackground(CellColors.DEAD);
		deadPane.add(dead);
		deadPane.add(deadColor);
		
		key.setLayout(new BoxLayout(key, BoxLayout.Y_AXIS));
		key.add(alivePane);
		key.add(deadPane);
		
		addBorder(bottom);
		bottom.setLayout(new FlowLayout());
		bottom.add(this.run);
		bottom.add(this.generation);
		bottom.add(key);
		
		pane.add(bottom, BorderLayout.SOUTH);
	}
	
	private void populateBoard(JPanel pane) {
		JPanel board = new JPanel();
		JScrollPane scroll = new JScrollPane(board);
		int width = this.board.getLength();
		int height = this.board.getHeight();
		this.boardGUI = new JPanel[width][height];
		JPanel tempPane;
		
		board.setLayout(new GridLayout(width,height));
		for(int i = 0; i < width; i++) {
			for(int k = 0; k < height; k++) {
				tempPane = new JPanel();
				tempPane.setBackground(CellColors.EMPTY);
				tempPane.setSize(new Dimension(10,10));
				tempPane.setPreferredSize(new Dimension(10,10));
				addSelectedPane(tempPane);
				addBorder(tempPane);
				boardGUI[i][k] = tempPane;
				board.add(tempPane);
			}
		}
		
		pane.add(scroll);
	}
	
	private void addBorder(JPanel pane) {
		pane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	}
	
	private void addSelectedPane(JPanel pane) {
		pane.addMouseListener(new MouseListener() {
			private boolean isSelected = false;

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getModifiers() == MouseEvent.MOUSE_PRESSED) {
					if(!isSelected) {
						pane.setBackground(Color.GRAY);
						isSelected = true;
					}
					else {
						pane.setBackground(CellColors.EMPTY);
						isSelected = false;
					}
				}
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(!isSelected) {
					pane.setBackground(Color.GRAY);
					isSelected = true;
				}
				else {
					pane.setBackground(CellColors.EMPTY);
					isSelected = false;
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public void run() {
		this.window.setAlwaysOnTop(false);
		this.run.addActionListener(new ActionListener() {
			private boolean isRunning = true;
			private boolean clicked = false;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!clicked) {
					this.clicked = true;
					Thread thread = new Thread() {
						public void run() {
							injectStartingCluster();
							runSim(isRunning);
						}		
					};
					
					thread.start();
				}
			}
	});
		
	}
	
	private void runSim(boolean isRunning) {
		int gen = 0;
		while(true) {
			if(isRunning) {
				Utils.sweepBoard();
				generation.setText("Generation: "+gen);
				updateBoard();
				gen++;
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{		//paused
				try {
					Thread.sleep(600);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void injectStartingCluster() {
		int length = this.board.getLength();
		int height = this.board.getHeight();
		ArrayList<String> points = new ArrayList<>();
		String point;
		
		for(int i = 0; i < length; i++) {
			for(int k = 0; k < height; k++) {
				if(this.boardGUI[i][k].getBackground() == Color.GRAY) {	//if selected
					point = i+","+k;
					points.add(point);
				}
			}
		}
		Utils.addCells(points);
	}
	
	private void updateBoard() {
		int length = this.board.getLength();
		int height = this.board.getHeight();
		
		for(int i = 0; i < length; i++) {
			for(int k = 0; k < height; k++) {
				this.boardGUI[i][k].setBackground(this.board.getCell(i, k).getColor());
			}
		}
	}

}
