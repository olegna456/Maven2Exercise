package com.exist.exercise;


import java.io.*;
import java.util.*;


class Main {

	static String fileName;
	
	static Scanner console = new Scanner(System.in);

	public static void main(String[] args) throws IOException  {
		
		
		 while(true) {
		 	selectOption();
		 }
	}
	

	static void selectOption() throws IOException {
		showOptions();
		try {
			System.out.print("Enter your choice: ");
			int choice = Integer.parseInt(console.nextLine());
			if((choice < 0) || (choice > 7)) {
				System.out.println("Invalid Input.");
			} else {
				userInput(choice);
			}
		} catch(NumberFormatException e) { System.out.println("Invalid Input. Please input an integer."); selectOption(); }
		
	}

	static void showOptions() {		
		System.out.println("Select an Option");
		System.out.println("1. Print");
		System.out.println("2. Search");
		System.out.println("3. Edit");
		System.out.println("4. Add New Row");
		System.out.println("5. Sort");
		System.out.println("6. Reset");
		System.out.println("7. Exit");	
	}

	static void userInput(int choice) throws IOException {
			Services srv = new Services();
			// srv.update();
			srv.getValues();
		switch(choice) {
			case 1:					
				srv.printList();
				break;
			case 2:
				srv.concatenateForSearch();				
				break;
			case 3:
				srv.getInputs();					
				break;
			case 4:
				srv.addNewRow();
				break;
			case 5:
				srv.getRowToSort();
				break;
			case 6:
				break;
			case 7:
				System.exit(1);
				break;
		} 
	}
}