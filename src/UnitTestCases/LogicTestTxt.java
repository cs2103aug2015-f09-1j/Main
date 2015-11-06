/**
 * 
 */
package UnitTestCases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import executor.ClearCommand;
import executor.DeleteCommand;
import executor.MarkCommand;
import main.jarvas.Logic;

/**
 * 
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogicTestTxt {
	Logic logic = null;
	private static final String MSG_HELP =
			  "Add Task : add <name> -due  <date> -repeat <daily/weekly/monthly/yearly>\n"
			+ " Add Event: add <name> -from <date> -to <date>\n"
			+ " Delete   : delete task/event <index>\n"
			+ " Edit     : edit task/event <index> name/due/from/to <attribute>\n"
			+ " Save     : save <filename>\n"
			+ " Clear    : clear\n"
			+ " Mark     : mark task/event <index> <done/undone>\n"
			+ " Search   : search <content>\n"
			+ " Undo     : undo\n"
			+ " Exit     : exit";
			
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		logic=new Logic();
		logic.saveFile("logicTest.txt");
		logic.execute("add go martket -due 12/12/2015 00:11 -repeat daily");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		logic.execute("clear");
	}

	@Test
	public void testExecute_add() {
		assertEquals("task \"do homework(DAILY)\" successfully added", 
				logic.execute("add do homework -due 12/12/2015 00:11 -repeat daily"));
	}
	
	@Test
	public void testExecute_delete() {
		assertEquals("\"task 1\" successfully deleted", logic.execute("delete task 1" ));
	}
	
	@Test
	public void testExecute_edit(){
		assertEquals("\"12/13/2015\" successfully edited", logic.execute("edit task 1 due 12/13/2015 00:11" ));
	}
	@Test
	public void testExecute_help(){
		assertEquals(MSG_HELP, logic.execute("help"));
	}
	@Test
	public void testExecute_mark(){
		assertEquals("\"task 1\" is marked", logic.execute("mark task 1 done"));
	}
	@Test
	public void testExecute_invalid(){
		assertEquals("invalid input", logic.execute("ola"));
	}
	
}
