package main;

import java.io.File;

import entities.Phonebook;

public class Main {

	public static void main(String[] args) {
		// File where the program's main phonebook will be saved
		String executionPath = System.getProperty("user.dir").replace("\\", "/");
		File phonebookDefaultFile = new File(executionPath + "/phonebook.bin");
		
		// Create a phonebook and load to it from disk if there is a previous one
		Phonebook phonebook = new Phonebook();
		if(phonebookDefaultFile.exists()) {
			phonebook.importFromBin(phonebookDefaultFile);
		}
		
		// Start the user interface to interact with the phonebook
		PBInterface iface = new PBInterface(phonebook, phonebookDefaultFile);
		iface.startMenu();
	}

}
