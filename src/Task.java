

import java.util.Date;

public class Task {
	
	public static final String EMPTY_SPACE = " ";

	private static final String LABEL_TASK_NAME = "task name = ";

	private static final String LABEL_TASK_DUEDATE = "task due date = ";
	
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
	public String toString(){
		String temp = "";
		temp=temp.concat(String.format(LABEL_TASK_NAME, taskName));
		temp = temp.concat("    "+ String.format(LABEL_TASK_DUEDATE, dueDate));
		return temp;
		
	}
}
