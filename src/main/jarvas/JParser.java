package main.jarvas;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;
/**
 * 
 */


public class JParser {
	
	public static final String COMMAND_ADD = "add";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_CLEAR = "clear";
	public static final String COMMAND_FROM = "from";
	public static final String COMMAND_TO = "to";
	public static final String COMMAND_DISPLAY = "display";
	public static final String COMMAND_SORT = "sort";
	public static final String COMMAND_SEARCH = "search";
	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_HELP = "help";
	public static final String COMMAND_MARK = "mark";
	public static final String COMMAND_SAVE = "save";
	public static final String COMMAND_UNDO = "undo";
	public static final String ERROR_COMMAND_EMPTY = "command type string cannot be empty!";
	
	public enum CommandType {
		ADD, EDIT, DELETE, SORT, SEARCH, INVALID, EXIT, CLEAR, HELP, DISPLAY , FROM , TO , SAVE, MARK, UNDO
	};
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	static Parser parser = new Parser();
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
		else if (commandTypeString.equalsIgnoreCase(COMMAND_HELP)) {
		 	return CommandType.HELP;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_SAVE)) {
		 	return CommandType.SAVE;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_MARK)) {
		 	return CommandType.MARK;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_UNDO)) {
		 	return CommandType.UNDO;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_EXIT)) {
		 	return CommandType.EXIT;
		} 
		else {
			logger.log(Level.WARNING, "Invalid command entered by user");
			return CommandType.INVALID;
		}
	}
	
	public static Date dateConverter(String inputDate){
		List<DateGroup> groups = parser.parse(inputDate);
		Date convertedDate = null;
	    for(DateGroup group:groups)  {
		    Date dates = group.getDates().get(0);    
		    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mma");
		    System.out.println(format.format(dates));
		    int line = group.getLine();
		    int column = group.getPosition();
		    String matchingValue = group.getText();
		    String syntaxTree = group.getSyntaxTree().toStringTree();
		    Map parseMap = group.getParseLocations();
		    boolean isRecurreing = group.isRecurring();
		    Date recursUntil = group.getRecursUntil();
		    convertedDate = dates;
		    
	       }
		return convertedDate;
	}
	
}
