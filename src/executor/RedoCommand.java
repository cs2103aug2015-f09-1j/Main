/**
 * 
 */
package executor;

import main.jarvas.Storage;

/**
 * this class provide redo functionality to Jarvas
 *
 */
public class RedoCommand {
	
	Storage storage;
	String output;

	public RedoCommand(Storage stor){
		storage = stor;
		redo();
	}
	
	public String getOutput(){
		return output;
	}

	
	/**
	 * redo to previous command
	 */
	private void redo(){

		output = storage.redoStorage();;
	}
}
