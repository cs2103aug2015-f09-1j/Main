package main.jarvas;

import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author ONGJI_000
 *
 */
public class Logic {
	private static final String MSG_INVALID_INPUT = "invalid input";
	private static final String MSG_INVALID_DATEINPUT = "invalid date input";
	private static final String MSG_ADD_SUCCESS = "task \"%1$s\" successfully added";
	private static final String MSG_SEARCH_SUCCESS = "task \"%1$s\" found";
	private static final String MSG_SEARCH_FAIL = "task \"%1$s\" not found";
	private static final String MSG_ADDEVENT_SUCCESS = "event \"%1$s\" successfully added";
	private static final String MSG_ADD_FAIL_ALRTHERE = "task \"%1$s\" is already added, no changes";
	private static final String MSG_DELETE_SUCCESS = "task \"%1$s\" successfully deleted";
	private static final String MSG_DELETE_FAIL = "task \"%1$s\" failed to delete";
	private static final String MSG_TASK_NOTEXIST = "task \"%1$s\" does not exist";
	private static final String MSG_TASK_CLEAR = "tasks is clear";
	private static final String MSG_TASK_UNDO = "Undo success";
	private static final String MSG_EDIT_SUCCESS = "task \"%1$s\" successfully edited";
	private static final String MSG_SAVE_SUCCESS = "File \"%1$s\" successfully saved";
	private static final String MSG_SAVE_FAILURE = "File \"%1$s\" is not saved";
	private static final String MSG_DONE_SUCCESS = " \"%1$s\" is marked";
	private static final String MSG_DONE_FAIL = " \"%1$s\" not marked";
	private static final String MSG_HELP =
			"Add Task: add <task name> -due dd/mm/yyyy hh:mm\n"
			+ " Add Event: add <event name> -from dd/mm/yyyy hh:mm -to dd/mm/yyyy hh:mm\n"
			+ " Delete: delete <task/event> <index of item to be deleted>\n"
			+ " Edit task/event attribute: edit <task/event> <index> <name/due/from/to> <new attribute>\n"
			+ " Display all tasks/events: display\n"
			+ " Clear all tasks/events: clear\n"
			+ " Mark task/event as done: mark task/event <index> <done/undone>\n"
			+ " Undo most recent action: undo"
			+ " Exit the application: exit\n";
			
