package edu.iastate.cs228.hw4;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 * @author Dillon Hacker
 *
 */
public class BinarySearchMain {

	/**
	 * 
	 * This is the main method that I will use to call my Binary Search Tree
	 * algorithm (bst) to accomplish the goal of decoding text as well as printing
	 * the characters combined with their "code"
	 * 
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// Variables when we need them
		Scanner scan = new Scanner(System.in);
		String fileName = "";
		String encodingString = "";
		String decodeString = "";
		String codeString = "";

		System.out.println("Please enter filename to decode: ");

		// Gathering the name of the file; parsed from user.
		fileName = scan.next();
		scan.close();

		// Try to create a new FileInputStream or throw an Exception.
		try {
			FileInputStream file = new FileInputStream(fileName);
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}

		// The first line in our file will be used in the MsgTree constructor.
		encodingString = scan.nextLine();

		// The second line in our file will be used to decode the message later.
		decodeString = scan.nextLine();

		/*
		 * The second string of our file is not the binary code as expected. Concatenate
		 * the two strings, followed by a scan of the next line for our binary decode
		 * string. Concatenate the '\n' char between the first and second string.
		 */
		if (scan.hasNextLine()) {

			encodingString = encodingString + '\n' + decodeString;
			decodeString = scan.nextLine();

		}

		// Now we will create our main tree.
		MsgTree tree = new MsgTree(encodingString);

		/*
		 *  Now, we will print each character from the tree. We want to print the codes that
		 *  go along with these characters also, so we are including an empty string for it's
		 *  function.
		 */
		System.out.println("Character" + "\t" + "Code");
		System.out.println("~~~~~~~~~~~~~~~~~~~");
		MsgTree.printCodes(tree, codeString);

		// Making some space between the data.
		System.out.println("\n" + "\n" + "\n");

		/*
		 *  Now, decode our file. To do this we pass in the code that was given to us from 
		 *  the file above. This code contains binary instructions that solve that specific
		 *  tree. We also use our BST from earlier. This method will print the given "encrypted"
		 *  contents onto the screen.
		 */
		MsgTree.decode(tree, decodeString);

	}

}
