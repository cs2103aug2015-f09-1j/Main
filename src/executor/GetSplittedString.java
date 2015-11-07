/**
 * 
 */
package executor;

import java.util.logging.Level;
import java.util.logging.Logger;

import main.jarvas.Logic;
import main.jarvas.Logic.RequiredField;

/**
 * @author Li
 *
 *
 * This Class get the desire part of String
 * @param str
 * 			is the string to be splitted
 * @param requiredField
 * 			is the part of String that is required in term of RequiredField
 * @return	desire part of string
 */
public class GetSplittedString {
	private static final String MESSAGE_INPUT_WRONG_FORMAT = "Input is wrong format.";
	
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	private String output;
	private String returnStr;
	private String returnStr1;
	public GetSplittedString(String str,RequiredField requiredField){
		try{
			String removedTaskName = str.replace(getTask(str), "");
			String[] strArr = removedTaskName.split("-");
			switch (requiredField) {
			case TASKDUEDATE:
				returnStr = getContent(strArr,"due ");
				returnStr1 = getContent(strArr,"d ");
				if(returnStr.equals("")){
					returnStr = returnStr1;
				}
				break;
			case EVENT_STARTDATE:
				returnStr = getContent(strArr, "from ");
				returnStr1 = getContent(strArr,"f ");
				if(returnStr.equals("")){
					returnStr = returnStr1;
				}
				break;
			case EVENT_ENDDATE:
				returnStr = getContent(strArr, "to ");
				returnStr1 = getContent(strArr,"t ");
				if(returnStr.equals("")){
					returnStr = returnStr1;
				}
				break;
			case TASKLOCATION:
				returnStr = getContent(strArr, "at"	);
				returnStr1 = getContent(strArr,"a ");
				if(returnStr.equals("")){
					returnStr = returnStr1;
				}
				break;
			case REPEAT:
				returnStr = getContent(strArr, "repeat");
				returnStr1 = getContent(strArr,"r ");
				if(returnStr.equals("")){
					returnStr = returnStr1;
				}
				break;
			case UNTIL:
				returnStr = getContent(strArr,"until ");
				returnStr1 = getContent(strArr,"u ");
				if(returnStr.equals("")){
					returnStr = returnStr1;
				}
				break;
			default:
				logger.log(Level.INFO, "invalid RequiredField");
				output = "invalid RequiredField";
				break;
			}
			if(strArr.length > 1 && returnStr.equals("")){
				output = MESSAGE_INPUT_WRONG_FORMAT;
			}
		}catch(Exception e){
			returnStr = "";
		}
	}
	
	public String getReturnStr(){
		return returnStr;
	}
	
	public String getOutput(){
		return output;
	}
	/**
	 * This function get a string within array and remove the similar part of string
	 * @param arr
	 * 			is the array of string
	 * @param str
	 * 			is the desire String
	 * @return	the String after remove str
	 */
	private static String getContent(String[] arr, String str){
		int i=0;
		for(String s: arr){
			if(s.contains(str)){
				break;
			}
			i++;
		}
		String dueDateStr;
		if(i >= arr.length){
			dueDateStr = "";
		}
		else{
			dueDateStr = arr[i].trim().replace(str, "");
		}
		return dueDateStr;
	}
	/**
	 * This function get the task name from from user's input content
	 * @param str
	 * 			User's input
	 * @return task name
	 */
	public static String getTask(String str){
		if(str.contains("-")){
			String taskName = str.trim().substring(0, str.indexOf('-') - 1);	
			return taskName;
		}
		else
			return str;
	}
}
