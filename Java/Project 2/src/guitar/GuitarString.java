package guitar;
import guitar.RingBuffer;

import guitar.RingBuffer.RingBufferException;

import java.util.Random;

/**
 * @author Andrew Jalbert
 * @filename GuitarString.<!-- -->java
 * @description A class that utilizes a ring buffer to create a simulation of a guitar string
 */
public class GuitarString {
	private RingBuffer buffer; // RingBuffer to store frequencies
	private int ticCalled; // amount of times the tic function is called
	
	
	/**
	 * Overload constructor for Guitar string, fill buffer with 0's
	 * @param double frequency that is divided into 44100 to create the buffer size
	 * @throws RingBufferException 
	 */
	public GuitarString(double frequency) throws RingBufferException{
		buffer = new RingBuffer(((int) Math.ceil(44100/frequency))); // create a new buffer
		
		while(!buffer.isFull()){ // fill the buffer with zeros
			buffer.enqueue(0);
		}
		
	}
	
	/**
	 * Creates a guitar string with by passing in an already created double array
	 * @param double[] that will be copied into the buffer
	 * @throws RingBufferException 
	 */
	public GuitarString(double[] init) throws RingBufferException{
		buffer = new RingBuffer(init.length); // set the new buffer to a given array
		for(int i = 0; i < buffer.size(); i++){ // copy the values into the buffer
			buffer.enqueue(init[i]);
		}
	}

	/**
	 * Used to simulate the pluck of a guitar string by generating white noise
	 * Replaces all items in the ring buffer with values between -0.<!-- -->5 and +0.<!-- -->5
	 * @throws RingBufferException, if the buffer is empty it will not pluck
	 */
	public void pluck() throws RingBufferException{
		
		while(!buffer.isEmpty()){ 
		buffer.dequeue();
		}
		
		while(!buffer.isFull()){
			Random rand = new Random();
			double x = (-0.5 + (0.5 - -0.5) * rand.nextDouble());
			buffer.enqueue(x);
		}
	}
	
	/**
	 * advances the simulation by one step
	 * @throws RingBufferException, if the buffer is empty it will not tic
	 */
	public void tic() throws RingBufferException{
		ticCalled += 1;
		double x = buffer.dequeue();
		buffer.enqueue(((x+buffer.peek())/2)*0.994);
		
	}
	
	/**
	 * returns the current sample
	 * @return double the first value in the buffer, but does not delete it
	 * @throws RingBufferException, if the buffer is empty it will not return a sample
	 */
	public double sample() throws RingBufferException{
		return buffer.peek();
	}
	
	/**
	 * returns the number of tics
	 * @return int ticCalled, the number of times tic has been called
	 */
	public int time(){
		return ticCalled;
	}
}
