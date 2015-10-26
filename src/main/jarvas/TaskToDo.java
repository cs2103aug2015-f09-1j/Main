package main.jarvas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Task class is a helper class
 */

public class TaskToDo implements Task{
	
	public static final String EMPTY_SPACE = " ";
	private static final String LABEL_TASK_NAME = "task name = ";
	private static final String LABEL_TASK_DUEDATE = "task due date = ";
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private String taskName;
	private int index;
	DateOfEvent dateOfEvent;
	
	public TaskToDo(){
		taskName = new String();
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setStartDate(null);
		index = 0;
	}
	
	public TaskToDo(String taskName, int index){
		setName(taskName);
		this.index = index;
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setStartDate(null);
	}
	
	public TaskToDo(String taskName, String dueDate,  int index){
		setName(taskName);
		this.index = index;
		dateOfEvent = new DateOfEvent();
		try {
			dateOfEvent.setStartDate(sdf.parse(dueDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getIndex(){
		return index;
	}
	
	@Override
	public String getName() {
		return taskName;
	}

	@Override
	public void setName(String taskName) {
		this.taskName = taskName;
	}

	public void setStart(String startDate) throws ParseException {
		dateOfEvent.setStartDate(sdf.parse(startDate));
	}	
	
	public Date getStartDate() {
		return dateOfEvent.getStartDate();
	}

	public String getStringStartDate(){
		return sdf.format(getStartDate());
	}
	
	@Override
	public String print() {
		String temp = taskName + EMPTY_SPACE + sdf.format(getStartDate()); 
		return temp;
	}
	
	/**
	 * This function return tasks in term of string
	 */
	@Override
	public String toString(){
		String temp = "";
		temp=temp.concat(String.format(LABEL_TASK_NAME, taskName));
		temp = temp.concat("    "+ String.format(LABEL_TASK_DUEDATE, getStringStartDate()));
		return temp;
		
	}
}
