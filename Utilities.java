package com.exist.exercise;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileReader;
import java.io.BufferedReader;


class Utilities {

	static Scanner console = new Scanner(System.in);

	static int getNumOfColumn() throws IOException {
		FileReader file_to_read = new FileReader("D:/maven2revision/test.txt");
    	BufferedReader bf = new BufferedReader(file_to_read);
		Scanner scanner = new Scanner(bf);
		int column = 0;
		int temp = 0;
		while(scanner.hasNextLine()) {
			temp = scanner.nextLine().split("\t").length;
			if(temp > column) {
				column = temp;
			}	
		}
		return column;
	}

	static int getNumOfRow() throws IOException {
		FileReader file_to_read = new FileReader("D:/maven2revision/test.txt");
		BufferedReader br = new BufferedReader(file_to_read);
		int count = 0;
		String contents;
		while((contents = br.readLine()) != null) {
			count++;
		}
		return count;
	}

	static int getChoice(int min, int max, String question, String error) {
		int choice = 0;
		try {
			System.out.print(question);
			choice = Integer.parseInt(console.nextLine());
			if((choice < min) || (choice > max)) {
				System.out.println(error);
				getChoice(min, max, question, error);
			}
		} catch(NumberFormatException e) { System.out.println("Invalid Input. Please input an integer."); getChoice(min, max, question, error);}
		return choice;
	}

	static String getStringForInput(String question) {
		String userInput;
		 boolean invalidInput = false;
		do {
			System.out.print(question);
			userInput = console.nextLine();
			if(!userInput.isEmpty()) {
				invalidInput = true;
			}else {
				System.out.println("Invalid Input. Please Enter a value.");
			}
		}while(!invalidInput);
		return userInput;
	}

	static String sort(String toSort) {
		char[] toSortArr = toSort.toCharArray();
		Arrays.sort(toSortArr);
		return new String(toSortArr);
	}

	static int checkOccurence(String toBeSearched, String userInput) {
		int counter = 0;
		Pattern pattern = Pattern.compile(userInput);
		Matcher matcher = pattern.matcher(toBeSearched);
		while(matcher.find()) {
			counter++;
		}
		return counter;
	}

	static char checkIfRowColOrRowAndCol(String userInput) {
		char a;
		userInput = userInput.toLowerCase();
		a = userInput.charAt(0);

		return a;
	}

	static int getMultiplier(int rowToEdit) {
		int multiplier = 0;
		while(multiplier < rowToEdit) {
			multiplier++;
		}
		return multiplier;
	}

	static int computeForIndex(int rowToBeSorted,  int multiplier, int column) {
		int columnToEdit = 0;
		if(rowToBeSorted > 0) {
			columnToEdit = (multiplier * column) + columnToEdit;
		} else {
			columnToEdit = 0;
		}
		return columnToEdit;
	}

	static String reverse(String toBeReverse) {
		char[] toBeReversed = toBeReverse.toCharArray();
		String newString = "";
		for(int i = toBeReversed.length - 1; i >= 0; i--) {
			newString += toBeReversed[i];;
		}
		return new String(newString);
	}

}