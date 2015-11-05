package main.jarvas;

import java.util.Date;



public class DateOfEvent {
	Date startDate;
	Date endDate;
	/**
	 * 
	 */
	public DateOfEvent() {
		startDate = null;
		endDate = null;
	}
	public DateOfEvent(Date startDate , Date endDate){
		this.startDate = startDate;
		this.endDate = endDate;
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

}
