/**
 * 
 */
package executor;


import java.util.Date;
import java.util.Vector;

import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;
import executor.GetRepeat.RepeatingFrequency;

/**
 * this class provide mark functionality to Jarvas
 *
 */
public class MarkCommand {
	
	private static final String MSG_DONE_SUCCESS = "\"%1$s\" is marked";
	private static final String MSG_DONE_FAIL = "\"%1$s\" not marked";
	private static final String MSG_DONE_WRONG_FORMAT = "The format of mark is not correct";
	private static final String MSG_DONE_OUT_OF_BOUND = "The index is out of bound.";
	private static final String SPLITSTRING = "\\s+";
	private static final String EVENT = "event";
	private static final String TASK = "task";
	private static final String SPACE = " ";
	
	String contentString;
	Vector<TaskToDo> tasks;
	Vector<TaskEvent> events;
	int indexTask;
	int indexEvent;
	String output;
	
	public MarkCommand(Vector<TaskToDo> task, Vector<TaskEvent> event, int taskIndex, int eventIndex, String contentStr2){
		tasks = task;
		events = event;
		indexTask = taskIndex;
		indexEvent = eventIndex;
		contentString = contentStr2;
		System.out.println(contentStr2);
		doneTask();
	}
	
	
	public String getOutput(){
		return output;
	}
	
	public int getIndexTask(){
		return indexTask;
	}
	
	
	public int getIndexEvent(){
		return indexEvent;
	}

	private void doneTask(){
		String[] contentStr3 = contentString.split(SPLITSTRING);
		if(contentStr3 == null){
			markWithWrongNumber();
		}
		else{
			markWithValidFormat(contentStr3);
		}
	}


	/**
	 * this method store the "invalid" msg into output
	 */
	private void markWithWrongNumber() {
		output = String.format(MSG_DONE_WRONG_FORMAT);
	}


	/**
	 * this method mark the task and event
	 * @param contentStrArr is the string array content the command	
	 */
	private void markWithValidFormat(String[] contentStrArr) {
		TaskToDo temp = null;
		TaskEvent tempEvent = null;
		try{
			if(!tasks.isEmpty() && Integer.parseInt(contentStrArr[1]) <= tasks.size())
				temp = tasks.get(Integer.parseInt(contentStrArr[1])-1);
			if(!events.isEmpty() && Integer.parseInt(contentStrArr[1]) <= events.size())
				tempEvent = events.get(Integer.parseInt(contentStrArr[1])-1);
			if(contentStrArr[0].equals(TASK)){
				markTask(contentStrArr, temp);
			}
			else if(contentStrArr[0].equals(EVENT)){
				markEvent(contentStrArr, tempEvent);
			}
			else{
				markWithWrongFormat();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			markWithWrongNumber();
		}
	}


	/**
	 * this method save the output as Wrong Format
	 */
	private void markWithWrongFormat() {
		output = String.format(MSG_DONE_FAIL, contentString);
	}


	/**
	 * this method mark the specific event 
	 * @param contentStr3	is the string array content the command
	 * @param temp	the event that will be marked
	 */
	private void markEvent(String[] contentStrArr, TaskEvent temp) {
		if(Integer.parseInt(contentStrArr[1]) > events.size() || Integer.parseInt(contentStrArr[1]) < 1){
			markOutOfBound();
		}
		else{
			markEventWithCorrectIndex(contentStrArr, temp);
		}
	}



	/**
	 * this method save the output as out of bound
	 */
	private void markOutOfBound() {
		output = String.format(MSG_DONE_OUT_OF_BOUND);
	}


	/**
	 * this method mark the specific task
	 * @param contentStr3 is the string array contain command
	 * @param temp the task to be marked
	 */
	private void markTask(String[] contentStrArr, TaskToDo temp) {
		if(Integer.parseInt(contentStrArr[1]) > tasks.size() || Integer.parseInt(contentStrArr[1]) < 1){
			markOutOfBound();
		}
		else{
			markTaskWithCorrectIndex(contentStrArr, temp);
		}
	}


	/**
	 * this method mark the task after validate input is valid
	 * @param contentStr3 is the string array contain commands
	 * @param temp the task to be marked
	 */
	private void markTaskWithCorrectIndex(String[] contentStr3, TaskToDo temp) {
		tasks.get(Integer.parseInt(contentStr3[1])-1).setDone(contentStr3[2]);
		int taskIndex = Integer.parseInt(contentStr3[1])-1;
		Date currentDate = temp.getStartDate();
		Date dateUntil = tasks.get(taskIndex).getUntilDate();
		
		if(tasks.get(taskIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING &&dateUntil!=null&& temp.nextCompareDate().before(dateUntil)){
			tasks.add(new TaskToDo(temp.getName(), temp.nextDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			tasks.remove(taskIndex);
		}else if(tasks.get(taskIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING &&dateUntil==null){
			tasks.add(new TaskToDo(temp.getName(), temp.nextDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			tasks.remove(taskIndex);
		}
		output = String.format(MSG_DONE_SUCCESS,  contentStr3[0]+ SPACE + contentStr3[1]);
	}

	/**
	 * this method mark the event after validate the input
	 * @param contentStr3 is the string array contain commands
	 * @param temp the task to be marked
	 */
	private void markEventWithCorrectIndex(String[] contentStr3, TaskEvent temp) {
		events.get(Integer.parseInt(contentStr3[1])-1).setDone(contentStr3[2]);
		
		int eventIndex = Integer.parseInt(contentStr3[1])-1;
		Date dateUntil = temp.getUntilDate();
		Date dateEnd = temp.getEndDate();
		if(events.get(eventIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING && dateUntil !=null && temp.nextCompareDate().before(dateUntil)){			
			events.add(new TaskEvent(temp.getName(), temp.nextStartDate(), temp.nextEndDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			events.remove(eventIndex);
		} else if(events.get(eventIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING && dateUntil==null){
			events.add(new TaskEvent(temp.getName(), temp.nextStartDate(), temp.nextEndDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			events.remove(eventIndex);
		}
		output = String.format(MSG_DONE_SUCCESS, contentStr3[0]+ SPACE + contentStr3[1]);
	}

	

}
