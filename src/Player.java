
public class Player {
	private int xPos;
	private int yPos;
	public Player(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	
	
	
	
	
	public void movePlayer(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
	}



	/**
	 * @return getter for x position
	 */
	public int getxPos() {
		return xPos;
	}
	/**
	 * @return getter for y position
	 */
	public int getyPos() {
		return yPos;
	}
	
	
	
	
}
