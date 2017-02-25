///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            Program 3
// Files:           WordCloudGenerator.java, PriorityQueueADT.java, Prioritizable.java, KeyWord.java, DuplicateException.java, 
//					DictionaryADT.java, BSTnode.java, BStDIctionaryIterator.java, BSTDictionary.java, ArrayHeap.java
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
import java.io.*;

/**
 * This class creates a word cloud html file.  Given a text file and a list of words to ignore, 
 * it produces an html file of the most frequent words, with the more frequent words being larger than
 * the less frequently occuring words.
 * 
 * @author Skyler Norris and Marius Faktor.
 */

public class WordCloudGenerator {
	/**
	 * The main method generates a word cloud as described in the program 
	 * write-up.  You will need to add to the code given here.
	 * 
	 * @param args the command-line arguments that determine where input and 
	 * output is done:
	 * <ul>
	 *   <li>args[0] is the name of the input file</li>
	 *   <li>args[1] is the name of the output file</li>
	 *   <li>args[2] is the name of the file containing the words to ignore 
	 *       when generating the word cloud</li>
	 *   <li>args[3] is the maximum number of words to include in the word 
	 *       cloud</li> 
	 * </ul>
	 * @throws DuplicateException 
	 */
	public static void main(String[] args) throws DuplicateException {
		Scanner in = null;         // for input from text file
		PrintStream out = null;    // for output to html file
		Scanner inIgnore = null;   // for input from ignore file
		int maxWords = 0;

		
		// make sure four command line arguments were passed in
		if(args.length!=4 ){
			System.out.println("Four arguments required: inputFileName outputFileName ignoreFileName maxWords");
			System.exit(0);
		}

		//try to create an input file from the first command line argument of text file to parse
		File inFile = new File(args[0]);
		try {
			in = new Scanner(inFile);
		} catch (FileNotFoundException e2) {
			System.out.println("Error: cannot access file " + inFile);
			System.exit(0);
		}

		// try create an output file to save html word cloud to, make sure an arugment was passed in
		if(args[1] == null){
			System.out.println("Four arguments required: inputFileName outputFileName ignoreFileName maxWords");
			System.exit(0);
		}
		File outputFile = new File(args[1]);
		try {
			out = new PrintStream(outputFile);
		} catch (FileNotFoundException e1) {
			System.out.println("Error: Cannot acess input " + out);
			System.exit(0);
		}

		//try to create an input file from the third command line argument for words to ignore
		File ignoreFile = new File(args[2]);
		try {
			inIgnore = new Scanner(ignoreFile);
		} catch (FileNotFoundException e1) {
			System.out.println("Error: Cannot acess input " + out);
			System.exit(0);
		}

		//check if arg 4 is a number and is valid  
		if(args[3] == null){
			System.out.println("Four arguments required: inputFileName outputFileName ignoreFileName maxWords");
			System.exit(0);
		}
		//convert from string to int
		try{
			maxWords = Integer.parseInt(args[3]);
			if(maxWords <= 0 ){
				throw new NumberFormatException();
			}
		}
		catch(NumberFormatException e){
			System.out.println("Error: maxWords must be a positive integer");
			System.exit(0);
		}

	
		
		// Create the dictionary of words to ignore
		DictionaryADT<String> ignore = new BSTDictionary<String>();
		while (inIgnore.hasNext()) {
			try {
				ignore.insert(inIgnore.next().toLowerCase());
			} catch (DuplicateException e) {
				// if there is a duplicate, we'll just ignore it
			}
		}

		// Process the input file line by line
		//dictionary of words to create word cloud from 
		DictionaryADT<KeyWord> addedWords = new BSTDictionary<KeyWord>();
		KeyWord temp ;

		while (in.hasNext()) {
			String line = in.nextLine();
			List<String> words = parseLine(line);
			
			//go through each word in line
			for (String word : words){
				word = word.toLowerCase();
				if(ignore.lookup(word) == null){
					temp = new KeyWord(word);

					//insert new key word into the dictionary, if the keyword already exists, increment the key word
					try{
						addedWords.insert(temp);
					}catch(DuplicateException e){
						Iterator<KeyWord> itr1 = addedWords.iterator();
						KeyWord temp1 = null;
						while(itr1.hasNext()){
							temp1 = itr1.next();
							if(temp1.equals(temp))
								temp1.increment();	
						}
					}
				}

			}
		} 

	
		
		System.out.println("# keys: " + addedWords.size());
		System.out.println("avg path length: " + (addedWords.totalPathLength()) / (addedWords.size()) ) ;
		System.out.println("linear avg path : " + ( 1 + addedWords.size()) /2 ) ;

		//add all of our keywords into a priority queue
		ArrayHeap<KeyWord> priQ = new ArrayHeap<KeyWord>();
		Iterator<KeyWord> itr2 = addedWords.iterator();
		while(itr2.hasNext()){
			priQ.insert(itr2.next());
		}

		//add keywords from priority queue to new dictionary so the dictionary is filled from max to min priority 
		DictionaryADT<KeyWord> finalWords = new BSTDictionary<KeyWord>();

		//if max number of words is less than priority queue empty all of priority queue into dictionary
		if(priQ.size() < maxWords){
			while(!priQ.isEmpty()){
				finalWords.insert(priQ.removeMax());
			}

			//if number of max words is less than size of priority queue, only fill dictionary with max number of keywords
		}else{
			for(int i = 1; i <= maxWords; i++){
				finalWords.insert(priQ.removeMax());
			}
		}

		//make word cloud
		generateHtml(finalWords, out);

		// Close everything
		if (in != null) 
			in.close();
		if (inIgnore != null) 
			inIgnore.close();
		if (out != null) 
			out.close();
	}

