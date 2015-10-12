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
	private static Storage stg;
	
	public JSONArray newTask;
	
	String filename = "Jarvas_Storage.txt";
	Storage(){
		File temp = new File(filename);
		if(!temp.exists()){
			try {
				temp.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(!(temp.length()==0)){
			fileRead();
		}else{
			newTask = new JSONArray();
		}
	}
	
	
	public static Storage getInstance(){
		if(stg == null){
			stg = new Storage();
		}
		return stg;
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
	
	private void fileRead(){
		JSONParser jarvasParser = new JSONParser();
		try {
			newTask = (JSONArray)jarvasParser.parse(new FileReader(filename));
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
	}
	
	public Vector<Task> convertToVector(){
		Vector<Task> vecTask = new Vector<Task>();
		for(int i=0; i<newTask.size(); i++){
			JSONObject task = (JSONObject)newTask.get(i);
			String name = task.get("Task").toString();
			String age = task.get("Date").toString();
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
