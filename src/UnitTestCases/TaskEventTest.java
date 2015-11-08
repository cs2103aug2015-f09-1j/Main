package UnitTestCases;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.javafx.tk.Toolkit.Task;

import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * 
 */

/**
 * 
 *
 */
//@@author
public class TaskEventTest {
	TaskEvent event = null;
	@Before
	public void setUp() throws Exception {
		event = new TaskEvent("recruitment talk", "21/10/2015 10:00", 
				"23/10/2015 10:00", 1, false);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testIndex(){
		assertEquals(1, event.getIndex());
	}
	@Test
	public void testIsDoned(){
		assertEquals(false, event.getDone());
		
	}
	@Test
	public void testName(){
		assertEquals("recruitment talk", event.getName());
		
	}
//	@Test
//	public void testTaskEvent() {
//		
//		//This is the test for TaskEvent:: TaskEvent() in TaskEvent.java
//		TaskEvent event1 = new TaskEvent();
//		assertEquals(event1.getName(), null);
//		assertEquals(event1.getStartDate(), null);
//		assertEquals(event1.getEndDate(), null);
//		
//		//This is the test for TaskEvent::setName()
//		event1.setName("have tutorial");
//		assertEquals(event1.getName(), "have tutorial");
//
//		//This is the test for TaskEvent:: TaskEvent
//		//(String eventName , String startDate , String endDate)
//		try {
//			TaskEvent event2 = new TaskEvent("hello world",
//					"21/10/2015 10:00", "23/10/2015 10:00", 1, false);
//			assertEquals(event2.getName(), "hello world");
//			assertEquals(event2.getStartDate().toString(), 
//					"Wed Oct 21 10:00:00 CST 2015");
//			assertEquals(event2.getEndDate().toString(), 
//					"Fri Oct 23 10:00:00 CST 2015");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//This is the test for TaskEvent:: TaskEvent(String eventName , Date startDate , Date endDate)
//		TaskEvent event3 = new TaskEvent("catch cats", new Date(2015080300), new Date(2015120500), 2, false);
//		assertEquals(event3.getName(), "catch cats");
//		assertEquals(event3.getStartDate().toString(), "Sat Jan 24 15:44:40 CST 1970");
//		assertEquals(event3.getEndDate().toString(), "Sat Jan 24 15:45:20 CST 1970");
//		
//	}

}
