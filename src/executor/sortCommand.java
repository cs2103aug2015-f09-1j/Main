/**
 * 
 */
package executor;

import java.util.Vector;
import java.util.Collections;
import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * this class provide sort functionality to Jarvas
 *
 */
public class sortCommand{
	
	Vector<TaskToDo> tasks;
	Vector<TaskEvent> events;

	public sortCommand(Vector<TaskToDo> tasks, Vector<TaskEvent> events){
		this.tasks = tasks;
		this.events = events;
		sortAll();
	}

	
	/**
	 * this method sort all the content inside vector tasks and events
	 */
	private void sortAll() {
		Collections.sort(tasks);
		Collections.sort(events);
	}

	
	public Vector<TaskToDo> returnTasks(){
		return tasks;
	}
	
	public Vector<TaskEvent> returnEvents(){
		return events;
	}
	
	

}