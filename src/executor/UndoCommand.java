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
	//@@author A0134109N
	public UndoCommand(Storage stor){
		storage = stor;
		undo();
	}
	//@@author A0134109N
	public String getOutput(){
		return output;
	}

	
	/**
	 * this method undo the the previous command
	 */
	//@@author A0134109N
	private void undo(){
		output = storage.undoStorage();
	}
}
