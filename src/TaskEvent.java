import java.util.Date;

public class TaskEvent implements Task{
	String eventName;
	DateOfEvent dateOfEvent;
	public TaskEvent() {
		dateOfEvent.setEndDate(null);
		dateOfEvent.setStartDate(null);
	}
	public TaskEvent(Date startDate , Date endDate){
		dateOfEvent.setEndDate(endDate);
		dateOfEvent.setStartDate(startDate);
	}
	public TaskEvent(String startDate , String endDate){
		
	}


	public Date getStartDate() {
		return dateOfEvent.getStartDate();
	}


	public Date getEndDate() {
		return dateOfEvent.getEndDate();
	}


	@Override
	public String getName() {
		return eventName;
	}


	@Override
	public void setName(String taskName) {
		this.eventName = taskName;
		
	}


	@Override
	public String print() {
		return null;
	}

}
