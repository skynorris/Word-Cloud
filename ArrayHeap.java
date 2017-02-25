///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// File:             ArrayHeap.java
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
import java.util.NoSuchElementException;

/**
 * This class creates a max priority queue that  is implemented via an Array Heap.  An expand method is the
 * only method not part of the PriorityQueueADT.  It doubles the size of the array.  
 *
 * @author Skyler Norris and Marius Faktor
 */

public class ArrayHeap<E extends Prioritizable> implements PriorityQueueADT<E> {

	// default number of items the heap can hold before expanding
	private static final int INIT_SIZE = 100;
	private E[] heap;

	/**
	 *Creates an array heap of size 100
	 */
	public ArrayHeap(){
		heap = (E[])(new Prioritizable[INIT_SIZE]);
	}

	/**
	 *Creates an array of the size passed in
	 *@param (int size) the size of the array heap when initialized
	 */
	public ArrayHeap(int size){
		heap = (E[])(new Prioritizable[size]);
	}
	

	/**
	 *@return true if heap is empty false otherwise
	 */
	public boolean isEmpty() {
		return (heap[1] == null);  
	}

	/**
	 * Inserts an item into Array Heap.  Must reheapify if applicable.  
	 * 
	 *@param(E item) the item to insert
	 */
	public void insert(E item) {

		//check if we need to expand array
		if(heap[heap.length-1] != null){
			expand();
		}

		//Insert item at end of heap
		int index = size()+1;
		heap[index] = item;

		//Keep swapping items until heap ordering constraint satisfied
		while(index != 1 && heap[index/2].getPriority() < heap[index].getPriority()){
			E temp = heap[index];
			heap[index] = heap[index/2];
			heap[index/2] = temp;
			index = index/2;
		}

	}

	/**
	 * Doubles the size of the array. Must make new array and fill with old items
	 */
	public void expand(){

		E[] heapNew = (E[])(new Prioritizable[heap.length *2]);
		for(int i = 0; i < heap.length - 1; i++){
			heapNew[i] = heap[i];
		}
		heap = heapNew;
	}

	/**
	 * Removes first item in array.  Must reheapify after removing item.  
	 * 
	 * @return the max priority item.  
	 */
	public E removeMax() {

		//data fields
		E temp = heap[1];
		int curr = 1;

		//move last element to front 
		heap[curr] = heap[size()];
		heap[size()] = null;

		//re-heapify if necessary
		while(heap[curr*2] != null && heap[(curr*2) +1] != null && 
				heap[curr].getPriority() < Math.max(heap[curr*2].getPriority(), heap[(curr*2) + 1].getPriority())){


			//determine which child is larger
			int maxChild = 0;
			if(heap[(curr*2)+1].getPriority() <= heap[curr*2].getPriority())
				maxChild= curr*2;
			else{
				maxChild = (curr*2) + 1;
			}

			//swap with parent
			E temp2 = heap[curr];
			heap[curr] = heap[maxChild];
			heap[maxChild] = temp2;
			curr = maxChild;

		}
		return temp;  
	}

	/**
	 * Gets max priority item but does not delete from heap.  
	 * 
	 * @return the max priority item.  
	 */
	public E getMax() {
		return heap[1];  // replace this stub with your code
	}

	/**
	 * @return the number (int) of items in the heap.    
	 */
	public int size() {
		int index = 1;
		while(heap[index] != null){
			index++;
		}
		return index-1;  // replace this stub with your code
	}
}
