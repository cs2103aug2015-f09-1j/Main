import java.util.GregorianCalendar;
import java.util.Vector;

public class Logic {
	
	enum RequiredField {
		TASKDUEDATE,TASKLOCATION
	};

	String commandStr;
	String contentStr;
	String output = "";
	Parser.CommandType commandType;
	Vector <Task> tasks = new Vector <Task>();
	Logic(){
		System.out.println("Logic is ready.");
	}
	public void getInput(String str){
		commandStr = getFirstWord(str);	
		contentStr = removeFirstWord(str);
		commandType = Parser.determineCommandType(commandStr);
	}
	public void execute(){
		//Execute the command
		switch(commandType){
			case ADD: 
				// Add to vector
				Task temp = new Task(getTask(contentStr).trim(),getDueDate(contentStr));
				tasks.add(temp);
				break;
			case DELETE:
				deleteTask(contentStr);
				
				break;
			case EDIT:
				editTask(contentStr);
				break;
			/*case SORT:
			case SEARCH:
				// get content from storage
				
				// display output
				
				break;*/
			case DISPLAY: 
				displayTask(tasks);
				break;
			case HELP:
				displayHelp();
				break;
			case EXIT:
				System.exit(0);
			default:
				System.out.println("Invalid Input\n");
		}
	}
	
	private void displayHelp(){
		System.out.println("\n#####Commands for JARVAS:#####");
		System.out.println("Add - Add task -due dd/mm/yyyy/hh/mm");
		System.out.println("Delete - Delete task");
		System.out.println("Edit - Edit task -due dd/mm/yyyy/hh/mm");
		System.out.println("Display - Show the total tasks.");
		System.out.println("Exit - Quit the problem");
	}
	
	private void displayTask(Vector<Task> tasks){
		
		System.out.println("###############################");
		for(int i=0; i<tasks.size(); i++){
			System.out.println( tasks.get(i).getTaskName() + " ");
		}
		System.out.println("###############################");
	}
	private void editTask(String contentStr2) {
		// TODO Auto-generated method stub
		String taskDateToBeEdit = getSplittedString(contentStr2, RequiredField.TASKDUEDATE);
		String taskNameToBeEdit = getTask(contentStr2);
		int indexOfTask = getIndexofTask(taskNameToBeEdit);
		if(indexOfTask == -1){
			System.out.println("no such task");
		} else {
			int[] dueDateIntArr = convertDueDateStrtoIntarr(taskDateToBeEdit);
			tasks.get(indexOfTask).setDueDate(new GregorianCalendar(
					dueDateIntArr[0],
					dueDateIntArr[1], 
					dueDateIntArr[2], 
					dueDateIntArr[3], 
					dueDateIntArr[4]));
			System.out.println("Task " + taskNameToBeEdit + " has been updated successfully.");
		}
		
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
	private void deleteTask(String contentStr2) {
		// TODO Auto-generated method stub
		for(int i=0; i<tasks.size();i++){
			if(tasks.get(i).getTaskName().equals(contentStr2)){
				tasks.remove(i);
			}
		}
	}
	public void getOriginalTasks(Vector<String> returnTasks) {
		//Initialize the vector for tasks
		for(int i=0; i<returnTasks.size(); i++){
			Task temp = new Task(getTask(returnTasks.get(i)),getDueDate(returnTasks.get(i)));
			tasks.add(temp);			
		}
	}
	public Vector<Task> returnNewTasks() {
		//Return the new vector contains tasks after each operation
		return tasks;
	}
	public void getOutput(String returnOutput) {
		//Deal with the output from storage
		output = returnOutput;
	}
	public String returnOutput() {
		/** TODO return the feedback to ui to print on screen
		 * (this should contains the feedback from storage, 
		 * use \n to make a new line in the string)
		 **/
		return output;
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
	private GregorianCalendar getDueDate(String contentStr2) {
		// TODO Auto-generated method stub
		String dueDateStr = getSplittedString(contentStr2, RequiredField.TASKDUEDATE);
		int[] dueDateIntArr = convertDueDateStrtoIntarr(dueDateStr);
		GregorianCalendar temp = new GregorianCalendar(
				dueDateIntArr[0],
				dueDateIntArr[1],
				dueDateIntArr[2],
				dueDateIntArr[3],
				dueDateIntArr[4]);
		return temp;
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