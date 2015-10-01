package repo;

public class Logic {
	String command;
	Logic(){
		System.out.println("Logic is ready.");
	}
	public void getCommand(String str){
		command = str;
		System.out.println("Logic is getting the command.");	
	}
	public void execute(){
		System.out.println("Command is executing.");
	}
}
