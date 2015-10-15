import java.io.*;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import src.Controller;

public class Storage{
	public static final String NEW_FILE_NAME = "mytextfile.txt";
	public static final String ERROR_NEW_FILE = "ERROR! In creating new file";
	public static final String TASK_UPLOADED = "Tasks has been updated";
	public static final String TASK_NOT_UPLOADED = "Tasks not updated";
	public static final String ERROR_FILE_UNREFRESH = "File not refrshed";
	public static final String EMPTY_STRING = "";
	private static Storage stg;
	
	public JSONArray newTask;
	private static final Logger logger = Logger.getLogger(Controller.class.getName());
	String filename = "Jarvas_Storage.txt";
	Storage(){
		File temp = new File(filename);
		if(!temp.exists()){
			logger.log(Level.INFO, filename + " not exist");
			try {
				temp.createNewFile();
				logger.log(Level.INFO,filename + " created");
			} catch (IOException e) {
				System.err.println("invalid input file " + e.getMessage());
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
				assert(file != null): filename + " is null";
				file.write(newTask.toJSONString());
				file.close();
				System.out.println("File saved");
		}catch(IOException e){
			System.err.println("invalid input " + e.getMessage());
		}
	}
	/**
	 * This function read the content of file 
	 */
	private void fileRead(){
		logger.log(Level.INFO, filename + " is being read");
		JSONParser jarvasParser = new JSONParser();
		try {
			newTask = (JSONArray)jarvasParser.parse(new FileReader(filename));
		} catch (FileNotFoundException e) {
			System.err.println("invalid file name" + e.getMessage());
		} catch (IOException e) {
			System.err.println("invalid input " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("invalid parse " + e.getMessage());
		}	
	}
	/**
	 * This function convert content of JSONArray into vector
	 * @return converted content in vector
	 */
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
	/**
	 * This function add an JSONObject into JSONArray 
	 * @param object
	 * 			is JSONObject that going to be added
	 */
	public void convertToJSONArray(JSONObject object){
		newTask.add(object);
	}
	/**
	 * This function convert tasks in vector into JSON
	 * @param tasks
	 * 			is the task to be converted
	 */
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
