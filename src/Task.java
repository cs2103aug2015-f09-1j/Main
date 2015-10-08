import java.util.Date;
import java.util.GregorianCalendar;

public class Task {
	private String taskName;
	private GregorianCalendar dueDate;
	
	public Task(){
		taskName = new String();
		dueDate = new GregorianCalendar();
	}
	public Task(String taskName,GregorianCalendar dueDate){
		setTaskName(taskName);
		setDueDate(dueDate);
	}
	
	
	
	
	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public GregorianCalendar getDueDate() {
		return dueDate;
	}


	public void setDueDate(GregorianCalendar dueDate) {
		this.dueDate = dueDate;
	}

	
}
