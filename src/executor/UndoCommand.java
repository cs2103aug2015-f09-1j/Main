/**
 * 
 */
package executor;

import main.jarvas.Storage;

/**
 * @author Li
 *
 */
public class UndoCommand {
	private static final String MSG_TASK_UNDO = "Undo success";
	
	Storage storage;
	String output;

	public UndoCommand(Storage stor){
		storage = stor;
		undo();
	}
	
	public String getOutput(){
		return output;
	}

	
	private void undo(){
		storage.undoStorage();
		output =  MSG_TASK_UNDO;
	}
}
