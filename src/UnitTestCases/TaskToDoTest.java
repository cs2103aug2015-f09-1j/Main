/**
 * 
 */
package UnitTestCases;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import executor.GetRepeat.RepeatingFrequency;
import main.jarvas.TaskToDo;

/**
 * 
 *
 */
//@@author A0126259B	
public class TaskToDoTest {
	TaskToDo task = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		task =new TaskToDo("Test1", "12/12/2015 00:11", 0, false, "weekly");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFrequency() {
		assertEquals(RepeatingFrequency.WEEKLY, task.getFrequency());
	}
	@Test
	public void testIndex(){
		assertEquals(0, task.getIndex());
		
	}
	@Test
	public void testIsDoned(){
		assertEquals(false, task.getDone());
		
	}
	@Test
	public void testName(){
		assertEquals("Test1", task.getName());
		
	}

}
