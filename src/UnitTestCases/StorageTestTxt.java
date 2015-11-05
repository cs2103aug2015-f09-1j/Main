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
 * 
 *
 */
public class StorageTestTxt {
	Logic logic = null;
	Storage storage = null;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		logic = new Logic();
		logic.saveFile("logicTest.txt");
		logic.execute("add go martket -due 12/12/2015 00:11 -repeat daily");
		logic.execute("add go buy milk -due 12/13/2015 00:11 -repeat weekly");
		storage = Storage.getInstance();
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		logic.execute("clear");
	}

	@Test
	public void testFileName(){
		assertEquals(0,storage.newEvent.size());
		assertEquals(Storage.filename, "logicTest.txt");
	}
	@Test
	public void testSaveToLocation(){
		assertEquals(true, storage.saveToLocation("logicTest.txt"));
		
	}
	@Test
	public void testSingleton(){
		assertEquals(storage, Storage.getInstance());
	}

}
