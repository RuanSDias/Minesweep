package br.com.portfolio.minesweeper.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.portfolio.minesweeper.exception.ExplosionException;

public class CellTest {
	
	private Cell cell;
	
	
	//Test to add cells around another cell(but the borders).
	
	@BeforeEach
	void cellInitializer() {
		cell = new Cell(3, 3);
	}
	
	@Test
	void addLeftBorderer() {
		Cell borderer = new Cell(3, 2);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addRightBorderer() {
		Cell borderer = new Cell(3, 4);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addUpperBorderer() {
		Cell borderer = new Cell(2, 3);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addBottomBorderer() {
		Cell borderer = new Cell(4, 3);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addUpperLeftBorderer() {
		Cell borderer = new Cell(2, 2);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addUpperRightBorderer() {
		Cell borderer = new Cell(2, 4);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addBottomLeftBorderer() {
		Cell borderer = new Cell(4, 2);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addBottomRightBorderer() {
		Cell borderer = new Cell(4, 4);
		boolean result = cell.addCellBorderer(borderer);
		assertTrue(result);
	}
	
	@Test
	void addNonBordererInline() {
		Cell borderer = new Cell(5, 3);
		boolean result = cell.addCellBorderer(borderer);
		assertFalse(result);
	}
	
	@Test
	void addNonBordererDiagonal() {
		Cell borderer = new Cell(5, 5);
		boolean result = cell.addCellBorderer(borderer);
		assertFalse(result);
	}
	
	
	//Method test *** cellMarkAlternation ***
	
	@Test
	void testDefaultValueOfMarkedAttribute() {
		assertFalse(cell.isMarked());
	}
	
	@Test
	void testCellMarkAlternation() {
		cell.cellMarkAlternation();
		assertTrue(cell.isMarked());
	}
	
	@Test
	void testCellMarkAlternationTwice() {
		cell.cellMarkAlternation();
		cell.cellMarkAlternation();
		assertFalse(cell.isMarked());
	}
	
	
	//Test open possibilities
	
	@Test
	void testOpenWithoutMarkWitoutMine() {
		assertTrue(cell.openCell());
	}
	
	@Test
	void testOpenMarkedWithoutMine() {
		cell.cellMarkAlternation();
		assertFalse(cell.openCell());
	}
	
	@Test
	void testOpenMinedWithoutMark() {
		cell.toMine();
		assertThrows(ExplosionException.class, () -> {
			cell.openCell();
		});
	}
	
	@Test
	void testOpenMarkedAndMined() {
		cell.cellMarkAlternation();
		cell.toMine();
		assertFalse(cell.openCell());
	}
	
	//Test for cells around
	
	@Test
	void testOpenMoreThanOne() {
		Cell cellOneOne = new Cell(1, 1);
		Cell cellTwoTwo = new Cell(2, 2);
		
		cellTwoTwo.addCellBorderer(cellOneOne);
		cell.addCellBorderer(cellTwoTwo);
		
		cell.openCell();
		
		assertTrue(cellOneOne.isOpen() && cellTwoTwo.isOpen());
	}
	
	@Test
	void testOpenMoreThanOneWithMine() {
		Cell cell11 = new Cell(1, 1);
		Cell cell12 = new Cell(1, 2);
		Cell cell22 = new Cell(2, 2);
		
		cell22.addCellBorderer(cell11);
		cell22.addCellBorderer(cell12);
		
		cell.addCellBorderer(cell22);
		
		cell12.toMine();
		cell.openCell();
		
		assertTrue(cell11.isClosed() && cell22.isOpen());
	}

}
