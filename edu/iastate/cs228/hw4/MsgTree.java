package edu.iastate.cs228.hw4;

/**
 * 
 * @author Dillon Hacker
 * 
 * 
 * 
 * The contents of this class allow you to build a BST
 *
 */
public class MsgTree {

	//Variables when we need them
	public char payloadChar;
	public MsgTree left;
	public MsgTree right;

	
	
	/*
	 * Can use a static char index to the tree string for recursive solution, but it
	 * is not strictly necessary
	 */
	// Tracks the location within the tree string. Zero spot is root so start at 1. 
	private static int staticCharIdx = 1;
	//STATIC char that represents a branch node in our BST.
	private static char BRANCH = '^';

	// Constructor building the tree from a string
	public MsgTree(String encodingString) {
		/*
		 * We will recursivly add nodes to this root via the encoding string.
		 * "this" represents the node we are currently on (root).
		 */
		MsgTreeInsert(this, encodingString);

	}

	// Constructor for a single node with null children. When called, sets the payloadChar.
	public MsgTree(char payloadChar) {

		this.payloadChar = payloadChar;
		this.left = null;
		this.right = null;

	}

	// Method used to recursivly add nodes to our BST.
	private void MsgTreeInsert(MsgTree node, String encodingString) {

		//We want to iterate though the encoding string so we know that we have gotten each character.
		for (int i = 0; i < encodingString.length(); i++) {

			//Character we got from our string on this iteration.
			char stringChar = encodingString.charAt(staticCharIdx);
			//recursive function so we need to iterate through the string another way. 
			staticCharIdx++;

			//This is a temporary node with the current stringChar as its payloadChar.
			MsgTree temp = new MsgTree(stringChar);

			//If the node we are on is NOT a branch.
			if (stringChar != BRANCH) {
				
				//First check so see if the left node is open. If it's open, insert our node there.
				if (node.left == null) {
					node.left = temp;
				}
				
				/*
				 * The left node equals null, so we add this character to the right node.
				 * Since we are using the right node. Return to break the recursion.
				 */
				else {
					node.right = temp;
					return;
				}

			}
			//Else, the node we are on is a branch.
			else {

				/*
				 * First, check the left node. If this node is open, we insert our temporary node
				 * to the left. Since our temporary node is a branch, we know we can add another
				 * object here. We will recursivly call this method with our left node now.
				 * 
				 */
				if (node.left == null) {
					node.left = temp;
					MsgTreeInsert(node.left, encodingString);
				}
				/*
				 * Else, our left node has an object so we can't insert our temporary node here.
				 * We set the right node to our temporary node and since our payloadChar was a branch;
				 * we recursivly call this function with the right node now. Return to break the recursion.
				 */
				else {
					node.right = temp;
					MsgTreeInsert(node.right, encodingString);
					return;
				}

			}

		}

	}

	/*
	 *  Method to decode the message. It will print the decoded message to the console. This method will
	 *  take the created binary code given to us in our message and use binary to navigate the same tree
	 *  that was given to us in the message.
	 */
	public static void decode(MsgTree codes, String msg) {

		//Copy of our tree that will bring us back to the root.
		MsgTree temp = codes;

		//If the node we have is invalid. Break out of this method.
		if (codes == null) {
			return;
		}

		for (int i = 0; i < msg.length(); i++) {
			
			//Char used to make the code easier to read.
			char direction = msg.charAt(i);

			//If we are asked to go 0, navigate our "pliable" tree to the left.
			if (direction == '0') {
				temp = temp.left;
			}
			//Else, we are asked to go to the right.
			else {
				temp = temp.right;
			}

			/*
			 * If both of our child nodes are empty, we know this is a character node. Print this character
			 * to the screen and reset to our root node.
			 */
			if (temp.right == null && temp.left == null) {
				System.out.print(temp.payloadChar);
				temp = codes;
			}

		}

	}

	// Recursive method to print characters and their binary codes
	public static void printCodes(MsgTree root, String code) {

		//If the node we are on is not null we can use this method.
		if(root != null) {
			
			//If the nodes left child is null, we have a valid character to print.
			if(root.left == null) {
				
				/*
				 * If our payloadChar is a new line character. We need to print this character followed by 
				 * its binary code. We reset the code string for a new character and return to break out
				 * of the recursion.
				 */
				if(root.payloadChar == '\n') {
					System.out.println("\\n" + "\t\t" + code);
					code = "";
					return;
				}
				
				/*
				 * Else, we have a normal character. Print this character along with its binary code. Reset the 
				 * string and break recursion.
				 */
				else {
					System.out.println(root.payloadChar + "\t\t" + code);
					code = "";
					return;
				}
			}
			
			/*
			 * If we are here, recursivly search the left and right children. When we search to the left we add a zero to 
			 * our code string. When we search to the right add a 1.
			 */
			printCodes(root.left, code + "0");
			printCodes(root.right, code += "1");
				
		}
	}

}
