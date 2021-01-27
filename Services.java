package com.exist.exercise;

import java.util.*;
import java.io.*;

class Services {

	ClassList cl = new ClassList();
	Utilities ui = new Utilities();
	ReadWrite rw = new ReadWrite();

	static List<String> listForSearch = new ArrayList<>();
	List<Integer> lengthPerKey = new ArrayList<>();
	List<Integer> lengthPerValue = new ArrayList<>();		
	static List<String> rowL = new ArrayList<>();
	static List<String> colList = new ArrayList<>();


	private int row;
	private int column;
	

	public void getValues() throws IOException {
		row = Utilities.getNumOfRow();
		column = Utilities.getNumOfColumn();
		setRow(row);
		setColumn(column);
		ArrayList<String> list = ReadWrite.readFile(row, column);
		ClassList cl = new ClassList(list, row, column);
		List<String> rl = cl.getRowList();
		setRowList(rl);
		List<String> colL = cl.getColumnList();	
		setColumnList(colL);
		
	}

	private void setRow(int row) {
		this.row = row;
	}

	private void setColumn(int column) {
		this.column = column;
	}

	private void setRowList(List<String> rowList) {
		this.rowL = rowList;
	}

	private void setColumnList(List<String> columnList) {
		this.colList = columnList;
	}

	// public void update() throws IOException {
	// 	clearList();
	// 	int row = Utilities.getNumOfRow();
	// 	int column = Utilities.getNumOfColumn();
	// 	ArrayList<String> list = ReadWrite.readFile(row, column);
	// 	ClassList clear = new ClassList(list, row, column);
	// 	rowList = cl.getRowList();
	// 	columnList = cl.getColumnList();
	// }


	public void clearList() {
		listForSearch.clear();
		lengthPerValue.clear();
		lengthPerKey.clear();
		cl.clearList();
	}


	public void printList() {
		int counter = 0,  i = 0;
		while(i < row) {
			int j = 0;
			while(j < column) {
				if(rowL.get(counter) == null) {
					j++;
				}else {
					System.out.print(rowL.get(counter) + ":" + colList.get(counter) + "\t");
					j++;
				}								
				counter++;
			}
			System.out.println();
			i++;
		}
	}

	public void concatenateForSearch() {
		
		String question = "Enter string to be searched: ";
		String userInput = Utilities.getStringForInput(question);
		int rowCtr = 0, columnCtr = 0, counter = 0;
		while(rowCtr < row) {
			columnCtr = 0;
			while(columnCtr < column) {				
					String key = rowL.get(counter);
					String value = colList.get(counter);
					String concatenated = Utilities.sort(key + value);
					listForSearch.add(concatenated);
					columnCtr++;
					counter++;				
			}
			rowCtr++;
		}
		search(userInput);
	}

	public void search(String userInput) {
		int rowCtr = 0, columnCtr = 0, counter = 0;
		while(rowCtr < row) {
			columnCtr = 0;
			while((columnCtr < column) && (counter < listForSearch.size())) {
				String sorted = listForSearch.get(counter);
				int count = Utilities.checkOccurence(sorted, userInput);
				if(count > 0) {
					System.out.println(userInput + " has " + count + " instance(s) on index " + rowCtr +  " " + columnCtr);
				}
				columnCtr++;
				counter++;
			}
			rowCtr++;
		}
	}

	public void computeForRowAndColumnIndex(int rowToEdit, int columnToEdit, String newKey, String newValue) throws IOException {
		int multiplier = ui.getMultiplier(rowToEdit);
		int difference = column - columnToEdit;
		if(rowToEdit > 0) {
			columnToEdit = (multiplier * column) + columnToEdit;
			rowToEdit = columnToEdit;
			insertToList(rowToEdit, columnToEdit, newKey, newValue);
		} else if ((rowToEdit > 0) && (columnToEdit < column)) {
			columnToEdit = (multiplier * column) + difference;
			rowToEdit = columnToEdit;
			insertToList(rowToEdit, columnToEdit, newKey, newValue);
		} else {
			rowToEdit = columnToEdit;
			insertToList(rowToEdit, columnToEdit, newKey, newValue);
		}
	}

	private void insertToList(int rowToEdit, int columnToEdit, String newKey, String newValue) throws IOException {
		if((!newKey.isEmpty()) && (newValue.isEmpty())) {
			rowL.set(rowToEdit, newKey);
		} else if ((newKey.isEmpty()) && (!newValue.isEmpty())) {
			colList.set(columnToEdit, newValue);;
		} else {
			rowL.set(rowToEdit, newKey);
			colList.set(columnToEdit, newValue);
		}
		rw.writeToFile(rowL, colList, row, column);
	}

	public void addNewRow() throws IOException {
		int rowBasedZero = row; int counter = 0;
		this.row = row + 1;
		while(counter < column) {
			String question = "Enter key for " + rowBasedZero + "," + counter +" : ";
			String newKey = ui.getStringForInput(question);
			question = "Enter value for " +  rowBasedZero + "," + counter + " : ";
			String newValue = ui.getStringForInput(question);
			rowL.add(newKey);
			colList.add(newValue);
			counter++;
		}
		
		rw.writeToFile(rowL, colList, row, column);
	}

