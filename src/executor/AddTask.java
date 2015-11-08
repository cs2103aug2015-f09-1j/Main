/**
 * 
 */
package executor;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.jarvas.JParser;
import main.jarvas.Logic;
import main.jarvas.TaskToDo;
import main.jarvas.Logic.RequiredField;
import executor.GetRepeat.RepeatingFrequency;


/**
 * this class perform add task functionality for jarvas
 *
 */
public class AddTask {

	private static final String MESSAGE_INPUT_WRONG_FORMAT = "Input is wrong format.";
	private static final String MSG_ADD_SUCCESS = "task \"%1$s\" successfully added";
	private static final String MSG_ADD_FAIL = "task date error";
	private static final Logger logger = Logger.getLogger(Logic.class.getName());

	private String output;
	private int indexTask;
	/**
	 * 
	 */
	public AddTask(String contentStr, int index, Vector<TaskToDo> task){
		indexTask = index;
		TaskToDo temp;
		if(getDueDate(contentStr) == null){
			output = MESSAGE_INPUT_WRONG_FORMAT;
		}
		else{
			if(getDueDate(contentStr).equals("") || JParser.dateChecker("today", getDueDate(contentStr))){
				if(getDueDate(contentStr).equals("")){
					temp = new TaskToDo(GetSplittedString.getTask(contentStr).trim(), ++indexTask, false);	
				}
				else if(GetRepeat.getRepeat(contentStr)==RepeatingFrequency.NOTREPEATING){
					temp = new TaskToDo(GetSplittedString.getTask(contentStr).trim(),getDueDate(contentStr), ++indexTask, false);
				}else if(getUntilDate(contentStr).equals("")){
					temp = new TaskToDo(GetSplittedString.getTask(contentStr).trim().concat(getRepeatString(GetRepeat.getRepeat(contentStr))),
							getDueDate(contentStr), ++indexTask, false,GetRepeat.getRepeat(contentStr));
				}else {
					temp = new TaskToDo(GetSplittedString.getTask(contentStr).trim().concat(getRepeatString(GetRepeat.getRepeat(contentStr))),
							getDueDate(contentStr), ++indexTask, false,GetRepeat.getRepeat(contentStr),getUntilDate(contentStr));
				}
				
				task.add(temp);
				logger.log(Level.INFO, "add task");
				output = String.format(MSG_ADD_SUCCESS,temp.getName());
			}
			else{
				output = String.format(MSG_ADD_FAIL);
			}
		}
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
		if(gsString.getOutput() == null){
			return gsString.getReturnStr();
		}
		else{
			return null;
		}
	}
	private String getUntilDate(String contentStr){
		GetSplittedString temp = new GetSplittedString(contentStr, RequiredField.UNTIL);
		return temp.getReturnStr();
	}
	

	/**
	 * this method convert the repeat frequency into string
	 * @param repeat enum RepeatingFrequency
	 * @return
	 * 		frequency in String
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
