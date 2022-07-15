package br.com.portfolio.minesweeper.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Board {
	
	private int mines;
	private int lines;
	private int columns;
	
	private final List<Cell> cells = new ArrayList<>();

	public Board(int mines, int lines, int column) {
		this.mines = mines;
		this.lines = lines;
		this.columns = column;
		
		generateCells();
		associateCells();
		sortMines();
	}
	
	public void openCell(int line, int column) {
		try {
			cells.parallelStream()
				.filter(c -> c.getLine() == line && c.getColumn() == column )
				.findFirst()
				.ifPresent(c -> c.openCell());
		} catch (Exception e) {
			cells.forEach(c -> c.setOpened(true));
			throw e;
		}
	}
	
	public void markCell(int line, int column) {
		cells.parallelStream()
			.filter(c -> c.getLine() == line && c.getColumn() == column )
			.findFirst()
			.ifPresent(c -> c.cellMarkAlternation());
	}

	private void generateCells() {
		for(int line = 0; line < lines; line++) {
			for(int column = 0; column < columns; column++) {
				cells.add(new Cell(line, column));
			}
		}
	}
	
	private void associateCells() {
		for(Cell cell1: cells) {
			for(Cell cell2: cells) {
				cell1.addCellBorderer(cell2);
			}
		}
	}
	
	private void sortMines() {
		long plantedMines = 0;
		Predicate<Cell> mined = c -> c.isMined();
		
		do {
			int randomCell = (int) (Math.random() * cells.size());
			cells.get(randomCell).toMine();
			plantedMines = cells.stream().filter(mined).count();
		} while (plantedMines < mines);
	}
	
	public boolean goalAchieved() {
		return cells.stream().allMatch(c -> c.goalAchieved());
	}
	
	public void resetGame() {
		cells.stream().forEach(c -> c.reset());
		sortMines();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("  ");
		for(int column = 0; column < columns; column++) {
				sb.append(" ");
				sb.append(column);
				sb.append(" ");
			}
		
		sb.append("\n");
		int i = 0;
		for(int line = 0; line < lines; line++) {
			sb.append(line);
			sb.append(" ");
			for(int column = 0; column < columns; column++) {
				sb.append(" ");
				sb.append(cells.get(i));
				sb.append(" ");
				i++;
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
