package game.cell;

import java.awt.Color;

public class Cell {
	private int state;
	private Color color;
	private char symbol;
	
	public Cell() {
		this.setState(CellStates.DEAD);
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
		
		if(this.state == CellStates.ALIVE) {
			this.color = CellColors.ALIVE;
			this.symbol = CellSymbols.ALIVE;
		}
		else if(this.state == CellStates.DEAD) {
			this.color = CellColors.DEAD;
			this.symbol = CellSymbols.DEAD;
		}
		else {
			this.color = CellColors.EMPTY;
			this.symbol = CellSymbols.EMPTY;
		}
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the symbol
	 */
	public char getSymbol() {
		return symbol;
	}
}
