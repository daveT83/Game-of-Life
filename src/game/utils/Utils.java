package game.utils;

import java.util.ArrayList;
import java.util.Scanner;

import game.cell.CellStates;
import game.storage.Board;

public class Utils {
	private static Board board;
	
	public static void setBoard(Board board) {
		Utils.board = board;
	}
	
	public static boolean canSpread(int x, int y) {
		boolean b = false;
		
		if(Utils.board.getCell(x, y).getState() == CellStates.DEAD 
				&& Utils.countNeighbors(x, y) == 3) {
			b = true;
		}
		
		return b;
	}
	
	public static boolean canDie(int x, int y) {
		boolean b = false;
		int neighbors = Utils.countNeighbors(x, y);
		if(Utils.board.getCell(x, y).getState() == CellStates.ALIVE) {
			if(neighbors < 2 || neighbors > 3) {
				b = true;
			}
		}
		
		return b;
	}
	
	public static int countNeighbors(int x, int y) {
		int count = 0;
		
		for(int i = -1; i <= 1; i++) {
			for(int k = -1; k <= 1; k++) {
				try {
					if(Utils.board.getCell(i+x, k+y).getState() == CellStates.ALIVE) {
						count++;
					}
				}catch(ArrayIndexOutOfBoundsException e) {}		//if the cell is on the edge ignore out or bounds elements
			}
		}
		return count;
	}
	
	public static void sweepBoard() {
		for(int i = 0; i < Utils.board.getLength(); i++) {
			for(int k = 0; k < Utils.board.getHeight(); k++) {
				if(Utils.canSpread(i, k)) {
					Utils.board.getCell(i, k).setState(CellStates.ALIVE);
				}
				else if(Utils.canDie(i, k)) {
					Utils.board.getCell(i, k).setState(CellStates.DEAD);
				}
			}
		}
	}
	
	public static void getStartingCluster(Scanner scanner) {
		ArrayList<String> points = new ArrayList<>();
		String input = "run";
		
		System.out.println("Below enter all coordinates that cells will begin at. \nWhen done just press 'Enter'");
		while(!input.isEmpty()) {
			System.out.print("Enter a coordinate: ");
			input = scanner.nextLine();
			if(!input.isEmpty()) {
				points.add(input);
			}
		}
		System.out.println("processing points...");
		addCells(points);
		System.out.println("All coordinates read in. press 'Enter' to run.");
	}
	
	public static void addCells(ArrayList<String> points) {
		int[] p;
		
		for(String point:points) {
			try {
				p = parsePoint(point);
				board.getCell(p[0], p[1]).setState(CellStates.ALIVE);
			}catch(NumberFormatException e) {}
		}
	}
	
	private static int[] parsePoint(String point) {
		String x = "";
		String y = "";
		char curChar;
		int comma = 0;
		
		//finds x coordinate 
		while(comma < point.length()) {
			curChar = point.charAt(comma);
			if(curChar == ',') {
				break;
			}
			
			else if((int)curChar >= 48 && (int)curChar <= 57) {		//uses ascii codes to determine if the char is an int
				x += curChar;
			}
			comma++;
		}
		
		//finds the y coordinate
		while(comma < point.length()) {
			curChar = point.charAt(comma);

			if((int)curChar >= 48 && (int)curChar <= 57) {		//uses ascii codes to determine if the char is an int
				y += curChar;
			}
			comma++;
		}
		
		return new int[] {Integer.parseInt(x), Integer.parseInt(y)};
	}
}
