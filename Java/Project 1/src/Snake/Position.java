package Snake;

import Snake.SinglyLinkedList.*;

public class Position {
	private int x;
	private int y;
	
	/**
	 * Default constructor for the Position class
	 * Position class contains two private ints, an x and y
	 */
	public Position(){
		x = 0;
		y = 0;
	}
	
	/**
	 * Overload Constructor for the Position class
	 * @param changes the x
	 * @param changes the y
	 */
	public Position(int startX, int startY){
		x = startX;
		y = startY;
	}
	/**
	 * Getter for x variable in position class
	 * @return integer, x variable of Position object
	 */
	public int getX(){
		return x;
	}
	/**
	 * Getter for y variable in position class
	 * @return integer, y variable of Position object
	 */
	public int getY(){
		return y;
	}
	/**
	 * Setter for the Position class, takes two variables. Sets x and y to new values
	 * @param newX
	 * @param newY
	 */
	public void setPosition(int newX, int newY){
		x = newX;
		y = newY;
	}
	
		
}
