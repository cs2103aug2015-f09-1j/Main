
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic {
	private static final String MSG_INVALID_INPUT = "invalid input";
	private static final String MSG_ADD_SUCCESS = "task \"%1$s\" successfully added";
	private static final String MSG_ADD_FAIL_ALRTHERE = "task \"%1$s\" is already added, no changes";
	private static final String MSG_DELETE_SUCCESS = "task \"%1$s\" successfully deleted";
	private static final String MSG_TASK_NOTEXIST = "task \"%1$s\" does not exist";
	private static final String MSG_EDIT_SUCCESS = "task \"%1$s\" successfully edited";
	private static final String MSG_HELP = 		
				"\n#####Commands for JARVAS:#####\n"
			+ "Add - Add task -due dd/mm/yyyy/hh/mm\n"
			+ "Delete - Delete task\n"
			+ "Edit - Edit task -due dd/mm/yyyy/hh/mm\n"
			+ "Display - Show the total tasks\n"
			+ "Exit - Quit the problem\n";
			
	enum RequiredField {
		TASKDUEDATE,TASKLOCATION
	};
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());

	String commandStr;
	String contentStr;
	String output = "";
	Storage storage;
	Parser.CommandType commandType;
	Vector <Task> tasks = new Vector <Task>();
	Logic(){
		storage = Storage.getInstance();
		getOriginalTasks();
	}
	private void digestInput(String str){
		commandStr = getFirstWord(str);	
		contentStr = removeFirstWord(str);
		commandType = Parser.determineCommandType(commandStr);
	}
	public String execute(String input){
		//Execute the command
		digestInput(input);
		String output=null;
		switch(commandType){
			case ADD: 
				// Add to vector
				output = addTask(contentStr);
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
			case HELP:
				displayHelp();
				break;
			case EXIT:
				System.exit(0);
			default:
				output = MSG_INVALID_INPUT;
		}
		storage.convertToJSONObject(returnNewTasks());
		storage.saveToStorage();
		return output;
	}
	
	// Methods for Execution of Instructions
	private String addTask(String contentStr){
		Task temp = new Task(getTask(contentStr).trim(),getDueDate(contentStr));
		tasks.add(temp);
		return String.format(MSG_ADD_SUCCESS,temp.getTaskName());
	}
	private String displayHelp(){
		return MSG_HELP;
	}
	public String displayTask(){
		//storage.display();
		String temp = "";
		for(int i=0;i<tasks.size();i++){
			temp = temp.concat(tasks.toString()+"\n");
		}
		return temp;
	}
	private String editTask(String contentStr2) {
		// TODO Auto-generated method stub
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
			//System.out.println("Task " + taskNameToBeEdit + " has been updated successfully.");
		}
		
	}
	private String deleteTask(String contentStr2) {
		// TODO Auto-generated method stub
		for(int i=0; i<tasks.size();i++){
			if(tasks.get(i).getTaskName().equals(contentStr2)){
				tasks.remove(i);
			}
		}
		return String.format(MSG_DELETE_SUCCESS, contentStr2);
	}
	private int getIndexofTask(String taskNameToBeEdit) {
		// TODO Auto-generated method stub
		int i;
		for(i=0; i<tasks.size();i++){
			if(tasks.get(i).getTaskName().equals(taskNameToBeEdit.trim())){
				break;
			}
		}
		if(i>=tasks.size()){
			return -1;
		}else{
			return i;
		}
		
	}
	public void getOriginalTasks() {
		//Initialize the vector for tasks
		Vector<Task> returnTask = storage.convertToVector();
		tasks = (Vector)returnTask.clone();
	}
	public Vector<Task> returnNewTasks() {
		//Return the new vector contains tasks after each operation
		return tasks;
	}
	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	private static String removeFirstWord(String userCommand) {
		String temp = userCommand.replace(getFirstWord(userCommand), "").trim();
		return temp;
	}
	private static String getTask(String str){
		String taskName = str.trim().substring(0, str.indexOf('-'));
		return taskName;
	}
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
	private static String getSplittedString(String str,RequiredField requiredField){
		String removedTaskName = str.replace(getTask(str), "");
		String[] strArr = removedTaskName.split("-");
		String returnStr = null;
		switch (requiredField) {
		case TASKDUEDATE:
			returnStr = getContent(strArr,"due ");
			break;
		case TASKLOCATION:
			returnStr = getContent(strArr, "at"	);
		default:
			break;
		}
		return returnStr;
	}
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