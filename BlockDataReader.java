
import java.util.LinkedList;
import java.util.Scanner;

public class BlockDataReader {
	
	String url;
	String [][] allBlockData;
	String [] flagPole = new String[3];
	String [] endCastle = new String[3];
	LinkedList<String[]> allStairBlocks = new LinkedList<String[]>();
	LinkedList<String[]> allBrickBlocks = new LinkedList<String[]>();
	LinkedList<String[]> allQuestionMarkBlocks = new LinkedList<String[]>();
	LinkedList<String[]> allSmallPipes = new LinkedList<String[]>();
	LinkedList<String[]> allPipes = new LinkedList<String[]>();
	LinkedList<String[]> allLongPipes = new LinkedList<String[]>();
	
	public BlockDataReader(String fileName) {
		
		url = "LevelData/" + fileName;
		
		allBlockData = breakApart();
		
		for (int i = 0; i < allBlockData.length; i ++) {
			
			if (allBlockData[i][0] == null) {
				
				break;
				
			}
			
			if (allBlockData[i][0].equals("BrickBlock")) {
				
				allBrickBlocks.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("QuestionMarkBlock")) {
				
				allQuestionMarkBlocks.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("StairBlock")) {
				
				allStairBlocks.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("ShortPipe")) {
				
				allSmallPipes.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("Pipe")) {
				
				allPipes.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("LongPipe")) {
				
				allLongPipes.add(allBlockData[i]);
				
			} else if (allBlockData[i][0].equals("FlagPole")) {
				
				flagPole = allBlockData[i];
				
			} else if (allBlockData[i][0].equals("EndCastle")) {
				
				endCastle = allBlockData[i];
				
			}
			
		}
	}
	
	private String[][] breakApart() {
		
		Scanner sc = new Scanner(getClass().getClassLoader().getResourceAsStream(url));

		String[][] file = new String[200][3];
		
		int i = 0;
		
		while (sc.hasNextLine()) {
			
			file[i] = sc.nextLine().split(";");
			i++;
			
		}
		
		sc.close();

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
	
	public LinkedList<String[]> getAllSmallPipes() {
		
		return allSmallPipes;
		
	}
	
	public LinkedList<String[]> getAllPipes() {
		
		return allPipes;
		
	}
	
	public LinkedList<String[]> getAllLongPipes() {
		
		return allLongPipes;
		
	}
	
	public String[] getFlagPole() {
		
		return flagPole;
		
	}
	
	public String[] getEndCastle() {
		
		
		return endCastle;
		
	}
	
}
