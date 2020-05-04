
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class BlockDataReader {
	
	String url;
	String [][] allBlockData;
	LinkedList<String[]> allStairBlocks = new LinkedList<String[]>();
	LinkedList<String[]> allBrickBlocks = new LinkedList<String[]>();
	LinkedList<String[]> allQuestionMarkBlocks = new LinkedList<String[]>();
	
	public BlockDataReader(String fileName) {
		
		url = "res/BlockData/" + fileName;
		
		allBlockData = breakApart();
		
		for (int i = 0; i < allBlockData.length; i ++) {
			
			if (allBlockData[i][0].equals("BrickBlock")) {
				
				allBrickBlocks.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("QuestionMarkBlock")) {
				
				allQuestionMarkBlocks.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("StairBlock")) {
				
				allStairBlocks.add(allBlockData[i]);
				
			}
			
		}
	}
	
	private String[][] breakApart() {
		
		String allLines[] = null;
		try {
			allLines = Files.readString(Paths.get(url)).split("\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String[][] file = null;
		try {
			file = new String[(int) Files.lines(Paths.get(url)).count()][3];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for (int i = 0; i < allLines.length; i ++) {
			
			file[i] = allLines[i].split(";");
			
		}

		return file;
	}
	
	public LinkedList<String[]> getAllStairBlocks() {
		
		return allStairBlocks;
		
	}
	
	public LinkedList<String[]> getAllBrickBlocks() {
		
		return allBrickBlocks;
		
	}
	
	public LinkedList<String[]> getAllQuestionMarkBlocks() {
		
		return allQuestionMarkBlocks;
		
	}
	
}
