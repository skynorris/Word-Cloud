///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  WordCloudGenerator.java
// File:             KeyWord.java
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

/**
 * This class constructs a KeyWord object.  A KeyWord is an object that combines
 * a string and the amount of times that String comes up in a file, paper, etc.  
 * We repersent the amount of times the String comes up as an int called occurence.
 *
 * @author Skyler Norris and Marius Faktor.
 */
public class KeyWord implements java.lang.Comparable<KeyWord>, Prioritizable {

	//data fields
	//string associated with the key word
	private String word;
	private int occurence;  //amount of times the KeyWord shows up

	/**
	 * The constructor.  Starts each word with one occurence, not 0.  
	 */
	public KeyWord(String word){
		if(word == null || word.length() ==0){
			throw new IllegalArgumentException();
		}

		this.word = word; 
		occurence = 1;
	}

	/**
	 *Compares two KeyWord objects by their String associated with it.  Uses the built in
	 *String Compare to method to compare the Strings. 
	 *
	 * @param (KeyWord other) the other KeyWord you are comparing this to.  
	 * @return  0 is returned if they are the same 
	 *string, -1 is returned if this comes earlier in the alphabet than other and 1 is returned if
	 *other comes earlier in the alphabet than other.
	 */
	@Override
	public int compareTo(KeyWord other) {
		
		if(this.word.compareTo(other.getWord()) < 0){
			return -1;
		}
		else if(this.word.compareTo(other.getWord()) > 0) {
			return 1;
		}

		else{
			return 0;
		}
	}
	
	/**
	 *Compares two KeyWord objects by their String associated with it.  Uses the built in
	 *String Equals method to compare the Strings. 
	 *
	 * @param (KeyWord other) the other KeyWord you are comparing this to.  
	 * @return True if the strings are the same, false otherwise.
	 */
	@Override
	public boolean equals(Object o ){
		if(o == null || this == null){
			return false;
		}
		
		String wordTemp = this.word.toLowerCase();
		String otherTemp =((KeyWord)o).getWord().toLowerCase();
		
		
		if(wordTemp.equals(otherTemp)){
			return true;
		}
		else{
			return false;
		}
	}


	/**  
	 * @return the amount of occurences associated with this KeyWord.
	 */
	public int getOccurrences(){
		return occurence;
	}


	@Override
	/**  
	 * @return the priority associated with this KeyWord, which is just the int number of
	 * occurences.
	 */
	public int getPriority() {
		// TODO Auto-generated method stub
		return occurence;
	}

	/**  
	 * @return the String associated with this KeyWord.
	 */
	public String getWord(){
		return word;
	}

	/**  
	 * This method increases the number of occurences for this KeyWord by 1.  
	 * Thus this also increases the priority of the KeyWord.
	 */
	public void increment(){
		occurence++;
	}

}
