import java.util.PriorityQueue;


/**
 * 
 * @author Andrew Jalbert
 * @description Constructs a binary tree using Huffman coding algorithm
 */
public class TreeMaker implements ITreeMaker {
	
	private TreeNode root; // node for the root of the tree
	private String preOrderBinaryString = ""; // string representation of a preOrder traversal
	
	
	/**
	 * default constructor for the tree
	 */
	public TreeMaker(){
		root = null;
	}

	/**
	 * helper function for the tree
	 * @param PriorityQueue<TreeNode>
	 */
	public void makeTree(PriorityQueue<TreeNode> nodeQueue){
		// make the tree
				while(nodeQueue.size() > 1){
					TreeNode node1 = nodeQueue.remove();
					TreeNode node2 = nodeQueue.remove();
					
					// assign a value of -1 so it does not map to an ASCII char
					TreeNode node3 = new TreeNode(-1, node1.myWeight + node2.myWeight, node1, node2);
					
					nodeQueue.add(node3);
				}
				
				// set the root to the last node in the queue
				setRoot(nodeQueue.remove());
				
	}
	
	/**
	 * Traverses the tree in a preOrder method
	 * Uses recursion
	 * @param TreeNode the root of the tree
	 */
	public void preOrderBinary(TreeNode root){
		
		String preOrder = "";
		
		if(root.myLeft == null && root.myRight == null){
			preOrderBinaryString += "1";
			preOrder += Integer.toBinaryString((root.myValue));
			
			preOrderBinaryString += preOrder;
			
		}
		
		
		else{
		
			preOrderBinaryString += "0";
			
	    preOrderBinary(root.myLeft);
	    preOrderBinary(root.myRight);

		}
		 
		
	}
	
	/**
	 * Sets the root of the tree to the TreeNode
	 * @param TreeNode
	 */
	public void setRoot(TreeNode node){
		root = node;
	}
	
	/**
	 * Returns the root of the tree
	 * @return TreeNode
	 */
	public TreeNode makeRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	/**
	 * Gets the preOrder representation of the tree
	 * @return String
	 */
	public String getPreOrderBinary() {
		return preOrderBinaryString;
	}

	/**
	 * Sets the preOrderBinary string to have a 2 at the end, which signals End of stream
	 */
	public void setPreOrderBinary() {
		this.preOrderBinaryString += "2";
	}

}
