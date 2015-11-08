package main.jarvas;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import GUI.Jarvas;
import executor.sortCommand;
import executor.GetRepeat.RepeatingFrequency;

//import src.Controller;
//@@author
public class Storage{
	public static final String NEW_FILE_NAME = "mytextfile.txt";
	public static final String ERROR_NEW_FILE = "ERROR! In creating new file";
	public static final String TASK_UPLOADED = "Tasks has been updated";
	public static final String TASK_NOT_UPLOADED = "Tasks not updated";
	public static final String ERROR_FILE_UNREFRESH = "File not refrshed";
	public static final String EMPTY_STRING = "";
	
	
	public JSONArray newTask;
	public JSONArray newEvent;
	public JSONObject totalTask;
	private boolean undoStatus = false;
	private Stack<JSONArray> tempTaskUndo;
	private Stack<JSONArray> tempEventUndo;
	private Stack<JSONArray> tempTaskRedo;
	private Stack<JSONArray> tempEventRedo;
	private int indexEvent;
	private int indexTask;
	public static Storage instance = null;
	
	private static final Logger logger = Logger.getLogger(Jarvas.class.getName());
	public static String filename = "Jarvas_Storage.txt";
	public Storage(){
		this(filename);
	}
	
	//@@author
	Storage(String inputFileName){
		filename = inputFileName;
		tempTaskUndo = new Stack<JSONArray>();
		tempEventUndo = new Stack<JSONArray>();
		tempTaskRedo = new Stack<JSONArray>();
		tempEventRedo = new Stack<JSONArray>();
		File temp = new File(filename);
		if(!temp.exists()){
			logger.log(Level.INFO, filename + " not exist");
			try {
				temp.createNewFile();
				logger.log(Level.INFO,filename + " created");
			} catch (IOException e) {
				System.err.println("invalid input file " + e.getMessage());
			}
		} else {
			logger.log(Level.INFO, "file exist");
		}
		if(!(temp.length()==0)){
			fileRead();
			seperateJSONArray();
		}else{
			newTask = new JSONArray();
			newEvent = new JSONArray();
			tempTaskUndo = new Stack<JSONArray>();
			tempEventUndo = new Stack<JSONArray>();
			tempTaskRedo = new Stack<JSONArray>();
			tempEventRedo = new Stack<JSONArray>();
			totalTask = new JSONObject();
			
		}
	}
	
	//@@author
	public static Storage getInstance(){
		if(instance == null){
			instance = new Storage();
		}
		return instance;
	}
	
	//@@author
	public void doStuff(){
		fileRead();
		seperateJSONArray();
	}
	
	//@@author
	public boolean saveToLocation(String newFileName){
		filename = newFileName;
		File newTemp = new File(filename);
		if(!newTemp.exists()){
			try {
				newTemp.createNewFile();
				logger.log(Level.INFO, "file created");
			} catch (IOException e) {
				logger.log(Level.INFO, filename + " not exist");
				return false;
			}
		} 
		saveToStorage();
		return true;
	}
	
	//@@author
	private void seperateJSONArray() {
		// TODO Auto-generated method stub

		newTask = (JSONArray)totalTask.get("Tasks");
		newEvent = (JSONArray)totalTask.get("Events");
	}

	//@@author
	private void saveToStorage(){
		try{
				
				FileWriter file = new FileWriter(filename);
				assert(file != null): filename + " is null";
				combineJSONArray();
				file.write(totalTask.toJSONString());
				file.close();
				logger.log(Level.INFO, "jsonobjects is saved into text file");
		}catch(IOException e){
			System.err.println("invalid input " + e.getMessage());
		}
	}
	
