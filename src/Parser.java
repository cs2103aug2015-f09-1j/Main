
/**
 * 
 */


public class Parser {
	
	public static final String COMMAND_ADD = "add";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_CLEAR = "clear";
	public static final String COMMAND_DISPLAY = "display";
	public static final String COMMAND_SORT = "sort";
	public static final String COMMAND_SEARCH = "search";
	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_EXIT = "exit";
	public static final String ERROR_COMMAND_EMPTY = "command type string cannot be empty!";
	
	public enum CommandType {
		ADD, EDIT, DELETE, SORT, SEARCH, INVALID, EXIT, CLEAR, DISPLAY
	};
	
	protected static CommandType determineCommandType(String commandTypeString) {
		if (commandTypeString == null){
			throw new Error();
		}
		
		if (commandTypeString.equalsIgnoreCase(COMMAND_ADD)) {
			return CommandType.ADD;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_EDIT)) {
			return CommandType.EDIT;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_DELETE)) {
		 	return CommandType.DELETE;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_CLEAR)) {
		 	return CommandType.CLEAR;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_DISPLAY)) {
		 	return CommandType.DISPLAY;
		}
		else if (commandTypeString.equalsIgnoreCase(COMMAND_SORT)) {
		 	return CommandType.SORT;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_SEARCH)) {
		 	return CommandType.SEARCH;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_EXIT)) {
		 	return CommandType.EXIT;
		} 
		else {
			return CommandType.INVALID;
		}
	}
	
	
}
