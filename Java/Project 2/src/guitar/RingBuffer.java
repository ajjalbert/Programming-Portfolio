package guitar;

/**
 * 
 * @author Andrew Jalbert
 * @filename RingBuffer.<!-- -->java
 * @description A static array data type that uses wrap around methodology
 */
public class RingBuffer {
	
	private int size = 0; // current size of the buffer
	private int maxCapacity; // max room in the buffer
	double array[]; // array that stores the notes
	int first = 0; // value that will keep track of the last index to be modified
	int last = 0; // Location of the last index
	/**
	 * Constructor for a RingBuffer
	 * User sets the RingBuffer capacity
	 * @param capacity
	 */
	public RingBuffer(int capacity){
		maxCapacity = capacity; // save the capacity
		array = new double[maxCapacity]; // create an array of the given size
	}
	
	/**
	 * Default constructor
	 * Creates a RingBuffer with a max capacity of 100
	 */
	public RingBuffer(){
		maxCapacity = 100; // create an array of a designated size
		array = new double[maxCapacity];
	}
	/**
	 * returns size of the array
	 * @return int current size of the array
	 */
	public int size(){
		return size;
	}
	
	/**
	 * Returns true or false depending if the buffer is empty
	 * @return boolean true if size is 0
	 */
	public boolean isEmpty(){
		return size==0;
	}
	
	/**
	 * Returns true or false depending if the buffer is full
	 * @return boolean true if size==maxCapacity
	 */
	public boolean isFull(){
		return size==maxCapacity;
	}
	/**
	 * queues an element in to the ring buffer
	 * @param double x
	 */
	public void enqueue(double x)throws RingBufferException{
		if(isFull())
			throw new RingBufferException("Array is full."); // throw an exception if the array is full
		
		array[last] = x; // update the last element to be x
		last = (last+1)%maxCapacity; // update the last index, make sure to wrap around
		
		size+=1; //increment the size
		
	}
	
	/**
	 * Removes and returns the value at the from of the Ring Buffer
	 * @return double value at the front of the array
	 * @throws RingBufferException
	 */
	public double dequeue() throws RingBufferException{
		
		if(isEmpty())
			throw new RingBufferException("Array is empty."); // thow an exception of the array is empty
		
		double x = 0;
		
		x = array[first]; // save the first element
		
		array[first] = 0; // update the first element
		
		first = (first+1)%maxCapacity; // update the index of the first element
		
		size-=1; // decrement the size
		
		return x; // return the first element
		
	}
	
	/**
	 * Returns but does not remove the front element of the array
	 * @return double first element of the array
	 * @throws RingBufferException
	 */
	public double peek() throws RingBufferException{
		
			return array[first];
		
	}
	
	/**
	 * 
	 * @author Andrew Jalbert
	 *
	 */
	public class RingBufferException extends Exception{
		/**
		 * 
		 * @param String argument to be output
		 */
		public RingBufferException(String arg) {
			
			super(arg);
		}
	}
	
}
