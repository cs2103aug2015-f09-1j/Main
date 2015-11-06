package main.jarvas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import executor.GetRepeat;
import executor.GetRepeat.RepeatingFrequency;

/**
 * Task class is a helper class
 */

public class TaskToDo implements Task, Comparable<TaskToDo>{
	
	public static final String EMPTY_SPACE = " ";
	private static final String LABEL_TASK_NAME = "task name = ";
	private static final String LABEL_TASK_DUEDATE = "task due date = ";
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	private String taskName;
	private int index;
	DateOfEvent dateOfEvent;
	private boolean done;
	RepeatingFrequency frequency;

	public TaskToDo(){
		taskName = new String();
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setStartDate(null);
		index = 0;
		frequency=RepeatingFrequency.NOTREPEATING;
	}
	
	public TaskToDo(String taskName, int index, boolean status){
		setName(taskName);
		this.index = index;
		dateOfEvent = new DateOfEvent();
		done = status;
		frequency=RepeatingFrequency.NOTREPEATING;
	}
	
	public TaskToDo(String taskName, String dueDate,  int index, boolean status){
		setName(taskName);
		this.index = index;
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setStartDate(JParser.dateConverter(dueDate));
		done = status;
		frequency=RepeatingFrequency.NOTREPEATING;
	}
	public TaskToDo(String taskName, String dueDate,  int index, boolean status,RepeatingFrequency frequency){
		setName(taskName);
		this.index = index;
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setStartDate(JParser.dateConverter(dueDate));
		done = status;
		this.frequency=frequency;
	}
	/**
	 * @param name
	 * @param date
	 * @param parseInt
	 * @param done2
	 * @param frequency2
	 */
	public TaskToDo(String taskName, String dueDate, int index, boolean status, String frequency) {
		// TODO Auto-generated constructor stub
		setName(taskName);
		this.index = index;
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setStartDate(JParser.dateConverter(dueDate));
		done = status;
		this.frequency= GetRepeat.convertStrtoFrequency(frequency);
	}

	/**
	 * @param frequency2
	 * @return
	 */



	public RepeatingFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(RepeatingFrequency frequency) {
		this.frequency = frequency;
	}
	public int getIndex(){
		return index;
	}
	
	public void setDone(String status){
		if(status.equals("done")){
			done = true;
		}
		else{
			done = false;
		}
	}
	
	public boolean getDone(){
		return done;
	}
	@Override
	public String getName() {
		return taskName;
	}

	@Override
	public void setName(String taskName) {
		this.taskName = taskName;
	}

	public void setStart(String startDate){
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
	}	
	
	public Date getStartDate() {
		return dateOfEvent.getStartDate();
	}

	public String getStringStartDate(){
		if(getStartDate()!=null){
			return sdf.format(getStartDate());	
		}
		else{
			return "";
		}
	}
	public String nextDate(){
		return getNextDate();
	}
	public String getNextDate(){
		GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(getStartDate());
	    switch (frequency) {
		case DAILY:
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			break;
		case MONTHLY:
			calendar.add(Calendar.MONTH, 1);
			break;
		case YEARLY:
			calendar.add(Calendar.YEAR, 1);
			break;
		case WEEKLY:
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			break;
		default:
			
			break;
		}
		return sdf.format(calendar.getTime());
	}
	
	public String getStrFrequency(){
		String temp=null;
		switch (frequency) {
		case DAILY:
			temp = "daily";
			break;
		case MONTHLY:
			temp = "monthly";
			break;
		case YEARLY:
			temp = "yearly";
			break;
		case WEEKLY:
			temp = "weekly";
			break;
		default:
			temp="not repeating";
			break;
		}
		return temp;
	}
	
	public void setNextDate(){
		try {
			dateOfEvent.setStartDate(sdf.parse(getNextDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	@Override
	public int compareTo(TaskToDo o) {
		if(getStartDate() == null){
			return 1;
		}
		return getStartDate().compareTo(o.getStartDate());
	}
}
