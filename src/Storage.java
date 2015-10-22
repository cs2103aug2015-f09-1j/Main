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
	public JSONArray newEvent;
	
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
				file.write("\n");
				file.write(newEvent.toJSONString());
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
			newEvent = (JSONArray)jarvasParser.parse(new FileReader(filename));
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
	public Vector<TaskToDo> convertToVector(){
		Vector<TaskToDo> vecTask = new Vector<TaskToDo>();
		for(int i=0; newTask != null && i<newTask.size(); i++){
			JSONObject task = (JSONObject)newTask.get(i);
			String name = task.get("Task").toString();
			String age = task.get("Date").toString();
			TaskToDo aTask = new TaskToDo(name, age);
			vecTask.add(aTask);
		}
		return vecTask;
	}
	/**
	 * This function add an JSONObject into JSONArray 
	 * @param object
	 * 			is JSONObject that going to be added
	 */
	public void convertToJSONArray(JSONObject newObject, JSONArray newArray){
		newArray.add(newObject);
	}
	/**
	 * This function convert tasks in vector into JSON
	 * @param tasks
	 * 			is the task to be converted
	 */
	public void convertTaskToJSONObject(Vector<TaskToDo> tasks){
		newTask = new JSONArray();
		for(int i=0; i<tasks.size(); i++){
			JSONObject entry = new JSONObject();
			entry.put("Task", tasks.get(i).getName());
			entry.put("Date", tasks.get(i).getDueDate());
			convertToJSONArray(entry, newTask);
		}
	}
	/**
	 * This function convert events in vector into JSON
	 * @param events
	 * 			is the event to be converted
	 */
	public void convertEventToJSONObject(Vector<TaskEvent> events){
		newEvent = new JSONArray();
		for(int i=0; i<events.size(); i++){
			JSONObject entry = new JSONObject();
			entry.put("Event", events.get(i).getName());
			entry.put("Start Date", events.get(i).getStartDate().toString());
			entry.put("End Date", events.get(i).getEndDate().toString());
			convertToJSONArray(entry, newEvent);
		}
	}
	
	
	
}
