/**
 * 
 */
package executor;

import executor.GetRepeat.RepeatingFrequency;
import main.jarvas.Logic.RequiredField;


/**
 * @author Li
 *
 */
public class GetRepeat {

	/**
	 * @param contentStr2
	 * @return
	 */
	public enum RepeatingFrequency {
		NOTREPEATING,DAILY,WEEKLY,MONTHLY,YEARLY
	};
	
	public static RepeatingFrequency getRepeat(String contentStr2) {
		// TODO Auto-generated method stub
		GetSplittedString gsString = new GetSplittedString(contentStr2, RequiredField.REPEAT);
		String temp = gsString.getReturnStr().trim();
		switch (temp) {
		case "weekly":
			return RepeatingFrequency.WEEKLY;
		case "monthly":
			return RepeatingFrequency.MONTHLY;
		case "daily":
			return RepeatingFrequency.DAILY;
		case "yearly":
			return RepeatingFrequency.YEARLY;
		default:
			return RepeatingFrequency.NOTREPEATING;
		}
	}
	
	public static RepeatingFrequency convertStrtoFrequency(String frequency) {
		switch (frequency) {
		case "weekly":
			return RepeatingFrequency.WEEKLY;
		case "monthly":
			return RepeatingFrequency.MONTHLY;
		case "daily":
			return RepeatingFrequency.DAILY;
		case "yearly":
			return RepeatingFrequency.YEARLY;
		default:
			return RepeatingFrequency.NOTREPEATING;
		}
	}
	

	
}
