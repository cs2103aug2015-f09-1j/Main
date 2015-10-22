import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskEvent implements Task{
	private static final Logger logger = Logger.getLogger(TaskEvent.class.getName());
	private static final String WORD_FROM = " from ";
	private static final String WORD_TO = " to ";
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	String eventName;
	DateOfEvent dateOfEvent;
	public TaskEvent() {
		dateOfEvent = new DateOfEvent();
		dateOfEvent.setEndDate(null);
		dateOfEvent.setStartDate(null);
	}
	public TaskEvent(String eventName , Date startDate , Date endDate){
		dateOfEvent = new DateOfEvent();
		this.eventName= eventName;
		dateOfEvent.setEndDate(endDate);
		dateOfEvent.setStartDate(startDate);
	}
	public TaskEvent(String eventName , String startDate , String endDate) throws ParseException{

			dateOfEvent = new DateOfEvent();
			this.eventName= eventName;
			dateOfEvent.setStartDate(sdf.parse(startDate));
			dateOfEvent.setEndDate(sdf.parse(endDate));

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
		String temp = eventName + WORD_FROM + sdf.format(getStartDate()) + WORD_TO + sdf.format(getEndDate()); 
		return temp;
	}

}
