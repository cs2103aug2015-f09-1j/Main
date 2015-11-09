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
		dateOfEvent.setUntilDate(null);
		frequency=RepeatingFrequency.NOTREPEATING;
	}
	
	//@@author A0134109N
	public TaskEvent(String eventName , String startDate , String endDate,
			int index, boolean status){
			dateOfEvent = new DateOfEvent();
			this.index = index;
			this.eventName= eventName;
			dateOfEvent.setStartDate(JParser.dateConverter(startDate));
			dateOfEvent.setEndDate(JParser.dateConverter(endDate));
			done = status;
			frequency=RepeatingFrequency.NOTREPEATING;
			dateOfEvent.setUntilDate(null);
	}
	//@@author A0134109N
	public TaskEvent(String eventName , String startDate , String endDate, int index, boolean status, String frequency){
		dateOfEvent = new DateOfEvent();
		this.index = index;
		this.eventName= eventName;
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
		done = status;
		this.frequency=GetRepeat.convertStrtoFrequency(frequency);
		dateOfEvent.setUntilDate(null);
	}
	//@@author A0134109N
	public TaskEvent(String eventName , String startDate , String endDate, int index, boolean status, RepeatingFrequency frequency){
		dateOfEvent = new DateOfEvent();
		this.index = index;
		this.eventName= eventName;
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
		done = status;
		this.frequency=frequency;
		dateOfEvent.setUntilDate(null);

	}
	//@@author A0134109N
	public TaskEvent(String eventName , String startDate , String endDate, int index, boolean status, RepeatingFrequency frequency,String untilDate){
		dateOfEvent = new DateOfEvent();
		this.index = index;
		this.eventName= eventName;
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
		done = status;
		this.frequency=frequency;
		dateOfEvent.setUntilDate(JParser.dateConverter(untilDate));

	}
	//@@author A0134109N
	public TaskEvent(String eventName , String startDate , String endDate, int index, boolean status, String frequency,String untilDate){
		dateOfEvent = new DateOfEvent();
		this.index = index;
		this.eventName= eventName;
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
		done = status;
		this.frequency=GetRepeat.convertStrtoFrequency(frequency);
		dateOfEvent.setUntilDate(JParser.dateConverter(untilDate));
	}
	
	/*********** START OF RECURRING *************/
	//@@author A0126259B
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
	//@@author A0134109N
	public RepeatingFrequency getFrequency() {
		return frequency;
	}
	//@@author A0126259B
	public void setFrequency(RepeatingFrequency frequency) {
		this.frequency = frequency;
	}
	//@@author A0126259B
	public String nextStartDate(){
		return getNextDate(getStartDate());
	}
	//@@author A0126259B
	public String nextEndDate(){
		return getNextDate(getEndDate());
	}
	

	//@@author A0126259B
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
	

	//@@author A0126259B
	public Date nextCompareDate(){
		return getNextCompareDate(getStartDate());
	}
	

	//@@author A0126259B
	public Date getNextCompareDate(Date date){
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
		return calendar.getTime();
	}
	
	//@@author A0134109N
	public void setNextStartDate(){
		try {
			dateOfEvent.setStartDate(sdf.parse(getNextDate(getStartDate())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	//@@author A0134109N
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
	//@@author A0126259B
	public void setDone(String status){
		if(status.equals("done")){
			done = true;
		}
		else{
			done = false;
		}
	}

	//@@author A0126159A
	public boolean getDone(){
		return done;
	}
	

	//@@author A0126259B
	@Override
	public String getName() {
		return eventName;
	}

	//@@author A0126259B
	@Override
	public void setName(String taskName) {
		this.eventName = taskName;
		
	}


	//@@author A0126259B
	public void setStart(String startDate){
		dateOfEvent.setStartDate(JParser.dateConverter(startDate));
	}	
	//@@author A0126259B
	public void setEnd(String endDate){
		dateOfEvent.setEndDate(JParser.dateConverter(endDate));
	}
	//@@author A0126259B
	public Date getStartDate() {
		return dateOfEvent.getStartDate();
	}
	//@@author A0126259B
	public String getStringStartDate(){
		return sdf.format(getStartDate());
	}
	//@@author A0126259B
	public String getStringEndDate(){
		return sdf.format(getEndDate());
	}
	//@@author A0126259B
	public Date getEndDate() {
		return dateOfEvent.getEndDate();
	}
	//@@author A0126259B
	public String getStringUntilDate(){
		if(getUntilDate()!=null){
			return sdf.format(getUntilDate());	
		}
		else{
			return "";
		}
	}

	//@@author A0126259B
	public Date getUntilDate() {
		return dateOfEvent.getUntilDate();
	}
	//@@author A0126259B
	public void setUntil(String untilDate){
		dateOfEvent.setUntilDate(JParser.dateConverter(untilDate));
	}


	//@@author A0126259B
	@Override
	public String print() {
		String temp = eventName + WORD_FROM + sdf.format(getStartDate()) + WORD_TO + sdf.format(getEndDate()); 
		return temp;
	}
	
	//@@author A0134109N
	@Override
	public int compareTo(TaskEvent o) {
		return getStartDate().compareTo(o.getStartDate());	
	}

}
