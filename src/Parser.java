
/**
 * 
 */


public class Parser {
	enum CommandType {
		ADD, EDIT, DELETE,SORT,SEARCH, INVALID, EXIT
	};
	
	protected static CommandType determineCommandType(String commandTypeString) {
		if (commandTypeString == null){
			throw new Error("command type string cannot be null!");
		}
		
		if (commandTypeString.equalsIgnoreCase("add")) {
			return CommandType.ADD;
		} else if (commandTypeString.equalsIgnoreCase("edit")) {
			return CommandType.EDIT;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
		 	return CommandType.DELETE;
		}else if (commandTypeString.equalsIgnoreCase("sort")) {
		 	return CommandType.SORT;
		} else if (commandTypeString.equalsIgnoreCase("search")) {
		 	return CommandType.SEARCH;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
		 	return CommandType.EXIT;
		} else {
			return CommandType.INVALID;
		}
	}
	
	
}
