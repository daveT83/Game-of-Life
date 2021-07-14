package game.run;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import game.output.File;
import game.output.GUI;
import game.output.TUI;
import game.storage.Board;
import game.utils.Utils;

public class Main {
	private final static int FILE = 1;
	private final static int TUI = 2;
	private final static int GUI = 3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Board board;
		int speed;
		int[] selections;
		int[] boardSize;
		Scanner input = new Scanner(System.in);
		
		selections = startupTUI(input);
		speed = getSpeed(input);
		boardSize = getBoardSize(input);
		board = new Board(boardSize[0], boardSize[1]);
		Utils.setBoard(board);
		
		for(int i:selections) {
			if(i == FILE) {
				runFile(board, input);
			}
			else if(i == TUI) {
				runTUI(board, input, speed);
			}
			else if(i == GUI) {
				runGUI(board, speed);
			}
		}
		input.close();
	}
	
	private static int[] getBoardSize(Scanner scanner) {
		int[] size;
		
		System.out.println("Enter the size of the board below. \nThe value entered below should look something like this: 25, 30");
		size = parseSelection(scanner.nextLine());
		
		if(size.length == 2) {
			return size;
		}
		else {
			System.out.println("Invalid dimensions...\nUsing default dimension (100,100)");
			return new int[] {100,100};
		}
	}
	
	private static int getSpeed(Scanner scanner) {
		System.out.println("Enter the speed in milliseconds below. \nThe higher the number the slower the simulation will run.");
		System.out.print("Speed: ");
		return parseSpeed(scanner.nextLine());
	}
	
	private static int parseSpeed(String input) {
		int[] speed = parseSelection(input);
		
		try {
			return speed[0];
		}catch (IndexOutOfBoundsException e) {		//if an invalid speed is passed
			System.out.println("Invalid speed...\nUsiing default value of 500");
			return 500;
		}
	}
	
	private static int[] startupTUI(Scanner scanner) {
		System.out.println("Choose one or more of the ways below to run 'The Game of Life':");
		System.out.println("*Note if selcting more than one option seperate each selection with a comma.");
		System.out.println("1. Writing The outcome to a file.");
		System.out.println("2. Displaying 'The Game of Life' in a TUI");
		System.out.println("3. Displaying 'The Game of Life' in a GUI\n");
		System.out.print("Choice(s): ");
		return parseSelection(scanner.nextLine());
	}
	
	private static int[] parseSelection(String selection) {
		ArrayList<Integer> selections = new ArrayList<>();
		int[] choices;
		char curChar;
		String temp = "";
		
		for(int i = 0; i < selection.length(); i++) {
			curChar = selection.charAt(i);
			if((int)curChar >= 48 && (int)curChar <= 57) {		//uses ascii codes to determine if the char is an int
				temp += curChar;
			}
			else if(curChar == ',') {
				selections.add(Integer.parseInt(temp));
				temp = "";
			}
		}
		
		if(!temp.isEmpty()) {
			selections.add(Integer.parseInt(temp));
		}
		
		choices = new int[selections.size()];
		for(int i = 0; i < selections.size(); i++) {
			choices[i] = selections.get(i);
		}
		
		return choices;
	}
	
	private static void runGUI(Board board, int speed) {
		GUI gui = new GUI(board, speed);
		
		gui.run();
	}
	
	private static void runTUI(Board board, Scanner input, int speed) {
		TUI tui = new TUI(board, speed);
		
		Utils.getStartingCluster(input);
		input.close();
		tui.run();
	}
	
	private static void runFile(Board board, Scanner input) {
		File file = new File(board);
		
		System.out.print("Enter Number of generations: ");
		file.setGenerations(Integer.parseInt(input.nextLine()));
		Utils.getStartingCluster(input);
		try {
			file.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("File sucessfully written.");
	}

}
