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
	//@@author A0134109N
	public RedoCommand(Storage stor){
		storage = stor;
		redo();
	}
	//@@author A0134109N
	public String getOutput(){
		return output;
	}

	
	/**
	 * redo to previous command
	 */
	//@@author A0134109N
	private void redo(){

		output = storage.redoStorage();;
	}
}
