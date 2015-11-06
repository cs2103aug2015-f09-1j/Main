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
		output = storage.undoStorage();
;
	}
}
