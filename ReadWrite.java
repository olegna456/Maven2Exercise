package com.exist.exercise;


import java.util.*;
import java.io.*;

class ReadWrite {
	
	static ArrayList<String> readFile(int row, int column) throws IOException {
		FileReader file_to_read = new FileReader("D:/maven2revision/test.txt");
    	BufferedReader bf = new BufferedReader(file_to_read);
    	ArrayList<String> list = new ArrayList<>();
		String line;
		while((line = bf.readLine()) != null) {			
			String[] lineContent = line.split("\t");
			int i = 0;
			while(i < lineContent.length) {
				list.add(lineContent[i]);
				i++;
			}
			while(i < column) {
				list.add(null + "\u00BD" + null);
				i++;
			}
		}
		return list;
	}

	static void writeToFile(List<String> rowList, List<String> columnList, int row, int column) throws IOException {
	
		int rowCtr = 0, columnCtr = 0, counter = 0;
		BufferedWriter bw =  new BufferedWriter(new FileWriter("D:/maven2revision/test.txt"));
		try {
			while(rowCtr < row) {
				columnCtr = 0;
				while(columnCtr < column) {
					bw.write(rowList.get(counter) + "\u00BD" + columnList.get(counter) + "\t"); 
					columnCtr++;
					counter++;
				}
				bw.newLine();
				rowCtr++;
			}
			bw.flush();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch(Exception e) {

			}
		}


	}



	



}