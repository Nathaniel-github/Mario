// Notes: This class is used as a base-line to create props
// like pipes, trees, gates, and mushrooms
import java.awt.Polygon;

public interface Prop extends Block{

	// Should return whether or not the prop is obstructive
	public boolean isObstructive();
	
	// Should return the polygon that encompasses this prop
	public Polygon getCollider();
}
