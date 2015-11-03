/**
 * 
 */
package executor;

import main.jarvas.Logic.RequiredField;
import main.jarvas.TaskToDo.RepeatingFrequency;

/**
 * @author Li
 *
 */
public class GetRepeat {

	/**
	 * @param contentStr2
	 * @return
	 */
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
}
