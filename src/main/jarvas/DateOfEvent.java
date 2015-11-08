package main.jarvas;

import java.util.Date;

//@@author
public class DateOfEvent {
	Date startDate;
	Date endDate;
	Date untilDate;

	/**
	 * 
	 */
	//@@author
	public DateOfEvent() {
		startDate = null;
		endDate = null;
		untilDate = null;
	}
	
	//@@author
	public DateOfEvent(Date startDate , Date endDate ,Date untilDate){
		this.startDate = startDate;
		this.endDate = endDate;
		this.untilDate = untilDate;
	}
	
	//@@author
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	
	//@@author
	public Date getEndDate() {
		return endDate;
	}
	
	//@@author
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	//@@author
	public Date getStartDate() {
		return startDate;
	}
	
	//@@author
	public Date getUntilDate() {
		return untilDate;
	}
	
	//@@author
	public void setUntilDate(Date untilDate) {
		this.untilDate = untilDate;
	}
}
