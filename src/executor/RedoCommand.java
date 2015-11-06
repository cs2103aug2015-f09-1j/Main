/**
 * 
 */
package executor;

import main.jarvas.Storage;

/**
 * @author YiHong
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

	
	private void redo(){

		output = storage.redoStorage();;
	}
}
