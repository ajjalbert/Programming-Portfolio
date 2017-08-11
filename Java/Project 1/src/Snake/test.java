package Snake;
import Snake.SinglyLinkedList.*;
import Snake.Position.*;
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SinglyLinkedList<Position> temp = new SinglyLinkedList<Position>();

		
		for(int i = 0; i < 5; i++){
			Position lit = new Position(i,1);
			temp.addLast(lit);
		}
		
		
	
		
		int dots = temp.size();
	
 
    	
    	// reverse the list
		temp.reverse();
		// create 2 nodes, one that is the current node and the next node
	/**	Node<Position> current = new Node<Position>(null,null);
		Node<Position> next = new Node<Position>(null,null);
		
		// assign current to the head node of the reversed list
		current = temp.getHead();
		// assign next to the node after the head of the reversed list
		next = temp.getHead().getNext();
		
		
    	 
    	// for the size-1 of the list, update positions. Do not update the snake's head this way only the body
        for (int z = dots-1; z > 0; z--) {
        	System.out.println("Iteration " + z + " ");
        	System.out.println("Making " + current.getElement().getX() + " into " + next.getElement().getX() + " ");
        	
        	// set the reversed head's position to the node that is next to it so that the body moves
        	current.getElement().setPosition(next.getElement().getX(), next.getElement().getY());
        	
        	
        	
        	System.out.println("snekMover new first is " + current.getElement().getX());
        	// update the nodes, current now needs to be it's next node
        	current = current.getNext();
        	// next node needs to walk as well to update the body properly
        	next = next.getNext();
        }
       */
       temp.reverse();
       
       for(int i = 0; i < dots; i++)
       	System.out.println(temp.removeFirst().getX());
        
      
	}
	
	

}
