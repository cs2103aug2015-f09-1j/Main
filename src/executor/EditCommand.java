/**
 * 
 */
package executor;

import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.jarvas.JParser;
import main.jarvas.Logic;
import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;

/**
 * this class provide edit functionality for jarvas
 *
 */
public class EditCommand {
	//method string that will be called for specific situation
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
	private static final String REPEAT = "repeat";
	
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	
	Vector<TaskToDo> tasks;
	Vector<TaskEvent> events;
	String output;
	String contentStr;
	
	//@@author A0134109N
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
	//@@author A0145381H
	private void edit() {
		String[] contentStrArr = contentStr.split(SPLITSTRING);
		if(contentStrArr == null || contentStrArr.length < 1){
			editWithInvalidFormat();	
		}
		else{
			editWithContent(contentStrArr);
		}
	}
	
	
	/**
	 * edit the task or event with given content
	 * @param contentStrArr is the content that splitted into array
	 */
	//@@author A0145381H
	private void editWithContent(String[] contentStrArr) {
		if(contentStrArr[0].equals(TASK)){
			editTask(contentStrArr);
		}
		else if(contentStrArr[0].equals(EVENT)){
			editEvent(contentStrArr);			
		}
		else{
			editWithInvalidFormat();	
		}
	}
	
	
	/**
	 * edit event with give content
	 * @param contentStrArr is the content that splitted into array
	 */
	//@@author A0145381H
	private void editEvent(String[] contentStrArr) {
		if(contentStrArr.length < 4){
			editWithInvalidFormat();	
		}
		else{
			addEventWithField(contentStrArr);
		}
	}
	
	
	/**
	 * edit the event from the given array
	 * @param contentStrArr is the content that splitted int array
	 */
	//@@author A0145381H
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
		else if(contentStrArr[2].equals(REPEAT)){
			editEventRepeat(contentStrArr);
		}
		else{
			editWithInvalidFormat();				
		}
	}
	
	
	/**
	 * @param contentStrArr
	 */
	//@@author A0134109N
	private void editEventRepeat(String[] contentStrArr) {
		String input = textFormer(contentStrArr,0);
		if(input.contains("until")){
			events.get(Integer.parseInt(contentStrArr[1])-1).setFrequency(GetRepeat.convertStrtoFrequency(contentStrArr[3]));
			input = textFormer(contentStrArr,5);
			events.get(Integer.parseInt(contentStrArr[1])-1).setUntil(input);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			events.get(Integer.parseInt(contentStrArr[1])-1).setFrequency(GetRepeat.convertStrtoFrequency(contentStrArr[3]));
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		String eventName = events.get(Integer.parseInt(contentStrArr[1])-1).getName();
		if(JParser.getRepeatString(GetRepeat.convertStrtoFrequency(contentStrArr[3]))== ""){
			events.get(Integer.parseInt(contentStrArr[1])-1).setName(eventName.replaceAll(" \\S*$", ""));
		}
		else{
			events.get(Integer.parseInt(contentStrArr[1])-1).setName(eventName + " " + JParser.getRepeatString(GetRepeat.convertStrtoFrequency(contentStrArr[3])));
		}
		
	}


	/*
	 * save the invalid msg into output 
	 * @param contentStr3
	 */
	//@@author A0145381H
	private void editWithInvalidFormat() {

		output = String.format(MSG_EDIT_INVALID_FORMAT);
		logger.log(Level.INFO, output);
	}
	
	
	/**
	 * edit the event's end date 
	 * @param contentStrArr	is the array that contain desire date
	 */
	//@@author A0134109N
	private void editEventEndDate(String[] contentStrArr) {
		if(events.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = textFormer(contentStrArr,3);
			events.get(Integer.parseInt(contentStrArr[1])-1).setEnd(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	/**
	 * edit the event's start date
	 * @param contentStrArr is the array that contain desire date
	 */
	//@@author A0134109N
	private void editEventStartDate(String[] contentStrArr) {
		if(events.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = textFormer(contentStrArr,3);
			events.get(Integer.parseInt(contentStrArr[1])-1).setStart(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	/**
	 * edit the name of event
	 * @param contentStrArr is the array that contain desire name
	 */
	//@@author A0134109N
	private void editEventName(String[] contentStrArr) {
		if(events.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_EVENT_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String name = textFormer(contentStrArr,3);
			events.get(Integer.parseInt(contentStrArr[1])-1).setName(name);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	/**
	 * edit the task
	 * @param contentStrArr is the content of user command
	 */
	//@@author A0145381H
	private void editTask(String[] contentStrArr) {
		if(contentStrArr.length < 4){
			editWithInvalidFormat();	
		}
		else{
			editTaskWithField(contentStrArr);
		}
	}
	
	
	/**
	 * edit the task with the given field
	 * @param contentStrArr is the content of array content the field
	 */
	//@@author A0145381H
	private void editTaskWithField(String[] contentStrArr) {
		if(contentStrArr[2].equals(NAME)){
			ediTaskName(contentStrArr);
		}
		else if(contentStrArr[2].equals(DUE)){
			editTaskDueDate(contentStrArr);
		}
		else if(contentStrArr[2].equals(REPEAT)){
			editTaskRepeat(contentStrArr);
		}
		else{
			editTaskInvalidFormat();			
		}
	}
	
	
	/**
	 * @param contentStrArr
	 */
	//@@author A0134109N
	private void editTaskRepeat(String[] contentStrArr) {
		String input = textFormer(contentStrArr,0);
		if(input.contains("until")){
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setFrequency(GetRepeat.convertStrtoFrequency(contentStrArr[3]));
			input = textFormer(contentStrArr,5);
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setUntil(input);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setFrequency(GetRepeat.convertStrtoFrequency(contentStrArr[3]));
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		String taskName = tasks.get(Integer.parseInt(contentStrArr[1])-1).getName();
		if(JParser.getRepeatString(GetRepeat.convertStrtoFrequency(contentStrArr[3]))== ""){
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setName(taskName.replaceAll(" \\S*$", ""));
		}
		else{
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setName(taskName + " " + JParser.getRepeatString(GetRepeat.convertStrtoFrequency(contentStrArr[3])));
		}
	}


	/*
	 * set the invalid msg into output
	 *
	 */
	//@@author A0145381H
	private void editTaskInvalidFormat() {
		output = String.format(MSG_EDIT_INVALID_FORMAT);	
		logger.log(Level.INFO, output);
	}
	
	
	/**
	 * edit task's due date
	 * @param contentStrArr is the content of array contain desire due date
	 */
	//@@author A0134109N
	private void editTaskDueDate(String[] contentStrArr) {
		if(tasks.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_TASK_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String dateText = textFormer(contentStrArr,3);
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setStart(dateText);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
	}
	
	
	/**
	 * edit the task name
	 * @param contentStrArr is the content of array content desire task name
	 */
	//@@author A0134109N
	private void ediTaskName(String[] contentStrArr) {
		if(tasks.size() < Integer.parseInt(contentStrArr[1])){
			output = String.format(MSG_TASK_NOTEXIST, contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
		else{
			String name = textFormer(contentStrArr,3);
			tasks.get(Integer.parseInt(contentStrArr[1])-1).setName(name);
			output = String.format(MSG_EDIT_SUCCESS, contentStrArr[0] + " " + contentStrArr[1]);
			logger.log(Level.INFO, output);
		}
	}
	//@@author A0134109N
	private String textFormer(String[] input, int start){
		String output = "";
		for(int i=start; i<input.length; i++){
			output += input[i] + " ";
		}
		System.out.println(output);
		return output;
	}
	
}
