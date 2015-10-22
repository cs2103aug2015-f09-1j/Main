
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;



//jarvas test skeleton
public class JarvasTest {
	Jarvas Jarvas = new Jarvas();
	
	@Test
	public void testDetermineCommandType(){
		assertEquals(Parser.CommandType.ADDTASK,Parser.determineCommandType("add"));

	}
	
	@Test
	public void testAddItem() throws IOException{
		// Testing adding of events
		assertEquals("event \"ola\" successfully added", Logic.addEvent("ola -from 12/12/1991 12:12 -to 12/12/1991 12:13"));
	}

	
	@Test
	public void testClear() throws IOException{

	}
	
	@Test
	public void testSort() throws IOException{

	}
	
	@Test
	public void testSearch() throws IOException{

	}
	
}
