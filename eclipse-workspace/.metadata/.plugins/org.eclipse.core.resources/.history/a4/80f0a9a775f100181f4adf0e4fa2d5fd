package util;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import entities.Contact;
import entities.Phonebook;

public class SAXHandler extends DefaultHandler {
	
	private Phonebook phonebook;
	private String value;
	private Contact contact;
	
	public SAXHandler(Phonebook phonebook) {
		this.phonebook = phonebook;
	}
	
	@Override  
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {  
		switch(localName) {
			case "contact":
				contact = new Contact();
				break;
			case "email":
				contact.setEmail(new ArrayList<String>());
				break;
			case "phoneNumbers":
				contact.setPhoneNumber(new ArrayList<String>());
				break;
		}
	}
		     
	@Override  
	public void characters(char[] ch, int start, int length) throws SAXException {   
		value = new String(ch, start, length);
	}
		  
	@Override  
	public void endElement(String uri, String localName, String name) throws SAXException {  
		switch (localName) {
			case "contact":
				phonebook.getContacts().add(contact);
				break;
			case "name":
				contact.setName(value);
				break;
			case "lastName":
				contact.setLastName(value);
				break;
			case "alias":
				contact.setAlias(value);
				break;
			case "email":
				contact.getEmail().add(value);
				break;
			case "address":
				contact.setAddress(value);
				break;
			case "landlinePhoneNumber":
				contact.setLandlinePhoneNumber(value);
				break;
			case "phoneNumber":
				contact.getPhoneNumber().add(value);
				break;
		}
	}
	
}