///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// File:             BSTDictionary.java
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
import java.util.Iterator;

/**
 * This class constructs a BSTDictionary object.  Our BSTDictionary is implemented via a binary search tree that 
 * implements the dictionary ADT which is a way to compare objects.    
 *
 * @author Skyler Norris and Marius Faktor.
 */
public class BSTDictionary<K extends Comparable<K>> implements DictionaryADT<K> {

	//data fields
	private BSTnode<K> root;  // the root node
	private int numItems;     // the number of items in the dictionary

	/**
	 *No arg constructor, makes the root a null BSTnode.  
	 */
	public BSTDictionary(){
		root = new BSTnode<K>(null);
		numItems = 0;

	}


	/**
	 *Inserts the KeyWord being passed into the BSTDictionary, such that is greater than
	 *everything to left of it, and less than everything to the right of it.  Inserted by Key.
	 *
	 * @param (K key) the key of the KeyWord being inserted  
	 */
	public void insert(K key) throws DuplicateException {

		//if tree is empty
		if(numItems ==0){
			root.setKey(key);
		}
		//recursively insert
		else{
			insert(root, key);
		}

		numItems++;
	}

	/**
	 *The helper method of the insert Method.  Recursively inserts node.  If Node already exists throws
	 *DuplicateException.
	 *
	 * @param (BSTnode<K> n) The node that is being recursively looked at.  
	 * @param (K key) the key of the KeyWord being inserted 
	 * 
	 * @return The BSTnode that was inserted.
	 */
	private BSTnode<K> insert(BSTnode<K> n, K key) throws DuplicateException {

		//base case
		if (n == null || n.getKey() ==null) {
			return new BSTnode<K>(key, null, null);
		}

		// make sure no duplicates
		if (n.getKey().equals(key)) {
			throw new DuplicateException();
		}

		//go left
		if (key.compareTo(n.getKey()) < 0) {
			// add key to the left subtree..throw it in there and it will set tree
			n.setLeft( insert(n.getLeft(), key) );
			return n;
		}

		//go right
		else {
			// add key to the right subtree
			n.setRight( insert(n.getRight(), key) );
			return n;
		}
	}
	
	
	/**
	 *Deletes a Node from the BSTDictionary.  Also adjusts nodes positions to keep it a BST.  
	 *
	 * @param (K key) the key of the KeyWord to be deleted.    
	 * @return true if a node was deleted, false otherwise.  
	 */
	public boolean delete(K key) {

		BSTnode<K> temp  = delete(root, key);

		//if what was returned from delete was null
		if(temp == null){
			return false;
		}else{
			numItems--;
			return true;
		}

	}

	/**
	 * Helper method that recursively deletes a node from the tree.  
	 *
	 * @param (BSTnode<K> n) The node that is being recursively looked at.  
	 * @param (K key) the key of the KeyWord to be deleted 
	 * 
	 * @return The BSTnode that was deleted, null if nithing was deleted.
	 */
	private BSTnode<K> delete(BSTnode<K> n, K key) {
		if (n == null) {
			return null;
		}

		//base case
		if (key.equals(n.getKey())) {
			// n is the node to be removed
			if (n.getLeft() == null && n.getRight() == null) {
				return null;
			}
			if (n.getLeft() == null) {
				return n.getRight();
			}
			if (n.getRight() == null) {
				return n.getLeft();
			}

			// if we get here, then n has 2 children
			//smallest val finds smallest value in right subtree, to replace node that is deleted
			K smallVal = smallest(n.getRight());
			n.setKey(smallVal);
			n.setRight( delete(n.getRight(), smallVal) );
			return n; 
		}

		//go left
		else if (key.compareTo(n.getKey()) < 0) {
			n.setLeft( delete(n.getLeft(), key) );
			return n;
		}

		//go right
		else {
			n.setRight( delete(n.getRight(), key) );
			return n;
		}
	}
	/**
	 * Method that finds the smallest value in a subtree.
	 *
	 * @param (BSTnode<K> n) root of tree or subtree 
	 * @return the smallest key in the subtree rooted at n
	 */
	private K smallest(BSTnode<K> n)

	//keep going left till you cant
	{
		if (n.getLeft() == null) {
			return n.getKey();
		} else {
			return smallest(n.getLeft());
		}
	}

	/**
	 * Method that looks up a node in this tree by the key passed in.
	 *
	 * @param (K key) the key of the node to be looked up
	 * @return The key that was looked up, null  if it wasnt found
	 */
	public K lookup(K key) {

		if(key == null){
			return null;
		}

		if(this.size() == 0){
			return null;
		}

		return lookup(root, key);
	}

	/**
	 * Helper Method that looks up a node in this tree by the key passed in, does so recursively.
	 *
	 * @param (K key) the key of the node to be looked up
	 * @param (BSTnode<K> n) The node that is being recursively looked at.
	 * 
	 * @return The key that was looked up, null  if it wasnt found
	 */
	private K lookup(BSTnode<K> n, K key) {

		//base case
		if (n == null || n.getKey()==null) {
			return null;
		}

		//base case
		if (n.getKey().equals(key)) {
			return key;
		}

		if (key.compareTo(n.getKey()) < 0) {
			// key < this node's key; look in left subtree
			return lookup(n.getLeft(), key);
		}

		else {
			// key > this node's key; look in right subtree
			return lookup(n.getRight(), key);
		}
	}

	/**
	 * @return true if tree is empty, false otherwise
	 */
	public boolean isEmpty() {
		return numItems<1;  // replace this stub with your code
	}

	/**
	 * @return number (int) of nodes in the tree
	 */
	public int size() {
		return numItems;  // replace this stub with your code
	}

	/**
	 * Finds the total path length of the tree.  The total path length is sum of all the paths of every node
	 * from the root to that node.
	 * 
	 * @return number (int) total pathlength 
	 */
	public int totalPathLength() {
		int depth = 0;
		return totalPathLength(depth, root);
	}

	/**
	 * Heler method that recursively finds the depth of each node 
	 * 
	 * 
	 * @param (int depth) the depth of the node being reursively looked at
	 * @param (BSTnode<K> n) The node that is being recursively looked at.
	 * @return number (int) total pathlength 
	 */
	private int totalPathLength(int depth, BSTnode<K> n) {
		
		//base case
		if(n == null){
			return 0;
		}
		
		depth++;
		
		//depth of each node in left and right sub tree
		return depth + totalPathLength(depth, n.getLeft()) + totalPathLength(depth, n.getRight());

	}

	/**
	 * @return an iterator to iterate through this BSTDictionary.  Must pass in root to Iterator 
	 * constructor. 
	 */
	public Iterator<K> iterator() {
		return new BSTDictionaryIterator<K>(this.root);
	}
}
