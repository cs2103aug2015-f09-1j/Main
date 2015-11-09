package main.jarvas;

import java.util.Date;

//@@author A0126259B
public class DateOfEvent {
	Date startDate;
	Date endDate;
	Date untilDate;

	/**
	 * 
	 */
	//@@author A0126259B
	public DateOfEvent() {
		startDate = null;
		endDate = null;
		untilDate = null;
	}
	
	//@@author A0126259B
	public DateOfEvent(Date startDate , Date endDate ,Date untilDate){
		this.startDate = startDate;
		this.endDate = endDate;
		this.untilDate = untilDate;
	}
	
	//@@author A0126259B
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	
	//@@author A0126259B
	public Date getEndDate() {
		return endDate;
	}
	
	//@@author A0126259B
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	//@@author A0126259B
	public Date getStartDate() {
		return startDate;
	}
	
	//@@author A0126259B
	public Date getUntilDate() {
		return untilDate;
	}
	
	//@@author A0126259B
	public void setUntilDate(Date untilDate) {
		this.untilDate = untilDate;
	}
}
