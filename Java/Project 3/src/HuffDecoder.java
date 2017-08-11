import java.io.IOException;

/**
* @author: Andrew Jalbert
* @description: Decodes a given input stream that was coded by Huffman coding
*/

public class HuffDecoder implements IHuffDecoder {

	private Boolean preExistingTree; // Boolean value to determine if there is an existing tree
	private TreeMaker decodeTree;
	
	/**
	 * Sets up the decoder to be ready to decode
	 * This creates a binary tree that represents the data read from the file
	 * @param TreeMaker
	 */
	@Override
	public void initialize(TreeMaker treeMaker) {
		decodeTree = treeMaker;
		// if the tree is null, then a new tree must be constructed
		if(treeMaker.makeRoot() == null){
			preExistingTree = false;
		}
		
		else{
			preExistingTree = true;
		}

	}

	/**
	 * WIP
	 * Helper function to build a binary tree that represents the given Huffing Coding from the file
	 * @param node - the node to be updated
	 * @param input - the bitinputstream to be read
	 * @param zeroCounter - counting the occurence of zeros
	 * @param goRight - boolean to determine if the node must go right
	 * @throws IOException
	 */
	private void buildTree(TreeNode node, BitInputStream input, int zeroCounter, Boolean goRight) throws IOException{
		int inbits = 0;
		
		String string = "";
		
	// read the stream
		while((inbits = input.read(1)) !=-1){
			// if there is a zero then make a new node and set it as a child
			if(inbits == 0){
				TreeNode newNode = new TreeNode(-1,0);
				node.myLeft = newNode;
				zeroCounter++;
				
				// call the function with the new node
				buildTree(newNode,input,zeroCounter, false);
				
			}
				
				
			// if read a 1 then get the 8 bit value for the node
			if(inbits ==1){
				zeroCounter = 0;
				string = "";
				for(int i = 0; i < 8; i++){
					inbits = input.read(1);
					string += inbits;
				
				
			
				}
				
				// crate a a new node, and convert the 8bit string into a number
				TreeNode newNode = new TreeNode(Integer.getInteger(string),-1);
				
				
				if(goRight){
					node.myRight = newNode;
					buildTree(node.myRight,input,zeroCounter, false);
				}
				
				else{
				node.myLeft = newNode;
				
				buildTree(node.myRight,input,zeroCounter, true);
				}
			}
		}
		
	}
	
	/**
	 * decodes the given input stream and generates a output stream
	 * throws an IOexception if stream not read or written properly
	 * @param BitInputStream, BitOutputStream
	 */
	@Override
	public void doDecode(BitInputStream input, BitOutputStream output) throws IOException {
				
		
		int inbits = 0;

		Boolean buildTree = false;
		// create a tempNode to walk the tree when decoding
		TreeNode tempNode;
		tempNode = decodeTree.makeRoot();
		
		
		while((inbits = input.read(1))!=-1){
			
				// create a tree if there is no tree
				if(!preExistingTree && !buildTree){
					
					
					buildTree(tempNode,input,0,false);
					
					decodeTree.setRoot(tempNode);
					
					
					// tree has been updated, set tree value to true
					buildTree = true;
					
				}
				
				
				// else use the tree to decode the message
				else{
				
					
						// use the code to traverse the tree and decode the message
						while((inbits=input.read(1))!=-1){
							
							// if reading a 2 then break
							if(inbits == 2){
								break;
							}
								
								// if leaf then add its value and output it
								if(tempNode.myLeft == null && tempNode.myRight==null){
									output.write(BITS_PER_WORD, tempNode.myValue);
									// reset to the root
									tempNode = decodeTree.makeRoot();
									
									
									
								}
								
								// if 0 then go right
								if(inbits==0){
									tempNode = tempNode.myLeft;
									
								}
								
								// if 1 then go left
								if(inbits==1){
									tempNode = tempNode.myRight;
								
								}
								
							
							
							}
											
												
						}
					}
						
		// close the streams
		input.close();
		output.close();
		
		

	}

}
