package experiment;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntBoardTests {
	
	public IntBoard board;
	
	@Before
	public void setUp(){
		board = new IntBoard();
	}
//adjacency tests
	@Test
	public void testAdjacency0_0() {
		BoardCell cell = board.getCell(0,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(0, 1)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency3_3() {
		BoardCell cell = board.getCell(3,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency1_3() {
		BoardCell cell = board.getCell(1,3);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(2, 3)));
		assertTrue(testList.contains(board.getCell(0,3)));
		assertEquals(3, testList.size());
	}
	@Test
	public void testAdjacency3_0() {
		BoardCell cell = board.getCell(3,0);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(2, 0)));
		assertTrue(testList.contains(board.getCell(3, 1)));
		assertEquals(2, testList.size());
	}
	@Test
	public void testAdjacency1_1() {
		BoardCell cell = board.getCell(1,1);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 0)));
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertEquals(4, testList.size());
	}
	@Test
	public void testAdjacency2_2() {
		BoardCell cell = board.getCell(2,2);
		LinkedList<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1, 2)));
		assertTrue(testList.contains(board.getCell(3, 2)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(4, testList.size());
	}
	
	//pathing tests
	@Test
	public void testTargets00_3()
	{
		BoardCell cell = board.getCell(0, 0);
		Set<BoardCell> targets = board.getTargets(cell, 3);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	@Test
	public void testTargets01_3()
	{
		BoardCell cell = board.getCell(0,1);
		Set<BoardCell> targets = board.getTargets(cell, 3);
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(1, 3)));
	}
	@Test
	public void testTargets11_2()
	{
		BoardCell cell = board.getCell(1, 1);
		Set<BoardCell> targets = board.getTargets(cell, 2);
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(0, 0)));
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
	}
	@Test
	public void testTargets32_3()
	{
		BoardCell cell = board.getCell(3, 2);
		Set<BoardCell> targets = board.getTargets(cell, 3);
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(2, 0)));
		assertTrue(targets.contains(board.getCell(1, 1)));
		assertTrue(targets.contains(board.getCell(0, 2)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(3, 1)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	@Test
	public void testTargets21_4()
	{
		BoardCell cell = board.getCell(2, 1);
		Set<BoardCell> targets = board.getTargets(cell, 4);
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCell(1, 0)));
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(3, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(2, 3)));
	}
	@Test
	public void testTargets23_1()
	{
		BoardCell cell = board.getCell(2, 3);
		Set<BoardCell> targets = board.getTargets(cell, 1);
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1, 3)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
}
