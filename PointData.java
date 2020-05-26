//Notes: this class is used to display points and add points to
// the total when Mario stomps on a Goomba or Koopa
import java.util.LinkedList;

public class PointData {
	
	private LinkedList<int[]> allData = new LinkedList<int[]>();

	public void addData(int[] data) {
		
		allData.add(data);
		
	}
	
	public LinkedList<int[]> getAllData() {
		
		return allData;
		
	}
	
	public void clearList() {
		
		allData.clear();
		
	}
	
}
