package sample;



import java.util.Scanner;

/**
 * This class is used to show a simple UI.
 * It contains the constant Strings in public.
 * There are two APIs: 
 * showMessage(String str): void for main function to show the message.
 * getCommand(): String for logic part to get input.
 * @author Li Huiying
 */

public class UI {
	
	public static final String MESSAGE_WELCOME = "#####################\n# WELCOME TO JARVAS #\n#####################";
	public static final String MESSAGE_ADD = "added to mytextfile.txt: \"%s\"";
	public static final String MESSAGE_DELETE = "deleted from mytextfile.txt: \"%s\"";
	public static final String MESSAGE_DISPLAY_FORMAT = "%d. %s\n";
	public static final String MESSAGE_CLEAR = "all content deleted from %s";
	public static final String MESSAGE_SORTED = "Tasks sorted";
	public static final String MESSAGE_INVAILD_ARGUMENTS_NUMBER = "Enter mytextfile!";
	public static final String MESSAGE_INVALID_FILE = "Enter valid mytextfile!";
	public static final String MESSAGE_INVALID_COMMAND = "Invalid Command.";
	public static final String MESSAGE_IOEXCEPTION = "IOException.";
	public static final String MESSAGE_SEARCH = "Results of searching:\n";
	
	Scanner input;
	String command;
	UI(){
		input = new Scanner(System.in);
		System.out.println(MESSAGE_WELCOME);
	}
	public void showMessage(String str){
		System.out.println(str);
	}
	public String returnInput(){
		System.out.print("Please input your command: ");
		command = input.nextLine();
		return command;
	}
}
