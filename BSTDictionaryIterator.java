///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// File:             BSTDictionaryIterator.java
// Semester:         Summer 2016
//
// Author:           Skyler Norris
// Email:            sgnorris@wisc.edu
// CS Login:         skyler
// Lecturer's Name:  Strominger
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Marius Facktor
// Email:            marius.facktor@doit.wisc.edu
// CS Login:         marius
import java.util.*;

/**
 * BSTDictionaryIterator implements an iterator for a binary search tree (BST)
 * implementation of a Dictionary.  The iterator iterates over the tree in 
 * order of the key values (from smallest to largest).
 */
public class BSTDictionaryIterator<K> implements Iterator<K> {

	//the Stack we will use to store the BSTnodes in from the tree
	private Stack<BSTnode<K>> tree;

	/**
	 * The Iterator is constructed by pushing every Left node from the root.  Thus when 
	 * the Object is constructed, the leftmost (smallest value) node will be at the top of the 
	 * Stack.  
	 * 
	 */
	public BSTDictionaryIterator(BSTnode<K> root){
		
		tree = new Stack<BSTnode<K>>();
		tree.push(root);
		
		//keep pushing left node untill you hit leaf
		while(tree.peek().getLeft() != null){
			tree.push(tree.peek().getLeft());	

		}
	}

	/**
	 * @return true if the Stack is not empty, false if it is.  
	 */
	public boolean hasNext() {
		return !tree.isEmpty();  // replace this stub with your code
	}


	/**
	 * The method returns the next lowest priortiy Node.  In addition, it also push the next lowest 
	 * Nodes into the Stack.  
	 * 
	 * @return the next lowest priortiy Node's Key.  
	 */
	public K next() {

		K temp = tree.peek().getKey();
		
		//Fill Stack with right sub tree of Node you are returning 
		if(tree.peek().getRight() != null){
			tree.push(tree.pop().getRight());
			while(tree.peek().getLeft() != null){
				tree.push(tree.peek().getLeft());
			}
		}else{
			tree.pop();
		}
		return temp;  // replace this stub with your code
	}

	public void remove() {
		// DO NOT CHANGE: you do not need to implement this method
		throw new UnsupportedOperationException();
	}    
}
