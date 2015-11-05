/**
 * 
 */
package executor;

import java.text.ParseException;
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
public class EditCommand {

	private static final String MSG_EDIT_SUCCESS = "\"%1$s\" successfully edited";
	private static final String MSG_TASK_NOTEXIST = "Task \"%1$s\" does not exist";
	private static final String MSG_EVENT_NOTEXIST = "Event \"%1$s\" does not exist";
	private static final String MSG_EDIT_INVALID_FORMAT = "The input format is not correct";
	private static final String TASK = "task";
	private static final String EVENT = "event";
	private static final String SPLITSTRING = "\\s+";
	private static final String NAME = "name";
	private static final String FROM = "from";
	private static final String TO = "to";
	private static final String DUE = "due";
	
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	
	Vector<TaskToDo> tasks;
	Vector<TaskEvent> events;
	String output;
	String contentStr;
	
	public EditCommand(Vector<TaskToDo> task, Vector<TaskEvent> event, String contentStr){
		tasks = task;
		events = event;
		this.contentStr = contentStr;
		edit();
	}
	
	
	public String getOutput(){
		return output;
	}
	/**
	 * This function edit the current task key in by user by name of task
	 * @param contentStr2
	 * 				content of task task to be edited
	 * @return msg of edit task's info
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	private void edit() {
		String[] contentStrArr = contentStr.split(SPLITSTRING);
		if(contentStrArr == null || contentStrArr.length < 1){
			editWithInvalidFormat(contentStrArr);	
		}
		else{
			editWithContent(contentStrArr);
		}
	}
	
	
	private void editWithContent(String[] contentStrArr) {
		if(contentStrArr[0].equals(TASK)){
			editTask(contentStrArr);
		}
		else if(contentStrArr[0].equals(EVENT)){
			editEvent(contentStrArr);			
		}
		else{
			editWithInvalidFormat(contentStrArr);	
		}
	}
	
	
	private void editEvent(String[] contentStrArr) {
		if(contentStrArr.length < 4){
			editWithInvalidFormat(contentStrArr);	
		}
		else{
			addEventWithField(contentStrArr);
		}
	}
	
	
	private void addEventWithField(String[] contentStrArr) {
		if(contentStrArr[2].equals(NAME)){
			editEventName(contentStrArr);
		}
		else if(contentStrArr[2].equals(FROM)){
			editEventStartDate(contentStrArr);
		}
		else if(contentStrArr[2].equals(TO)){
			editEventEndDate(contentStrArr);
		}
		else{
			editWithInvalidFormat(contentStrArr);				
		}
	}
	
	
	private void editWithInvalidFormat(String[] contentStr3) {
		output = String.format(MSG_EDIT_INVALID_FORMAT);
		logger.log(Level.INFO, output);
	}
	
	
	private void editEventEndDate(String[] contentStrArr) {
		if(events.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = null;
			for(int i=3; i<contentStrArr.length; i++){
				dateText += contentStrArr[i] + " ";
			}
			events.get(Integer.parseInt(contentStrArr[1])-1).setEnd(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[3]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	private void editEventStartDate(String[] contentStrArr) {
		if(events.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = null;
			for(int i=3; i<contentStrArr.length; i++){
				dateText += contentStrArr[i] + " ";
			}
			events.get(Integer.parseInt(contentStrArr[1])-1).setStart(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[3]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	private void editEventName(String[] contentStr3) {
		if(events.size() < Integer.parseInt(contentStr3[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStr3[1]);
			logger.log(Level.INFO, output);
		}
		else{
			events.get(Integer.parseInt(contentStr3[1])-1).setName(contentStr3[3]);
			output = String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	private void editTask(String[] contentStrArr) {
		if(contentStrArr.length < 4){
			editWithInvalidFormat(contentStrArr);	
		}
		else{
			editTaskWithField(contentStrArr);
		}
	}
	
	
	private void editTaskWithField(String[] contentStrArr) {
		if(contentStrArr[2].equals(NAME)){
			ediTaskName(contentStrArr);
		}
		else if(contentStrArr[2].equals(DUE)){
			editTaskDueDate(contentStrArr);
		}
		else{
			editTaskInvalidFormat(contentStrArr);			
		}
	}
	
	
	private void editTaskInvalidFormat(String[] contentStrArr) {
		output = String.format(MSG_EDIT_INVALID_FORMAT);	
		logger.log(Level.INFO, output);
	}
	
	
	private void editTaskDueDate(String[] contentStrArr) {
		if(tasks.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_TASK_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = null;
			for(int i=3; i<contentStrArr.length; i++){
				dateText += contentStrArr[i] + " ";
			}
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setStart(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[3]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	private void ediTaskName(String[] contentStrArr) {
		if(tasks.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_TASK_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setName(contentStrArr[3]);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[3]);
			logger.log(Level.INFO, output);
		}
	}
	
}
