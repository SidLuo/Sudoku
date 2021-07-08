/**
 * Object representing a node in GenerateMap
 * @author Guozhao Luo
 *
 */
public class Node {
	private boolean inRoute;
	private int type;
	/**
	 * Constructs a new node with default type 0 and inRoute false
	 */
	public Node() {
		inRoute = false;
		type = 0;
	}
	/**
	 * Getter of inRoute
	 * @return whether or not the Node is inRoute
	 */
	public boolean isInRoute() {
		return inRoute;
	}
	/**
	 * Getter of type
	 * @return type of Node
	 */
	public int getType() {
		return type;
	}
	/**
	 * enRoute the Node and change the type
	 * Does not change the type if Node is already inRoute
	 * @param type 
	 * 			The type of Node
	 */
	public void enRoute(int type) {
		
		if (!inRoute) {
			inRoute = true;
			this.type = type;
		}
	}
	/**
	 * force enRoute the Node and change the type regardless whether Node is inRoute or not
	 * @param type
	 * 			The type Node should change to
	 */
	public void forceEnRoute(int type) {
		inRoute = true;
		this.type = type;
	}
	
	/**
	 * Change the Node to String accordingly
	 */
	public String toString() {
		if (!inRoute) return "#";
		else {
			if (type == 1) return "x";
			else if (type == 2) return "b";
			else if (type == 3) return "p";
			else if (type == 4) return "c"; // boxes on goal
			else if (type == 5) return "*"; // person on goal
			else return " ";
		}
	}
}
