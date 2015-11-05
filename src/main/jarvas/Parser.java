package main.jarvas;
/**
 * 
 */


public class Parser {
	
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
	public static final String COMMAND_EDIT = "edit";
	public static final String COMMAND_EDIT_SHORT = "e";
	public static final String COMMAND_EXIT = "exit";
	public static final String COMMAND_HELP = "help";
	public static final String COMMAND_HELP_SHORT = "h";
	public static final String COMMAND_MARK = "mark";
	public static final String COMMAND_MARK_SHORT = "m";
	public static final String COMMAND_SAVE = "save";
	public static final String COMMAND_UNDO = "undo";
	public static final String ERROR_COMMAND_EMPTY = "command type string cannot be empty!";
	
	public enum CommandType {
		ADD, EDIT, DELETE, SORT, SEARCH, INVALID, EXIT, CLEAR, HELP, DISPLAY , FROM , TO , SAVE, MARK, UNDO
	};
	
	
	
	
}
