package main.jarvas;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import executor.AddCommand;
import executor.ClearCommand;
import executor.DeleteCommand;
import executor.DigestInput;
import executor.EditCommand;
import executor.MarkCommand;
import executor.RedoCommand;
import executor.SearchCommand;
import executor.UndoCommand;
import executor.sortCommand;


//@@author JQ
public class Logic {
	private static final String MSG_INVALID_INPUT = "invalid input";
	private static final String MSG_SAVE_SUCCESS = "File \"%1$s\" successfully saved";
	private static final String MSG_SAVE_FAILURE = "File \"%1$s\" is not saved";
	private static final String MSG_HELP =
			  "Add Task	: add/a <name> -due/d  <date>\n"
			+ "                  -repeat/r <daily/weekly/monthly/yearly> -until/u <date>\n"
			+ " Add Event	: add/a <name> -from/f <date> -to/t <date>\n"
			+ "                  -repeat/r <daily/weekly/monthly/yearly> -until/u <date>\n"
			+ " Delete		: delete/d task/event <index>\n"
			+ " Edit		: edit/e task/event <index> name/due/from/to/repeat <attribute>\n"
			+ " Save		: save <filename>\n"
			+ " Clear		: clear\n"
			+ " Mark		: mark/m task/event <index> <done/undone>\n"
			+ " Search		: search/s <content>\n"
			+ " Undo		: undo\n"
			+ " Redo		: redo\n"
			+ " Exit		: exit";
			
	public enum RequiredField {
		TASKDUEDATE,TASKLOCATION,EVENT_STARTDATE,EVENT_ENDDATE,REPEAT,UNTIL
	};
	
	//@@author
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
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
	//@@author Jaime
	public String execute(String input){
	
		assert(input!=null):"input in excute function is null";
		logger.log(Level.INFO, "execute function");
		IsCommandSearch = false;
		DigestInput di = new DigestInput(input);
		commandType = di.getCommandType();
		contentStr = di.getContentStr();
		String output=null;
		Boolean status = true;
		switch(commandType){
			case ADD: 
				output = executeAdd();
				break;
			case DELETE:
				output = executeDelete();
				break;
			case EDIT:
				output = executeEdit();
				break;
			case DISPLAY: 
				displayTask();
				break;
			case SEARCH:
				output = executeSearch();
				break;
			case HELP:
				output = displayHelp();
				break;
			case EXIT:
				System.exit(0);
			case CLEAR:
				output = executeClear();
				break;
			case MARK:
				output = executeMark();
				break;
			case SAVE:
				output = saveFile(contentStr);
				break;
			case UNDO:
				output = executeUndo();
				break;
			case REDO:
				output = executeRedo();
				break;
			default:
				status = false;
				output = MSG_INVALID_INPUT;
				logger.log(Level.WARNING, output);
		}
		sortCommand sort = new sortCommand(tasks,events);
		storage.processTasks(tasks,events,status);
		return output;
	}
	//@@author A0134109N
	private String executeRedo() {
		String output;
		RedoCommand redoing = new RedoCommand(storage);
		output =redoing.getOutput();
		getOriginalTasks();
		return output;
	}

	//@@author A0134109N
	private String executeUndo() {
		String output;
		UndoCommand undoing = new UndoCommand(storage);
		output =undoing.getOutput();
		getOriginalTasks();
		return output;
	}

	private String executeMark() {
		String output;
		MarkCommand marking = new MarkCommand(tasks, events, indexTask, indexEvent, contentStr);
		indexTask = marking.getIndexTask();
		indexEvent = marking.getIndexEvent();
		output = marking.getOutput();
		return output;
	}

	private String executeEdit() {
		String output;
		EditCommand editing = new EditCommand(tasks, events, contentStr);
		output = editing.getOutput();
		return output;
	}

	private String executeClear() {
		String output;
		ClearCommand clearing = new ClearCommand(tasks, events);
		output = clearing.getOutput();
		return output;
	}

	private String executeSearch() {
		String output;
		IsCommandSearch = true;
		SearchCommand searching = new SearchCommand(tasks, events, contentStr);
		output = searching.getOutput();
		tasksForSearch = searching.getTaskResult();
		eventsForSearch = searching.getEventResult();
		return output;
	}

	private String executeDelete() {
		String output;
		DeleteCommand deleting = new DeleteCommand(contentStr, tasks, events);
		output = deleting.getOutput();
		return output;
	}

	private String executeAdd() {
		String output;
		AddCommand adding = new AddCommand(contentStr, indexTask, indexEvent, tasks, events);
		indexTask = adding.getIndexTask();
		indexEvent = adding.getIndexEvent();
		output = adding.getOutput();
		return output;
	}
	

	//@@author A0145381H
	public boolean getIsCommandSearch(){
		return IsCommandSearch;
	}
	

	//@@author A0145381H
	public Vector<TaskToDo> getTasksForSearch(){
		return tasksForSearch;
	}
	

	//@@author A0145381H
	public Vector<TaskEvent> getEventsForSearch(){
		return eventsForSearch;
	}
	
	//@@author A0134109N
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
	//@@author
	private String displayHelp(){
		logger.log(Level.INFO, "display help function");
		return MSG_HELP;
	}
	
	/**
	 * This function display all the current tasks
	 * 
	 * @return all tasks in String
	 */
	//@@author
	public String displayTask(){
		logger.log(Level.INFO, "display task function");
		String temp = "";
		for(int i=0;i<tasks.size();i++){
			temp = temp.concat(tasks.toString()+"\n");
		}
		return temp;
	}
	
	/** 
	 * This function initialize the vector for tasks
	 */
	//@@author A0134109N
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
	//@@author Jaime
	public Vector<TaskToDo> returnNewTasks() {
		//Return the new vector contains tasks after each operation
		return tasks;
	}
	
	/**
	 * This function return the new vector contains tasks after each operation
	 * @return  new tasks vector
	 */
	//@@author Jaime
	public Vector<TaskEvent> returnNewEvents() {
		//Return the new vector contains tasks after each operation
		return events;
	}
}