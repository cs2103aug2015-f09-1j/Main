package main.jarvas;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import executor.GetRepeat;
import executor.GetRepeat.RepeatingFrequency;

public class TaskEvent implements Task, Comparable<TaskEvent>{
	private static final Logger logger = Logger.getLogger(TaskEvent.class.getName());
	private static final String WORD_FROM = " from ";
	private static final String WORD_TO = " to ";
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	
	int index;
	String eventName;
	DateOfEvent dateOfEvent;
	private boolean done;
	RepeatingFrequency frequency;
	
	public TaskEvent() {
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setEndDate(null);
		dateOfEvent.setStartDate(null);
		frequency=RepeatingFrequency.NOTREPEATING;
	}
	public TaskEvent(String eventName , String startDate , String endDate,
			int index, boolean status){
			dateOfEvent = new DateOfEvent();
			this.index = index;
			this.eventName= eventName;
			dateOfEvent.setStartDate(JParser.dateConverter(startDate));
			dateOfEvent.setEndDate(JParser.dateConverter(endDate));
			done = status;
			frequency=RepeatingFrequency.NOTREPEATING;
	}
	
	public TaskEvent(String eventName , String startDate , String endDate, int index, boolean status, String frequency){
		dateOfEvent = new DateOfEvent();
		this.index = index;
		this.eventName= eventName;
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
		done = status;
		this.frequency=GetRepeat.convertStrtoFrequency(frequency);

	}
	
	public TaskEvent(String eventName , String startDate , String endDate, int index, boolean status, RepeatingFrequency frequency){
		dateOfEvent = new DateOfEvent();
		this.index = index;
		this.eventName= eventName;
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
		done = status;
		this.frequency=frequency;

	}
	/*********** START OF RECURRING *************/
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

	public RepeatingFrequency getFrequency() {
		return frequency;
	}

	public void setFrequency(RepeatingFrequency frequency) {
		this.frequency = frequency;
	}
	
	public String nextStartDate(){
		return getNextDate(getStartDate());
	}
	
	public String nextEndDate(){
		return getNextDate(getEndDate());
	}
	
	public String getNextDate(Date date){
		GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(date);
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
	    //dateOfEvent.setStartDate(calendar.getTime());
		return sdf.format(calendar.getTime());
	}
	
	public void setNextStartDate(){
		try {
			dateOfEvent.setStartDate(sdf.parse(getNextDate(getStartDate())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void setNextEndDate(){
		try {
			dateOfEvent.setStartDate(sdf.parse(getNextDate(getEndDate())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/***********   END OF RECURRING *************/
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
		return eventName;
	}


	@Override
	public void setName(String taskName) {
		this.eventName = taskName;
		
	}
	
	public void setStart(String startDate){
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
	}	
	
	public void setEnd(String endDate){
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
	}
	public Date getStartDate() {
		return dateOfEvent.getStartDate();
	}
	public String getStringStartDate(){
		return sdf.format(getStartDate());
	}
	public String getStringEndDate(){
		return sdf.format(getEndDate());
	}
	public Date getEndDate() {
		return dateOfEvent.getEndDate();
	}
	@Override
	public String print() {
		String temp = eventName + WORD_FROM + sdf.format(getStartDate()) + WORD_TO + sdf.format(getEndDate()); 
		return temp;
	}
	@Override
	public int compareTo(TaskEvent o) {
		return getStartDate().compareTo(o.getStartDate());	
	}

}
