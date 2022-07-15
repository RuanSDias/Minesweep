package br.com.portfolio.minesweeper;

import br.com.portfolio.minesweeper.model.Board;
import br.com.portfolio.minesweeper.view.BoardConsole;

public class Aplication {

	public static void main(String[] args) {
		
		Board board = new Board(6, 6, 6);
		
		new BoardConsole(board);
	}

}
