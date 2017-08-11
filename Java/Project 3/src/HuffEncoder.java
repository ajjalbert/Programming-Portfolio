import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

public class HuffEncoder implements IHuffEncoder, IHuffConstants {

	private Hashtable<Integer, String> hashTable = new Hashtable<>(257); // hastable that stores the code of each character as a string
	private String encodedMessage = ""; // the encoded message of the given file
	private Hashtable<Integer, byte[]> binaryEncodedData = new Hashtable<>(257); // hashtable of the byte data for each character
	
	/**
	 * Recursive function that walks the tree to create the code
	 * @param node
	 */
	private void walkTree(TreeNode node, String newCode){
		
		// -1 value means a leaf node
		if(node.myValue !=-1){
			// store the data in to the hashTable for codes
			hashTable.put(node.myValue, newCode);
		}
		// base case, the node is a leaf
		if(node.myRight == null && node.myLeft == null){
			// do nothing
		}
			
	
		else{			
			// if walking left then append a 0
			if(node.myLeft != null)
			walkTree(node.myLeft, (newCode+"0"));
			
			// if walking right then append a 1
			if(node.myRight != null)
			walkTree(node.myRight, (newCode+"1"));
		}
		
		
	}
	
	/**
	 * 
	 * (non-Javadoc)
	 * @see IHuffEncoder#makeTable(TreeMaker)
	 * @param TreeMaker
	 */
	@Override
	public void makeTable(TreeMaker treeMaker) {
		
		
		TreeNode walk = treeMaker.makeRoot();
		String walkString = "";
		
		// if there is only one letter in the entire file, set its code to zero
		if(walk.myLeft == null && walk.myRight == null){
			hashTable.put(walk.myValue, "0");
			
			
		}
		
		// else perform assignment based off given algorithm
		else{
		walkTree(walk, walkString);
		}
		
	}
	
	/**
	 * returns the value at the requested index
	 * @param int
	 * @return int at index i
	 */
	@Override
	public String getCode(int i) {
		
		if(i > -1 && i < 257)
			return hashTable.get(i);
		
		// return nothing if OoB
		else
			return "";
	}
	
	public Hashtable<Integer, String> getHashtable(){
		
		return hashTable;
	}
	
	
	/**
	 * Codes the given input stream to the generated tree
	 * @param InputStream
	 */
	public void codeString(InputStream stream){
		encodedMessage = "";
		
		
		int inbits;
		BitInputStream bit = new BitInputStream(stream);
		try {
			while((inbits = bit.read(BITS_PER_WORD))!=-1){
				if(hashTable.get(inbits)!=null)
				encodedMessage += hashTable.get(inbits);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}
	
	
	/**
	 * takes the encoded message and converts it to a bit representation
	 */
	public void encodeStringToBits(){
		    //Extend the length of the stringEncodedData to cause
		    // it to be a multiple of 8.
		    int remainder = encodedMessage.length()%8;
		    for(int cnt = 0;cnt < (8 - remainder);cnt++){
		      encodedMessage += "0";
		    }
		
		    //Extract the String representations of the required
		    // eight bits.  Generate eight actual matching bits by
		    // looking the bit combination up in a table.
		    for(int cnt = 0;cnt < encodedMessage.length();
		                                                 cnt += 8){
		      String strBits  = encodedMessage.substring(
		                                                cnt,cnt+8);
		      if(hashTable.get(strBits)!=null){
		    	  byte[] realBits = hashTable.get(strBits).getBytes();
		      
		     
		    	  binaryEncodedData.put(cnt,realBits);
		      }
		    }
	}

	/**
	 * Gets the encoded text
	 * @return String representation of the encoded message
	 */
	public String getEncodedMessage() {
		return encodedMessage;
	}

	/**
	 * Sets the encoded message
	 * @param encodedMessage the new message
	 */
	public void setEncodedMessage(String encodedMessage) {
		this.encodedMessage = encodedMessage;
	}
	
	/**
	 * 
	 * @param int index to be retrieved
	 * @return byte[] of the encoded data at selected index
	 */
	public byte[] getBinaryData(int i){
		return binaryEncodedData.get(i);
	}
}
