

public class Jarvas {
	private static UI ui;
	private static Logic logic;
	private static Storage storage;
	public static void main(String[] args){
		initialize(args);
		run();
	}
	private static void initialize(String[] args){
		ui = new UI();
		logic = new Logic();
		if(args.length == 0){
			storage = new Storage();
		}
		else{
			storage = new Storage(args[0]);
		}
		logic.getOriginalTasks(storage.returnTasks());
	}
	private static void run(){
		while(true){
			logic.getInput(ui.returnInput());
			logic.execute();
			storage.getNewTasks(logic.returnNewTasks());
			storage.refreshFile();
			logic.getOutput(storage.returnOutput());
			ui.showMessage(logic.returnOutput());
		}
	}

	
}
