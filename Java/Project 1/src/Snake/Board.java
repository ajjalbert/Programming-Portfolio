package Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Snake.SinglyLinkedList.*;

/**
 * The board where a snake game takes place.
 * 
 * @author YOUR NAME
 *
 */
public class Board extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 300;	// board width (pixels)
    private final int BOARD_HEIGHT = 300;	// board height
    private final int SCORE_X = 20;			// x coordinate of score
    private final int SCORE_Y = BOARD_HEIGHT - 30;	// y coordinate of score
    private final int DOT_SIZE = 10;		// height & width of a dot (snake joint / apple)					
    private final int ALL_DOTS = 900;		// total number of possible dots on the board
    
    private static SinglyLinkedList<Position> snek = new SinglyLinkedList<Position>(); // Linked list for snake's coordinates
    
    private static SinglyLinkedList<Position> wallList; // linked list for the wall obstacle(s)
    
    private int dots;	// number of joints in the snake body
    private int applePositionX;	// x coordinate of the current position of the apple
    private int applePositionY; // y coordinate of the current position of the apple

    // direction of the snake
    private boolean leftDirection = false;	
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    // whether the game is in play
    private boolean inGame = true;

    private Timer timer; // timer used to animate the game
    
    private Image ball;  // image for the snake joint
    private Image apple; // image for the apple
    private Image head;  // image for the snake head
    private Image wall;	 // image for the obstacles
    
    private int score;	// player's score
    private String name;	// player's name
    private int delay; 	// speed of the game
    private Scoreboard scoreboard; // score board of highest scores

    /**
     * Default constructor.  Sets up the score board.  Attaches a {@link TAdapter}
     * object that listens for key strokes. Sets the background color and preferred
     * size of the board. Loads images required for the game and initializes game state.
     */
    public Board() {
    	// create a Scoreboard object to keep 15 highest scores
    	scoreboard = new Scoreboard(15);
    	
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        
      
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        loadImages();
        // allocate memory for the wallList
        wallList = new SinglyLinkedList<Position>();
        initGame();
    }

    /**
     * Loads images needed for the board.
     */
    private void loadImages() {

    	
        ImageIcon iid = new ImageIcon(SnakeGame.class.getResource("/dot.png")); // joint of the snake
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon(SnakeGame.class.getResource("/apple.png")); // apple
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon(SnakeGame.class.getResource("/head.png")); // head of the snake
        head = iih.getImage();
        
        ImageIcon iiw = new ImageIcon(SnakeGame.class.getResource("/wall.png")); // obstacle
        wall = iiw.getImage();
    }

    /**
     * Initializes game state. Prompts for the player's name. Initializes
     * the number of snake joints and their corresponding positions.
     * Initializes the score and speed of the game. Randomly places an apple
     * on the game board. Starts the game timer.
     */
    private void initGame() {
    	// prompt for player's name
    	name = (String)JOptionPane.showInputDialog(
    	                    this,
    	                    "What is your name?",
    	                    "What is your name?",
    	                    JOptionPane.PLAIN_MESSAGE,
    	                    null,
    	                    null,
    	                    "your name");

    	
    	
        dots = 3;	// the snake has three joints initially
        
        // computes (x, y) coordinates of each of the joints
        for (int i = 0; i < dots; i++) {
        	
        	Position node = new Position(); // create a position
        	node.setPosition(50-i*DOT_SIZE,50); // update the position
        	
        	snek.addLast(node); // add the node at the back
          
        	
        }
        
        // randomly locates an apple on the board
        locateApple();

        // initializes score to 0
        score = 0;
        
        // initializes the speed of the game
        delay = 200;
        
      createWalls();
        
       // for(int i = 0; i < )
        
        // creates a Timer object needed for animation
        // each DELAY ms, the timer will call the actionPerformed method
        timer = new Timer(delay, this);
        timer.start();
       
    }
    
    /**
     * creates the wall obstacles at random locations
     */
    public void createWalls(){
    	// generate a new random value
    	Random rn = new Random();
    	
    	
    	 // let there be a max of 5 walls as to not clutter the screen
    	for(int j = 0; j < 10; j++){
        
        // generate a random number to be the size of the wall obstacle, letting no wall be greater length that 1/10 of the game window
        int wallSize = rn.nextInt((BOARD_HEIGHT/30));
  
        
        	
        	// create two ints that will be the new x and y position
        	int newX = 0;
        	int newY = 0;
        	
        	// generate a random starting X and Y for the wall until it does not spawn on the snake or apple
        	newX = rn.nextInt((BOARD_WIDTH) + 1);
        	newY = rn.nextInt((BOARD_HEIGHT) + 1); 
        	
        	// The snake updates in increments of ten, convert the x and y coordinates to increments of 10
        	newX = newX/DOT_SIZE;
        	newX = newX*DOT_SIZE;
        	
        	newY = newY/DOT_SIZE;
        	newY = newY*DOT_SIZE;
        	
        	// Do not let the wall spawn on the snake or the apple
        	while(newX == 50 && newY == 50 || newX == 50-DOT_SIZE && newY == 50 ||newX == 50-2*DOT_SIZE && newY == 50 || newX == applePositionX && newY == applePositionY){
        		// generate a random starting X and Y for the wall until it does not spawn on the snake or apple
            	newX = rn.nextInt((BOARD_WIDTH));
            	newY = rn.nextInt((BOARD_HEIGHT));
            	
            	// The snake updates in increments of ten, convert the x and y coordinates to increments of 10
            	newX = newX/DOT_SIZE;
            	newX = newX*DOT_SIZE;
            	
            	newY = newY/DOT_SIZE;
            	newY = newY*DOT_SIZE;
        	}
        	
        	
        	
        	// create two ints that determine if the wall expand in the x or y direction, or both
        	int xDirection;
        	int yDirection;
        	
        	// add the wall into its starting position
        	Position thing = new Position(newX, newY);
            wallList.addFirst(thing);
            
        	// let their range be 0 to 1
            Random delta = new Random();
        	xDirection = delta.nextInt(2);
        	yDirection = delta.nextInt(2);
        	
        	// make sure the wall will never spawn on itself
        	while(xDirection == 0 && yDirection == 0){
        		xDirection = delta.nextInt(2);
            	yDirection = delta.nextInt(2);
        	}
        	
        	// make the change in x and y a multiple of ten
        	xDirection = xDirection * DOT_SIZE;
        	yDirection = yDirection * DOT_SIZE;
        	
        	// loop to create the list of walls
        		for(int i = 0; i < wallSize; i++){
        			       			
        			// make sure the wall does not leave the board
        			// change the direction of X and Y if it leaves the bounds of the board
        			if(newX+xDirection > 300){
        				newX = 0;        				
        			}
        			
        			if(newY+yDirection>300)
        				newY = 0;
        			
        			// make sure the wall does not spawn on the apple
        			if(newX+xDirection == applePositionX)
        				newX+=DOT_SIZE+xDirection;
        			if(newY+xDirection == applePositionY)
        				newY+=DOT_SIZE+yDirection;
        			
        			// make sure the wall will not spawn on the snake
        			if(newX+xDirection == snek.first().getX())
        				newX+=DOT_SIZE+xDirection;
        			
        			if(newY+yDirection == snek.first().getY())
        				newY+=DOT_SIZE+yDirection;
        			
        			// else update the next object's position
        			else{
        				newX+=xDirection;
        				newY+=yDirection;		
        			}
        			
        			// update the wall's location
        			thing = new Position(newX, newY);
                    wallList.addLast(thing);
        		}
        		
        		// update the next wall size
        		wallSize = rn.nextInt((BOARD_HEIGHT)/100);
        	}
    	
       
    }

    @Override
    /**
     * Paints the board by calling {@link doDrawing}.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
    
    /**
     * Draws the 2-D graphics for a snake game.
     * 
     * @param g the graphics object on which the drawing is performed
     */
    private void doDrawing(Graphics g) {
    	// if game is in play, displays game interface

        if (inGame) { 
            g.drawImage(apple, applePositionX, applePositionY, this); // displays an apple
            
            // create a node to walk through the snake
           Node<Position> temp = new Node<Position>(null,null);
           temp = snek.getHead();
            
          
            // displays the snake
            for (int z = 0; z < dots; z++){
            
            	
            	if(z == 0)
                    g.drawImage(head, temp.getElement().getX(), temp.getElement().getY(), this);
            		
            	
            	else
            		 g.drawImage(ball, temp.getElement().getX(), temp.getElement().getY(), this);
            		
            	temp = temp.getNext();
            }
            
            // create a node to walk through the wallList
            Node<Position> wallTemp = new Node<Position>(null,null);
            wallTemp = wallList.getHead();
           
              for(int i = 0; i < wallList.size(); i++){
                	 g.drawImage(wall, wallTemp.getElement().getX(), wallTemp.getElement().getY(),this);
                	 wallTemp = wallTemp.getNext();
                 }

            // displays score board (current score and player's name)
            String msg = "Score:  " + score;
            Font small = new Font("Helvetica", Font.BOLD, 14);
            
            g.setFont(small);
            g.setColor(Color.white);
            g.drawString(msg, SCORE_X, SCORE_Y);           
                       
            msg = "Player:  " + name;
            g.drawString(msg, SCORE_X, SCORE_Y + 15); 
            
            // synchronizes the painting on systems that buffer graphics events
            // in order to allow smooth animation
            Toolkit.getDefaultToolkit().sync();

        } 
        // game is over
        else {
            gameOver(g);
        }        
    }

    /**
     * Displays the game over screen. Displays game over message, player's last score,
     * and a list of highest scores.
     * 
     * @param g the graphics object on which the drawing is performed
     */
    private void gameOver(Graphics g) {
        // adds score to score board
    	scoreboard.add(new GameEntry(name, score));
    	
    	// displays game over message
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        
        String msg = "Game Over";
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, 20);
        
        msg = "Your score was " + score;
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, 50);
        
        msg = "Press Enter to Play Again";
        g.setColor(Color.yellow);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, 80);
     
        // displays highest scores
        msg = "----------------------------------";
        g.setColor(Color.green);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, 120);
        
        msg = "Highest scores:";
        g.setColor(Color.green);
        g.drawString(msg, (BOARD_WIDTH - metr.stringWidth(msg)) / 2, 140);
        g.setColor(Color.gray);
        msg = scoreboard.toString();
        int x = 20;
        int y = 150;
        for (String line : msg.split("\n")) {
        	if (y > BOARD_HEIGHT) {
        		y = 150;
        		x = 150;
        	}
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
        }
       
        // Remove the old snake
        while(snek.size()!=0){
        	snek.removeFirst();
        }
        // remove the old walls
        while(wallList.size()!=0)
        	wallList.removeFirst();
        
        
    }

    /**
     * Checks if the head of the snake collides with an apple.
     * 
     * If the head of the snake collides with the apple,
     * the game level is increased, and
     * an apple is placed in a new random location.
     */
    private void checkApple() {

        if ((snek.first().getX() == applePositionX) && (snek.first().getY() == applePositionY)) {
        	increaseLevel();
            locateApple();
        }
    }

    /**
     * Moves the snake for one time step. 
     * Changes the head of the snake based on the direction of movement
     * and shift the rest of the joint positions up the array.
     * (that is, the second joint moves where the first was, the 
     * third joint moves where the second was, etc.)
     */
    private void move() {
    	// shifts the joint positions, except the head, up the array
    	
    	// set the dot count to the size of the snake
		int dots = snek.size();   	
    	// reverse the list
		snek.reverse();
		// create 2 nodes, one that is the current node and the next node
		Node<Position> current = new Node<Position>(null,null);
		Node<Position> next = new Node<Position>(null,null);
		
		// assign current to the head node of the reversed list
		current = snek.getHead();
		// assign next to the node after the head of the reversed list
		next = snek.getHead().getNext();
		
		
    	 
    	// for the size-1 of the list, update positions. Do not update the snake's head this way only the body
        for (int z = dots-1; z > 0; z--) {
        	
        	// set the reversed head's position to the node that is next to it so that the body moves
        	// Essentially set the very end of the snake to be the node in front of it, up to the head
        	current.getElement().setPosition(next.getElement().getX(), next.getElement().getY());
        	
        	// update the nodes, current now needs to be it's next node
        	current = current.getNext();
        	
        	// next node needs to walk as well to update the body properly
        	next = next.getNext();
        }
        
        // reverse the snake again so that the 'head' is the correct head
        snek.reverse();
        
        // updates the head based on the current direction
        if (leftDirection) {
        	snek.first().setPosition(snek.first().getX()-DOT_SIZE, snek.first().getY());
        	
        }

        if (rightDirection) {
        	snek.first().setPosition(snek.first().getX()+DOT_SIZE, snek.first().getY());
           
        }

        if (upDirection) {
        	snek.first().setPosition(snek.first().getX(), snek.first().getY()-DOT_SIZE);
           
        }

        if (downDirection) {
        	snek.first().setPosition(snek.first().getX(), snek.first().getY()+DOT_SIZE);
           
            
        }
    }

    /**
     * Checks if the snake has collided with itself or with one of the walls.
     * The game is over if a collision has occurred.
     */
    
    private void checkCollision() {
    	// checks if the snake has hit itself
    	
    	// Create two nodes to represent the head of the snake, and the next node to check
    	Node<Position> snekHead = new Node<Position>(null,null);
    	Node<Position> snekAfter = new Node<Position>(null,null);
    	// assign the head the current head
    	snekHead = snek.getHead();
    	// start the check form the node after the head
    	snekAfter = snek.getHead().getNext();
        for (int z = dots; z > 0; z--) {
        	
        	
            if ((z > 4) && (snekHead.getElement().getX() == snekAfter.getElement().getX()) && (snekHead.getElement().getY() ==snekAfter.getElement().getY())) {
                inGame = false;
            }
            if(z > 4)
                snekAfter = snekAfter.getNext();

        }
       
        // checks if the snake has hit one of the walls
        if (snek.first().getY() >= BOARD_HEIGHT) { // top wall
            inGame = false;
        }
        else if (snek.first().getY() < 0) { // bottom wall
            inGame = false;
        }
        if (snek.first().getX() >= BOARD_WIDTH) { // right wall
            inGame = false;
        }
        else if (snek.first().getX() < 0) { // left wall
            inGame = false;
        }
        
        // check for a collision with the wall
        
        // create a temp node that will be the start of the wall
        Node<Position> wallStart = new Node<Position>(null,null);
        wallStart = wallList.getHead();
       

        // loop through the wallList and check to see if the snake has hit the wall
        for(int i = 0; i < wallList.size(); i++){
        	
        	if(snek.first().getX() == wallStart.getElement().getX() && snek.first().getY() == wallStart.getElement().getY()){
        		inGame = false;	
        	}
        	else{
        		
        		
        	wallStart = wallStart.getNext();
        	}
        }

        // stop the timer if game is over
        if(!inGame) {
            timer.stop();
        }
    }

    /**
     * Sets the position of the apple to a random location.
     */
    private void locateApple() {
    	
        int r = (int) (Math.random() * BOARD_WIDTH);
        
        applePositionX = r / DOT_SIZE * DOT_SIZE;
        r = (int) (Math.random() * BOARD_HEIGHT);
        applePositionY = r / DOT_SIZE * DOT_SIZE;
        
        // check that the apple will not spawn into a wall
        Node<Position> temp = new Node<Position>(null,null);
        
        temp = wallList.getHead();
        for(int i = 0; i < wallList.size(); i++){
        	// if the apple spawns on a wall, create a new spawn
        	if(applePositionX == temp.getElement().getX() && applePositionY == temp.getElement().getY()){
        			r = (int) (Math.random() * BOARD_WIDTH);
        			applePositionX = r / DOT_SIZE * DOT_SIZE;
        	        r = (int) (Math.random() * BOARD_HEIGHT);
        	        applePositionY = r / DOT_SIZE * DOT_SIZE;
        	        i = 0;
        	        temp = wallList.getHead();
        	}
        		temp = temp.getNext();
        	
        }
    }

    /**
     * Increases the game level by increases the score, speed, and the number of joints
     * in the snake body.
     */
    private void increaseLevel() {
    	Position next = new Position();
    	snek.addLast(next);
        dots++;
        score += dots * 5;
        delay -= 30;
        if (delay < 20)
        	delay = 20;
    }
    
    @Override
    /**
     * Updates the game state and graphics. 
     * This method is repeated called by the timer. 
     * Represents one time step of the game. If the 
     * game is on, the game state is updated based on whether 
     * the snake eats an apple or experiences a collision.
     * The score is decreased each time this method is called.
     */
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple();
            checkCollision();
            move();
            score--;
        }

        repaint();
    }

    /**
     * An adapter class for receiving keyboard events.
     * The direction keys are detected if the game is in play.
     * If the game is over, the enter key is detected.
     */
    private class TAdapter extends KeyAdapter {

        @Override
        /**
         * Updates the direction of the snake according to keystrokes
         */
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            if (inGame) { // game is in play
	            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) { // left
	                leftDirection = true;
	                upDirection = false;
	                downDirection = false;
	            }
	
	            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) { // right
	                rightDirection = true;
	                upDirection = false;
	                downDirection = false;
	            }
	
	            if ((key == KeyEvent.VK_UP) && (!downDirection)) { // up
	                upDirection = true;
	                rightDirection = false;
	                leftDirection = false;
	            }
	
	            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) { // down
	                downDirection = true;
	                rightDirection = false;
	                leftDirection = false;
	            }
            }
            else { // game over
            	if (key == KeyEvent.VK_ENTER) {
            		// restarts game
            		inGame = true;
            		initGame();
            	}
            }
            
        }
    }
}