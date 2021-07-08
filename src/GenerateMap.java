import java.util.concurrent.ThreadLocalRandom;
/**
 * Map generation
 * Algorithm: move the boxes randomly from randomly placed goals
 * and fill the parts that are not in the route of the boxes with walls
 * route takes in consideration of corners and edges
 * 
 * For multiple boxes, route is maintained by enRouting every starting points
 * and the paths connecting them, and then the person is put randomly in any of the starting points
 * 
 * Can further configure the map by pass in the size of map, number of boxes and difficulty
 * 
 * Map is returned as
 * @author Sidney Luo
 *
 */
public class GenerateMap {
	private Node[][] map;
	private int size;
	private int[][] goals;
	private int[][] boxes;
	private int[][] startPoints;
	private int difficulty;
	/**
	 * Instantiates a new GenerateMap with empty map and list of goals
	 * 
	 * @param size
	 * 			size of the map to be generated, square
	 * @param goals
	 * 			number of boxes in map
	 * @param difficulty
	 * 			difficulty level
	 */
	public GenerateMap (int size, int goals, int difficulty) {
		map = new Node[size][size];
		
		// restrict goals so that the game is playable
		if (goals > size * 2) goals = size * 2;
		this.goals = new int[goals][2];
		boxes = new int[goals][2];
		startPoints = new int[goals][2];
		for(int x = 0; x < size; x++)
		    for(int y = 0; y < size; y++)
		        map[x][y] = new Node();
		this.size = size;
		
		// difficulty level is bounded
		if (difficulty < size/goals) this.difficulty = size/goals;
		else if (difficulty > (size * 8) / goals) this.difficulty = (size * 4) / goals;
		else this.difficulty = difficulty;
	}
	
