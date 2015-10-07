package repo;

import java.io.File;

public class Start {
	public static void main(String[] args){
		String input;
		File file = new File("mytextfile.txt");
		UI ui = new UI();
		Storage storage = new Storage();
		Logic logic = new Logic();
		input = ui.getCommand();
		logic.getCommand(input);
		logic.execute();
		storage.refreshFile(file);
	}
}
