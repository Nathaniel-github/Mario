//Author: Nathaniel Thomas, Aayush Lakhotia, and Dhruv Masurekar(Team Effort)
//Notes: This class is used to read and add the data in the 
//text files to create the level 

import java.util.LinkedList;
import java.util.Scanner;

public class DataReader {
	
	// Fields
	private String url;
	// Holds the large linked list that contains all the data for everything that needs to be rendered in a level
	private LinkedList<String[]> allData;
	// Holds the data for the flag pole
	private String [] flagPole = new String[3];
	// Holds the data for the castle
	private String [] endCastle = new String[3];
	// All of these hold their appropriate data in the form of a linked list
	private LinkedList<String[]> allStairBlocks = new LinkedList<String[]>();
	private LinkedList<String[]> allBrickBlocks = new LinkedList<String[]>();
	private LinkedList<String[]> allQuestionMarkBlocks = new LinkedList<String[]>();
	private LinkedList<String[]> allBarrierBlocks = new LinkedList<String[]>();
	private LinkedList<String[]> allSmallPipes = new LinkedList<String[]>();
	private LinkedList<String[]> allPipes = new LinkedList<String[]>();
	private LinkedList<String[]> allLongPipes = new LinkedList<String[]>();
	private LinkedList<String[]> allGoombas = new LinkedList<String[]>();
	private LinkedList<String[]> allKoopas = new LinkedList<String[]>();
	private LinkedList<String[]> allLongTrees = new LinkedList<String[]>();
	private LinkedList<String[]> allShortTrees = new LinkedList<String[]>();
	private LinkedList<String[]> allGates = new LinkedList<String[]>();
	private LinkedList<String[]> allMushrooms = new LinkedList<String[]>();

	
	public DataReader(String fileName) {
		
		url = "LevelData/" + fileName;
		
		// Gets all the data from the text file and stores it
		allData = breakApart();
		
		// Pretty much self explanatory
		for (int i = 0; i < allData.size(); i ++) {
			
			if (allData.get(i)[0] == null) {
				
				break;
				
			}
			
			if (allData.get(i)[0].equals("BrickBlock")) {
				
				allBrickBlocks.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("QuestionMarkBlock")) {
				
				allQuestionMarkBlocks.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("StairBlock")) {
				
				allStairBlocks.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("BarrierBlock")) {
				
				allBarrierBlocks.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("ShortPipe")) {
				
				allSmallPipes.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("Pipe")) {
				
				allPipes.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("LongPipe")) {
				
				allLongPipes.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("FlagPole")) {
				
				flagPole = allData.get(i);
				
			} else if (allData.get(i)[0].equals("EndCastle")) {
				
				endCastle = allData.get(i);
				
			} else if (allData.get(i)[0].equals("Goomba")) {
				
				allGoombas.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("Koopa")) {
				
				allKoopas.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("LongTree")) {
				
				allLongTrees.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("ShortTree")) {
				
				allShortTrees.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("Gate")) {
				
				allGates.add(allData.get(i));
				
			} else if (allData.get(i)[0].equals("Mushroom")) {
				
				allMushrooms.add(allData.get(i));
				
			}
			
		}
	}
	
	// Breaks up the text file into a LinkedList that can be returned and stored into separate LinkedLists
	private LinkedList<String[]> breakApart() {
		
		Scanner sc = new Scanner(getClass().getClassLoader().getResourceAsStream(url));

		LinkedList<String[]> file = new LinkedList<String[]>();
		
		
		while (sc.hasNextLine()) {
			
			file.add(sc.nextLine().split(";"));
			
		}
		
		sc.close();

		return file;
	}
	
	// Returns the list of all stair blocks
	public LinkedList<String[]> getAllStairBlocks() {
		
		return allStairBlocks;
		
	}
	
	// Returns the list of all the brick blocks
	public LinkedList<String[]> getAllBrickBlocks() {
		
		return allBrickBlocks;
		
	}
	
	// Returns the list of all the question mark blocks
	public LinkedList<String[]> getAllQuestionMarkBlocks() {
		
		return allQuestionMarkBlocks;
		
	}
	
	// Returns the list of all the barrier blocks
	public LinkedList<String[]> getAllBarrierBlocks() {
		
		return allBarrierBlocks;
		
	}
	
	
	// Returns the list of all the small pipes
	public LinkedList<String[]> getAllSmallPipes() {
		
		return allSmallPipes;
		
	}
	
	// Returns the list of all the regularly sized pipes
	public LinkedList<String[]> getAllPipes() {
		
		return allPipes;
		
	}
	
	// Returns the list of all the long pipes
	public LinkedList<String[]> getAllLongPipes() {
		
		return allLongPipes;
		
	}
	
	// Returns the data for the flag pole
	public String[] getFlagPole() {
		
		return flagPole;
		
	}
	
	// Returns the data for the castle at the end of a level
	public String[] getEndCastle() {
		
		
		return endCastle;
		
	}
	
	// Returns the data for the goombas
	public LinkedList<String[]> getAllGoombas() {
		
		return allGoombas;
		
	}
	
	// Returns the data for the koopas
	public LinkedList<String[]> getAllKoopas() {
		
		return allKoopas;
		
	}
	
	public LinkedList<String[]> getAllLongTrees() {
		
		return allLongTrees;
		
	}
	
	public LinkedList<String[]> getAllShortTrees() {
		
		return allShortTrees;
		
	}
	
	public LinkedList<String[]> getAllGates() {
		
		return allGates;
		
	}
	
	public LinkedList<String[]> getAllMushrooms(){
		
		return allMushrooms;
		
	}
	
	
}