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
private static final String MSG_TASK_REDO = "Undo success";
	
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
		storage.redoStorage();
		output =  MSG_TASK_REDO;
	}
}
