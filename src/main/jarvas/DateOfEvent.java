package main.jarvas;

import java.util.Date;



public class DateOfEvent {
	Date startDate;
	Date endDate;
	Date untilDate;

	/**
	 * 
	 */
	public DateOfEvent() {
		startDate = null;
		endDate = null;
		untilDate = null;
	}
	public DateOfEvent(Date startDate , Date endDate ,Date untilDate){
		this.startDate = startDate;
		this.endDate = endDate;
		this.untilDate = untilDate;
	}
	
	public void setStartDate(Date startDate){
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getUntilDate() {
		return untilDate;
	}
	public void setUntilDate(Date untilDate) {
		this.untilDate = untilDate;
	}

}
