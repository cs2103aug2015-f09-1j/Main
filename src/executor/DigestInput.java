/**
 * 
 */
package executor;

import main.jarvas.JParser;

/**
 * This Class digest the input command into content and command type
 *
 */
//@@author A0126259B	
public class DigestInput {
	private String commandStr;
	private String contentStr;
	private JParser.CommandType commandType; 
	//@@author A0126259B	
	public DigestInput(String str){
		commandStr = getFirstWord(str);	
		contentStr = removeFirstWord(str);
		commandType = JParser.determineCommandType(commandStr);
	}
	//@@author A0126259B	
	public String getCommandStr(){
		return commandStr;
	}
	
	//@@author A0126259B	
	public String getContentStr(){
		return contentStr;
	}
	
	//@@author A0126259B	
	public JParser.CommandType getCommandType(){
		return commandType;
	}
	
	/**
	 * This function get the first word of input key in by user
	 * @param userCommand
	 * 			original input key in by user
	 * @return the first word of user's input
	 */
	//@@author A0126259B	
	private String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	

	/**
	 * This function remove the first word of user's input
	 * @param userCommand
	 * 			original input key in by user
	 * @return	the rest of String without first word
	 */
	//@@author A0126259B	
	private String removeFirstWord(String userCommand) {
		int len = getFirstWord(userCommand).length();
		String temp = userCommand.substring(len).trim();
		return temp;
	}
}
