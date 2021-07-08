
public class Block {
	// should block have name to identify differences?
	private int xPos;
	private int yPos;
	
	// if at goal then status is true
	private boolean status;
	
	public Block(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.status = false;
	}
	
			//movement of blocks
	public boolean Move(String mov, String type){
		if(mov.equals("up")){
			if(type.equals("b")) return false;
			this.yPos--;
			return true;
		} else if (mov.equals("down")){
			if(type.equals("b")) return false;
		
			this.yPos++;
			return true;
		} else if (mov.equals("left")){
			if(type.equals("b")) return false;
			this.xPos--;
			return true;
		} else { //right
			if(type.equals("b")) return false;
			this.xPos++;
			return true;
		}
	}
	
	
	//if the player is aside the block, return "b"
	public String CheckBlock(int x, int y){
		if(x + 1 == this.xPos || x-1==this.xPos || y+1 == this.yPos || y-1 == this.yPos) return "b";
		return " ";
	}
	
	
	
	/**
	 * moves the coordinate of the block
	 * @param xPos
	 * @param yPos
	 */
	public void moveBlock(int xPos, int yPos) {
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
	 * 
	 * @return getter for y position
	 */
	public int getyPos() {
		return yPos;
	}
	/**
	 * @return getter for the status of the block
	 */
	public boolean getStatus() {
		return status;
	}
	/**
	 * setter for status
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
}
