/**
 * 
 */
package executor;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.jarvas.Logic;
import main.jarvas.TaskToDo;
import main.jarvas.Logic.RequiredField;
import main.jarvas.TaskToDo.RepeatingFrequency;

/**
 * @author Li
 *
 */
public class AddTask {

	private static final String MSG_ADD_SUCCESS = "task \"%1$s\" successfully added";

	private static final Logger logger = Logger.getLogger(Logic.class.getName());

	private String output;
	private int indexTask;
	/**
	 * 
	 */
	public AddTask(String contentStr, int index, Vector<TaskToDo> task){
		indexTask = index;
		TaskToDo temp;
		if(getDueDate(contentStr).equals("")){
			temp = new TaskToDo(GetSplittedString.getTask(contentStr).trim(), ++indexTask, false);	
		}
		else if(GetRepeat.getRepeat(contentStr)==RepeatingFrequency.NOTREPEATING){
			temp = new TaskToDo(GetSplittedString.getTask(contentStr).trim(),getDueDate(contentStr), ++indexTask, false);
		}else {
			temp = new TaskToDo(GetSplittedString.getTask(contentStr).trim().concat(getRepeatString(GetRepeat.getRepeat(contentStr))),
					getDueDate(contentStr), ++indexTask, false,GetRepeat.getRepeat(contentStr));
		}
		
		task.add(temp);
		logger.log(Level.INFO, "add task");
		output = String.format(MSG_ADD_SUCCESS,temp.getName());
	}
	
	public int getIndex(){
		return indexTask;
	}
	
	
	public String getOutput(){
		return output;
	}
	/**
	 * This function get the due date of task base on user's input content
	 * @param contentStr2
	 * 			user's input content
	 * @return the due date of task
	 */
	private String getDueDate(String contentStr2) {
		// TODO Auto-generated method stub
		GetSplittedString gsString = new GetSplittedString(contentStr2, RequiredField.TASKDUEDATE);
		return gsString.getReturnStr();
	}
	

	/**
	 * @param repeat
	 * @return
	 */
	private String getRepeatString(RepeatingFrequency repeat) {
		// TODO Auto-generated method stub
		String temp=null;
		switch (repeat) {
		case DAILY:
			temp = "(DAILY)";
			break;
		case MONTHLY:
			temp = "(MONTHLY)";
			break;
		case YEARLY:
			temp = "(YEARLY)";
			break;
		case WEEKLY:
			temp = "(WEEKLY)";
			break;
		default:
			temp=" ";
			break;
		}
		return temp;
	}
	
}
