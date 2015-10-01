package repo;

import java.util.Scanner;

public class UI {
	Scanner input;
	UI(){
		input = new Scanner(System.in);
		System.out.println("UI is ready.");
	}
	public void show(String str){
		System.out.println(str);
	}
	public String getCommand(){
		return input.nextLine();
	}
}
