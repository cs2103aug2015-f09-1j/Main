import java.io.*;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Storage{
	public static final String NEW_FILE_NAME = "mytextfile.txt";
	public static final String ERROR_NEW_FILE = "ERROR! In creating new file";
	public static final String TASK_UPLOADED = "Tasks has been updated";
	public static final String TASK_NOT_UPLOADED = "Tasks not updated";
	public static final String ERROR_FILE_UNREFRESH = "File not refrshed";
	public static final String EMPTY_STRING = "";
	
	public JSONArray newTask;
	
	String filename = "Jarvas_Storage.txt";
	Storage(){
		// No filename indicated by user
		File storage = new File(filename);
		newTask = new JSONArray();
		try{
			// Creating new files
			storage.createNewFile();
		}catch(IOException e){
			System.out.println(ERROR_NEW_FILE);
		}
	}
	
	public Storage(String str) {
		File temp = new File(str);
		if(!temp.exists()){
			try {
				temp.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!(temp.length()==0)){
			JSONParser jarvarsParser = new JSONParser();
			try {
				newTask = (JSONArray)jarvarsParser.parse(new FileReader(str.toString()));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}else{
			newTask = new JSONArray();
		}
		
		filename = str;
	}
	
	public void saveToStorage(){
		try{
				FileWriter file = new FileWriter(filename);
				file.write(newTask.toJSONString());
				file.close();
				System.out.println("File saved");
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public Vector<Task> convertToVector(){
		Vector<Task> vecTask = new Vector<Task>();
		for(int i=0; i<newTask.size(); i++){
			JSONObject task = (JSONObject)newTask.get(i);
			String name = task.get("name").toString();
			String age = task.get("age").toString();
			Task aTask = new Task(name, age);
			vecTask.add(aTask);
		}
		return vecTask;
	}
	
	public void convertToJSONArray(JSONObject object){
		newTask.add(object);
	}
	public void convertToJSONObject(Vector<Task> tasks){
		newTask.clear();
		for(int i=0; i<tasks.size(); i++){
			JSONObject entry = new JSONObject();
			entry.put("Task", tasks.get(i).getTaskName());
			entry.put("Date", tasks.get(i).getDueDate());
			convertToJSONArray(entry);
		}
	}
	
	
	
}
