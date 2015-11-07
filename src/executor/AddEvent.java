/**
 * 
 */
package executor;

import main.jarvas.TaskEvent;
import java.util.Vector;
import main.jarvas.JParser;
import main.jarvas.Logic.RequiredField;
import executor.GetRepeat.RepeatingFrequency;

/**
 * @author A0145381H
 *
 */
public class AddEvent {
	private static final String MSG_ADDEVENT_SUCCESS = "event \"%1$s\" successfully added";
	private static final String MSG_ADDEVENT_FAIL = "Event date error";
	private int indexEvent;
	private String output;
	
	
	public AddEvent(String contentStr2, int index, Vector<TaskEvent> events) {
		indexEvent = index;
		String startDate = getStartDate(contentStr2);
		String endDate = getEndDate(contentStr2);
		TaskEvent temp;
		if(JParser.dateChecker(startDate, endDate)){
			if(GetRepeat.getRepeat(contentStr2)==RepeatingFrequency.NOTREPEATING){
				temp = new TaskEvent(GetSplittedString.getTask(contentStr2), startDate, endDate, ++indexEvent, false);
				events.add(temp);
				output = String.format(MSG_ADDEVENT_SUCCESS, temp.getName());
			}
			else if(getUntilDate(contentStr2).equals("")){
				temp = new TaskEvent(GetSplittedString.getTask(contentStr2), startDate, endDate, ++indexEvent, false, GetRepeat.getRepeat(contentStr2));
				events.add(temp);
				output = String.format(MSG_ADDEVENT_SUCCESS, temp.getName());
			} else{
				temp = new TaskEvent(GetSplittedString.getTask(contentStr2), startDate, endDate, ++indexEvent, false, GetRepeat.getRepeat(contentStr2),getUntilDate(contentStr2));
				events.add(temp);
				output = String.format(MSG_ADDEVENT_SUCCESS, temp.getName());
			}
		}
		else{
			output = String.format(MSG_ADDEVENT_FAIL);
		}
	}
	
	private String getUntilDate(String contentStr){
		GetSplittedString temp = new GetSplittedString(contentStr, RequiredField.UNTIL);
		return temp.getReturnStr();
	}
	public String getOutput(){
		return output;
	}
	
	
	public int getIndex(){
		return indexEvent;
	}

	/**
	 * @param contentStr2
	 * @return
	 */
	private String getStartDate(String contentStr2) {
		GetSplittedString gsString = new GetSplittedString(contentStr2, RequiredField.EVENT_STARTDATE);
		return gsString.getReturnStr();
	}
	

	/**
	 * @param contentStr2
	 * @return
	 */
	private String getEndDate(String contentStr2) {
		GetSplittedString gsString = new GetSplittedString(contentStr2, RequiredField.EVENT_ENDDATE);
		return gsString.getReturnStr();
	}
	
	
	
	/*private String getRepeatString(RepeatingFrequency repeat) {
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
			temp=" ";
			break;
		}
		return temp;
	}*/
	
	
}
