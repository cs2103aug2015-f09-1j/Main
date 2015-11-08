package UnitTestCases;

import static org.junit.Assert.*;

import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.jarvas.Logic;


//jarvas test skeleton
public class JarvasTest {

	GUI.Jarvas jarvas = new GUI.Jarvas();
	Logic logic = new Logic();

	
	@Test
	public void testDetermineCommandType(){
		//Need to add test case		
	}
	
	@Test
	public void testExecute() throws IOException{
		//equivalence partitioning for invalid value partition(without command)
		assertEquals("invalid input", logic.execute("444"));
		assertEquals("invalid input", logic.execute("eeeee"));
		//equivalence partitioning for valid value partition (addtask command follow by dueDate)
		assertEquals("task \"ola\" successfully added", logic.execute("addtask ola -due 3/3/3/3/3"));
		//Test short form
		assertEquals("task \"yolo\" successfully added", logic.execute("a yolo"));
		assertEquals("\"task 1\" successfully edited", logic.execute("e task 1 due now"));
		assertEquals("\"task 1\" successfully deleted", logic.execute("d task 1"));

	}
	@Test
	public void testAddEvent(){
		// equivalence partitioning with valid date format dd/mm/yyyy HH/MM
		assertEquals("event \"ola\" successfully added", logic.execute("ola -from 12/12/1991 12:12 -to 12/12/1991 12:13"));
		//equivalence partitioning with invalid date format
		assertEquals("invalid format of date", logic.execute("ola -from 12/12/1991 12 -to 12/12/1991 12:13"));
	}

	
	@Test
	public void testClear() throws IOException{
		//assertEquals("tasks is clear", logic.clearTask());
	}
	
	@Test
	public void testSort() throws IOException{
		
	}
	
	@Test
	public void testSearch() throws IOException{

	}
	@Before
	public void beforeRunTest(){
	//	logic.clearTask();
	}
	@After
	public void afterRunTest(){
	//	logic.clearTask();
	}
	
	/*
	package tests;

	import static org.junit.Assert.*;

	import logic.AddCommand;
	import logic.TaskManager;
	import storage.StorageControl;
	import common.Task;
	import common.TaskType;
	import java.time.LocalDate;
	import java.time.LocalTime;
	import java.util.ArrayList;
	import java.util.SortedMap;

	import org.junit.Test;

	public class AddCommandTest {

		@Test
		public void execute_NonBoundary_FloatingTask() {
			StorageControl testStorageControl = new StorageControl();
			TaskManager testManager = new TaskManager(testStorageControl);
			AddCommand testAddCommand = new AddCommand(testManager);
			Task testTask = new Task("testing");
			
			assertEquals(testAddCommand.execute(testTask), true);
			
			ArrayList<Task> testFloatingList;
			testFloatingList = testManager.getFloating(false);
			assertEquals(testFloatingList.get(0), testTask);
		}
		
		@Test
		public void execute_NonBoundary_EventTask() {
			StorageControl testStorageControl = new StorageControl();
			TaskManager testManager = new TaskManager(testStorageControl);
			AddCommand testAddCommand = new AddCommand(testManager);
			Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
			
			assertEquals(testAddCommand.execute(testTask), true);
			
			SortedMap<LocalDate, ArrayList<Task>> testDatedList;
			testDatedList = testManager.getDated(false);
			assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
		}
		
		@Test
		public void execute_NonBoundary_TodoTask() {
			StorageControl testStorageControl = new StorageControl();
			TaskManager testManager = new TaskManager(testStorageControl);
			AddCommand testAddCommand = new AddCommand(testManager);
			Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), TaskType.TODO);
			
			assertEquals(testAddCommand.execute(testTask), true);
			
			SortedMap<LocalDate, ArrayList<Task>> testDatedList;
			testDatedList = testManager.getDated(false);
			assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
		}
		
		@Test
		public void execute_NonBoundary_DeadlineTask() {
			StorageControl testStorageControl = new StorageControl();
			TaskManager testManager = new TaskManager(testStorageControl);
			AddCommand testAddCommand = new AddCommand(testManager);
			Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), TaskType.DEADLINE);
			
			assertEquals(testAddCommand.execute(testTask), true);
			
			SortedMap<LocalDate, ArrayList<Task>> testDatedList;
			testDatedList = testManager.getDated(false);
			assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
		}
		
		@Test
		public void execute_Boundary_Null() {
			StorageControl testStorageControl = new StorageControl();
			TaskManager testManager = new TaskManager(testStorageControl);
			AddCommand testAddCommand = new AddCommand(testManager);
			Task testTask = null;
			assertEquals(testAddCommand.execute(testTask), false);
		}
		
		@Test
		public void execute_Boundary_RepeatedTask() {
			StorageControl testStorageControl = new StorageControl();
			TaskManager testManager = new TaskManager(testStorageControl);
			AddCommand testAddCommand = new AddCommand(testManager);
			Task testTask = new Task("testing", LocalDate.of(2015, 12, 20), LocalTime.of(10, 15), LocalDate.of(2015, 12, 25), LocalTime.of(18, 30));
			
			assertEquals(testAddCommand.execute(testTask), true);
			
			SortedMap<LocalDate, ArrayList<Task>> testDatedList;
			testDatedList = testManager.getDated(false);
			assertEquals(testDatedList.get(LocalDate.of(2015, 12, 20)).toString(), "[" + testTask.toString() + "]");
			
			assertEquals(testAddCommand.execute(testTask), false);
		}

	}
	*/
}
