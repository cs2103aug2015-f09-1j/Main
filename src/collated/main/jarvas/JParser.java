package main.jarvas;
import java.util.logging.Logger;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.junit.Before;

import java.util.logging.Level;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import executor.GetRepeat.RepeatingFrequency;

//@@author A0126259B	
public class JParser {
	public static final String COMMAND_ADD = "add";
	public static final String COMMAND_ADD_SHORT = "a";
	public static final String COMMAND_DELETE = "delete";
	public static final String COMMAND_DELETE_SHORT = "d";
	public static final String COMMAND_CLEAR = "clear";
	public static final String COMMAND_FROM = "from";
	public static final String COMMAND_TO = "to";
	public static final String COMMAND_DISPLAY = "display";
	public static final String COMMAND_SORT = "sort";
	public static final String COMMAND_SEARCH = "search";
	public static final String COMMAND_SEARCH_SHORT = "s";
	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_EDIT_SHORT = "e";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_HELP = "help";
	public static final String COMMAND_HELP_SHORT = "h";
	public static final String COMMAND_MARK = "mark";
	public static final String COMMAND_MARK_SHORT = "m";
	public static final String COMMAND_SAVE = "save";
	public static final String COMMAND_UNDO = "undo";
	public static final String COMMAND_REDO = "redo";
	public static final String ERROR_COMMAND_EMPTY = "command type string cannot be empty!";
	//@@author A0126259B	
	public enum CommandType {
		ADD, EDIT, DELETE, SORT, SEARCH, INVALID, EXIT, CLEAR, HELP, DISPLAY , FROM , TO , SAVE, MARK, UNDO, REDO, 
	};
	

	//@@author A0126159A
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	static Parser parser = new Parser();
	//@@author A0126259B	
	public static CommandType determineCommandType(String commandTypeString) {
		if (commandTypeString == null){
			throw new Error();
		}
		
		if (commandTypeString.equalsIgnoreCase(COMMAND_ADD) || commandTypeString.equalsIgnoreCase(COMMAND_ADD_SHORT)) {
			return CommandType.ADD;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_EDIT) || commandTypeString.equalsIgnoreCase(COMMAND_EDIT_SHORT)) {
			return CommandType.EDIT;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_DELETE) || commandTypeString.equalsIgnoreCase(COMMAND_DELETE_SHORT)) {
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
		else if (commandTypeString.equalsIgnoreCase(COMMAND_SEARCH) || commandTypeString.equalsIgnoreCase(COMMAND_SEARCH_SHORT)) {
		 	return CommandType.SEARCH;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_HELP) || commandTypeString.equalsIgnoreCase(COMMAND_HELP_SHORT)) {
		 	return CommandType.HELP;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_SAVE)) {
		 	return CommandType.SAVE;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_MARK) || commandTypeString.equalsIgnoreCase(COMMAND_MARK_SHORT)) {
		 	return CommandType.MARK;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_UNDO)) {
		 	return CommandType.UNDO;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_REDO)) {
		 	return CommandType.REDO;
		} 
		else if (commandTypeString.equalsIgnoreCase(COMMAND_EXIT)) {
		 	return CommandType.EXIT;
		} 
		else {
			logger.log(Level.WARNING, "Invalid command entered by user");
			return CommandType.INVALID;
		}
	}
	
	//@@author A0134109N-reused
	public static Date dateConverter(String inputDate){
		List<DateGroup> groups = parser.parse(inputDate);
		Date convertedDate = null;
		
			for(DateGroup group:groups)  {
			    Date dates = group.getDates().get(0);    
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
	
	//@@author A0134109N
	public static boolean dateChecker(String startDate, String endDate){
		boolean checkBefore = true;
		boolean checkEquals= true;
		try {
			checkBefore = dateConverter(startDate).before(dateConverter(endDate));
			checkEquals = dateConverter(startDate).equals(dateConverter(endDate));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		if( checkBefore|| checkEquals ){
			return true;
		}
		else{
			return false;
		}
	}
	//@@author A0134109N
	public static String getRepeatString(RepeatingFrequency repeat) {
		// TODO Auto-generated method stub
		String temp=null;
		switch (repeat) {
		case DAILY:
			temp = "(DAILY)";
			break;
		case MONTHLY:
			temp = "(MONTHLY)";
			break;
		case YEARLY:
			temp = "(YEARLY)";
			break;
		case WEEKLY:
			temp = "(WEEKLY)";
			break;
		default:
			temp="";
			break;
		}
		return temp;
	}
	
}
