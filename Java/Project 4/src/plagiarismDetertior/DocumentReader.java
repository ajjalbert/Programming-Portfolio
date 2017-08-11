package plagiarismDetertior;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;


/**
 * A class that reads a file set, and determines the number of collisions based on chunk size. Prints the number of collisions past a threshold
 * @author Andrew Jalbert, Jameson Dy, & Leonid Olekh
 */
public class DocumentReader
{
		
	/**
	 * Gets the hits/collisions between files and prints them in max to min order
	 * @param Hashtable t -  the table to be sorted and printed
	 */
	public static void sortValue(Hashtable<String, Integer> t){

	       //Transfer as List and sort it
	       ArrayList<Map.Entry<?, Integer>> l = new ArrayList(t.entrySet());
	       Collections.sort(l, new Comparator<Map.Entry<?, Integer>>(){

	         public int compare(Map.Entry<?, Integer> o1, Map.Entry<?, Integer> o2) {
	            return o2.getValue().compareTo(o1.getValue());
	        }});
	       
	       System.out.printf("%-25s %-25s %s\n", "file1", " file2", "Collisions");
	       System.out.printf("%-25s %-25s %s\n", "-----", " -----", "----------");
	       for (Entry<?, Integer> p : l){
	    	   	String[] words = p.getKey().toString().split(",");
	    	   	String word1 = words[0];
	    	   	String word2 = words[1];
	    	   	System.out.printf("%-25s %-25s %s\n", word1, word2, p.getValue());	       		
	       }
	}
	
	/**
	 * Gets the hits of the hit table and sorts them before printing
	 * Uses a helper function to sort and print
	 * @param hitTable - the table that will be sorted and printed out
	 */
	public void getHits(Hashtable<String,Integer> hitTable){
		
		sortValue(hitTable);		
		
	}
	/**
	 * Method for reading in a set of documents, parsing them into n-grams then comparing them
	 * @param directoryPath the path to the directory (i.e., small_set)
	 * @param n the size of each n-gram (number of words to consider as a bunch)
	 */
	public void readFiles(String directoryPath, int n, int threshold)
	{
		// create a hashtable for the requested directory
		Hashtable<String, Integer> hitTable = new Hashtable<String, Integer>();
		
		File dir = new File(directoryPath); //a file to represent the directory
		if(!dir.isDirectory())
			throw new IllegalArgumentException("Specify a directory, not a file!");

		String[] files = dir.list(); //get list of files in the directory
		
		
		for(int fi=0; fi<files.length; fi++) //go through the list
		{
			HashMap<Integer, Integer> hitMap = new HashMap<Integer, Integer>(); //create a a new map for the file to be used
			
			File f = new File(directoryPath+"/"+files[fi]);
			//make a file object representing that file in the directory
			for(int k = fi+1; k<files.length; k++){
				
				File fAfter = new File(directoryPath+"/"+files[k]);
				int hits = 0;
				
			try{
				//System.out.println("*** Processing "+f.getPath()+" ***");
				//System.out.println("*** Processing "+fAfter.getPath()+" ***");				
				//Scanner doesn't work with these text files, so need to use a lower-level class BufferedReader.
				//It works similarly to the Scanner though		
				
				// only create a new hashmap if one does not exist
				if(hitMap.size() <= 0){
					BufferedReader reader = new BufferedReader(new FileReader(f)); 
					String text = ""; //the whole text of the document
					String line = reader.readLine(); //the line we're currently reading
					while(line != null)
					{
						text += line.toLowerCase()+" "; //append the (lowercase) version of the line to the text
						line = reader.readLine();
							
					}	
				
					String[] words = text.split("\\W+"); //split into an array of words (breaking on any number of non-word letters)

					// loop to create the hashMap with the first file
					for(int i=0; i<words.length-(n-1); i++)
					{
											
						String[] ngram = Arrays.copyOfRange(words, i, i+n); //load a chunk of n words into a local array
						
						String ngramString = "";
						
						
						
						//read the chunk and save it as a string
						for(int j=0; j<ngram.length; j++){
							ngramString+=ngram[j];
							
						
							// add the string's hashcode into the HashMap
							hitMap.put(ngramString.hashCode(), 0);						
								
							}
				
					}
					
					// close the reader to end the stream
					reader.close();
				}
				
				
				BufferedReader readerAfter = new BufferedReader(new FileReader(fAfter)); 
				String textAfter = ""; //the whole text of the next document
				String lineAfter = readerAfter.readLine(); // the line of the second file being read
				
				// read the second file
				while(lineAfter!=null)
				{
						
					textAfter+= lineAfter.toLowerCase()+" ";
					lineAfter = readerAfter.readLine();
					
				}
				
				String[] wordsAfter = textAfter.split("\\W+"); // split in an array for the second file
				
				// loop to check the hashMap with the second file
				for(int i=0; i<wordsAfter.length-(n-1); i++)
				{
					
					String ngramAfterString = "";
				
					String[] ngramAfter = Arrays.copyOfRange(wordsAfter,i, i+n);
					
					// save the chunk as a string
					for(int j=0; j<ngramAfter.length; j++){
					ngramAfterString+= ngramAfter[j];										
					}
					
					// compare this read string's hashcode and see if it generates a hit
					// if that string's hashcode exists, then increase the hits counter
					if(hitMap.get(ngramAfterString.hashCode())!= null){
						hits+=1;
					}
					
			
				}
								
				readerAfter.close(); // close the reader to ensure stream ended properly
				
				
			}
			catch(IOException ioe) //in case of any problems
			{
				System.out.println("Error reading file: "+files[fi]);
			}
			// if the hit is greater than or equal to the threshold, add it to the container
			if(hits >= threshold){
				hitTable.put(f.getName() + ", " + fAfter.getName(), hits);
			}			
			}
		}
		System.out.println();
		
		getHits(hitTable); // at the end of the file reading, print the hitTable's collisions

	}

	//demo of the method
	public static void main(String[] args) 
	{
		DocumentReader detector = new DocumentReader();
		Scanner scanner = new Scanner(System.in);
		
		while (true){
		// let user input file directory, length of chunks, and the threshold
		System.out.print("Enter file directory, length of chunks, and match threshold: ");
		String userfile = scanner.next();		
		int userchunk = scanner.nextInt();		
		int userthreshold = scanner.nextInt();
		
		
		// timer
		long tStart = System.currentTimeMillis();
		
		// read the file
		detector.readFiles(userfile, userchunk, userthreshold);
		
		// end the timer
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		double elapsedSeconds = tDelta / 1000.0;
		
		// print out elapsed time in seconds
		System.out.println("\nTime elapsed: " + elapsedSeconds);
		System.out.println();
		}
	}
}