	enum RequiredField {
		TASKDUEDATE,TASKLOCATION,EVENT_STARTDATE,EVENT_ENDDATE
	};
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());

	String commandStr;
	String contentStr;
	String output;
	Storage storage;
	int indexEvent;
	int indexTask;
	JParser.CommandType commandType;
	Vector <TaskToDo> tasks = new Vector <TaskToDo>();
	Vector <TaskEvent> events = new Vector<TaskEvent>();
	public Logic(){
		storage = Storage.getInstance();
		storage.doStuff();
		getOriginalTasks();
		output = "";
	}
	
	/**
	 * This function digest the input command into content and command type
	 * @param str
	 * 			is user input
	 */
	private void digestInput(String str){
		commandStr = getFirstWord(str);	
		contentStr = removeFirstWord(str);
		commandType = JParser.determineCommandType(commandStr);
	}
	/**
	 * This function is called to execute users command
	 * @param input
	 * 			is user input
	 * @return message to be printed
	 */
	public String execute(String input){
	
		assert(input!=null):"input in excute function is null";
		logger.log(Level.INFO, "execute function");
		
		digestInput(input);
		String output=null;
		switch(commandType){
			case ADD: 
				output = addTodo(contentStr);
				break;
			case DELETE:
				output = deleteTask(contentStr);
				break;
			case EDIT:
				try {
					output = edit(contentStr);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case DISPLAY: 
				displayTask();
				break;
			case SEARCH:
				output = searchTask(contentStr);
			case HELP:
				output = displayHelp();
				break;
			case EXIT:
				System.exit(0);
			case CLEAR:
				output = clearTask();
				break;
			case MARK:
				output = doneTask(contentStr);
				break;
			case SAVE:
				output = saveFile(contentStr);
				break;
			case UNDO:
				output = undo();
				getOriginalTasks();
				break;
			default:
				logger.log(Level.WARNING, "user invalid input");
				output = MSG_INVALID_INPUT;
		}
		storage.processTasks(tasks,events);
		return output;
	}
	
	public String clearTask() {
		tasks.clear();
		events.clear();
		return MSG_TASK_CLEAR;
	}
	
	private String undo(){
		storage.undoStorage();
		return MSG_TASK_UNDO;
	}
	
	private String doneTask(String contentStr2){
		String[] contentStr3 = contentStr.split("\\s+");
		if(contentStr3[0].equals("task")){
			tasks.get(Integer.parseInt(contentStr3[1])-1).setDone(contentStr3[2]);
			return String.format(MSG_DONE_SUCCESS,  contentStr3[0]+ " " + contentStr3[1]);
		}
		else if(contentStr3[0].equals("event")){
			events.get(Integer.parseInt(contentStr3[1])-1).setDone(contentStr3[2]);
			return String.format(MSG_DONE_SUCCESS, contentStr3[0]+ " " + contentStr3[1]);
		}
		else{
			return String.format(MSG_DONE_FAIL, contentStr2);
		}
	}
	/**
	 * @param contentStr2
	 * @return
	 */
	private String addEvent(String contentStr2) {
		String startDate = getStartDate(contentStr2);
		String endDate = getEndDate(contentStr2);
		TaskEvent temp;
		try {
			temp = new TaskEvent(getTask(contentStr2), startDate, endDate, ++indexEvent, false);
			
		} catch (ParseException e) {
			System.err.println("invalid date format" + e.getMessage());
			logger.log(Level.WARNING, "invalid date format for event");
			return MSG_INVALID_DATEINPUT;
		}
		events.add(temp);
		return String.format(MSG_ADDEVENT_SUCCESS, temp.getName());
	}
	
	public String saveFile(String contentStr2){
		if(storage.saveToLocation(contentStr2)){
			return String.format(MSG_SAVE_SUCCESS, contentStr2);
		}
		else{
			return String.format(MSG_SAVE_FAILURE, contentStr2);
		}
	}
	
	/**
	 * @param contentStr2
	 * @return
	 */
	private String getEndDate(String contentStr2) {
		String endDate = getSplittedString(contentStr2, RequiredField.EVENT_ENDDATE);
		return endDate;
	}

	/**
	 * @param contentStr2
	 * @return
	 */
	private String getStartDate(String contentStr2) {
			String startDate = getSplittedString(contentStr2, RequiredField.EVENT_STARTDATE);
			return startDate;
	}
	public String addTodo(String contentStr){
		if(checkTodo(contentStr)){
			return addEvent(contentStr);
		}
		else{
			return addTask(contentStr);
		}
	}
	
	private boolean checkTodo(String contentStr){
		
		String tempTodo = contentStr.replace(getTask(contentStr), "");
		tempTodo = contentStr.replace("-", "");
		if(tempTodo.contains("from")){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * This function add task input by user into storage
	 * @param contentStr
	 * 				is the content of user input to be store
	 * @return add success msg
	 */
	private String addTask(String contentStr){
		TaskToDo temp;
		if(getDueDate(contentStr).equals("")){
			temp = new TaskToDo(getTask(contentStr).trim(), ++indexTask, false);	
		}
		else{
			temp = new TaskToDo(getTask(contentStr).trim(),getDueDate(contentStr), ++indexTask, false);
		}
		tasks.add(temp);
		System.out.println(temp.getIndex());
		logger.log(Level.INFO, "add task");
		return String.format(MSG_ADD_SUCCESS,temp.getName());
	}
	
	/**
	 * This function search input by user from Task and Event 
	 * @param contentStr
	 * 				is the content to be used for the search
	 * @return add success msg
	 */
	private String searchTask(String contentStr){
		// Search Task
		for(int i=0; i<tasks.size(); i++){
			if(getTask(contentStr).trim().equals(tasks.get(i).getName())){
				return String.format(MSG_SEARCH_SUCCESS,tasks.get(i).getName());
			}
		}
		// Search Event
		for(int i=0; i<events.size(); i++){
			if(getTask(contentStr).trim().equals(events.get(i).getName())){
				return String.format(MSG_SEARCH_SUCCESS,events.get(i).getName());
			}
		}
		return String.format(MSG_SEARCH_FAIL,getTask(contentStr).trim());
	}
	/**
	 * This function handle display help menu
	 * 
	 * @return help msg
	 */
	private String displayHelp(){
		logger.log(Level.INFO, "display help function");
		return MSG_HELP;
	}
	
	/**
	 * This function display all the current tasks
	 * 
	 * @return all tasks in String
	 */
	public String displayTask(){
		logger.log(Level.INFO, "display task function");
		String temp = "";
		for(int i=0;i<tasks.size();i++){
			temp = temp.concat(tasks.toString()+"\n");
		}
		return temp;
	}
	
	/**
	 * This function edit the current task key in by user by name of task
	 * @param contentStr2
	 * 				content of task task to be edited
	 * @return msg of edit task's info
	 * @throws ParseException 
	 * @throws NumberFormatException 
	 */
	private String edit(String contentStr2) throws NumberFormatException, ParseException {
		String[] contentStr3 = contentStr.split("\\s+");
		logger.log(Level.INFO, "edit task function");
		if(contentStr3[0].equals("task")){
			if(contentStr3[2].equals("name")){
				tasks.get(Integer.parseInt(contentStr3[1])-1).setName(contentStr3[3]);
				logger.log(Level.INFO, "Task edit successful");
				return String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			}
			else if(contentStr3[2].equals("due")){
				tasks.get(Integer.parseInt(contentStr3[1])-1).setStart(contentStr3[3] + " " + contentStr3[4]);
				logger.log(Level.INFO, "Task edit successful");
				return String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			}
			else{
				logger.log(Level.WARNING, "Task does not exist");
				return String.format(MSG_TASK_NOTEXIST, contentStr3[1]);				
			}
		}
		else if(contentStr3[0].equals("event")){
			if(contentStr3[2].equals("name")){
				events.get(Integer.parseInt(contentStr3[1])-1).setName(contentStr3[3]);
				logger.log(Level.INFO, "Event edit successful");
				return String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			}
			else if(contentStr3[2].equals("from")){
				events.get(Integer.parseInt(contentStr3[1])-1).setStart(contentStr3[3] + " " + contentStr3[4]);
				logger.log(Level.INFO, "Event edit successful");
				return String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			}
			else if(contentStr3[2].equals("to")){
				events.get(Integer.parseInt(contentStr3[1])-1).setEnd(contentStr3[3] + " " + contentStr3[4]);
				logger.log(Level.INFO, "Event edit successful");
				return String.format(MSG_EDIT_SUCCESS, contentStr3[3]);
			}
			else{
				logger.log(Level.WARNING, "Event does not exist");
				return String.format(MSG_TASK_NOTEXIST, contentStr3[1]);				
			}			
		}
		else{
			return String.format(MSG_TASK_NOTEXIST, contentStr3[1]);
		}
	}
	
	/**
	 * This function delete the current task required by user
	 * @param contentStr2
	 * 				the name of task to be deleted
	 * @return	msg of deleted task's info
	 */
	private String deleteTask(String contentStr2) {
		// TODO Auto-generated method stub
		String[] input = contentStr2.split("\\s+");
		int indexToDelete = Integer.parseInt(input[1]);
		if(input[0].equals("task")){
			tasks.remove(indexToDelete-1);
			logger.log(Level.INFO, "Task deleted");
			return String.format(MSG_DELETE_SUCCESS, contentStr2);		
		}
		else if(input[0].equals("event")){
			events.remove(indexToDelete-1);
			logger.log(Level.INFO, "Task Event deleted");		
			return String.format(MSG_DELETE_SUCCESS, contentStr2);
		}
		else{
			return String.format(MSG_DELETE_FAIL, contentStr2);	
		}
	}
	
	/**
	 * This function get the index of task by task name
	 * 
	 * @param taskNameToBeEdit
	 * 				is name of task 
	 * @return index of task within the vector
	 */
	private int getIndexofTask(String taskNameToBeEdit) {
		
		int i;
		for(i=0; i<tasks.size();i++){
			if(tasks.get(i).getName().equals(taskNameToBeEdit.trim())){
				break;
			}
		}
		
		assert(i>=0):"index of task is negative";
		if(i>=tasks.size()){
			logger.log(Level.INFO, "index of task not found");
			return -1;
		}else{
			return i;
		}
		
	}
	/**
	 * This fucntion initialise the vector for tasks
	 */
	public void getOriginalTasks() {
		Vector<TaskToDo> returnTask = storage.convertToTask();
		indexTask = returnTask.size();
		Vector<TaskEvent> returnEvent = null;
		try {
			returnEvent = storage.convertToEvent();
			indexEvent = returnEvent.size();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.log(Level.WARNING, "fail to load event vector in logic class");
			e.printStackTrace();
		}
		tasks = (Vector)returnTask.clone();
		events = (Vector)returnEvent.clone();
	}
	
	/**
	 * This function return the new vector contains tasks after each operation
	 * @return  new tasks vector
	 */
	public Vector<TaskToDo> returnNewTasks() {
		//Return the new vector contains tasks after each operation
		return tasks;
	}
	
	/**
	 * This function return the new vector contains tasks after each operation
	 * @return  new tasks vector
	 */
	public Vector<TaskEvent> returnNewEvents() {
		//Return the new vector contains tasks after each operation
		return events;
	}
	
	
	/**
	 * This function get the first word of input key in by user
	 * @param userCommand
	 * 			original input key in by user
	 * @return the first word of user's input
	 */
	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	/**
	 * This function remove the first word of user's input
	 * @param userCommand
	 * 			original input key in by user
	 * @return	the rest of String without first word
	 */
	private static String removeFirstWord(String userCommand) {
		String temp = userCommand.replace(getFirstWord(userCommand), "").trim();
		return temp;
	}
	
	/**
	 * This function get the task name from from user's input content
	 * @param str
	 * 			User's input
	 * @return task name
	 */
	private static String getTask(String str){
		if(str.contains("-")){
			String taskName = str.trim().substring(0, str.indexOf('-'));	
			return taskName;
		}
		else
			return str;
	}
	
	/**
	 * This function get the due date of task base on user's input content
	 * @param contentStr2
	 * 			user's input content
	 * @return the due date of task
	 */
	private String getDueDate(String contentStr2) {
		// TODO Auto-generated method stub
		String dueDateStr = getSplittedString(contentStr2, RequiredField.TASKDUEDATE);
		return dueDateStr;
	}

	private int[] convertDueDateStrtoIntarr(String dueDateStr) {
		String[] dueDateStrArr= dueDateStr.split("/");
		int[] dueDateIntArr=new int[dueDateStrArr.length];
		for(int i=0;i<dueDateStrArr.length;i++){
			dueDateIntArr[i] = Integer.parseInt(dueDateStrArr[i]);
		}
		return dueDateIntArr;
	}
	
	/**
	 * This function get the desire part of String
	 * @param str
	 * 			is the string to be splitted
	 * @param requiredField
	 * 			is the part of String that is required in term of RequiredField
	 * @return	desire part of string
	 */
	private static String getSplittedString(String str,RequiredField requiredField){
		try{
			String removedTaskName = str.replace(getTask(str), "");
			String[] strArr = removedTaskName.split("-");
			String returnStr = null;
			switch (requiredField) {
			case TASKDUEDATE:
				returnStr = getContent(strArr,"due ");
				break;
			case EVENT_STARTDATE:
				returnStr = getContent(strArr, "from ");
				break;
			case EVENT_ENDDATE:
				returnStr = getContent(strArr, "to ");
				break;
			case TASKLOCATION:
				returnStr = getContent(strArr, "at"	);
			default:
				logger.log(Level.INFO, "invalid RequiredField");
				break;
		}
		return returnStr;
		}catch(Exception e){
			return "";
		}
	}
	
	/**
	 * This fucntion get a string within array and remove the similar part of string
	 * @param arr
	 * 			is the array of string
	 * @param str
	 * 			is the desire String
	 * @return	the String after remove str
	 */
	private static String getContent(String[] arr, String str){
		int i=0;
		for(String s: arr){
			if(s.contains(str)){
				break;
			}
			i++;
		}
		String dueDateStr = arr[i].trim().replace(str, "");
		return dueDateStr;
	}
}