package UnitTestCases;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.jarvas.Logic;


//jarvas test skeleton
public class JarvasTest {

	GUI.Jarvas jarvas = new GUI.Jarvas();
	Logic logic = new Logic();

	
	@Test
	public void testDetermineCommandType(){
		//Need to add test case		
	}
	
	@Test
	public void testExecute() throws IOException{
		//equivalence partitioning for invalid value partition(without command)
		assertEquals("invalid input", logic.execute("444"));
		assertEquals("invalid input", logic.execute("eeeee"));
		//equivalence partitioning for valid value partition (addtask command follow by dueDate)
		assertEquals("task \"ola\" successfully added", logic.execute("addtask ola -due 3/3/3/3/3"));

	}
	@Test
	public void testAddEvent(){
		// equivalence partitioning with valid date format dd/mm/yyyy HH/MM
		assertEquals("event \"ola\" successfully added", logic.execute("ola -from 12/12/1991 12:12 -to 12/12/1991 12:13"));
		//equivalence partitioning with invalid date format
		assertEquals("invalid format of date", logic.execute("ola -from 12/12/1991 12 -to 12/12/1991 12:13"));
	}

	
	@Test
	public void testClear() throws IOException{
		//assertEquals("tasks is clear", logic.clearTask());
	}
	
	@Test
	public void testSort() throws IOException{
		
	}
	
	@Test
	public void testSearch() throws IOException{

	}
	@Before
	public void beforeRunTest(){
	//	logic.clearTask();
	}
	@After
	public void afterRunTest(){
	//	logic.clearTask();
	}
}
