package game.output;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import game.storage.Board;
import game.utils.Utils;

public class File {
	private Board board;
	private int generations;
	
	public File(Board board) {
		this.board = board;
	}
	
	public void setGenerations(int generations) {
		this.generations = generations;
	}
	
	public void run() throws IOException {
		Writer output = new FileWriter("The Game of Life");
		int count = 0;
		
		while(count <= this.generations) {
			Utils.sweepBoard();
			output.write("Generation: "+count);
			output.write('\n');
			writeBoard(output);		
			output.write('\n');
			count++;
		}
		
		output.close();
	}
	
	private void writeBoard(Writer output) throws IOException {
		int length = this.board.getLength();
		int height = this.board.getHeight();
		
		for(int i = 0; i < length; i++) {
			output.write('|');
			for(int k = 0; k < (this.board.getHeight()*2) - 1; k++) {
				output.write('-');
			}
			output.write('|');
			output.write('\n');
			output.write('|');
			for(int k = 0; k < height; k++) {
				output.write(this.board.getCell(i, k).getSymbol()+"|");
			}
			output.write('\n');
		}
	}
}
