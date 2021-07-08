
public class ExtraMap extends Map{

	public ExtraMap(int size, GameSystem gs, int level) {
		super(size, gs, level);
		// TODO Auto-generated constructor stub
	}
	@Override
	/**
	 * moves the player, block based on input
	 * changes the 
	 * @param mov
	 */
	public void moveAll(String mov) {
		boolean check = false;
		// based on input if up down left or right
		if (isFinished()) return;
		if(mov.equals("up")|mov.equals("down")){
			int ychange = 0;
			int ynext = 0;
			switch(mov){
			case "up":
				ychange = getPlayer().getyPos()-1;
				ynext = ychange-1;
				break;
			case "down":
				ychange = getPlayer().getyPos()+1;
				ynext = ychange+1;
				break;
				
			}
			
				
				String type = getMap()[getPlayer().getxPos()][ychange];
				// if empty space can move
				if (type.equals(" ")){
					// updating position of player and changing relevant on map
					// move to new spot
					getMap()[getPlayer().getxPos()][ychange] = "p";
					//original spot now be " " or "*"
					if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
					} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
					} else {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					}
					getPlayer().movePlayer(getPlayer().getxPos(), ychange);
				}
				else if (type.equals("b")) {
					String shift = getMap()[getPlayer().getxPos()][ynext];
					Block b = getBlock(getPlayer().getxPos(),ychange);
					if (b.getStatus() == false||b.getStatus() == true) {
						if (shift.equals(" ") || shift.equals("x")){
							// change the pos of block as well
							getMap()[getPlayer().getxPos()][ychange] = "p";
							getMap()[getPlayer().getxPos()][ynext] = "b";
							if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
								getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
							} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
								getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
							} else {
								getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
							}
							//update player
							getPlayer().movePlayer(getPlayer().getxPos(), ychange);
							// get block and update it
							
							b.moveBlock(getPlayer().getxPos(), ynext);
							// if block is at goal	
							if (shift.equals("x")) {
								getMap()[getPlayer().getxPos()][ynext] = "c";
								b.setStatus(true);
							} else b.setStatus(false);
						}
						else if (shift.equals("w")) {
							System.out.println("working");
							
							b.moveBlock(getxMapType("W"), getyMapType("W"));
							
							// finds the position of the other warp	
							if (getxMapType("W")>=0 && getyMapType("W")>=0) {
								System.out.println(getxMapType("W") + " ----- " + getyMapType("W"));
								getMap()[getxMapType("W")][getyMapType("W")] = "o";
							}
							
							getMap()[getPlayer().getxPos()][ychange] = "p";
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
							
							getPlayer().movePlayer(getPlayer().getxPos(), ychange);
						}
						else if (shift.equals("W")) {
							
							
							b.moveBlock(getxMapType("w"), getyMapType("w"));
							getMap()[getPlayer().getxPos()][ychange] = "p";
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
							
							getPlayer().movePlayer(getPlayer().getxPos(), ychange);
						}
					}
					
				}
				//keep pushing the box while in goal
				else if (type.equals("c")){

					String shift = getMap()[getPlayer().getxPos()][ynext];
					if (shift.equals(" ") || shift.equals("x")){
						Block b = getBlock(getPlayer().getxPos(),ychange);
						
							// change the pos of block as well
							getMap()[getPlayer().getxPos()][ychange] = "*";
							getMap()[getPlayer().getxPos()][ynext] = "b";
							if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
								getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
							} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
								getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
							} else {
								getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
							}
							// update player
							getPlayer().movePlayer(getPlayer().getxPos(), ychange);
							// get block and update it
							
							//update the map[i][j]to "c" indicates block in goal
							b.moveBlock(getPlayer().getxPos(), ynext);
							if (shift.equals("x")) {
								getMap()[getPlayer().getxPos()][ynext] = "c";
								b.setStatus(true);
							} else b.setStatus(false);
					}
					
				}
				else if(type.equals("x")){
					System.out.println("test in goal");
					// updating position of player and changing relevant on map
					// move to new spot
					// use the charactor "*" to represent user in goal field
					getMap()[getPlayer().getxPos()][ychange] = "*";
					// original spot now empty or restore to goal
					if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
					} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
					} else {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					}
					getPlayer().movePlayer(getPlayer().getxPos(), ychange);
				}
				else if (type.equals("s")) {
					System.out.println("pressed the switch");
					mark:
					for (int i = 0;i < getMap().length;i++) {
						for (int j = 0;j < getMap()[i].length;j++) {
							if (getMap()[i][j].equals("D")) {
								getMap()[i][j] = " ";
								// change " " to "k" later
								break mark;
							}
						}
					}
					getMap()[getPlayer().getxPos()][ychange] = "p";
					//original spot now be " " or "*"
					if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
					} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
					} else {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					}
					getPlayer().movePlayer(getPlayer().getxPos(), ychange);
				}
				else if (type.equals("o")) {
					System.out.println("working");
					String shift = getMap()[getPlayer().getxPos()][ynext];
					if (shift.equals(" ") || shift.equals("x")) {
						Block b = getBlock(getPlayer().getxPos(),ychange);
						System.out.println(b.getxPos()+"--"+b.getyPos());
						b.moveBlock(getPlayer().getxPos(), ynext);
							// change the pos of block as well
						getMap()[getPlayer().getxPos()][ychange] = "+";
						getMap()[getPlayer().getxPos()][ynext] = "b";
						if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
						} 
						else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
						}
						else {
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
						}
						// update player
						getPlayer().movePlayer(getPlayer().getxPos(), ychange);
						// get block and update it
						if (shift.equals("x")) {
							getMap()[getPlayer().getxPos()][ynext] = "c";
							b.setStatus(true);
						} else b.setStatus(false);
					}
				}
				else if (type.equals("W")) {
					getMap()[getPlayer().getxPos()][ychange] = "+";
					//original spot now be " " or "*"
					if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
					} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
					} else {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					}
					getPlayer().movePlayer(getPlayer().getxPos(), ychange);
				}
				
		}
		//----------------------------------------------------------------------------------
		// moving left and right
		else if(mov.equals("left")|mov.equals("right")){
			int xchange = 0;
			int xnext = 0;
			switch(mov){
			case "left":
				xchange = getPlayer().getxPos()-1;
				xnext = xchange-1;
				break;
			case "right":
				xchange = getPlayer().getxPos()+1;
				xnext = xchange+1;
				break;
			}
			String type = getMap()[xchange][getPlayer().getyPos()];
			System.out.println(type + "- < ");
			if (type.equals(" ")){
				// move to new spot
				getMap()[xchange][getPlayer().getyPos()] = "p";
				//original spot now be " " or "*"
				if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
				} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
				} else {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
				}
				getPlayer().movePlayer(xchange, getPlayer().getyPos());
			}
			// if obstacle then needa check if next block is empty
			else if (type.equals("b")) {
				String shift = getMap()[xnext][getPlayer().getyPos()];
				if (shift.equals(" ") || shift.equals("x")) {
					Block b = getBlock(xchange,getPlayer().getyPos());
					if (b.getStatus() == false|| b.getStatus()==true) {
						// change the pos of block as well
						getMap()[xchange][getPlayer().getyPos()] = "p";
						getMap()[xnext][getPlayer().getyPos()] = "b";
						if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
						} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
						} else {
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
						}
						
						
						// update player
						getPlayer().movePlayer(xchange, getPlayer().getyPos());
						// get block and update it
						
						b.moveBlock(xnext, getPlayer().getyPos());
						if (shift.equals("x")) {
							getMap()[xnext][getPlayer().getyPos()] = "c";
							b.setStatus(true);
						} else b.setStatus(false);
					}
				}
				else if (shift.equals("w")) {
					System.out.println("working");
					Block b = getBlock(xchange,getPlayer().getyPos());
					b.moveBlock(getxMapType("W"), getyMapType("W"));
					
					// finds the position of the other warp	
					if (getxMapType("W")>=0 && getyMapType("W")>=0) {
						System.out.println(getxMapType("W") + " ----- " + getyMapType("W"));
						getMap()[getxMapType("W")][getyMapType("W")] = "o";
					}
					
					getMap()[xchange][getPlayer().getyPos()] = "p";
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					
					getPlayer().movePlayer(xchange, getPlayer().getyPos());
				}
				else if (shift.equals("W")) {
					Block b = getBlock(xchange,getPlayer().getyPos());
					
					b.moveBlock(getxMapType("w"), getyMapType("w"));
					getMap()[xchange][getPlayer().getyPos()] = "p";
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					
					getPlayer().movePlayer(xchange, getPlayer().getyPos());
				}
			}
			else if (type.equals("c")){
				
				String shift = getMap()[xnext][getPlayer().getyPos()];
				if (shift.equals(" ") || shift.equals("x")){
					Block b = getBlock(xchange,getPlayer().getyPos());
					if (b.getStatus() == false||b.getStatus() == true) {
						// change the pos of block as well
						getMap()[xchange][getPlayer().getyPos()] = "*";
						getMap()[xnext][getPlayer().getyPos()] = "b";
						if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
						} else {
							getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
						}
						// update player
						getPlayer().movePlayer(xchange, getPlayer().getyPos());
						// get block and update it
						
						b.moveBlock(xnext, getPlayer().getyPos());
						if (shift.equals("x")) {
							getMap()[xnext][getPlayer().getyPos()]="c";
							b.setStatus(true);
						} else b.setStatus(false);
					}
				}
			}
			else if (type.equals("o")) {
				System.out.println("working");
				String shift = getMap()[xnext][getPlayer().getyPos()];
				if (shift.equals(" ") || shift.equals("x")) {
					Block b = getBlock(xchange,getPlayer().getyPos());
					System.out.println(b.getxPos()+"--"+b.getyPos());
					b.moveBlock(xnext, getPlayer().getyPos());
						// change the pos of block as well
					getMap()[xchange][getPlayer().getyPos()] = "+";
					getMap()[xnext][getPlayer().getyPos()] = "b";
					if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
					} 
					else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
					}
					else {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					}
					// update player
					getPlayer().movePlayer(xchange, getPlayer().getyPos());
					// get block and update it
					if (shift.equals("x")) {
						getMap()[xnext][getPlayer().getyPos()] = "c";
						b.setStatus(true);
					} else b.setStatus(false);
				}
			}
			else if (type.equals("W")) {
				getMap()[xchange][getPlayer().getyPos()] = "+";
				//original spot now be " " or "*"
				if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
				} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
				} else {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
				}
				getPlayer().movePlayer(xchange, getPlayer().getyPos());
			}
			else if (type.equals("x")){
				// move to new spot 
				// representing player in goal sopt as "*"
				getMap()[xchange][getPlayer().getyPos()] = "*";
				// original spot is now empty or restored to "x"
				if(getMap()[getPlayer().getxPos()][getPlayer().getxPos()].equals("*")){
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
				}else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
				}else {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";	
				}
				getPlayer().movePlayer(xchange, getPlayer().getyPos());
			}
			else if (type.equals("s")) {
				System.out.println("pressed the switch");
				mark:
				for (int i = 0;i < getMap().length;i++) {
					for (int j = 0;j < getMap()[i].length;j++) {
						if (getMap()[i][j].equals("D")) {
							getMap()[i][j] = " ";
							// change " " to "k" later
							break mark;
						}
					}
				}
				getMap()[xchange][getPlayer().getyPos()] = "p";
				//original spot now be " " or "*"
				if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
				} else if (getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("+")) {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "W";
				} else {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
				}
				getPlayer().movePlayer(xchange, getPlayer().getyPos());
			}
			

		}
		
	}
	/**
	 * returns the x position of the type of interest
	 * @param type a string showing the type of object represented
	 * @return
	 */
	public int getxMapType(String type) {
		int xpos = -1;
		end:
		for (int i = 0; i < getMap().length;i++) {
			for (int j = 0; j < getMap()[i].length; j++) {
				if (getMap()[i][j].equals(type)) {
					xpos = i;
					break end;
				}
			}
		}
		return xpos;
		
	}
	/**
	 * returns the y position of the type of interest
	 * @param type a string showing the type of object represented
	 * @return 
	 */
	public int getyMapType(String type) {
		int ypos = -1;
		end:
		for (int i = 0; i < getMap().length;i++) {
			for (int j = 0; j < getMap()[i].length; j++) {
				if (getMap()[i][j].equals(type)) {
					ypos = j;
					break end;
				}
			}
		}
		return ypos;
		
	}
}	
