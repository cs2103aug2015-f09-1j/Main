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


//@@author A0145381H
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogicTestTxt {
	Logic logic = null;
	private static final String MSG_HELP =
			  "Add Task	: add/a <name> -due/d  <date>\n"
			+ "                  -repeat/r <daily/weekly/monthly/yearly> -until/u <date>\n"
			+ " Add Event	: add/a <name> -from/f <date> -to/t <date>\n"
			+ "                  -repeat/r <daily/weekly/monthly/yearly> -until/u <date>\n"
			+ " Delete		: delete/d task/event <index>\n"
			+ " Edit		: edit/e task/event <index> name/due/from/to/repeat <attribute>\n"
			+ " Save		: save <filename>\n"
			+ " Clear		: clear\n"
			+ " Mark		: mark/m task/event <index> <done/undone>\n"
			+ " Search		: search/s <content>\n"
			+ " Undo		: undo\n"
			+ " Redo		: redo\n"
			+ " Exit		: exit";
			
	
	/**
	 * @throws java.lang.Exception
	 */
	//@@author A0126259B	
	@Before
	public void setUp() throws Exception {
		logic=new Logic();
		logic.saveFile("logicTest.txt");
		logic.execute("add go martket -due 12/12/2015 00:11 -repeat daily");
	}

	/**
	 * @throws java.lang.Exception
	 */
	//@@author A0126259B	
	@After
	public void tearDown() throws Exception {
		logic.execute("clear");
	}

	//@@author A0126159A
	@Test
	public void testExecute_add() {
		assertEquals("task \"go to park\" successfully added", 
				logic.execute("add go to park"));
		assertEquals("task \"do homework\" successfully added", 
				logic.execute("a do homework"));
		assertEquals("task \"go to the tutorial for SE\" successfully added", 
				logic.execute("add go to the tutorial for SE -due tommorrow"));
		assertEquals("task \"fix the bug in Jarvas\" successfully added", 
				logic.execute("a fix the bug in Jarvas -d 12/12/2015 00:11"));
		assertEquals("task \"finish the lab for OS(WEEKLY)\" successfully added", 
				logic.execute("add finish the lab for OS -due Monday 10am - repeat weekly"));
		assertEquals("task \"have breakfast(DAILY)\" successfully added", 
				logic.execute("a have breakfast -d tommorrow 9am -r daily"));
		assertEquals("Input is wrong format.", 
				logic.execute("add do homework -du 12/12/2015 00:11 -repeat daily"));
	}
	//@@author A0126259B	
	@Test
	public void testExecute_delete() {
		assertEquals("\"task 1\" successfully deleted", logic.execute("delete task 1" ));
	}
	
	//@@author A0126259B	
	@Test
	public void testExecute_edit(){
		assertEquals("\"task 1\" successfully edited", logic.execute("edit task 1 due 12/13/2015 00:11" ));
	}
	//@@author A0126259B	
	@Test
	public void testExecute_help(){
		assertEquals(MSG_HELP, logic.execute("help"));
	}
	//@@author A0126259B	
	@Test
	public void testExecute_mark(){
		assertEquals("\"task 1\" is marked", logic.execute("mark task 1 done"));
	}
	//@@author A0126259B	
	@Test
	public void testExecute_invalid(){
		assertEquals("invalid input", logic.execute("ola"));
	}
	
}
