import java.io.IOException;
import java.io.InputStream;

/**
 * 
 */

/**
* @author: Andrew Jalbert
* @description: A class that reads InputStreams and counts the frequency of characters
*/
public class CharCounter implements ICharCounter {
	
	
	BitInputStream bit; // stores an instance of the bit input
	private int[] characterOccurance = new int[257]; // uses an int array to save counts of characters
	private int counter; // keeps a total count of characters
	
	/**
	 * Getter function for the total count of characters
	 * @return int counter
	 */
	public int getCounter(){
		return counter;
	}
	
	/**
	 * gets the count of that specific char
	 * @return int
	 */
	public int getCount(int ch) {
		
		if(ch >-1 && ch < 257)
		return characterOccurance[ch];
		
		else
			// out of bounds error
			return -1;
	}

	/**
	 * Counts all the chars read in the stream
	 * @return int; number of total chars
	 */
	public int countAll(InputStream stream) throws IOException {
		counter = 0;
		int inbits;
		bit = new BitInputStream(stream);
		while((inbits = bit.read(BITS_PER_WORD))!=-1){
			add(inbits);
			counter++;
		}
		
		return counter;
	}
	
	/**
	 * Increments the value at the given index by 1
	 */
	public void add(int i) {
		
		if(i!=-1)
		characterOccurance[i]+=1;

		
	}

	/**
	 * Sets the char index to the given value
	 * @param int i, int value
	 */
	public void set(int i, int value) {
		
		characterOccurance[i] = value;
		
	}

	/**
	 * Clears the characterOccurance array
	 * Sets all values back to zero
	 */
	public void clear() {
		for(int i = 0; i < 256; i++)
		set(i,0);
		
	}

}