	private int checkNull(int index) {
		int counter = 0;
		while(rowL.get(index) != null) {
			counter++;
			index++;
		}
		return counter;
	}

	public void concatenateRow(char input, int rowToSort) throws IOException {
		int multiplier = ui.getMultiplier(rowToSort);
		int index = ui.computeForIndex(rowToSort, multiplier, column);
		int columnCtr = 0; String concat = "";
		int test = checkNull(index);
		if(test > 0) {
			while(columnCtr < test) {			
				String temp1 = rowL.get(index);
				String temp2 = colList.get(index);
				lengthPerKey.add(temp1.length());
				lengthPerValue.add(temp2.length());
				concat += (temp1 + temp2);				
				 columnCtr++; index++;
			}		
		} else {
			while(columnCtr < column) {
				String temp1 = rowL.get(index);
				String temp2 = colList.get(index);
				lengthPerKey.add(temp1.length());
				lengthPerValue.add(temp2.length());
				concat += (temp1 + temp2);
				columnCtr++; index++;				
			}
		}
		if(input == 'A') {
			String toBeSorted = Utilities.sort(concat);
			subStringSorted(toBeSorted, rowToSort, multiplier);
			System.out.println(toBeSorted);
		} else {
			String toBeSorted = ui.sort(concat);
			String reversed = ui.reverse(toBeSorted);
			subStringSorted(reversed, rowToSort, multiplier);
		}
	}

	private void subStringSorted(String sorted, int rowToBeSorted, int multiplier) throws IOException {
		int columnCtr = 0, startKey = 0, endKey = 0, startValue = 0, endValue = 0;
		int index = Utilities.computeForIndex(rowToBeSorted, multiplier, column);
		int test = checkNull(index);
		if(test > 0) {
			while(columnCtr < test) {
				endKey = endKey + lengthPerKey.get(columnCtr);
				String temp = sorted.substring(startKey, endKey);
				rowL.set(index, temp);
				startValue = endKey;
				endValue = startValue + lengthPerValue.get(columnCtr);
				String temp2 = sorted.substring(startValue, endValue);
				colList.set(index, temp2);
				startKey = endValue;
				endKey = startKey;
				columnCtr++;
					index++;
			}
		}else {
			while(columnCtr < column) {
				endKey = endKey + lengthPerKey.get(columnCtr);
				String temp = sorted.substring(startKey, endKey);
				rowL.set(index, temp);
				startValue = endKey;
				endValue = startValue + lengthPerValue.get(columnCtr);
				String temp2 = sorted.substring(startValue, endValue);
				colList.set(index, temp2);
				startKey = endValue;
				endKey = startKey;
				columnCtr++;
				index++;
			}
		}		
		ReadWrite.writeToFile(rowL, colList, row, column);
	}

	public void getInputs() throws IOException {
		int min = 0;
		int max = row - 1;
		String question = "Enter row to edit: ";
		String error = "Invalid Input.  max  row xis only " + max + "." ;
		int userRowToEdit = ui.getChoice(min , max, question, error);
		max = column - 1;
		question = "Enter column to edit: ";
		error = "Invalid Input.  max of column is only " + max + "."; 
		int userColToEdit = ui.getChoice(min, max, question, error);
		question = "What do you want to edit? \n a. key \n b. value \n c. key and value \n Enter choice: ";
		String userInput = ui.getStringForInput(question);
		char charInput = ui.checkIfRowColOrRowAndCol(userInput);
		String newKey = "";
		String newValue = "";
		if(charInput == 'a') {
			String question1 = "Enter key to be inserted to " + userRowToEdit + "," + userColToEdit+": ";
			newKey = ui.getStringForInput(question1);
			computeForRowAndColumnIndex(userRowToEdit, userColToEdit, newKey, newValue);
		} else if(charInput == 'b') {
			question = "Enter value to be inserted to " + userRowToEdit + "," + userColToEdit+": ";
			newValue = ui.getStringForInput(question);
			computeForRowAndColumnIndex(userRowToEdit, userColToEdit, newKey, newValue);
		} else {
			String question1 = "Enter key to be inserted to " + userRowToEdit + "," + userColToEdit+": ";
			newKey = ui.getStringForInput(question1);
			question = "Enter value to be inserted to " + userRowToEdit + "," + userColToEdit +": ";
			newValue = ui.getStringForInput(question);
			computeForRowAndColumnIndex(userRowToEdit, userColToEdit, newKey, newValue);
		}					
	}

	public void getRowToSort() throws IOException {
		String question = "Enter row to sort(base 0): ";
		int min = 0;
		int max = row - 1;
		String error = "Invalid Input please enter a valid input";
		int rowToSort = ui.getChoice(min, max, question, error);
		question = "How do you want it to be sorted: \n A. Ascending \n B. Descending \n Enter your choice: ";
		String descOrAsc = ui.getStringForInput(question);
		char input = descOrAsc.toUpperCase().charAt(0);
		if((input == 'A') || ((input == 'B'))) {
			concatenateRow(input, rowToSort);
		}
	}

}