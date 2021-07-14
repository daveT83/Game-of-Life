package game.output;

import game.storage.Board;
import game.utils.Utils;

public class TUI {
	private Board board;
	private boolean running;
	private int speed;
	
	public TUI(Board board, int speed) {
		this.board = board;
		this.running = true;
		this.speed = speed;
	}
	
	public void run() {
		Thread thread = new Thread() {
			public void run() {
				int gen = 0;
				while(true) {
					if(running) {
						Utils.sweepBoard();
						System.out.println("Generation: "+gen);
						printBoard();
						System.out.println("\n");
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
		};
		
		thread.start();
	}
	
	public void setRunning(boolean b) {
		this.running = b;
	}
	
	public boolean getRunning() {
		return running;
	}
	
	private void printBoard() {	
		for(int i = 0; i < board.getLength(); i++) {
			System.out.print('|');
			for(int k = 0; k < (this.board.getHeight()*2) - 1; k++) {
				System.out.print('-');
			}
			System.out.println('|');
			System.out.print('|');
			for(int k = 0; k < board.getHeight(); k++) {
				System.out.print(this.board.getCell(i, k).getSymbol()+"|");
			}
			System.out.println();
		}
	}
}
