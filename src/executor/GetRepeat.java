/**
 * 
 */
package executor;

import executor.GetRepeat.RepeatingFrequency;
import main.jarvas.Logic.RequiredField;


/**
 * this class provide the repeat functionality for Jarvas
 *
 */
public class GetRepeat {


	public enum RepeatingFrequency {
		NOTREPEATING,DAILY,WEEKLY,MONTHLY,YEARLY,WRONG
	};
	
	public static RepeatingFrequency getRepeat(String contentStr2) {
		GetSplittedString gsString = new GetSplittedString(contentStr2, RequiredField.REPEAT);
		String temp = gsString.getReturnStr().trim();
		if(gsString.getOutput() == null){
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
		else{
			return RepeatingFrequency.WRONG;
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
