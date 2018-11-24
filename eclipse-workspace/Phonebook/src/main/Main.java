package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import entities.Contact;
import entities.Phonebook;
import util.PhonebookUtil;

public class Main {
	
	private static Scanner scanner;
	private static String executionPath;
	private static File phonebookDefaultFile;
	private static Phonebook phonebook;
	
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		executionPath = System.getProperty("user.dir").replace("\\", "/");
		phonebookDefaultFile = new File(executionPath + "/phonebook.bin");
		phonebook = PhonebookUtil.importFromBin(phonebookDefaultFile); // Load a phonebook from disk
		
		if(phonebook == null) {
			phonebook = new Phonebook();
		}
		
		String mainMenuAnsw;
		do {
			mainMenu();
			mainMenuAnsw = scanner.nextLine();
			
			switch(mainMenuAnsw) {
				case "1":
					addContactMenu();
					break;
				case "2":
					searchForContactMenu();
					break;
				case "3":
					exportPhonebookMenu();
					break;
				case "4":
					break;
				case "5":
					// Before exiting, the phonebook gets saved to disk
					PhonebookUtil.exportToBin(phonebook, phonebookDefaultFile);
					System.out.println("GOODBYE!");
					break;
				default:
					break;
			}
		} while (!mainMenuAnsw.equals("5"));
		
