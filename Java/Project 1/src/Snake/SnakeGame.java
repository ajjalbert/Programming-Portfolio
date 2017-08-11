package Snake;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Snake game application.
 * <p>
 * The player of the game controls a snake to eat as many apples
 * as possible. Each time the snake eats an apple, its body grows.
 * The game is over when the snake hits a wall or its own body.
 * Based on <a href="http://zetcode.com/tutorials/javagamestutorial/snake/">
 * Java 2D Game Tutorial</a>. 
 * 
 * @author  YOUR NAME
 * @use 	Board
 */
public class SnakeGame extends JFrame {

    public SnakeGame() {

    	// place a Board object at the center of the JFrame container
        add(new Board());
        // do not allow the frame to be resized
        setResizable(false);
        // cause the frame to be sized to fit the preferred size and layouts of its children
        pack();
        
        // application title (PLACE YOUR NAME HERE)
        setTitle("Snake - PLACE YOUR HERE HERE");
        // center the frame on the screen
        setLocationRelativeTo(null);
        // close the application when the close button is clicked.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    

    /**
     * Entry point of the game
     * 
     * @param args
     */
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {          
            	// create an instance of the SnakeGame and
            	// make it visible
                JFrame ex = new SnakeGame();
                ex.setVisible(true);                
            }
        });
    }
}
