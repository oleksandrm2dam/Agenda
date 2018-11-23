package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entities.Phonebook;

public class PhonebookUtil {
	
	private static String executionPath = System.getProperty("user.dir").replace("\\", "/");
	private static File phonebookDir = new File(executionPath + "/files");
	
	public static Phonebook readPhonebook() {
		File file;
		FileInputStream fis;
		ObjectInputStream ois = null;
		try {
			file = new File(phonebookDir, "phonebook.pb");
			if(file.exists()) {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				return (Phonebook) ois.readObject();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Phonebook();
	}
	
	public static void writePhonebook(Phonebook phonebook) {
		File file;
		FileOutputStream fos;
		ObjectOutputStream oos = null;
		try {
			if(!phonebookDir.exists()) {
				phonebookDir.mkdirs();
			}
			file = new File(phonebookDir, "phonebook.pb");
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(phonebook);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
