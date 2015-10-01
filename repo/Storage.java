package repo;

import java.io.File;

public class Storage {
	Storage(){
		System.out.println("Storage is ready.");
	}
	public void refreshFile(File file){
		System.out.println("New version of file is stored.");
	}
}
