import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	private Map m;
	
	public KeyHandler(Map m) {
		this.m = m;
	}

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
				if (m.checkValidMove("up")) {
					// move
					//System.out.println("can move up");
					m.moveAll("up");
					m.printMap();
					//updating gui
					m.printpix();
					
					
					if (m.isFinished()) {
						System.out.println("YOU HAVE WON");
					}
				};
			break;
			case KeyEvent.VK_DOWN: 
				//System.out.println("down");
				if (m.checkValidMove("down")) {
					// move
					//System.out.println("can move down");
					m.moveAll("down");
					m.printMap();
					
					m.printpix();
					
					if (m.isFinished()) {
						System.out.println("YOU HAVE WON");
					}
				};
			break;
			case KeyEvent.VK_LEFT: 
				//System.out.println("left");
				if (m.checkValidMove("left")) {
					// move
					//System.out.println("can move left");
					m.moveAll("left");
					m.printMap();
					
					m.printpix();
					
					if (m.isFinished()) {
						System.out.println("YOU HAVE WON");
					}
				};
			break;	
			case KeyEvent.VK_RIGHT: 
				//System.out.println("right");
				if (m.checkValidMove("right")) {
					// move
					m.moveAll("right");
					m.printMap();
					
					m.printpix();
					
					if (m.isFinished()) {
						System.out.println("YOU HAVE WON");
					}
				};
			break;
			case KeyEvent.VK_Q:
				// quits the game
				System.out.println("exit");
			break;
			case KeyEvent.VK_R:
				// quits the game
				//System.out.println("reset");
				//m.resetMap();
				m.printMap();
				m.printpix();
			break;
		}
	}

}
