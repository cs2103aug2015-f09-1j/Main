
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;



//jarvas test skeleton
public class JarvasTest {
	Jarvas textBuddy = new Jarvas();
	
	@Test
	public void testDetermineCommandType(){
		assertEquals(Parser.CommandType.ADDTASK,Parser.determineCommandType("add"));

	}
	
	@Test
	public void testAddItem() throws IOException{

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
