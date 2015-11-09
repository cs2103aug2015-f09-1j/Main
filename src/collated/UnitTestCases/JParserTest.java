/**
 * 
 */
package UnitTestCases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.jarvas.JParser;
import main.jarvas.JParser.CommandType;

/**
 * this class do Unit Testing for Jparser class
 *
 */
//@@author A0126259B	
public class JParserTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	//@@author A0126159A
	//white box approach, equivalent partition, function coverage
	@Test
	public void testDetermineCommand(){
		assertEquals(CommandType.ADD, JParser.determineCommandType("add"));
		assertEquals(CommandType.DELETE, JParser.determineCommandType("delete"));
		assertEquals(CommandType.EDIT, JParser.determineCommandType("edit"));
	}
	
	//@@author A0126159A
	@Test
	public void testDetermineCommand_shortForm(){
		assertEquals(CommandType.ADD, JParser.determineCommandType("a"));
	}
	
	@Test
	public void testInvalidCommand(){
		assertEquals(CommandType.INVALID, JParser.determineCommandType("ola"));
	}

}
