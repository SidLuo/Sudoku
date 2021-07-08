import java.awt.*;
import java.io.File;

import javax.swing.*;
public class Pixel extends JPanel{

	private String type;
	
	public Pixel(String type, int size){
		this.type = type;
		
			this.setLayout(new GridLayout(1,1));
			this.setSize(100, 100);
			this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			JLabel label = new JLabel();
			
			label.setSize(100, 100);
			
			String path = "\\";

		    switch(type){
		    case "p":
				path = "./src/boxman.jpg";
				break;
		    case "b":
		    	path = "./src/block.jpg";
		    	break;
		    case "#":
		    	path = "./src/wall.jpg";
		    	break;
		    case " ":
		    	path = "./src/floor.jpg";
		    	break;
		    case "x":
				path = "./src/goal.jpg";
				break;
		    case "*":
		    	path = "./src/playeringoal.jpg";
		    	break;
		    case "+":
		    	path = "./src/playeringoal.jpg";
		    	break;
		    case "D":
		    	path = "./src/closed_door.jpg";
		    	break;
		    case "o":
		    	path = "./src/open_door.jpg";
		    	break;
	    	case "O":
		    	path = "./src/open_door.jpg";
		    	break;
		    case "s":
		    	path = "./src/switch.jpg";
		    	break;
		    case "w":
		    	path = "./src/warp.jpg";
		    	break;
		    case "W":
		    	path = "./src/warp.jpg";
		    	break;
		    case "c":
		    	path = "./src/blockingoal.jpg";
		    	break;
		    }
		    Image img = (new ImageIcon(path)).getImage();
		    	Image newimg = img.getScaledInstance(100, 100,java.awt.Image.SCALE_SMOOTH ) ;  
				ImageIcon icon = new ImageIcon(newimg);
				label = new JLabel(icon);
				this.add(label);
				label.setVisible(true);
	

	}
	
	
	
}
