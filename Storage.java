package repo;

import java.io.File;
import java.util.Vector;

public class Storage {
	Vector <String> tasks = new Vector <String>();
	int size = tasks.size();
	Storage(){
		// No filename indicated by user
		try{
			// Creating new files
			FileWriter fw = new FileWriter("mytextfile.txt");
			PrintWriter pw = new PrintWriter(fw);
		}catch(IOException e){
			out.println("ERROR! In creating new file");
		}
	}
	public Storage(String str) {
		try{
			// Creating new files
			FileWriter fw = new FileWriter(str);
			PrintWriter pw = new PrintWriter(fw);
		}catch(IOException e){
			out.println("ERROR! In creating new file");
		}
	}
	public void refreshFile(){
		//Save the new Vector into the file
		for(String str : tasks){
			pw.println(str);
		}
		pw.close();
	}
	public Vector<String> returnTasks() {
		// Return the vector contains tasks
		return tasks;
	}
	public void getNewTasks(Vector<String> returnNewTasks) {
		//Get the new tasks for storing into the file
		tasks.add(returnNewTasks);
	}
	public String returnOutput() {
		//Return the feedback to logic
		if(size != tasks.size()){
			size = tasks.size();
			return "Tasks has been updated";
		}
		else
			return "Tasks not updated";
	}
}
