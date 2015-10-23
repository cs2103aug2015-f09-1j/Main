package UnitTestCases;
import static org.junit.Assert.*;

import java.util.Vector;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import main.jarvas.Storage;
import main.jarvas.TaskToDo;

/**
 * 
 */

/**
 * @author YiHong
 *
 */
public class StorageTest {
	Vector<TaskToDo> tasks = new Vector<TaskToDo>();
	
	@Test
	public void testStorageDefault() {
		Storage stg = new Storage();
		assertEquals(stg.newTask.size(), 1);
		assertEquals(stg.newEvent.size(),0);
		assertEquals(stg.filename, "Jarvas_Storage.txt");
	}
	
	@Before
	public void beforeTest(){
		Storage stg = new Storage();
		JSONObject test = new JSONObject();
		test.put("Task", "name");
		test.put("Date", "date");
	}
	
	@Test
	public void testConvertToTask(){
		Storage stg = new Storage();
		assertEquals(stg.convertToTask().toString(), "[task name =     task due date = ]");
	}
}
