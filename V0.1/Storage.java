package repo;

import java.io.File;

public class Storage {
	Storage(){
		
		System.out.println("Storage is ready.");
		
	}
	public void refreshFile(File file){
		System.out.println("New version of file is stored.");
	}
	
	public void contentDelete(File file){
		System.out.println("Content deleted.");
	}
	
	public void contentAdd(File file){
		System.out.println("Content added.");
	}
	
	public void contentEdit(File file){
		System.out.println("Content Edited.");
	}
}
