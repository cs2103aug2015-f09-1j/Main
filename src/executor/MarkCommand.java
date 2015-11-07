/**
 * 
 */
package executor;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import main.jarvas.TaskEvent;
import main.jarvas.TaskToDo;
import executor.GetRepeat.RepeatingFrequency;

/**
 * @author A0145381H
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


	private void markWithWrongNumber() {
		output = String.format(MSG_DONE_WRONG_FORMAT);
	}


	private void markWithValidFormat(String[] contentStr3) {
		TaskToDo temp = null;
		TaskEvent tempEvent = null;
		if(!tasks.isEmpty())
				temp = tasks.get(Integer.parseInt(contentStr3[1])-1);
		if(!events.isEmpty())
				tempEvent = events.get(Integer.parseInt(contentStr3[1])-1);
		if(contentStr3[0].equals(TASK)){
			markTask(contentStr3, temp);
		}
		else if(contentStr3[0].equals(EVENT)){
			markEvent(contentStr3, tempEvent);
		}
		else{
			markWithWrongFormat();
		}
	}


	private void markWithWrongFormat() {
		output = String.format(MSG_DONE_FAIL, contentString);
	}


	private void markEvent(String[] contentStr3, TaskEvent temp) {
		if(Integer.parseInt(contentStr3[1]) > events.size() || Integer.parseInt(contentStr3[1]) < 1){
			markOutOfBound();
		}
		else{
			markEventWithCorrectIndex(contentStr3, temp);
		}
	}



	private void markOutOfBound() {
		output = String.format(MSG_DONE_OUT_OF_BOUND);
	}


	private void markTask(String[] contentStr3, TaskToDo temp) {
		if(Integer.parseInt(contentStr3[1]) > tasks.size() || Integer.parseInt(contentStr3[1]) < 1){
			markOutOfBound();
		}
		else{
			markTaskWithCorrectIndex(contentStr3, temp);
		}
	}


	private void markTaskWithCorrectIndex(String[] contentStr3, TaskToDo temp) {
		tasks.get(Integer.parseInt(contentStr3[1])-1).setDone(contentStr3[2]);
		int taskIndex = Integer.parseInt(contentStr3[1])-1;
		Date currentDate = temp.getStartDate();
		Date dateUntil = tasks.get(taskIndex).getUntilDate();
		
		if(tasks.get(taskIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING &&dateUntil!=null&& currentDate.before(dateUntil)){
			tasks.add(new TaskToDo(temp.getName(), temp.nextDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			tasks.remove(taskIndex);
		}else if(tasks.get(taskIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING &&dateUntil==null){
			tasks.add(new TaskToDo(temp.getName(), temp.nextDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			tasks.remove(taskIndex);
		}
		output = String.format(MSG_DONE_SUCCESS,  contentStr3[0]+ SPACE + contentStr3[1]);
	}

	private void markEventWithCorrectIndex(String[] contentStr3, TaskEvent temp) {
		events.get(Integer.parseInt(contentStr3[1])-1).setDone(contentStr3[2]);
		
		int eventIndex = Integer.parseInt(contentStr3[1])-1;
		Date dateUntil = tasks.get(eventIndex).getUntilDate();
		Date dateEnd = temp.getEndDate();
		if(events.get(eventIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING &&dateUntil !=null&& dateEnd.before(dateUntil)){			
			events.add(new TaskEvent(temp.getName(), temp.nextStartDate(), temp.nextEndDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			events.remove(eventIndex);
		} else if(events.get(eventIndex).getFrequency()!=RepeatingFrequency.NOTREPEATING && dateUntil==null){
			events.add(new TaskEvent(temp.getName(), temp.nextStartDate(), temp.nextEndDate(), ++indexTask, false, temp.getFrequency(),temp.getStringUntilDate()));
			events.remove(eventIndex);
		}
		output = String.format(MSG_DONE_SUCCESS, contentStr3[0]+ SPACE + contentStr3[1]);
	}

	

}