	public String[][] giveMap(){
//		for (int j = 0; j < size + 2; j++)
//			System.out.print("#");
		String[][] gm = new String[size][size];
		System.out.println();
		for (int j = 0; j < size; j++) {
			//System.out.print("#");
			for (int i = 0; i < size; i++) {
				gm[i][j] = map[i][j].toString();
				System.out.print(map[i][j].toString());
			}
			System.out.println('#');
		}
		return gm;
	}
	/**
	 * Generates map
	 */
	public void generateMap() {
		for (int i = 0; i < goals.length; i++) {
			int x = ThreadLocalRandom.current().nextInt(1, size-1);
			int y = ThreadLocalRandom.current().nextInt(1, size-1);
			System.out.println(x);System.out.println(y);
			
			// check if coordinates appeared in goal list and boxes list
			if (i > 0) {
				int j;
				for (j = 0; j < i; j++) {
					if (x == goals[j][0] && y == goals[j][1])
						// increment x to avoid conflict
						x++;
					if (x == boxes[j][0] && y == boxes[j][1])
						// found box at goal, change type
						{map[x][y].forceEnRoute(4); break;}
				}
				if (j == i) map[x][y].forceEnRoute(1);
			} else map[x][y].forceEnRoute(1);
			// record goal
			goals[i][0] = x;
			goals[i][1] = y;
			
			int direction = ThreadLocalRandom.current().nextInt(1, 5);
			// if close to edges change the direction
			if (x <= 1) direction = 2;
			if (y <= 1) direction = 4;
			if (x >= size - 2) direction = 4;
			if (y >= size - 2) direction = 2;
	
			for (int j = 0; j < difficulty; j++) {
				// enRoute the next immediate Node in this direction
				if (direction == 1) map[x+1][y].enRoute(0);
				else if (direction == 2) map[x][y-1].enRoute(0);
				else if (direction == 3) map[x-1][y].enRoute(0);
				else if (direction == 4) map[x][y+1].enRoute(0);
				int random = ThreadLocalRandom.current().nextInt(1, 4);
				
				// change direction accordingly, if going backwards then don't change direction
				if (x <= 1) {
					if (direction == 3) random = 2;
					else random = 1;
				} else if (y <= 1) {
					if (direction == 2) random = 1;
					else random = 4;
				} else if (x >= size - 2) {
					if (direction == 1) random = 4;
					else random = 3;
				} else if (y >= size - 2) {
					if (direction == 4) random = 3;
					else random = 2;
				}
				
				// taking care of edge cases
				if (x <= 1 && y <= 1) {
					random = direction = 1;
				} else if (x >= size - 2 && y <= 1) {
					random = direction = 4;
				} else if (x >= size - 2 && y >= size - 2) {
					random = direction = 3;
				} else if (x <= 1 && y >= size - 2) {
					random = direction = 2;
				}
				
				// if direction changed, enRoute necessary Nodes
				// so that the person can push the box around the corner
				if (random == 1) {
					if (direction == 4) map[x+1][y+1].enRoute(0);
					else if (direction == 2) map[x+1][y-1].enRoute(0);
					if (direction == 3) map[x--][y].enRoute(0);
					else {
						map[x++][y].enRoute(0);
						direction = random;
					}
						
				}
				else if (random == 2) {
					if (direction == 1) map[x+1][y-1].enRoute(0);
					else if (direction == 3) map[x-1][y-1].enRoute(0);
					if (direction == 4) map[x][y++].enRoute(0);
					else {
						map[x][y--].enRoute(0);
						direction = random;
					}
				}
				else if (random == 3) {
					if (direction == 2) map[x-1][y-1].enRoute(0);
					else if (direction == 4) map[x-1][y+1].enRoute(0);
					if (direction == 1) map[x++][y].enRoute(0);
					else {
						direction = random;
						map[x--][y].enRoute(0);
					}
				}
				else if (random == 4) {
					if (direction == 1) map[x+1][y+1].enRoute(0);
					else if (direction == 3) map[x-1][y+1].enRoute(0);
					if (direction == 2) map[x][y--].enRoute(0);
					else {
						direction = random;
						map[x][y++].enRoute(0);
					}
				}
				// direction is the same
				else {
					if (direction == 1) map[x++][y].enRoute(0);
					else if (direction == 2) map[x][y--].enRoute(0);
					else if (direction == 3) map[x--][y].enRoute(0);
					else if (direction == 4) map[x][y++].enRoute(0);
				}
			}
			
			// check if coordinates appeared in goal list and boxes list
			int k;
			for (k = 0; k <= i; k++) {
				if (x == boxes[k][0] && y == boxes[k][1])
					// increment x to avoid conflict (might not be safe)
					x++;
				if (x == goals[k][0] && y == goals[k][1])
					// found goal, change type
					{map[x][y].forceEnRoute(4); break;}
			}
			if (k > i) map[x][y].forceEnRoute(2);
			// record boxes
			boxes[i][0] = x;
			boxes[i][1] = y;
			
			// starting point is the next Node in the same direction
			System.out.println(x);System.out.println(y);
			if (direction == 1) x++;
			else if (direction == 2) y--;
			else if (direction == 3) x--;
			else if (direction == 4) y++;
			map[x][y].enRoute(0);
			// record starting point
			startPoints[i][0] = x;
			startPoints[i][1] = y;
		}
		// create a passage for person to move from starting points, so that after finishing one
		// box can go and push another box. Also connects empty spaces together
		for (int i = 0; i < goals.length - 1; i++) {
			int x1 = startPoints[i][0];
			int x2 = startPoints[i+1][0];
			int y1 = startPoints[i][1];
			int y2 = startPoints[i+1][1];
			if (x1 >= x2 && y1 >= y2) {
				// case with box in the way
				if (map[x2+1][y2].getType() == 2) {
					map[x2][y2+1].enRoute(0);
					map[x2+1][y2+1].enRoute(0);
					map[x2+2][y2+1].enRoute(0);
				}
				
				for (int j = x2; j <= x1; j++) {
					map[j][y2].enRoute(0);
				}
				for (int j = y2; j <= y1; j++)
					map[x1][j].enRoute(0);
			} else {
				int start = x1 < x2 ? x1 : x2;
				int end = x1 < x2 ? x2 : x1;
				for (int j = start; j <= end; j++) {
					// case with box in the way
					if (map[j][y1].getType() == 2) {
						map[j+1][y1-1].enRoute(0);
						map[j+1][y1].enRoute(0);
						map[j+1][y1+1].enRoute(0);
					}
					else map[j][y1].enRoute(0);
				}
					
				start = y1 < y2 ? y1 : y2;
				end = y1 < y2 ? y2 : y1;
				for (int j = start; j <= end; j++) {
					// case with box in the way
					if (map[x2][j].getType() == 2) {
						map[x2-1][j+1].enRoute(0);
						map[x2][j+1].enRoute(0);
						map[x2+1][j+1].enRoute(0);
					}
					else map[x2][j].enRoute(0);
				}
			}
		}
		// randomly put person at one of the starting points
		int random = ThreadLocalRandom.current().nextInt(0, goals.length);
		int j;
		// check if person is on goal or box
		for (j = 0; j < goals.length; j++) {
			if (startPoints[random][0] == boxes[j][0] && startPoints[random][1] == boxes[j][1])
			{map[startPoints[random][0]][startPoints[random][1]+1].forceEnRoute(3); break;}
			if (startPoints[random][0] == goals[j][0] && startPoints[random][1] == goals[j][1])
			{map[startPoints[random][0]][startPoints[random][1]].forceEnRoute(5); break;}
		}
		if (j == goals.length) map[startPoints[random][0]][startPoints[random][1]].forceEnRoute(3);
		//giveMap();
	}
	
}
