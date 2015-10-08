

import java.io.*;
import java.util.Vector;

public class Storage {
	public static final String NEW_FILE_NAME = "mytextfile.txt";
	public static final String ERROR_NEW_FILE = "ERROR! In creating new file";
	public static final String TASK_UPLOADED = "Tasks has been updated";
	public static final String TASK_NOT_UPLOADED = "Tasks not updated";
	public static final String ERROR_FILE_UNREFRESH = "File not refrshed";
	public static final String EMPTY_STRING = "";
	
	Vector <Task> tasks = new Vector <Task>();
	int size = tasks.size();
	String filename = EMPTY_STRING;
	Storage(){
		// No filename indicated by user
		try{
			// Creating new files
			FileWriter fw = new FileWriter(NEW_FILE_NAME);
			PrintWriter pw = new PrintWriter(fw);
			filename = NEW_FILE_NAME;

		}catch(IOException e){
			System.out.println(ERROR_NEW_FILE);
		}
	}
	public Storage(String str) {
		try{
			// Creating new files
			FileWriter fw = new FileWriter(str);
			PrintWriter pw = new PrintWriter(fw);
			filename = str;
		}catch(IOException e){
			System.out.println(ERROR_NEW_FILE);
		}
	}
	public void refreshFile(){
		//Save the new Vector into the file
		 try{
			 // Creating new files
			 FileWriter fw = new FileWriter(filename);
			 PrintWriter pw = new PrintWriter(fw);
			 for(Task task : tasks){
			    pw.println(task.printTasks());
			 }
			 pw.close();
		 }
		 catch(IOException e){
			   System.out.println(ERROR_FILE_UNREFRESH);
		 }
		
	}
	public Vector<Task> returnTasks() {
		// Return the vector contains tasks
		return tasks;
	}
	public void getNewTasks(Vector<Task> returnNewTasks) {
		//Get the new tasks for storing into the file
		tasks = (Vector<Task>)returnNewTasks.clone();
	}
	public String returnOutput() {
		//Return the feedback to logic
		if(size != tasks.size()){
			size = tasks.size();
			return TASK_UPLOADED;
		}
		else
			return TASK_NOT_UPLOADED;
	}
}
