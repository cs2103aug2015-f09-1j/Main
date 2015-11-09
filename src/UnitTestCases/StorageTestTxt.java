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
public class StorageTestTxt {
	Logic logic = null;
	Storage storage = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void storageTester() throws Exception {
		logic = new Logic();
		logic.saveFile("storage.txt");
		logic.execute("add attend lecture -due tomorrow -repeat weekly");
		logic.execute("add attend tutorial -due day after tomorrow");
		logic.execute("add attend lab -from today 2pm -to today 4pm");
		storage = Storage.getInstance();
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Test
	public void checkFileName(){
		assertEquals(2,storage.newTask.size());
		assertEquals(1,storage.newEvent.size());
		assertEquals(Storage.filename, "storage.txt");
	}
	@Test
	public void storageLocation(){
		assertEquals(true, storage.saveToLocation("storage.txt"));
		
	}
	
	//@@author A0126259B	
	@Test
	public void testSingleton(){
		assertEquals(storage, Storage.getInstance());
	}
	
	@After
	public void tearDown() throws Exception {
		logic.execute("clear");
	}

	
	
}
