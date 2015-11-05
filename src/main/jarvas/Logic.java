package main.jarvas;

import java.text.ParseException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.jarvas.TaskToDo.RepeatingFrequency;
import executor.AddCommand;
import executor.AddEvent;
import executor.AddTask;
import executor.ClearCommand;
import executor.DeleteCommand;
import executor.DigestInput;
import executor.EditCommand;
import executor.GetSplittedString;
import executor.SearchCommand;
import executor.UndoCommand;
/**
 * @author ONGJI_000
 *
 */
public class Logic {
	private static final String MSG_INVALID_INPUT = "invalid input";
	private static final String MSG_SAVE_SUCCESS = "File \"%1$s\" successfully saved";
	private static final String MSG_SAVE_FAILURE = "File \"%1$s\" is not saved";
	private static final String MSG_DONE_SUCCESS = " \"%1$s\" is marked";
	private static final String MSG_DONE_FAIL = " \"%1$s\" not marked";
	private static final String MSG_HELP =
			  "Add Task : add <name> -due  <date> -repeat <daily/weekly/monthly/yearly>\n"
			+ " Add Event: add <name> -from <date> -to <date>\n"
			+ " Delete   : delete task/event <index>\n"
			+ " Edit     : edit task/event <index> name/due/from/to <attribute>\n"
			+ " Display  : display\n"
			+ " Clear    : clear\n"
			+ " Mark     : mark task/event <index> <done/undone>\n"
			+ " Undo     : undo\n"
			+ " Exit     : exit";
			
	public enum RequiredField {
		TASKDUEDATE,TASKLOCATION,EVENT_STARTDATE,EVENT_ENDDATE,REPEAT
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
	Vector <TaskToDo> tasksForSearch;
	Vector <TaskEvent> eventsForSearch;
	boolean IsCommandSearch;
	public Logic(){
		storage = Storage.getInstance();
		storage.doStuff();
		getOriginalTasks();
		output = "";
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
		IsCommandSearch = false;
		DigestInput di = new DigestInput(input);
		commandType = di.getCommandType();
		commandStr = di.getCommandStr();
		contentStr = di.getContentStr();
		String output=null;
		switch(commandType){
			case ADD: 
				AddCommand adding = new AddCommand(contentStr, indexTask, indexEvent, tasks, events);
				output = adding.getOutput();
				break;
			case DELETE:
				DeleteCommand deleting = new DeleteCommand(contentStr, tasks, events);
				output = deleting.getOutput();
				break;
			case EDIT:
				EditCommand editing = new EditCommand(tasks, events, contentStr);
				output = editing.getOutput();
				break;
			case DISPLAY: 
				displayTask();
				break;
			case SEARCH:
				IsCommandSearch = true;
				SearchCommand searching = new SearchCommand(tasks, events, contentStr);
				output = searching.getOutput();
				tasksForSearch = searching.getTaskResult();
				eventsForSearch = searching.getEventResult();
				break;
			case HELP:
				output = displayHelp();
				break;
			case EXIT:
				System.exit(0);
			case CLEAR:
				ClearCommand clearing = new ClearCommand(tasks, events);
				output = clearing.getOutput();
				break;
			case MARK:
				output = doneTask(contentStr);
				break;
			case SAVE:
				output = saveFile(contentStr);
				break;
			case UNDO:
				UndoCommand undoing = new UndoCommand(storage);
				output =undoing.getOutput();
				getOriginalTasks();
				break;
			default:
				output = MSG_INVALID_INPUT;
				logger.log(Level.WARNING, output);
		}
		storage.processTasks(tasks,events);
		return output;
	}
	
	public boolean getIsCommandSearch(){
		return IsCommandSearch;
	}
	
	
	public Vector<TaskToDo> getTasksForSearch(){
		return tasksForSearch;
	}
	
	
	public Vector<TaskEvent> getEventsForSearch(){
		return eventsForSearch;
	}
	
	
	
	private String doneTask(String contentStr2){
		String[] contentStr3 = contentStr.split("\\s+");
		TaskToDo temp = tasks.get(Integer.parseInt(contentStr3[1])-1);
		if(contentStr3[0].equals("task")){
			tasks.get(Integer.parseInt(contentStr3[1])-1).setDone(contentStr3[2]);
			if(tasks.get(Integer.parseInt(contentStr3[1])-1).getFrequency()!=RepeatingFrequency.NOTREPEATING){
				tasks.add(new TaskToDo(temp.getName(), temp.nextDate(), ++indexTask, false, temp.getFrequency()));
			}
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
	
	
	public String saveFile(String contentStr2){
		if(storage.saveToLocation(contentStr2)){
			return String.format(MSG_SAVE_SUCCESS, contentStr2);
		}
		else{
			return String.format(MSG_SAVE_FAILURE, contentStr2);
		}
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
	 * This function initialize the vector for tasks
	 */
	public void getOriginalTasks() {
		Vector<TaskToDo> returnTask = storage.convertToTask();
		indexTask = returnTask.size();
		Vector<TaskEvent> returnEvent = null;
		returnEvent = storage.convertToEvent();
		indexEvent = returnEvent.size();
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
	
	

	private int[] convertDueDateStrtoIntarr(String dueDateStr) {
		String[] dueDateStrArr= dueDateStr.split("/");
		int[] dueDateIntArr=new int[dueDateStrArr.length];
		for(int i=0;i<dueDateStrArr.length;i++){
			dueDateIntArr[i] = Integer.parseInt(dueDateStrArr[i]);
		}
		return dueDateIntArr;
	}
}