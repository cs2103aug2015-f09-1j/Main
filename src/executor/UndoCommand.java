/**
 * 
 */
package executor;

import main.jarvas.Storage;

/**
 * this class provide undo functionality to Jarvas
 *
 */
public class UndoCommand {
	
	Storage storage;
	String output;

	public UndoCommand(Storage stor){
		storage = stor;
		undo();
	}
	
	public String getOutput(){
		return output;
	}

	
	/**
	 * this method undo the the previous command
	 */
	private void undo(){
		output = storage.undoStorage();
	}
}
