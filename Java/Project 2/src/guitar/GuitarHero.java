package guitar;
import guitar.RingBuffer.RingBufferException;
import guitar.GuitarString;

/**
 * @author: Andrew Jalbert
 * @version: 1.0
 * @filename: GuitarHero.<!-- -->java
 * @description: Creates a synthetic guitar of 37 different notes. Also graphs the sound wave in real time. 
 */
public class GuitarHero {
	public static void main(String[] args) throws RingBufferException {
		String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' "; // string of valid keys
		
		GuitarString[] guitar = new GuitarString[37]; // GuitarString array that saves all the playable frequencies
		
		// create a buffer to store the sample, a bigger buffer can yield less delay but a large buffer will cause delay
		RingBuffer wave = new RingBuffer(3700);
		
		// Set up each guitar string to have the proper frequencies
		for(int i = 0; i < keyboard.length(); i++){
			guitar[i] = new GuitarString(440 * (Math.pow(1.05956, i-24)));
			
		}
		
		// while the program is running, allow input
		while(true){
			
			
			// if the buffer becomes full, draw the wave
			if(wave.isFull()){
				// local variables that will help as points for the line
				double x0,y0,x1,y1;
				// start x from 0
				x0 = x1 = 0;
				// the y-axis is 0 to 1 so put the starting point at 0.5
				y0 = y1 = 0.5;
				// create a counter to help update the x position start it at 1
				double counter = 1;
				// lower the show delay to help reduce sound and graph delay
				StdDraw.show(2);
				StdDraw.clear();
				
				// clear the wave buffer
				while(!wave.isEmpty()){
					// set new x as the iteration divided by a width
					//Note: a high width creates a better wave,
					// but too high and the wave becomes bunched up
					
					// update the new end of the line
					x1 = counter/880;
					
					// get the new pitch of the wave
					y1 = wave.dequeue() + 0.5;
					
					// draw the sound wave
					StdDraw.line(x0, y0, x1, y1);
					
					// update the y0 and x0 to be the x1 y1 values so a continuous line is made
					y0 = y1;
					x0 = x1;
					
					// increase the counter
					counter++;
				}
				// show the drawing
				StdDraw.show(2);
			}
			
				
			// Check for key input
			 if (StdDraw.hasNextKeyTyped()) {
				 
	                // the user types this character
	                char key = StdDraw.nextKeyTyped();
	                
	                // convert the character to an index
	                int index = keyboard.indexOf(key);
	                
	                // pluck the corresponding string if a correct key is hit
	                if (index > -1){
	                	guitar[index].pluck();
	                	
	                }
	            }
			 
			 // initial sample
			 double sample = 0; 
			 
			// compute the superposition of the samples
	            for(int i = 0; i < keyboard.length(); i++){
	            	sample += guitar[i].sample();
	            }
	          
	        // Play the generated sample
			StdAudio.play(sample);
			
			// Store the sample in the wave buffer if it's empty
			if(!wave.isFull())
				wave.enqueue(sample);
			
			// Advance the simulation
			for(int i = 0; i < keyboard.length(); i++){
            	guitar[i].tic();
            }
			
			
		}
	}
}	
