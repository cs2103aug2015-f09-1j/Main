import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Date;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Li
 *
 */
public class TaskEventTest {

	@Test
	public void testTaskEvent() {
		
		//This is the test for TaskEvent:: TaskEvent() in TaskEvent.java
		TaskEvent event1 = new TaskEvent();
		assertEquals(event1.getName(), null);
		assertEquals(event1.getStartDate(), null);
		assertEquals(event1.getEndDate(), null);
		
		//This is the test for TaskEvent::setName()
		event1.setName("have tutorial");
		assertEquals(event1.getName(), "have tutorial");

		//This is the test for TaskEvent:: TaskEvent
		//(String eventName , String startDate , String endDate)
		try {
			TaskEvent event2 = new TaskEvent("hello world",
					"21/10/2015 10:00", "23/10/2015 10:00");
			assertEquals(event2.getName(), "hello world");
			assertEquals(event2.getStartDate().toString(), 
					"Wed Oct 21 10:00:00 CST 2015");
			assertEquals(event2.getEndDate().toString(), 
					"Fri Oct 23 10:00:00 CST 2015");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//This is the test for TaskEvent:: TaskEvent(String eventName , Date startDate , Date endDate)
		TaskEvent event3 = new TaskEvent("catch cats", new Date(2015080300), new Date(2015120500));
		assertEquals(event3.getName(), "catch cats");
		assertEquals(event3.getStartDate().toString(), "Sat Jan 24 15:44:40 CST 1970");
		assertEquals(event3.getEndDate().toString(), "Sat Jan 24 15:45:20 CST 1970");
		
	}

}
