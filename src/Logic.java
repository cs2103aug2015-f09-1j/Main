
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
	private static final String MSG_TASK_NOTEXIST = "task \"%1$s\" does not exist";
	private static final String MSG_TASK_CLEAR = "tasks is clear";
	private static final String MSG_EDIT_SUCCESS = "task \"%1$s\" successfully edited";
	private static final String MSG_HELP = 		
				"\n#####Commands for JARVAS:#####\n"
			+ "Add - Add task -due dd/mm/yyyy/hh/mm\n"
			+ "Delete - Delete task\n"
			+ "Edit - Edit task -due dd/mm/yyyy/hh/mm\n"
			+ "Display - Show the total tasks\n"
			+ "Exit - Quit the problem\n";
			
	enum RequiredField {
		TASKDUEDATE,TASKLOCATION,EVENT_STARTDATE,EVENT_ENDDATE
	};
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());

	String commandStr;
	String contentStr;
	String output;
	Storage storage;
	Parser.CommandType commandType;
	Vector <TaskToDo> tasks = new Vector <TaskToDo>();
	Vector <TaskEvent> events = new Vector<TaskEvent>();
	Logic(){
		storage = Storage.getInstance();
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
		commandType = Parser.determineCommandType(commandStr);
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
			case ADDTASK: 
				output = addTask(contentStr);
				break;
			case ADD_EVENT:
				output = addEvent(contentStr);
				break;
			case DELETE:
				output = deleteTask(contentStr);
				break;
			case EDIT:
				output = editTask(contentStr);
				break;
			case DISPLAY: 
				displayTask();
				break;
			case SEARCH:
				output = searchTask(contentStr);
			case HELP:
				displayHelp();
				break;
			case EXIT:
				System.exit(0);
			case CLEAR:
				output = clearTask();
			default:
				logger.log(Level.WARNING, "user invalid input");
				output = MSG_INVALID_INPUT;
		}
		storage.convertTaskToJSONObject(returnNewTasks());
		storage.convertEventToJSONObject(returnNewEvents());
		storage.saveToStorage();
		return output;
	}
	
	public String clearTask() {
		tasks.clear();
		events.clear();
		return MSG_TASK_CLEAR;
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
			temp = new TaskEvent(getTask(contentStr2), startDate, endDate);
		} catch (ParseException e) {
			System.err.println("invalid date format" + e.getMessage());
			logger.log(Level.WARNING, "invalid date format for event");
			return MSG_INVALID_DATEINPUT;
		}
		events.add(temp);
		return String.format(MSG_ADDEVENT_SUCCESS, temp.getName());
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

	/**
	 * This function add task input by user into storage
	 * @param contentStr
	 * 				is the content of user input to be store
	 * @return add success msg
	 */
	private String addTask(String contentStr){
		TaskToDo temp = new TaskToDo(getTask(contentStr).trim(),getDueDate(contentStr));
		tasks.add(temp);
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
	 */
	private String editTask(String contentStr2) {
		logger.log(Level.INFO, "edit task function");
		String taskDateToBeEdit = getSplittedString(contentStr2, RequiredField.TASKDUEDATE);
		String taskNameToBeEdit = getTask(contentStr2);
		int indexOfTask = getIndexofTask(taskNameToBeEdit);
		if(indexOfTask == -1){
			logger.log(Level.WARNING, "Task does not exist");
			return String.format(MSG_TASK_NOTEXIST, taskNameToBeEdit);
		} else {
			logger.log(Level.INFO, "Task edit successful");
			tasks.get(indexOfTask).setDueDate(taskDateToBeEdit);
			return String.format(MSG_EDIT_SUCCESS, taskNameToBeEdit);
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
		for(int i=0; i<tasks.size();i++){
			if(tasks.get(i).getName().equals(contentStr2)){
				tasks.remove(i);
				logger.log(Level.INFO, "Task ACTUALLY deleted");
			}
		}
		return String.format(MSG_DELETE_SUCCESS, contentStr2);
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
		Vector<TaskEvent> returnEvent = null;
		try {
			returnEvent = storage.convertToEvent();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
		String taskName = str.trim().substring(0, str.indexOf('-'));
		return taskName;
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