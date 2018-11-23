package entities;

import java.util.ArrayList;

public class Phonebook {
	
	private ArrayList<Contact> contacts;

	// START Constructors
	public Phonebook() {
		contacts = new ArrayList<Contact>();
	}
	
	public Phonebook(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}
	// END Constructors
	
	// START Getters and Setters
	public ArrayList<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(ArrayList<Contact> contacts) {
		this.contacts = contacts;
	}
	// END Getters and Setters
	
	@Override
	public String toString() {
		return "Phonebook [contacts=" + contacts + "]";
	}
	
	// START Search methods
	public ArrayList<Contact> searchByName(String wantedName) {
		ArrayList<Contact> foundContacts = new ArrayList<Contact>();
		wantedName = wantedName.toLowerCase();
		
		for(Contact currentContact : contacts) {
			if(currentContact.getName().toLowerCase().equals(wantedName)) {
				foundContacts.add(currentContact);
			}
		}
		
		return foundContacts;
	}
	
	public ArrayList<Contact> searchByLastName(String wantedLastName) {
		ArrayList<Contact> foundContacts = new ArrayList<Contact>();
		wantedLastName = wantedLastName.toLowerCase();
		
		for(Contact currentContact : contacts) {
			if(currentContact.getLastName().toLowerCase().equals(wantedLastName)) {
				foundContacts.add(currentContact);
			}
		}
		
		return foundContacts;
	}
	
	public ArrayList<Contact> searchByAlias(String wantedAlias) {
		ArrayList<Contact> foundContacts = new ArrayList<Contact>();
		wantedAlias = wantedAlias.toLowerCase();
		
		for(Contact currentContact : contacts) {
			if(currentContact.getAlias().toLowerCase().equals(wantedAlias)) {
				foundContacts.add(currentContact);
			}
		}
		
		return foundContacts;
	}
	// END Search methods
		
}