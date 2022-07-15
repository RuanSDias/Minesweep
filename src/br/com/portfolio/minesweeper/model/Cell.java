package br.com.portfolio.minesweeper.model;

import java.util.ArrayList;
import java.util.List;

import br.com.portfolio.minesweeper.exception.ExplosionException;

public class Cell {
	
	private final int line;
	private final int column;
	
	private boolean mined;
	private boolean opened;
	private boolean marked;
	
	private List<Cell> borderers = new ArrayList<>();
	
	public Cell(int line, int column) {
		this.line = line;
		this.column = column;
	}
	
	boolean addCellBorderer(Cell possibleBorderer) {
		boolean differentLine = line != possibleBorderer.line;
		boolean differentColumn = column != possibleBorderer.column;
		boolean diagonal = differentLine && differentColumn;
		
		int lineDelta = Math.abs(line - possibleBorderer.line);
		int columnDelta = Math.abs(column - possibleBorderer.column);
		int generalDelta = lineDelta + columnDelta;
		
		if(generalDelta == 1 && !diagonal) {
			borderers.add(possibleBorderer);
			return true;
		} else if (generalDelta == 2 && diagonal) {
			borderers.add(possibleBorderer);
			return true;
		} else {
			return false;
		}	
	}
	
	void cellMarkAlternation() {
		if(!opened) {
			marked = !marked;
		}
	}
	
	boolean openCell() {
		if(!opened && !marked) {
			opened = true;
			
			if(mined) {
				throw new ExplosionException();
			}
			
			if(verifySafetyAroundCell()) {
				borderers.forEach(around -> around.openCell());
			}
			
			return true;
		} else {
			return false;
		}	
	}
	
	boolean verifySafetyAroundCell() {
		return borderers.stream().noneMatch(around -> around.mined);
	}
	
	boolean goalAchieved() {
		boolean revealed = !mined && opened;
		boolean secure = mined && marked;
		return revealed || secure;
	}
	
	long minesAround() {
		return borderers.stream().filter(around -> around.mined).count();
	}
	
	void reset() {
		mined = false;
		opened = false;
		marked = false;
	}
	
	public String toString() {
		if(marked) {
			return "x";
		} else if (mined && opened) {
			return "*";
		} else if (opened && minesAround() > 0) {
			return Long.toString(minesAround());
		} else if (opened) {
			return "";
		} else {
			return "?";
		}
	}
	
	
	//Getter/Setter
	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public boolean isMined() {
		return mined;
	}
	
	

	void setOpened(boolean opened) {
		this.opened = opened;
	}

	//Method for tests.
	public boolean isMarked() {
		return marked;
	}
	
	void toMine() {
		mined = true;
	}
	
	public boolean isOpen() {
		return opened;
	}
	
	public boolean isClosed() {
		return !isOpen();
	}

}
