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
 * @author ONGJI_000
 *
 */
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


	@Test
	public void testDetermineCommand(){
		assertEquals(CommandType.ADD, JParser.determineCommandType("add"));
		assertEquals(CommandType.DELETE, JParser.determineCommandType("delete"));
	}
	
	@Test
	public void testDeterminCommand_shortForm(){
		assertEquals(CommandType.ADD, JParser.determineCommandType("a"));
	}
	
	@Test
	public void testInvalidCommand(){
		assertEquals(CommandType.INVALID, JParser.determineCommandType("ola"));
	}

}
