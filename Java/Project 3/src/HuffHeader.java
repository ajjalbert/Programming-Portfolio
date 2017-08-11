import java.io.IOException;
import java.io.InputStream;

/**
* @author: Andrew Jalbert
* @description: Used to write and read a magic number to a file so that the GUI will know if it can uncompress a file 
*/

public class HuffHeader implements IHuffHeader, IHuffConstants {

	private int size = 0; // the size of the header
	
	private TreeMaker tree = new TreeMaker(); // tree associated with preorder binary tree message
		
	/**
	 * Returns the size of the header
	 * @return int size of the header
	 */
	@Override
	public int headerSize() {
			
		
		return size;
	}

	/**
	 * Writes the header to the output file
	 * @param BitOutputStream the stream to write to
	 */
	@Override
	public void writeHeader(BitOutputStream out) {
		
		
		// write out the magic number
		out.write(BITS_PER_INT, MAGIC_NUMBER);	
		// update the header size to the total bytes of the header
		size = 1;		

	}

	/**
	 * Reads the header of the file to be uncompressed, will return an error if the magic number does not match
	 * @param BitInputStream the stream to be read
	 * @param HuffViewer the viewer to update if there is an error
	 * @return TreeMaker the tree made after reading the file
	 */
	@Override
	public ITreeMaker readHeader(BitInputStream in, HuffViewer view) throws IOException {
		
		int magic = in.read(BITS_PER_INT);
		// check to see if the coded magic number matches
		if(magic != MAGIC_NUMBER){
			
			// show an error if the magic number does not match
			view.showError("magic number is not right");
			
			// throw an exception to stop the uncompression
			throw new IOException("magic number is not right");
			
			
		}
				
		return tree;
	}

}
