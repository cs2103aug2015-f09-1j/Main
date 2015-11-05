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
import executor.SearchCommand;
import executor.UndoCommand;
import executor.sortCommand;
/**
 * @author ONGJI_000
 *
 */
public class Logic {
	private static final String MSG_INVALID_INPUT = "invalid input";
	private static final String MSG_SAVE_SUCCESS = "File \"%1$s\" successfully saved";
	private static final String MSG_SAVE_FAILURE = "File \"%1$s\" is not saved";
	private static final String MSG_HELP =
			  "Add Task : add <name> -due  <date> -repeat <daily/weekly/monthly/yearly>\n"
			+ " Add Event: add <name> -from <date> -to <date>\n"
			+ " Delete   : delete task/event <index>\n"
			+ " Edit     : edit task/event <index> name/due/from/to <attribute>\n"
			+ " Save     : save <filename>\n"
			+ " Clear    : clear\n"
			+ " Mark     : mark task/event <index> <done/undone>\n"
			+ " Search   : search <content>\n"
			+ " Undo     : undo\n"
			+ " Exit     : exit";
			
	public enum RequiredField {
		TASKDUEDATE,TASKLOCATION,EVENT_STARTDATE,EVENT_ENDDATE,REPEAT
	};
	
	
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
	public String execute(String input){
	
		assert(input!=null):"input in excute function is null";
		logger.log(Level.INFO, "execute function");
		IsCommandSearch = false;
		DigestInput di = new DigestInput(input);
		commandType = di.getCommandType();
		contentStr = di.getContentStr();
		String output=null;
		switch(commandType){
			case ADD: 
				AddCommand adding = new AddCommand(contentStr, indexTask, indexEvent, tasks, events);
				indexTask = adding.getIndexTask();
				indexEvent = adding.getIndexEvent();
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
				MarkCommand marking = new MarkCommand(tasks, events, indexTask, indexEvent, contentStr);
				indexTask = marking.getIndexTask();
				indexEvent = marking.getIndexEvent();
				output = marking.getOutput();
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
		sortCommand sort = new sortCommand(tasks,events);
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
}