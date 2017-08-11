
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

/**
* @author: Andrew Jalbert
* @description: Creates a model for a implemented GUI that implements options for the GUI
*/

public class HuffModel implements IHuffModel{
	
	private CharCounter countercc = new CharCounter();
	private ArrayList<String> chunkStatement = new ArrayList();
	private HuffViewer view;
	private PriorityQueue<TreeNode> nodeQueue = new PriorityQueue();
	private TreeMaker tree = new TreeMaker();
	private HuffEncoder code = new HuffEncoder();
	private HuffHeader header = new HuffHeader();
	
	/**
	 * Shows the code for the character
	 */
	public void showCodings() {
		
		// if the chunkStatement is not empty, clear it
		if(!chunkStatement.isEmpty())
			chunkStatement.clear();
		
		// populate the chunkStatement with the proper data
		for(int i = 0; i < 257; i++){
		
			// for indices with no value, do not add them to the statement
			if(code.getCode(i) != null)
		chunkStatement.add(Integer.toString(i) + " " + code.getCode(i));
		
		}
		
		// set the viewer
		setViewer(view);
		
		// update the GUI with the new output
		view.update(chunkStatement);
	}

	/**
	 * Shows the number of counts of each character
	 */
	public void showCounts() {
		
		// if the chunkStaement if not empty, clear it
		if(!chunkStatement.isEmpty())
			chunkStatement.clear();
		
		// populate the collection with the proper data
		for(int i = 0; i < 257; i++){
			// only add not empty values
			if(countercc.getCount(i) != 0){
			chunkStatement.add(Integer.toString(i) + " " +Integer.toString(countercc.getCount(i)));
			
			}
			
		}
				
		// set the viewer
		setViewer(view);
		
		// update the viewer to display the new collection
		view.update(chunkStatement);
		
	}

	/**
	 * Creates the GUI output section
	 * Updates the count of the chars
	 * Sets the tree for the chars
	 */
	public void initialize(InputStream stream) {
		
		// count all the chars in the stream
		try {
			countercc.clear();
			countercc.countAll(stream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// if the queue is not empty then clear it
		if(!nodeQueue.isEmpty())
			nodeQueue.clear();
		
		// create a node for each char
		for(int i = 0; i < 256; i++){
			// make sure there is a value for that char
			if(countercc.getCount(i) != 0){
			TreeNode newNode = new TreeNode(i,countercc.getCount(i));
			nodeQueue.add(newNode);
			}
		}
		
		// make the tree
		tree.makeTree(nodeQueue);
		
		// make the code table
		code.makeTable(tree);
		
		// create the preOrder binary representation of the tree
		tree.preOrderBinary(tree.makeRoot());
		
		// add an EoF value
		tree.setPreOrderBinary();		
		
	}

	/**
	 * writes the compressed file
	 */
	public void write(InputStream stream, File file, boolean force)
	{
		

		// create a value to store the bit count
		int newBits = 0;
		
		// count the number of bits for the new file
		for(int i = 0; i < 257; i++){
			// only get values for non zero/non null values
			if(countercc.getCount(i) > 0 && code.getCode(i) != null)
			newBits += (countercc.getCount(i) * code.getCode(i).length());
			
		}	
		
		// add the length of the binary tree and its EoF
		newBits+= tree.getPreOrderBinary().length();
		
		// force is false
		if(force == false){
			// check to see if the new number of bytes is less than the char count, divide bits by 8 for bytes
			if(countercc.getCounter() < newBits/8){
				// new file is larger, do not compress
				view.showError("Compressed file is larger. Use force compression to continue.");
				
			}
			
			// new file is smaller, compress
			else{
				
				// code the text
				code.codeString(stream);
				code.encodeStringToBits();
				
				// write the magic number to the file
				BitOutputStream bit = new BitOutputStream(file.getAbsolutePath());
				header.writeHeader(bit);
				
				
				
				// the tree could not be properly used, but this is how it was stored
				/**
				// write the preOrder of the tree in bytes to the file
				for(int i = 0; i < tree.getPreOrderBinary().length()-1;i++){
					if(tree.getPreOrderBinary().charAt(i) == '0')
						bit.write(1,0);
					if(tree.getPreOrderBinary().charAt(i)=='1')
						bit.write(1, 1);
					
					
				}
				
				
				TO DO: Write the EoF value.
				
				*/
				
				// write the new file in the coded message
						int j = 0;
						while(j<code.getEncodedMessage().length()-1){
							if(code.getEncodedMessage().charAt(j) == '0')
								bit.write(1,0);
							if(code.getEncodedMessage().charAt(j)=='1')
								bit.write(1, 1);
							
							j++;
						
							}
						
						bit.write(1, 2);
					
				// close the stream
				bit.close();
								
				}
						
			
		}
					
			
		// if force is true, always compress
		else{
			
			// code the text
			code.codeString(stream);
			code.encodeStringToBits();
			
			// write the magic number to the file
			BitOutputStream bit = new BitOutputStream(file.getAbsolutePath());
			header.writeHeader(bit);
			
			
			// the tree could not be properly used, but this is how it was stored
			/**
			// write the preOrder of the tree in bytes to the file
			for(int i = 0; i < tree.getPreOrderBinary().length()-1;i++){
				if(tree.getPreOrderBinary().charAt(i) == '0')
					bit.write(1,0);
				if(tree.getPreOrderBinary().charAt(i)=='1')
					bit.write(1, 1);
				
				
			}
			
			TO DO: Write the EoF value.
			
			*/
			
			// write the new file in the coded message
					int j = 0;
					while(j<code.getEncodedMessage().length()-1){
						if(code.getEncodedMessage().charAt(j) == '0')
							bit.write(1,0);
						if(code.getEncodedMessage().charAt(j)=='1')
							bit.write(1, 1);
						
						j++;
					
						}
					
					bit.write(1, 2);
					
					
			// close the stream
			bit.close();
							
			}
				
	}

	/**
	 * Updates the given viewer to the current viewer
	 */
	public void setViewer(HuffViewer viewer) {
		
		// set the view to the current viewer
		view = viewer;
		
	}

	/**
	 * Attempts to uncompress the file stream
	 */
	@Override
	public void uncompress(InputStream in, OutputStream out) {
		
		

		
		HuffDecoder decoder = new HuffDecoder(); // create a decoder
		
		// create bit stream to read and write the file and new file
		BitInputStream input = new BitInputStream(in);
		BitOutputStream output = new BitOutputStream(out);
		
		try {
				
			// read the header to check for magic number
				header.readHeader(input, view);
			} catch (IOException e) {
		}
		
		// start the decoder by generating a tree if needed
		decoder.initialize(tree);
		
		try {
			// decode the message
			decoder.doDecode(input, output);
		} catch (IOException e1) {

			e1.printStackTrace();
		}

			
			

		
			
	}


}
