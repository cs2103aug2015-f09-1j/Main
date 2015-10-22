
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



//jarvas test skeleton
public class JarvasTest {

	Jarvas jarvas = new Jarvas();
	Logic logic = new Logic();

	
	@Test
	public void testDetermineCommandType(){
		assertEquals(Parser.CommandType.ADDTASK,Parser.determineCommandType("addtask"));
	}
	
	@Test
	public void testAddItem() throws IOException{

		
		

		//equivalence partitioning for invalid value partition
		assertEquals("invalid input", logic.execute("444"));
		assertEquals("invalid input", logic.execute("eeeee"));
		//equivalence partitioning for valid value partition
		assertEquals("task \"ola\" successfully added", logic.execute("addtask ola -due 3/3/3/3/3"));
		// Testing adding of events
		assertEquals("event \"ola\" successfully added", logic.addEvent("ola -from 12/12/1991 12:12 -to 12/12/1991 12:13"));

	}

	
	@Test
	public void testClear() throws IOException{
		assertEquals("tasks is clear", logic.clearTask());
	}
	
	@Test
	public void testSort() throws IOException{
		
	}
	
	@Test
	public void testSearch() throws IOException{

	}
	@Before
	public void beforeRunTest(){
		logic.clearTask();
	}
	@After
	public void afterRunTest(){
		logic.clearTask();
	}
}
