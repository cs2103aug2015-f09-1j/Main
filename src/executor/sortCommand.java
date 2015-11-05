/**
 * 
 */
package executor;

import java.util.Vector;
import java.util.Collections;
import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * @author YiHong
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
	 * @param events2
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