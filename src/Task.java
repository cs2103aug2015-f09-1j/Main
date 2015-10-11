

import java.util.Date;

public class Task {
	
	public static final String EMPTY_SPACE = " ";
	
	private String taskName;
	private String dueDate;
	
	public Task(){
		taskName = new String();
		dueDate = new String();
	}
	
	public Task(String taskName){
		setTaskName(taskName);
		dueDate = new String();
	}
	
	public Task(String taskName,String dueDate){
		setTaskName(taskName);
		setDueDate(dueDate);
	}
	
	
	public String getTaskName() {
		return taskName;
	}


	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}


	public String getDueDate() {
		return dueDate;
	}


	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String printTasks(){
		return taskName + EMPTY_SPACE + dueDate.toString();
	}
	
}
