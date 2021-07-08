/**
 * Sample code to use GenerateMap
 * @author Sidney Luo
 *
 */
public class Generate {
	private GenerateMap map;
	
	public Generate(int size) {
		map = new GenerateMap(size, 5, 100);
	}
	
	public void generateMap() {
		map.generateMap();
	}
	
	public static void main(String[] args) {
		Generate g = new Generate(17);
		g.generateMap();
	}
}
