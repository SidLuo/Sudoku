import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class Map extends JPanel{
	

	
	private Pixel[][] pix;
	private JPanel panel = new JPanel();
	
	private JPanel menu = new JPanel();
	
	
	
	//using array if not allowed will change to arraylist
	private String[][] map; 
	private Player player;
	private ArrayList<Block> blocks;
	
	//in order to go to next level (call main with another argument
	GameSystem gs;
	int levelnum = 1;
	//public Map m =this;
	
	
	// may be easier to store size (optional)s
	public int size;
	
	//backup for reset
	public boolean notre = true;
	public String[][] mapback;
	
	
	
	
	
	
	
	public Map(int size, GameSystem gs, int level){
		
		this.gs = gs;
		this.levelnum = level;
		//jframe
		
		
		
		this.setLayout(new BorderLayout(0,0));
		
		this.setSize((size+1)*100, size*100);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//panel.setLayout(new GridLayout(size,size));
		

		
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setSize(200, size*100);
		
		
		JButton restart =  new JButton("   Restart   ");
		restart.setActionCommand("restart");
		restart.addActionListener(new ButtonClick());//check ButtonClick Class at the bottom
		/*
		Graphics shape = Map
		shape.setColor(Color.darkGray);
		shape.fillOval(0, 0, getSize().width-1, getSize().height-1);		
		restart.paint(shape);
		*/
		menu.add(restart);
		menu.add(Box.createRigidArea(new Dimension(0,100)));
		restart.setFocusable(false);
		
		JButton instruc = new JButton("Instruction");
		instruc.setActionCommand("instruction");
		instruc.addActionListener(new ButtonClick());
		menu.add(instruc);
		menu.add(Box.createRigidArea(new Dimension(0,100)));
		instruc.setFocusable(false);

		
		JButton skip =  new JButton("   Skip   ");
		skip.setActionCommand("skip");
		skip.addActionListener(new ButtonClick());//check ButtonClick Class at the bottom
		/*
		Graphics shape = Map
		shape.setColor(Color.darkGray);
		shape.fillOval(0, 0, getSize().width-1, getSize().height-1);		
		restart.paint(shape);
		*/
		menu.add(skip);
		menu.add(Box.createRigidArea(new Dimension(0,100)));
		skip.setFocusable(false);

		//*/
		
		
		menu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//this.getContentPane().add(menu);
		this.add(menu, BorderLayout.EAST);

		menu.setVisible(true);
				
		//this.add(panel, BorderLayout.CENTER);

		
		
		
		this.size = size;
		this.map = new String[size][size];
		setPlayer(new Player(0,0));
		blocks = new ArrayList<Block>();
		pix = new Pixel[size][size];
		
		
		mapback = new String[size][size];
	}
	/* -------------IMPORTANT-----------------------
	 * 
	 * Couldnt be bothered so right now the blocks once are at goal are locked meaning they can't be moved anymore
	 * 
	 * 
	 * ----------------------------------------------
	 */
	 
	 
	 
	/**
	 * replaces all the values in the map with empty spaces
	 */
	public void initMap(){

		
		for (int i = 0; i < getMap().length;i++) {
			for (int j = 0; j < getMap().length; j++) {
				getMap() [i] [j] = " ";
				
				mapback[i][j] = " ";
			}
		}
	}
	/**
	 * prints the map out
	 * 
	 */
	public void printMap(){
		for (int j = 0; j < getMap().length;j++) {
			for (int i = 0; i < getMap().length; i++) {
				System.out.print(getMap()[i][j]);

			}
			System.out.println();

		}
		
	}
	
	//updating the gui after each move
	public void printpix(){
		this.remove(panel);
		
		
		gs.f.remove(panel);
		//gs.f.getContentPane().remove(panel);
		
		
		panel = new JPanel();
		//this.add(panel, BorderLayout.CENTER);
		
		
		//background image
		JLabel winback = new JLabel();
		
		if(isFinished()){
			
			//this.remove(menu);
			
			
		    Image img = (new ImageIcon("./src/win.jpg")).getImage();
	    	Image newimg = img.getScaledInstance((size)*100, (size)*100-150,java.awt.Image.SCALE_SMOOTH ) ;  
			ImageIcon icon = new ImageIcon(newimg);
			winback = new JLabel(icon);
			winback.setSize((size)*100, size*100-100);
			
			panel.setLayout(new BorderLayout());
			//panel.setBackground(Color.WHITE);
			panel.setSize((size)*100, size*100);
			JButton next = new JButton("next");
			next.setPreferredSize(new Dimension(100,50));
			next.setActionCommand("next");
			next.addActionListener(new ButtonClick());
			panel.add(winback, BorderLayout.PAGE_START);
			panel.add(next, BorderLayout.PAGE_END);
			next.setFocusable(false);
			
			winback.setVisible(true);
			//this.getContentPane().add(winback);
			panel.setVisible(true);
		} else{
			panel.remove(winback);
		
			panel.setLayout(new GridLayout(size,size));
			panel.setSize(size*100, size*100);
			for(int j = 0; j <getMap().length;j++){
				for(int i = 0; i < getMap().length; i++){
					pix[i][j] = new Pixel(getMap()[i][j], size);
					panel.add(pix[i][j]);
				}
			}
		}
		//this.add(panel, BorderLayout.BEFORE_LINE_BEGINS);
		//System.out.println(pix[0][0].size());
		
		//gs.f.getContentPane().add(panel);
		panel.setVisible(true);
		
		//gs.f.add(this);
		
		this.add(panel);
		gs.f.revalidate();
		//this.add(panel, BorderLayout.BEFORE_LINE_BEGINS);

		
		this.setVisible(true);
	}
	

	
	/**
	 * takes in coordinates and value and sets the map accordingly
	 * creates a player and stores it in Map
	 * creates blocks and stores them in Map
	 * @param i first coordinate
	 * @param j second coordinate
	 * @param type the type of the object
	 */
	public void setMap(int i, int j, String type) {
		//System.out.println(Integer.toString(i)+Integer.toString(j));
		map [i][j] = type;
		if(notre){ //only backup initial map
			mapback[i][j] = type;
		}
		if (type.equals("p")) {
			//System.out.println("Player");
			getPlayer().movePlayer(i, j);
			}
		else if (type.equals("b")) {
			Block b = new Block(i,j);
			blocks.add(b);
			}


		
	}
	

	
	
	/**
	 * checks if the player can move or not
	 * if empty can move directly
	 * if block then if other side is empty can move
	 * @return
	 */
	public boolean checkValidMove(String mov) {
		notre = false;
		// based on input if up down left or right
		if(mov.equals("up")|mov.equals("down")){
			int ychange = 0;
			int ynext = 0;
			switch(mov){
			case "up":
				ychange = getPlayer().getyPos()-1;
				ynext = ychange -1;
				break;
			case "down":
				ychange = getPlayer().getyPos()+1;
				ynext = ychange+1;
				break;
			}
			String type = getMap()[getPlayer().getxPos()][ychange];
			if (type.equals(" ")|type.equals("x")){
				
				return true;
			}
			// if obstacle then needa check if next block is empty
			else if (type.equals("b")|type.equals("c")) {
				String shift = getMap()[getPlayer().getxPos()][ynext];
				if (shift.equals(" ")|| shift.equals("x")){
					return true;
				}
			}
			
		}
		else if (mov.equals("left")|mov.equals("right")){
			int xchange = 0;
			int xnext = 0;
			switch(mov){
			case "left":
				xchange = getPlayer().getxPos()-1;
				xnext = xchange -1;
				break;
			case "right":
				xchange = getPlayer().getxPos()+1;
				xnext = xchange+1;
				break;
			}
			String type = getMap()[xchange][getPlayer().getyPos()];
			if (type.equals(" ")|type.equals("x")){
				return true;
			}
			// if obstacle then needa check if next block is empty
			else if (type.equals("b")|type.equals("c")) {
				String shift = getMap()[xnext][getPlayer().getyPos()];
				if (shift.equals(" ")|| shift.equals("x")){
					return true;
				}
			}
		}

		
		
		return false;
	}
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
					} else {
						getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
					}
					getPlayer().movePlayer(getPlayer().getxPos(), ychange);
				}

				
		}
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
			if (type.equals(" ")){
				// move to new spot
				getMap()[xchange][getPlayer().getyPos()] = "p";
				//original spot now be " " or "*"
				if(getMap()[getPlayer().getxPos()][getPlayer().getyPos()].equals("*")){
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
				} else {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";
				}
				getPlayer().movePlayer(xchange, getPlayer().getyPos());
			}
			// if obstacle then needa check if next block is empty
			else if (type.equals("b")) {
				String shift = getMap()[xnext][getPlayer().getyPos()];
				if (shift.equals(" ") || shift.equals("x")){
					Block b = getBlock(xchange,getPlayer().getyPos());
					if (b.getStatus() == false|| b.getStatus()==true) {
						// change the pos of block as well
						getMap()[xchange][getPlayer().getyPos()] = "p";
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
							getMap()[xnext][getPlayer().getyPos()] = "c";
							b.setStatus(true);
						} else b.setStatus(false);
					}
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
			else if (type.equals("x")){
				// move to new spot 
				// representing player in goal sopt as "*"
				getMap()[xchange][getPlayer().getyPos()] = "*";
				// original spot is now empty or restored to "x"
				if(getMap()[getPlayer().getxPos()][getPlayer().getxPos()].equals("*")){
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = "x";
				}else {
					getMap()[getPlayer().getxPos()][getPlayer().getyPos()] = " ";	
				}
				getPlayer().movePlayer(xchange, getPlayer().getyPos());
			}

		}
		/*

		//*/
	}
	
	/**
	 * takes in the coordinates and searches list of blocks and returns Block object from list
	 * @param xPos
	 * @param yPos
	 * @return Block b from list
	 */
	public Block getBlock(int xPos, int yPos) {
		
		for (Block b: blocks) {
			if (b.getxPos() == xPos && b.getyPos() == yPos) {
				return b;
			}
		}
		return null;
	}

	/**
	 * takes in the x,y coordinates and returns the type at the coordinate on the map
	 * @param x
	 * @param y
	 * @return
	 */
	public String getType(int x, int y){
		return getMap()[x][y];
	}
	
	/**
	 * checks if all the blocks are on goals
	 * if status is true for all blocks then return true
	 * @return
	 */
	public boolean isFinished() {
		boolean check = true;
		// for each block check if status is completed
		for (Block b : blocks) {
			if (b.getStatus() == false) {
				check = false;
				break;
			}
		}
		return check;
	}
	
	public void loading(String file) throws IOException{
		int tempsize = 0;
		BufferedReader br = new BufferedReader(new FileReader(file)) ;
	    String line;
	    int row = 0;
	    while ((line = br.readLine()) != null) {
	    	row = 0;
	    	for(String word : line.split("")){
	    		System.out.println(Integer.toString(tempsize)+Integer.toString(row));
	    		getMap()[row][tempsize] = word;
	    		//storeMap.add(word);
	    		setMap(row, tempsize, word);
	    		row++;

	    	}
	    	tempsize++;

	    }
	}

	
	public Player getPlayer() {
		return player;
	}



	public void setPlayer(Player player) {
		this.player = player;
	}


	public String[][] getMap() {
		return map;
	}



	public void setPreloadedMap(String[][] map) {
		this.map = map;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				setMap(i, j, map[i][j]); 
			}
		}
	}


	//handles the Buttons in menu
	private class ButtonClick implements ActionListener {

		@SuppressWarnings({ "static-access", "static-access" })
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("restart")){
				blocks.clear();
				
				for (int j = 0; j < getMap().length;j++) {
					for (int i = 0; i < getMap().length; i++) {
						getMap()[i][j] = mapback[i][j];
						String type = getMap()[i][j];
						setMap(i,j,type);

					}
				}
				printMap();
				printpix();
				
			}
			else if(e.getActionCommand().equals("instruction")){
				String howtoplay = "Press \"up\" \"down\" \"left\" and \"right\" button to move the player ( green pig) \n"
					      + "push all the boxes into the place has marks\n"
					      + "click \"Restart\" to restart current game";

				panel.add(new JTextArea(howtoplay));
				System.out.println(howtoplay);
				

				//System.out.println("instru");
			} else if (e.getActionCommand().equals("next") || e.getActionCommand().equals("skip")){
				
				levelnum++;
//				String[] level = new String[] {"../input".concat(Integer.toString(levelnum)).concat(".txt")};
//				File a = new File(level[0]);
//				
//				if(!a.exists()){
//					levelnum--;
//					
//				} else{
					
					//gs.m.invalidate();
					System.out.println(levelnum);
//					try {
//						size = gs.countline(level[0]);
//					} catch (IOException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}
					//System.out.println(size);
					gs.f.remove(gs.m);
					size = 7+levelnum;
					gs.m = new Map(7+levelnum, gs, levelnum);
					
					gs.m.initMap();
					
					
						//gs.readfile(level[0],gs.sys, size, row, preRow, storeMap);
						//gs.main(level);
					
//					try {
//						gs.m.loading(level[0]);
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
					GenerateMap gm = new GenerateMap(7+levelnum, levelnum * 2, 7+levelnum * 5);
					gm.generateMap();
					gs.m.setPreloadedMap(gm.giveMap());
					gs.m.printMap();
					
					gs.f.setSize((size+1)*100, size*100 +50);
					gs.m.printpix();
					gs.m.revalidate();
					gs.f.add(gs.m);
					//gs.keys();
					gs.f.revalidate();
					gs.m.setVisible(true);
			        gs.f.setVisible(true);
			        	
					/*} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						//levelnum -= 2;
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}//*/
				//}
			}//*/
			
		}

	}
	
	
}









