import java.util.LinkedList;

public class PointData {
	
	private LinkedList<int[]> allData = new LinkedList<int[]>();

	public void addData(int[] data) {
		
		allData.add(data);
		
	}
	
	public LinkedList<int[]> getAllData() {
		
		return allData;
		
	}
	
}