	/**
	 * Parses the given line into an array of words.
	 * 
	 * @param line a line of input to parse
	 * @return a list of words extracted from the line of input in the order
	 *         they appear in the line
	 *         
	 * DO NOT CHANGE THIS METHOD.
	 */
	private static List<String> parseLine(String line) {
		String[] tokens = line.split("[ ]+");
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i < tokens.length; i++) {  // for each word

			// find index of first digit/letter
			boolean done = false; 
			int first = 0;
			String word = tokens[i];
			while (first < word.length() && !done) {
				if (Character.isDigit(word.charAt(first)) ||
						Character.isLetter(word.charAt(first)))
					done = true;
				else first++;
			}

			// find index of last digit/letter
			int last = word.length()-1;
			done = false;
			while (last > first && !done) {
				if (Character.isDigit(word.charAt(last)) ||
						Character.isLetter(word.charAt(last)))
					done = true;
				else last--;
			}

			// trim from beginning and end of string so that is starts and
			// ends with a letter or digit
			word = word.substring(first, last+1);

			// make sure there is at least one letter in the word
			done = false;
			first = 0;
			while (first < word.length() && !done)
				if (Character.isLetter(word.charAt(first)))
					done = true;
				else first++;           
			if (done)
				words.add(word);
		}

		return words;
	}

	/**
	 * Generates the html file using the given list of words.  The html file
	 * is printed to the provided PrintStream.
	 * 
	 * @param words a list of KeyWords
	 * @param out the PrintStream to print the html file to
	 * 
	 * DO NOT CHANGE THIS METHOD
	 */
	private static void generateHtml(DictionaryADT<KeyWord> words, 
			PrintStream out) {
		String[] colors = { 
				"6F", "6A", "65", "60",
				"5F", "5A", "55", "50",
				"4F", "4A", "45", "40",
				"3F", "3A", "35", "30",
				"2F", "2A", "25", "20",
				"1F", "1A", "15", "10",        
				"0F", "0A", "05", "00" 
		};
		int initFontSize = 80;

		// Print the header information including the styles
		out.println("<head>\n<title>Word Cloud</title>");
		out.println("<style type=\"text/css\">");
		out.println("body { font-family: Arial }");

		// Each style is of the form:
		// .styleN {
		//      font-size: X%;
		//      color: #YYAA;
		// }
		// where N and X are integers and Y is two hexadecimal digits
		for (int i = 0; i < colors.length; i++)
			out.println(".style" + i + 
					" {\n    font-size: " + (initFontSize + i*20)
					+ "%;\n    color: #" + colors[i] + colors[i]+ "AA;\n}");

		out.println("</style>\n</head>\n<body><p>");        

		// Find the minimum and maximum values in the collection of words
		int min = Integer.MAX_VALUE, max = 0;
		for (KeyWord word : words) {
			int occur = word.getOccurrences();
			if (occur > max)
				max = occur;
			else if (occur < min)
				min = occur;
		}

		double slope = (colors.length - 1.0)/(max - min);

		for (KeyWord word : words) {
			out.print("<span class=\"style");

			// Determine the appropriate style for this value using
			// linear interpolation
			// y = slope *(x - min) (rounded to nearest integer)
			// where y = the style number
			// and x = number of occurrences
			int index = (int)Math.round(slope*(word.getOccurrences() - min));

			out.println(index + "\">" + word.getWord() + "</span>&nbsp;");
		}

		// Print the closing tags
		out.println("</p></body>\n</html>");
	}
}
