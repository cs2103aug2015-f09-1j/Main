/**
 * 
 */
package executor;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.jarvas.Logic;
import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * @author A0145381H
 *
 */
public class DeleteCommand {
	private static final String MSG_DELETE_SUCCESS = "\"%1$s\" successfully deleted";
	private static final String MSG_DELETE_FAIL = "\"%1$s\" failed to delete";
	private static final String MSG_INVALID_INPUT = "invalid input";
	private static final String TASK = "task";
	private static final String EVENT = "event";
	private static final String SPLITSTRING = "\\s+";

	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	private String output;
	
	
	/**
	 * This function delete the current task required by user
	 * @param contentStr2
	 * 				the name of task to be deleted
	 * @return	msg of deleted task's info
	 */
	public DeleteCommand(String contentStr2, Vector<TaskToDo> tasks, Vector<TaskEvent> events) {
		// TODO Auto-generated method stub
		String[] input = contentStr2.split(SPLITSTRING);
		if(input.length != 2){
			output = String.format(MSG_INVALID_INPUT);
		}
		else{
			int indexToDelete = Integer.parseInt(input[1]);
			if(input[0].equals(TASK)){
				if(tasks.size() < indexToDelete){
					output = String.format(MSG_DELETE_FAIL, contentStr2);
				}
				else{
					tasks.remove(indexToDelete-1);
					output = String.format(MSG_DELETE_SUCCESS, contentStr2);
					logger.log(Level.INFO, output);
				}
						
			}
			else if(input[0].equals(EVENT)){
				if(events.size() < indexToDelete){
					output = String.format(MSG_DELETE_FAIL, contentStr2);
				}
				else{
					events.remove(indexToDelete-1);
					output = String.format(MSG_DELETE_SUCCESS, contentStr2);
					logger.log(Level.INFO, output);
				}
			}
			else{
				output = String.format(MSG_INVALID_INPUT);	
			}
			
		}
	}
	
	public String getOutput(){
		return output;
	}
}
