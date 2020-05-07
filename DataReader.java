
import java.util.LinkedList;
import java.util.Scanner;

public class DataReader {
	
	private String url;
	private String [][] allData;
	private String [] flagPole = new String[3];
	private String [] endCastle = new String[3];
	private LinkedList<String[]> allStairBlocks = new LinkedList<String[]>();
	private LinkedList<String[]> allBrickBlocks = new LinkedList<String[]>();
	private LinkedList<String[]> allQuestionMarkBlocks = new LinkedList<String[]>();
	private LinkedList<String[]> allSmallPipes = new LinkedList<String[]>();
	private LinkedList<String[]> allPipes = new LinkedList<String[]>();
	private LinkedList<String[]> allLongPipes = new LinkedList<String[]>();
	private LinkedList<String[]> allGoombas = new LinkedList<String[]>();
	private LinkedList<String[]> allKoopas = new LinkedList<String[]>();

	
	public DataReader(String fileName) {
		
		url = "LevelData/" + fileName;
		
		allData = breakApart();
		
		for (int i = 0; i < allData.length; i ++) {
			
			if (allData[i][0] == null) {
				
				break;
				
			}
			
			if (allData[i][0].equals("BrickBlock")) {
				
				allBrickBlocks.add(allData[i]);
				
			} else if (allData[i][0].equals("QuestionMarkBlock")) {
				
				allQuestionMarkBlocks.add(allData[i]);
				
			} else if (allData[i][0].equals("StairBlock")) {
				
				allStairBlocks.add(allData[i]);
				
			} else if (allData[i][0].equals("ShortPipe")) {
				
				allSmallPipes.add(allData[i]);
				
			} else if (allData[i][0].equals("Pipe")) {
				
				allPipes.add(allData[i]);
				
			} else if (allData[i][0].equals("LongPipe")) {
				
				allLongPipes.add(allData[i]);
				
			} else if (allData[i][0].equals("FlagPole")) {
				
				flagPole = allData[i];
				
			} else if (allData[i][0].equals("EndCastle")) {
				
				endCastle = allData[i];
				
			} else if (allData[i][0].equals("Goomba")) {
				
				allGoombas.add(allData[i]);
				
			} else if (allData[i][0].equals("Koopa")) {
				
				allKoopas.add(allData[i]);
				
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
	
	public LinkedList<String[]> getAllGoombas() {
		
		return allGoombas;
		
	}
	
	public LinkedList<String[]> getAllKoopas() {
		
		return allKoopas;
		
	}
	
}
