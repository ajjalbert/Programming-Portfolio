package Snake;
import Snake.SinglyLinkedList.*;

/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/** Class for storing high scores in an array in nondecreasing order. */
public class Scoreboard {
  private int numEntries = 0; // number of actual entries
  private int MAX_CAPACITY = 0;
  private GameEntry[] board;               // array of game entries (names & scores)
  private SinglyLinkedList<GameEntry> boardList;
  /** Constructs an empty scoreboard with the given capacity for storing entries. */
  public Scoreboard(int capacity) {
	    // Allocate memory for the linked list of game entry
    boardList = new SinglyLinkedList<GameEntry>();
    // define the max size of the linked list
    MAX_CAPACITY = capacity;
    
  
  }

  /** Attempt to add a new score to the collection (if it is high enough) */
  public void add(GameEntry e) {
	  
	  // store the new score
    int newScore = e.getScore();
  
    
    
   /**
    if(boardList.first() == null){
    	boardList.removeFirst();
    	boardList.addFirst(e);
    	System.out.println("Added a score");
    }
    */
    
    // reverse the list to have highest score at the front if it is not at the front
    if(boardList.size() > 1 && boardList.first().getScore() < boardList.last().getScore())
    	boardList.reverse();
    	
    
    // is the new entry e really a high score?
    if (numEntries < MAX_CAPACITY+1 || newScore > boardList.last().getScore()) {
      if (numEntries < MAX_CAPACITY+1)        // no score drops from the board
        numEntries++;                       // so overall number increases
           
     
     
      // start at the end of the list, so reverse the scores list back to where the highest score is the last element
      if(boardList.size() > 0)
      boardList.reverse();
      
      
      // if the list is empty, then add the score to the front
      if(boardList.size() == 0){
		  boardList.addFirst(e);
      }
      
      // else if the score at the first list is greater than the new score, put the new score at the front
      else if(boardList.first().getScore() > newScore)
    	  boardList.addFirst(e);
    	  
      // else if the new score is greater than or equal to the last score, add it to the end
      // this allows the scoreboard to become populated quicker
      else if(boardList.last().getScore() <= newScore)
    	  boardList.addLast(e);
      
      // else one needs to walk the list to find where the score needs to go
      else{
    	  
    	// create two nodes to later transverse the linked list
    	    Node<GameEntry> current = new Node<GameEntry>(null,null);
    	    Node<GameEntry> previous = new Node<GameEntry>(null,null);
    	  
    	    // initialize the current node to be the head of the list (the lowest score)
    	  current = boardList.getHead();
    	  
    	  // set the the previous pointer to point to the current node, in case the list needs to update immediately
    	  previous.setNext(current);
    	  
    	  // walk the list to find where the new score should go
    	  for(int l = 0; l < boardList.size()-1; l++) {
    	  
    	  // if the new score is bigger than the current score, update the current pointer to its next
    		  // and update the previous pointer to be the current pointer
    		  // this will allow the new entry to find a node it is bigger than (previous), but also less than (current)
    	 if(current.getElement().getScore() < newScore){
    		 previous = current;
    		 current = current.getNext();
   	  }
    	
      	}
    	  
     // once the loop exits, then create a new node to be added to the list
    	  // this node will go between the previous node and the current node
      Node<GameEntry> newEntry = new Node<GameEntry>(e,null);
      
      // set the new node to point to the previous node's next value
      newEntry.setNext(previous.getNext());
      
      // set the previous node to now point to the new node
      previous.setNext(newEntry);
      
      // update the boardList size after each addition of a new node
      boardList.setSize(boardList.size()+1);
    
     
      
      }
      // do not let the list grow bigger than 16, the screen can only print 16 high scores
      if(boardList.size() > 16)
      	boardList.removeFirst();
      
      
      
      
    }	
    
    // reverse the list once more to set it back to the way it was before it was modified
    if(boardList.size() > 0)
    	boardList.reverse();
    	
    // reverse the list so that the lowest score is at the top, and the highest score is at the bottom
    if(boardList.size() > 1 && boardList.last().getScore() < boardList.first().getScore())
    	boardList.reverse();
   
    
  }

  /** Remove and return the high score at index i. */
  public GameEntry remove(int i) throws IndexOutOfBoundsException {
    if (i < 0 || i >= numEntries)
      throw new IndexOutOfBoundsException("Invalid index: " + i);
    GameEntry temp = board[i];                 // save the object to be removed
    
    Node<GameEntry> current = new Node<GameEntry>(null,null);
    Node<GameEntry> previous = new Node<GameEntry>(null,null);
    Node<GameEntry> next = new Node<GameEntry>(null,null);
    
    current = boardList.getHead();
    
    for(int k = 0; k < i; k++){
    previous = current;
    current = current.getNext();
    
    }
    
    next = current.getNext();
    previous.setNext(next);
    current.setNext(null);
    for (int j = i; j < numEntries - 1; j++)  // count up from i (not down)
    	
      //board[j] = board[j+1];                   // move one cell to the left
    //board[numEntries -1 ] = null;              // null out the old last score
    numEntries--;
    System.out.println(current.getElement().getScore());
    return current.getElement();                               // return the removed object
  }

  /** Returns a string representation of the high scores list. */
  public String toString() {
    StringBuilder sb = new StringBuilder("");
    Node<GameEntry> walk = new Node<GameEntry>(null,null);
    walk = boardList.getHead();
    for (int j = 0; j < numEntries; j++) {
      if (j > 0)
        sb.append("\n");                   // separate entries by commas
     // sb.append(board[j]);
      if(walk == null)
    	  break;
      sb.append(walk.getElement());
      if(walk.getNext()!=null)
      walk = walk.getNext();
    }

    return sb.toString();
  }
}