	/**
	 * This function read the content of file 
	 */
	//@@author
	private void fileRead(){
		logger.log(Level.INFO, filename + " is being read");
		JSONParser jarvasParser = new JSONParser();
		try {
			 totalTask = (JSONObject)jarvasParser.parse(new FileReader(filename));
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
	//@@author
	public Vector<TaskToDo> convertToTask(){
		Vector<TaskToDo> vecTask = new Vector<TaskToDo>();
		for(int i=0; newTask != null && i<newTask.size(); i++){
			JSONObject task = (JSONObject)newTask.get(i);
			String name = task.get("Task").toString();
			String date = task.get("Date").toString();
			String index = task.get("Index").toString();
			boolean done = Boolean.valueOf(task.get("Done").toString());
			String frequency = task.get("RepeatFrequency").toString();
			String untilDate = task.get("UntilDate").toString();
			TaskToDo aTask = new TaskToDo(name, date, Integer.parseInt(index), done,frequency,untilDate);
			vecTask.add(aTask);
		}
		return vecTask;
	}
	
	//@@author
	public Vector<TaskEvent> convertToEvent() {
		Vector<TaskEvent> vecEvent = new Vector<TaskEvent>();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		for(int i=0; newEvent != null && i<newEvent.size(); i++){
			JSONObject event = (JSONObject)newEvent.get(i);
			String name = event.get("Event").toString();
			String index = event.get("Index").toString();
			String startDate = event.get("Start Date").toString();
			String endDate = event.get("End Date").toString();
			String untilDate = event.get("Until Date").toString();
			boolean done = Boolean.valueOf(event.get("Done").toString());
			String frequency;
			if(event.get("RepeatFrequency") == null){
				frequency = "";
			}
			else{
				frequency = event.get("RepeatFrequency").toString();
			}
			TaskEvent aEvent = new TaskEvent(name, startDate, endDate, Integer.parseInt(index), done, frequency,untilDate);
			vecEvent.add(aEvent);
		}
		return vecEvent;
	}
	
	/**
	 * This function add an JSONObject into JSONArray 
	 * @param object
	 * 			is JSONObject that going to be added
	 */
	//@@author
	public void convertToJSONArray(JSONObject newObject, JSONArray newArray){
		newArray.add(newObject);
	}
	
	//@@author
	private void combineJSONArray(){
		Map<String, JSONArray> mapTask = new HashMap<String, JSONArray>();
		mapTask.put("Tasks", newTask);
		mapTask.put("Events", newEvent);
		totalTask.putAll(mapTask);
	}
	
	/**
	 * This function convert tasks in vector into JSON
	 * @param tasks
	 * 			is the task to be converted
	 */
	//@@author
	private void convertTaskToJSONObject(Vector<TaskToDo> tasks){
		newTask = new JSONArray();
		for(int i=0; i<tasks.size(); i++){
			Map<String, String> entry = new HashMap<String, String>();
			entry.put("Index", String.valueOf(tasks.get(i).getIndex()));
			entry.put("Task", tasks.get(i).getName());
			entry.put("Date", tasks.get(i).getStringStartDate());
			entry.put("Done", String.valueOf(tasks.get(i).getDone()));
			entry.put("RepeatFrequency", tasks.get(i).getStrFrequency());
			entry.put("UntilDate", tasks.get(i).getStringUntilDate());
			JSONObject jsonEntry = new JSONObject();
			jsonEntry.putAll(entry);
			convertToJSONArray(jsonEntry, newTask);
		}
	}
	
	/**
	 * This function convert events in vector into JSON
	 * @param events
	 * 			is the event to be converted
	 */
	//@@author
	private void convertEventToJSONObject(Vector<TaskEvent> events){
		newEvent = new JSONArray();
		for(int i=0; i<events.size(); i++){
			Map<String, String> entryline = new HashMap<String, String>();
			entryline.put("Event", events.get(i).getName());
			Map<String, String> entry = new HashMap<String, String>();
			entry.put("Index", String.valueOf(events.get(i).getIndex()));
			entry.put("Start Date", events.get(i).getStringStartDate());
			entry.put("End Date", events.get(i).getStringEndDate());
			entry.put("Until Date", events.get(i).getStringUntilDate());
			entry.put("Done", String.valueOf(events.get(i).getDone()));
			entry.put("RepeatFrequency", events.get(i).getStrFrequency());
			JSONObject jsonEntry = new JSONObject();
			jsonEntry.putAll(entryline);
			jsonEntry.putAll(entry);
			convertToJSONArray(jsonEntry, newEvent);
		}
	}
	
	/**
	 * @param events 
	 * @param tasks 
	 * 
	 */
	//@@author
	public void processTasks(Vector<TaskToDo> tasks, Vector<TaskEvent> events, boolean status) {
		if(status == true)
			pushToHistory();
		convertTaskToJSONObject(tasks);
		convertEventToJSONObject(events);
		saveToStorage();
		
	}
	
	//@@author
	public String undoStorage(){
		try{
			if(checker() == false){
				tempTaskRedo.push(newTask);
				tempEventRedo.push(newEvent);
				newTask = tempTaskUndo.pop();
				newEvent = tempEventUndo.pop();
				undoStatus = true;		
				return "Undo Success";
			}	
		}catch(EmptyStackException o){
			return "Nothing to undo";
		}
		return "Nothing to undo";
	}
	
	//@@author
	public String redoStorage(){
		try{
			if(checker() == false){
				//tempTaskUndo.push(newTask);
				//tempEventUndo.push(newEvent);
				newTask = tempTaskRedo.pop();
				newEvent = tempEventRedo.pop();
				undoStatus = true;	
				return "Redo Success";
			}
		}catch(EmptyStackException o){
			return "Nothing to redo";
		}
		return "Nothing to Redo";
			
	}
	
	//@@author
	private boolean checker(){
		try{
			if(tempTaskUndo.peek().equals(newTask) && tempEventUndo.peek().equals(newEvent))
				return true;
			else 
				return false;
		}catch(EmptyStackException o){
			return false;
		}
	}
	
	//@@author
	private void pushToHistory(){
		if(undoStatus==false){
			tempTaskUndo.push(newTask);	
			tempEventUndo.push(newEvent);
		}
		undoStatus=false;
	}
}