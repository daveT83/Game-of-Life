package game.storage;

import game.cell.Cell;
import game.cell.CellStates;

public class Board {
	private Cell[][] board;
	
	public Board(int x, int y) {
		this.board = new Cell[x][y];
		reset();
	}
	
	public void reset() {
		int x = this.board.length;
		int y = this.board[0].length;
		
		for(int i = 0; i < x; i++) {
			for(int k = 0; k < y; k++) {
				this.board[i][k] = new Cell();
				setDead(i, k);
			}
		}
	}
	
	public Cell getCell(int x, int y) {
		return this.board[x][y];
	}
	
	public int getLength() {
		return this.board[0].length;
	}
	
	public int getHeight() {
		return this.board.length;
	}
	
	public void setAlive(int x, int y) {
		this.board[x][y].setState(CellStates.ALIVE);
	}
	
	public void setDead(int x, int y) {
		this.board[x][y].setState(CellStates.DEAD);
	}
	
	public void setEmpty(int x, int y) {
		this.board[x][y].setState(CellStates.EMPTY);
	}
}
