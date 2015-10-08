

import java.util.Vector;

public class Logic {
	
	String commandStr;
	String contentStr;
	String output = "";
	Parser.CommandType commandType;
	Vector <String> tasks = new Vector <String>();
	Logic(){
		System.out.println("Logic is ready.");
	}
	public void getInput(String str){
		commandStr = getFirstWord(str);	
		contentStr = removeFirstWord(str);
		commandType = Parser.determineCommandType(commandStr);
	}
	public void execute(Vector<String> currentTasks){
		//Execute the command
		switch(commandType){
			case ADD: 
				// Add to vector
				tasks.add(contentStr);
				break;
			case DELETE:
				for(int i=0; i<currentTasks.size(); i++){
					if(contentStr.equals(currentTasks.get(i))){
						System.out.println("TRUELA!");
						currentTasks.remove(i);
						tasks=(Vector)currentTasks.clone();
					}
				}
				break;
			/*case SORT:
			case SEARCH:
				// get content from storage
				
				// display output
				
				break;
			case EDIT: 
				// get content from storage
				
				// update content
				
				break;*/
			case EXIT:
				System.exit(0);
			default:
				System.out.println("Invalid Input\n");
		}
	}
	public void getOriginalTasks(Vector<String> returnTasks) {
		// TODO initialize the vector for tasks
		
	}
	public Vector<String> returnNewTasks() {
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
}