		scanner.close();
	}
	
	private static void mainMenu() {
		String menu = "---MENU---\n";
		menu += "1) Add contact.\n";
		menu += "2) Search for contact.\n";
		menu += "3) Export phonebook.\n";
		menu += "4) Import phonebook.\n";
		menu += "5) Exit.\n";
		menu += "----------";
		System.out.println(menu);
	}
	
	private static void addContactMenu() {
		String name;
		String lastName;
		String alias;
		ArrayList<String> email = new ArrayList<String>();
		String address;
		String landlinePhoneNumber;
		ArrayList<String> phoneNumber = new ArrayList<String>();
		
		do {
			System.out.println("Name (required): ");
			name = scanner.nextLine();
		} while (name.length() == 0);
		
		do {
			System.out.println("Last name (required): ");
			lastName = scanner.nextLine();
		} while (lastName.length() == 0);
		
		System.out.println("Alias: ");
		alias = scanner.nextLine();
		
		String another;
		do {
			System.out.println("Email: ");
			email.add(scanner.nextLine());
			System.out.println("Add another email? (Y/n): ");
			another = scanner.nextLine();
		} while (!another.equals("n") && !another.equals("N"));
		
		System.out.println("Address: ");
		address = scanner.nextLine();
		
		System.out.println("Landline phone number: ");
		landlinePhoneNumber = scanner.nextLine();
		
		do {
			System.out.println("Mobile phone number: ");
			phoneNumber.add(scanner.nextLine());
			System.out.println("Add another mobile phone number? (Y/n): ");
			another = scanner.nextLine();
		} while (!another.equals("n") && !another.equals("N"));
		
		// if empty, then the value is null
		if(alias.length() == 0) {
			alias = null;
		}
		if(email.size() == 0) {
			email = null;
		}
		if(address.length() == 0) {
			address = null;
		}
		if(landlinePhoneNumber.length() == 0) {
			landlinePhoneNumber = null;
		}
		if(phoneNumber.size() == 0) {
			phoneNumber = null;
		}
		
		Contact newContact = new Contact(
				name,
				lastName,
				alias,
				email,
				address,
				landlinePhoneNumber,
				phoneNumber
				);
		
		System.out.println("Adding to phonebook: ");
		System.out.println(newContact.toString());
		
		phonebook.getContacts().add(newContact);
	}
	
	private static void searchForContactMenu() {
		ArrayList<Contact> foundContacts = new ArrayList<Contact>();
		
		System.out.println("Search by (1) name, (2) last name, (3) alias: ");
		String searchBy = scanner.nextLine();
		
		switch(searchBy) {
			case "1":
				System.out.println("Name: ");
				String name = scanner.nextLine();
				foundContacts = phonebook.searchByName(name);
				break;
			case "2":
				System.out.println("Last name: ");
				String lastName = scanner.nextLine();
				foundContacts = phonebook.searchByLastName(lastName);
				break;
			case "3":
				System.out.println("Alias: ");
				String alias = scanner.nextLine();
				foundContacts = phonebook.searchByAlias(alias);
				break;
			default:
				System.out.println("Not valid.");
				return;
		}
		
		int numFoundContacts = foundContacts.size();
		if(numFoundContacts == 0) {
			System.out.println("Matching contacts not found.");
			return;
		}
		
		int selectedContactIndex;
		if(numFoundContacts == 1) {
			selectedContactIndex = 0; // Index 0
			System.out.println("Printing found contact: ");
			System.out.println(foundContacts.get(0));
		} else {
			System.out.println("Printing found contacts: ");
			for(int i = 0; i < numFoundContacts; ++i) {
				System.out.println((i + 1) + " " + foundContacts.get(i).toString());
				// Internally an ArrayList behaves as a conventional array, 
				// so accessing objects by index is not really inefficient.
			}
			try {
				System.out.println("Choose the wanted contact by it's index: ");
				selectedContactIndex = Integer.parseInt(scanner.nextLine()) - 1; // (- 1) because the first found contact shows with index (1)
				if(selectedContactIndex < 0 || selectedContactIndex >= numFoundContacts) {
					System.out.println("Index not valid.");
					return;
				}
			} catch (NumberFormatException e) {
				System.out.println("Index not valid.");
				return;
			}
		}
		
		System.out.println("(1) Delete contact, (2) edit contact, (3) do nothing: ");
		String choice = scanner.nextLine();
		switch(choice) {
			case "1":
				deleteContactMenu(foundContacts.get(selectedContactIndex));
				break;
			case "2":
				editContactMenu(foundContacts.get(selectedContactIndex));
				break;
		}
	}
	
	private static void deleteContactMenu(Contact contact) {
		System.out.println("Are you sure you want to delete the following contact? (y/n): ");
		System.out.println(contact.toString());
		String choice = scanner.nextLine();
		
		if(choice.equals("Y") || choice.equals("y")) {
			phonebook.getContacts().remove(contact);
			System.out.println("Contact deleted successfully.");
		} else {
			System.out.println("Contact not deleted.");
		}
	}

	private static void editContactMenu(Contact contact) {
		String fields = "(1) Name.\n";
		fields += "(2) Last name.\n";
		fields += "(3) Alias.\n";
		fields += "(4) Email.\n";
		fields += "(5) Address.\n";
		fields += "(6) Landline phone number.\n";
		fields += "(7) Mobile phone number.\n";
		
		String choice;
		do {
			System.out.println(contact.toString());
			System.out.println("Choose the field to edit: ");
			System.out.println(fields);
			choice = scanner.nextLine();
			switch(choice) {
				case "1":
					System.out.println("New name: ");
					contact.setName(scanner.nextLine());
					break;
				case "2":
					System.out.println("New last name: ");
					contact.setLastName(scanner.nextLine());
					break;
				case "3":
					System.out.println("New alias: ");
					contact.setAlias(scanner.nextLine());
					break;
				case "4":
					int numEmails = contact.getEmail().size();
					if(numEmails == 0) {
						// If there are no email addresses
						contact.setEmail(new ArrayList<String>());
						System.out.println("New email: ");
						contact.getEmail().add(scanner.nextLine());
					} else {
						// If there are multiple email addresses
						System.out.println("Choose the email to modify: ");
						for(int i = 0; i < numEmails; ++i) {
							System.out.println((i + 1) + " " + contact.getEmail().get(i));
						}
						System.out.println((numEmails + 1) + " Add new email.");
						try {
							int selectedEmailIndex = Integer.parseInt(scanner.nextLine()) - 1;
							if(selectedEmailIndex < 0 || selectedEmailIndex > numEmails) {
								System.out.println("Invalid email index.");
							} else {
								if(selectedEmailIndex == numEmails) {
									// Add new email
									System.out.println("New email: ");
									contact.getEmail().add(scanner.nextLine());
								} else {
									// Edit the selected email
									System.out.println("New email: ");
									contact.getEmail().set(selectedEmailIndex, scanner.nextLine());
								}
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid email index.");
						}
					}
					break;
				case "5":
					System.out.println("New address: ");
					contact.setAddress(scanner.nextLine());
					break;
				case "6":
					System.out.println("New landline phone number: ");
					contact.setLandlinePhoneNumber(scanner.nextLine());
					break;
				case "7":
					int numPhones = contact.getPhoneNumber().size();
					if(numPhones == 0) {
						// If there are no phone numbers
						contact.setPhoneNumber(new ArrayList<String>());
						System.out.println("New mobile phone number: ");
						contact.getPhoneNumber().add(scanner.nextLine());
					} else {
						// If there are multiple phone numbers
						System.out.println("Choose the mobile phone number to modify: ");
						for(int i = 0; i < numPhones; ++i) {
							System.out.println((i + 1) + " " + contact.getPhoneNumber().get(i));
						}
						System.out.println((numPhones + 1) + " Add new phone number.");
						try {
							int selectedPhoneIndex = Integer.parseInt(scanner.nextLine()) - 1;
							if(selectedPhoneIndex < 0 || selectedPhoneIndex > numPhones) {
								System.out.println("Invalid mobile phone number index.");
							} else {
								if(selectedPhoneIndex == numPhones) {
									// Add new phone number
									System.out.println("New mobile phone number: ");
									contact.getPhoneNumber().add(scanner.nextLine());
								} else {
									// Edit the selected phone number
									System.out.println("New mobile phone number: ");
									contact.getPhoneNumber().set(selectedPhoneIndex, scanner.nextLine());
								}
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid mobile phone number index.");
						}
					}
					break;
				default:
					System.out.println("Invalid field index.");
					break;
			}
			System.out.println(contact.toString());
			System.out.println("Keep editing the contact? (Y/n): ");
			choice = scanner.nextLine();
		} while (!choice.equals("n") && !choice.equals("N"));
	}
	
	private static void exportPhonebookMenu() {
		System.out.println("Folder: ");
		String dir = scanner.nextLine();
		
		System.out.println("File name: ");
		String fileName = scanner.nextLine();
		
		String fileExtension;
		do {
			System.out.println("File type (.txt, .bin, .xml): ");
			fileExtension = scanner.nextLine();
			File phonebookFile = new File(dir, fileName + fileExtension);
			
			if(phonebookFile.exists()) {
				System.out.println("File already exists, override? (Y/n): ");
				String override = scanner.nextLine();
				if(override.equals("n") || override.equals("N")) {
					System.out.println("Export cancelled.");
					return;
				}
			}
			
			switch(fileExtension) {
				case ".txt":
					PhonebookUtil.exportToTxt(phonebook, phonebookFile);
					break;
				case ".bin":
					PhonebookUtil.exportToBin(phonebook, phonebookFile);
					break;
				case ".xml":
					PhonebookUtil.exportToXml(phonebook, phonebookFile);
					break;
				default:
					System.out.println("File type not valid.");
					break;
			}
		} while (!fileExtension.equals(".txt") && !fileExtension.equals(".bin") && !fileExtension.equals(".xml"));
	}
	
	private static void importPhonebookMenu() {
		
	}

}