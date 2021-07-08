import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;


public class GameSystem{
	public Map m;
	public JFrame f;
	public String sys;
	//public int size = 0;

	public GameSystem(){
		// do not want to initialize m here because size yet unknown
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {

		
		
		GameSystem gs = new GameSystem();
		ArrayList<String> storeMap = new ArrayList<>();
		
		
		int row = 0;
		int preRow = -1;
		int size = 0;
		
		Pattern p = Pattern.compile("input(\\d)\\.txt");
		Matcher m = p.matcher(args[0]);
		int levelnum = 1;
		if(m.find()){
			MatchResult re = m.toMatchResult();
		
			String num1 = re.group(1);
			
		
			levelnum = Integer.parseInt(num1);
		}

		
		gs.sys = System.getProperty("os.name").replaceFirst("Windows.*", "Windows");
		gs.sys.replaceFirst("Linux.*", "Linux");
		//readfile(args[0], gs.sys, gs.size, row,preRow, storeMap);
		
		System.out.println(gs.sys);
		//gs.countline(args[0]);
			Scanner sc = null;
			
			switch(gs.sys){
			case "Linux":
			
		    try
		    {
		    	sc = new Scanner(new FileReader(args[0]));    // args[0] is the first command line argument
		    	System.out.println(sc);
		        while (sc.hasNextLine()) {

					// scan word by word
					String word = "";
					// allowing scanner to take char by char
					sc.useDelimiter("");
					// if next word exists then move on to next word in line
					// else jump to next line
					if (sc.hasNext()) {
						word = sc.next();
						if (word.equals("\n")) {
							size++;
							// System.out.pri ntln(preRow+ " - "+row);
							// first time then directly copy
							if (preRow == -1) {
								preRow = row;
								row = 0;
							}
							// if the previous row is not equal in size to the current row
							else if (preRow != row) {
								System.out.println("Error in size not square");
								return;
							}
							else {
								preRow = row;
								row = 0;
							}
						}
						else {
							// taking out the new lines in array
							storeMap.add(word);
							row ++;
						}

					} else {
						sc.nextLine();
					}
		        }

		    }
		    catch (FileNotFoundException e) {}
		    finally
		    {
		    	if (sc != null) sc.close();
		    }
		    break;
		    //*/

			// Win10 version
			case "Mac OS X":
			case "Windows":
			BufferedReader br = new BufferedReader(new FileReader(args[0])) ;
			    String line;
			    while ((line = br.readLine()) != null) {
			    	size++;
			    	for(String word : line.split("")){

			    		storeMap.add(word);
			    		row++;
			    		if(preRow == -1){
			    			preRow = row;
			    			row = 0;
			    		}
			    		else if(preRow != row){
			    			System.out.println("Error in size not square");
			    			return;
			    		}
			    		else {
			    			preRow = row;
			    			row = 0;
			    		}
			    	}

			    }
			
			break;
	//*/
			}
			
			//System.out.println(file+sys+Integer.toString(size)+Integer.toString(row)+Integer.toString(preRow)+storeMap);
			
		
		//System.out.println(gs.size);

		gs.f = new JFrame("Game"); 
		gs.f.setVisible(true);
		gs.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gs.f.setSize((size+1)*100, size*100+50);
		
		//gs.f.setLayout(new BorderLayout());
		
		
		
		
	    //gs.m = new Map(size, gs, levelnum);
	    gs.m = new ExtraMap(size, gs, levelnum);
	    
	    
	    
	   // gs.f.add(gs.m);
	    //gs.f.getContentPane().add(gs.m);
	    // initializes map with spaces
	    
	    gs.m.initMap();
	    gs.loadMap(storeMap, size);
	    System.out.println(gs.m.getMap()[1][1]);
        gs.m.printMap();

        gs.m.printpix();
        
        gs.f.add(gs.m);
        gs.keys();
        gs.m.setVisible(true);
        gs.f.setVisible(true);
        
        gs.f.setResizable(false);

	}


	public void keys() {
		
		
		//change f to m since m extends JFrame
        
        //m.setSize(700,700);
        //m.setVisible(true);
      
        f.setLocationRelativeTo(null);
       // f.add(m);
        //f.addKeyListener(new KeyListenerPanel());
        //JPanel p = new JPanel();
        //p.setLayout(new GridLayout());
        //m.add(p);
        
        
        

        KeyListener k = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				
				switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						//System.out.println("up");
						//if (m.checkValidMove("up")) {
							// move
							//System.out.println("can move up");
							m.moveAll("up");
							m.printMap();

							//updating gui
							m.printpix();
							

							if (m.isFinished()) {
								System.out.println("YOU HAVE WON");
							}
						//};
					break;
					case KeyEvent.VK_DOWN:
						//System.out.println("down");
						//if (m.checkValidMove("down")) {
							// move
							//System.out.println("can move down");
							m.moveAll("down");
							m.printMap();

							m.printpix();
							
							if (m.isFinished()) {
								System.out.println("YOU HAVE WON");
							}
						//};
					break;
					case KeyEvent.VK_LEFT:
						//System.out.println("left");
						//if (m.checkValidMove("left")) {
							// move
							//System.out.println("can move left");
							m.moveAll("left");
							m.printMap();

							m.printpix();
							
							if (m.isFinished()) {
								System.out.println("YOU HAVE WON");
							}
						//};
					break;
					case KeyEvent.VK_RIGHT:
						//System.out.println("right");
						//if (m.checkValidMove("right")) {
							// move
							m.moveAll("right");
							m.printMap();

							m.printpix();
							
							if (m.isFinished()) {
								System.out.println("YOU HAVE WON");
							}
						//};
						break;
				}

			}
		};
		
		f.addKeyListener(k);
        m.addKeyListener(k);
       
       // m.setVisible(true);

	}


	/**
	 * takes in the list of chars and converts into map
	 * @param storeMap list of strings
	 * @param size size of the map
	 */
	private void loadMap(ArrayList<String> storeMap, int size) {
		int i = 0;
		int j = 0;
		//System.out.println(size);
		for (String type : storeMap) {
			if (i == (size)) {
				j++;
				i = 0;
			}

			//System.out.println(i + " - " + j + " ----" + type);
			m.setMap(i, j, type);
			i++;

		}

	}
	
	public static int countline(String file) throws IOException{
		int count = 0;
		BufferedReader b = new BufferedReader(new FileReader(file));
		String line;
		while((line = b.readLine()) != null){
			count++;
		}
		b.close();
		
		return count;
	}



}



