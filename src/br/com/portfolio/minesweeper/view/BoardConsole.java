package br.com.portfolio.minesweeper.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.portfolio.minesweeper.exception.ExitException;
import br.com.portfolio.minesweeper.exception.ExplosionException;
import br.com.portfolio.minesweeper.model.Board;

public class BoardConsole {
	
	private Board board;
	private Scanner in = new Scanner(System.in);

	public BoardConsole(Board board) {
		this.board = board;
		
		executeGame();
	}

	private void executeGame() {
		try {
			boolean persist = true;
			
			while(persist) {
				gameCicle();
				
				System.out.println("Another match mate? (S/n)");
				String answer = in.nextLine();
				
				if("n".equalsIgnoreCase(answer)) {
					persist = false;
				} else {
					board.resetGame();
				}
			}
		} catch (ExitException e) {
			System.out.println("GoodBye :)");
		} finally {
			in.close();
		}
	}

	private void gameCicle() {
		try {
			
			while(!board.goalAchieved()) {
				System.out.println(board);
				
				String typed = getTypedValue("Type (x, y): ");
				
				Iterator<Integer> coordinates = Arrays.stream(typed.split(","))
						.map(e -> Integer.parseInt(e.trim())).iterator();
				
				typed = getTypedValue("1 - To open cell or 2 - To mark/unmark cell");
				
				if("1".equals(typed)) {
					board.openCell(coordinates.next(), coordinates.next());
				} else if("2".equals(typed)) {
					board.markCell(coordinates.next(), coordinates.next());
				} 
			}
			System.out.println(board);
			System.out.println("You Win! xD");
		} catch (ExplosionException e) {
			System.out.println(board);
			System.out.println("You lose! :(");
		}
	}
	
	private String getTypedValue(String text) {
		System.out.print(text);
		String typed = in.nextLine();
		
		if("exit".equalsIgnoreCase(typed)) {
			throw new ExitException();
		}
		return typed;
	}
	
	

}
