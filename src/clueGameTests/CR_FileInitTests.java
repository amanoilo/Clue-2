package clueGameTests;

import static org.junit.Assert.*;
import clueGame.*;

import java.io.FileNotFoundException;
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
		ClueGame game = new ClueGame("map/Clue Map.csv", "map/legend.txt");
		try {
			game.loadConfigFiles();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
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
		RoomCell room = (RoomCell) board.getBoardCell(1,4);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = (RoomCell) board.getBoardCell(14,12);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = (RoomCell) board.getBoardCell(4,6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = (RoomCell) board.getBoardCell(15,23);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		room = (RoomCell) board.getBoardCell(13,8);
		assertTrue(room.isDoorway());
	}
	
	@Test (expected = BadConfigFormatException.class) 
	public void testBadColums() throws BadConfigFormatException, FileNotFoundException {
<<<<<<< HEAD
		ClueGame game = new ClueGame("ClueBadMap.csv", "map/legend.txt");
=======
		ClueGame game = new ClueGame("map/ClueBadMap.csv", "map/legend.txt");
>>>>>>> 6a0e4b9a14cae072e46e8bb8d0814f2a31423398
		game.loadConfigFiles();
		game.getBoard().loadBoardConfig();
	}
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomsFormat() throws BadConfigFormatException, FileNotFoundException{
<<<<<<< HEAD
		ClueGame game = new ClueGame("map/Clue Map.csv", "Badlegend.txt");
=======
		ClueGame game = new ClueGame("map/Clue Map.csv", "map/Badlegend.txt");
>>>>>>> 6a0e4b9a14cae072e46e8bb8d0814f2a31423398
		game.loadConfigFiles();
		game.getBoard().loadBoardConfig();
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
