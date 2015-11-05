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
	
	public EditCommand(Vector<TaskToDo> task, Vector<TaskEvent> event, String contentStr2){
		tasks = task;
		events = event;
		contentStr = contentStr2;
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
		String[] contentStr3 = contentStr.split(SPLITSTRING);
		if(contentStr3 == null || contentStr3.length < 1){
			editWithInvalidFormat(contentStr3);	
		}
		else{
			editWithContent(contentStr3);
		}
	}
	
	
	private void editWithContent(String[] contentStr3) {
		if(contentStr3[0].equals(TASK)){
			editTask(contentStr3);
		}
		else if(contentStr3[0].equals(EVENT)){
			editEvent(contentStr3);			
		}
		else{
			editWithInvalidFormat(contentStr3);	
		}
	}
	
	
	private void editEvent(String[] contentStr3) {
		if(contentStr3.length < 4){
			editWithInvalidFormat(contentStr3);	
		}
		else{
			addEventWithField(contentStr3);
		}
	}
	
	
	private void addEventWithField(String[] contentStr3) {
		if(contentStr3[2].equals(NAME)){
			editEventName(contentStr3);
		}
		else if(contentStr3[2].equals(FROM)){
			editEventStartDate(contentStr3);
		}
		else if(contentStr3[2].equals(TO)){
			editEventEndDate(contentStr3);
		}
		else{
			editWithInvalidFormat(contentStr3);				
		}
	}
	
	
	private void editWithInvalidFormat(String[] contentStr3) {
		output = String.format(MSG_EDIT_INVALID_FORMAT);
		logger.log(Level.INFO, output);
	}
	
	
	private void editEventEndDate(String[] contentStr3) {
		if(events.size() < Integer.parseInt(contentStr3[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStr3[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = null;
			for(int i=3; i<contentStr3.length; i++){
				dateText += contentStr3[i] + " ";
			}
			events.get(Integer.parseInt(contentStr3[1])-1).setEnd(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	private void editEventStartDate(String[] contentStr3) {
		if(events.size() < Integer.parseInt(contentStr3[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStr3[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = null;
			for(int i=3; i<contentStr3.length; i++){
				dateText += contentStr3[i] + " ";
			}
			events.get(Integer.parseInt(contentStr3[1])-1).setStart(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
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
	
	
	private void editTask(String[] contentStr3) {
		if(contentStr3.length < 4){
			editWithInvalidFormat(contentStr3);	
		}
		else{
			editTaskWithField(contentStr3);
		}
	}
	
	
	private void editTaskWithField(String[] contentStr3) {
		if(contentStr3[2].equals(NAME)){
			ediTaskName(contentStr3);
		}
		else if(contentStr3[2].equals(DUE)){
			editTaskDueDate(contentStr3);
		}
		else{
			editTaskInvalidFormat(contentStr3);			
		}
	}
	
	
	private void editTaskInvalidFormat(String[] contentStr3) {
		output = String.format(MSG_EDIT_INVALID_FORMAT);	
		logger.log(Level.INFO, output);
	}
	
	
	private void editTaskDueDate(String[] contentStr3) {
		if(tasks.size() < Integer.parseInt(contentStr3[1])){
			output = String.format(MSG_TASK_NOTEXIST, contentStr3[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = null;
			for(int i=3; i<contentStr3.length; i++){
				dateText += contentStr3[i] + " ";
			}
			tasks.get(Integer.parseInt(contentStr3[1])-1).setStart(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	private void ediTaskName(String[] contentStr3) {
		if(tasks.size() < Integer.parseInt(contentStr3[1])){
			output = String.format(MSG_TASK_NOTEXIST, contentStr3[1]);
			logger.log(Level.INFO, output);
		}
		else{
			tasks.get(Integer.parseInt(contentStr3[1])-1).setName(contentStr3[3]);
			output = String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			logger.log(Level.INFO, output);
		}
	}
	
}
