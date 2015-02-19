package clueGameTests;

import static org.junit.Assert.*;
import clueGame.*;

import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class CR_FileInitTests {
	private static Board board;
	public static final int NUM_ROOMS = 9;
	public static final int NUM_ROWS = 21;
	public static final int NUM_COLUMNS = 25;
	
	@BeforeClass
	public static void setUp(){
		ClueGame game = new ClueGame("Clue Map.csv", "legend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}
	@Test
	public void testRooms(){
		Map<Character, String> rooms = board.getRooms();
		assertEquals(NUM_ROOMS, rooms.size());
		assertEquals("Innistrad", rooms.get('i'));
		assertEquals("Ravnica", rooms.get('r'));
		assertEquals("Alara", rooms.get('a'));
		assertEquals("Kamigawa", rooms.get('k'));
		assertEquals("Shandalar", rooms.get('s'));
		assertEquals("Blind Eternities", rooms.get('x'));
	}
	@Test
	public void testBoardDimentions(){
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());	
	}
	@Test
	public void FourDoorDirections(){
		RoomCell room = (RoomCell) board.getBoardCell(0,0);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = (RoomCell) board.getBoardCell(0,0);
	}
	@Test
	public void testNumberOfDoorways(){
		int numDoors = 0;
		int numCells = board.getNumRows() * board.getNumColumns();
		assertEquals(525,numCells);
		for(int i = 0; i<board.getNumRows(); i++){
			for(int j = 0; j<board.getNumColumns(); j++){
				BoardCell bc = board.getBoardCell(i, j);
				if(bc.isDoorway()){
					numDoors++;
				}
			}
		}
		assertEquals(11,numDoors);
	}
	/*@Test
	public void testRoomInitials(){
		assertEquals('')
	}*/
}
