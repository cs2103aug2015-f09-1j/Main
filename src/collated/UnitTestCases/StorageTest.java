/**
 * 
 */
package UnitTestCases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.jarvas.Logic;
import main.jarvas.Storage;
import sun.security.jca.GetInstance;
import sun.util.logging.resources.logging;

/**
 * this Storage class Unit testing included logicTest.txt
 *
 */
//@@author A0134109N	
public class StorageTest {
	Logic logic = null;
	Storage storage = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void storageTester() throws Exception {
		logic = new Logic();
		logic.saveFile("storageTest.txt");
		logic.execute("add do homework -due tomorrow 2pm");
		logic.execute("add do tutorial -due next week friday");
		logic.execute("add attend lab -from today 2pm -to today 4pm");
		logic.execute("delete task 1");
		logic.execute("delete event 1");
		storage = Storage.getInstance();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void checkFileName(){
		assertEquals(1,storage.newTask.size());
		assertEquals(0,storage.newEvent.size());
		assertEquals(Storage.filename, "storageTest.txt");
	}
	@Test
	public void storageLocation(){
		assertEquals(true, storage.saveToLocation("storageTest.txt"));
		
	}
	
	@Test
	public void testSingleton(){
		assertEquals(storage, Storage.getInstance());
	}
	
	@After
	public void tearDown() throws Exception {
		logic.execute("clear");
	}

	
	
}
