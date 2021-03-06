/*
 * authors: Anthony Spatafora, Robert Faigao
 * Spring 2013
 * CS421 Natural Language Processing
 * Project, Part 2
 * 
 * The goal of this project is to take a known POS Tagger
 *  and use it to make our own program. This program will
 *  be able to take in essays (input files), tag them,
 *  and grade the essays based on the tags that were made.
 *  
 *  **NOTE**
 *  We weren't able to complete the grading but this is along
 *  the lines were were thinking of doing.
 * 
 */

import java.io.*;
import java.util.*;

import opennlp.tools.postag.*;

public class project {
	static int scores[][];	//A double array holding the scores for each file
	static String finalGrade[][];	//A double array holding the final grade for each file
	private static File[] files;	//The list of files used only to count how many files there are
	private static int totalFiles;	//The total amount of files in the corpus
	private static int currentFile = 0;	//The current file we are working on

	private static void grader(File output) throws FileNotFoundException
	{
		// scans file and sets each tag to a spot in the String array
		Scanner scanner = new Scanner(output);
		String[] arr = null;
		int arrSize = 0;
		
		while ( scanner.hasNext() ) {
			arr[arrSize] = scanner.next();
			arrSize++;
		}
		scanner.close();
		
		// calls grading and looks at each individual tag in the array
		int temp = 0;
		char tense = ' ';
		char retTense = ' ';
		// start off with a perfect score
		int score = 5;
		
		while (temp != arrSize) {
			
			retTense = grading(arr[temp]);
			
			if ( (tense != ' ') && (tense != retTense) )
				System.out.println("Tense error: " + retTense + "!");
				// every time the tenses don't match, deduct points on the score
				score -= .5;
			// increment along the array
			temp++;
		}
		System.out.println("Tense Score: " + score);
	}
	
	/* This method uses a switch case statement to determine the tense of
	 * tag that was sent to it. Here is a quick key to show what the switch-
	 * case statement is doing.
	 * 
	 * Key:
	 * Singular Noun = n
	 * Plural Noun = N
	 * Singular Proper Noun = p
	 * Plural Proper Noun = P
	 * Verb Past Tese | Verb Past Participle = v
	 * Verb Gerund | Present Participle | Non-3rd Person Singular Present | Non-3rd Person Singular Present
	 * 	= V
	 * 
	 */
	private static char grading(String x)
	{
		char currTense = ' ';
		
		switch (x) {
		case "CC": // Coordinating conjunction
			break;
		case "CD": // Cardinal number
			break;
		case "DT": // Determiner
			break;
		case "EX": // Existential there
			break;
		case "FW": // Foreign word
			break;
		case "IN": // Preposition or subordinating conjunction
			break;
		case "JJ": // Adjective
			break;
		case "JJR": // Adjective, comparative
			break;
		case "JJS": // Adjective, superlative
			break;
		case "LS": // List item marker
			break;
		case "MD": // Modal
			break;
		case "NN": // Noun, singular or mass
			currTense = 'n';
			break;
		case "NNS": // noun, plural
			currTense = 'N';
			break;
		case "NNP": // Proper noun, singular
			currTense = 'p';
			break;
		case "NNPS": // Proper noun, plural
			currTense = 'P';
			break;
		case "PDT": // Predeterminer
			break;
		case "POS": // Possessive ending
			break;
		case "PRP": // Personal pronoun
			break;
		case "PRP$": // Possessive pronoun
			break;
		case "RB": // Adverb
			break;
		case "RBR": // Adverb, comparative
			break;
		case "RBS": // Adverb, superlative
			break;
		case "RP": // Particle
			break;
		case "SYM": // Symbol
			break;
		case "TO": // to
			break;
		case "UH": // Interjection
			break;
		case "VB": // Verb, base form
			break;
		case "VBD": // Verb, past tense
			currTense = 'v';
			break;
		case "VBG": // Verb, gerund or present participle
			currTense = 'V';
			break;
		case "VBN": // Verb, past participle
			currTense = 'v';
			break;
		case "VBP": // Verb, non-3rd person singular present
			currTense = 'V';
			break;
		case "VBZ": // Verb, 3rd person singular present
			currTense = 'V';
			break;
		case "WDT": // Wh-determiner
			break;
		case "WP": // Wh-pronoun
			break;
		case "WP$": // Possessive wh-pronoun
			break;
		case "WRB": // Wh-adverb
			break;


		} // end switch...
		
		return currTense;
	} // end grading...
	
	private static void getFile(File folder) throws IOException
	{
		for (final File fileEntry : folder.listFiles()) {
		    if (fileEntry.isDirectory()) {	//If the given input is a folder, search through the folder
		        getFile(fileEntry);
		    } else { //If the given input is a file, read through the file and POS tag it
		    	//Reads from the file and splits it by line
		        System.out.println(fileEntry.getName());
		    	@SuppressWarnings("resource")
				String content = new Scanner(new File(fileEntry.getAbsolutePath())).useDelimiter("\\Z").next();
		        //Splits the scanned file into individual words
				String array[] = content.split("[.\n\r\n]");
				POSTag(array);	//Calls the tagger to tag each file
				countSentences(array);	//Estimates how many sentences there are in a file
		    }
		    currentFile++;	//Move on to the next file
		}
	} // end getFile...
	
	/*
	 * Parse a sentence array and show the tags to each word
	 */
	private static void POSTag(String sent[]){
		InputStream modelIn = null;

		try {
			modelIn = new FileInputStream("en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			
			String tempWords[] = new String [1000];	//Total list of words
			String tempTags[] = new String[1000];	//Total list of tags
			int current = 0;
			
			POSTaggerME tagger = new POSTaggerME(model);
			/*
			 * Tags the whole file sentence by sentence
			 */
			for(int x=0; x<sent.length; x++){
				String array[] = sent[x].split(" ");
				String tags[] = tagger.tag(array);
	
				//Tags an individual sentence and adds it to the master list
				for(int i=0; i<tags.length; i++){
					if(array[i].length() > 0){	//As long as the string is not empty, add it to the list
						tempWords[current] = array[i];
						tempTags[current] = tags[i];
						current++;
					}
				}
			}
			String fileWords[] = new String[current];	//Total list of words with correct array length
			String fileTags[] = new String[current];	//Total list of tags with correct array length
			/*
			 * Creates an array of correct length of all the words we have and a corresponding array for the tags
			 */
			for(int x=0; x<current; x++){
				fileWords[x] = tempWords[x];
				fileTags[x] = tempTags[x];
			}

			//screen output to visualize individual tagging
			for(int i=0; i<fileWords.length; i++)
				System.out.print(fileWords[i] + ' ');
			System.out.println();
			for(int i=0; i<fileTags.length; i++)
				System.out.print(fileTags[i] + ' ');
			System.out.println();
			
			checkPerson(fileWords, fileTags);	//Part 2a
			checkTopic(fileWords, fileTags);	//Part 2b
		}
		catch (IOException e) {
			// Model loading failed, handle the error
			e.printStackTrace();
		}
		finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				}
				catch (IOException e) {
				}
			}
		}			
	} // end POSTag...

	/*
	 * Count the number of sentences in the given file
	 * Split by a newline, carriage return, or a period
	 * Discount any that are only one word long.
	 */
  	private static void countSentences(String sent[]) throws IOException{
		float sentences = 0;
		float curr = 0;
		int score;
		for(int i=0; i<sent.length; i++){
			String temp[] = sent[i].split("[ \n]");
			if(temp.length > 1)	//If the sentence length is more than one word it may be a proper sentence
				sentences++;
		}
		curr = sentences / 6;
		//Calculate the scores of each of the files rounded down to the nearest half
		if(curr > 1){
			score = 5;
			scores[6][currentFile] = score;
		}else{
			scores[6][currentFile] = (int)(Math.floor(curr * 5));
		}
  	} // end countSentences...
  	
  	/*
  	 * Currently only checks if the pronouns are first, second, or third person
  	 * and grades with how many their are
  	 * 
  	 * TODO: compare third person pronouns to previous nouns for tense
  	 */
  	private static void checkPerson(String array[], String tags[]){
  		//The arrays to compare the pronouns to
  		Set<String> first = new HashSet<String>(Arrays.asList(new String[] {"i", "i'm", "me", "my", "mine", "we", "us", "our"}));
  		Set<String> second = new HashSet<String>(Arrays.asList(new String[] {"you", "your"}));
  		Set<String> thirdSingular = new HashSet<String>(Arrays.asList(new String[] {"he", "she", "him", "her", "his", "hers", "it"}));
  		Set<String> thirdPlural = new HashSet<String>(Arrays.asList(new String[] {"they", "them", "their", "theirs"}));
  		
  		//Create an array of pronouns from the file
		String[] pronouns = new String[array.length];
		int temp = 0;
		for(int i=0; i<array.length; i++){
			if(tags[i].equals("PRP") || tags[i].equals("PRP$")){	//Get the pronouns in the file
				pronouns[temp] = array[i].toLowerCase();	//convert to lower case to make it easier to compare
				temp++;
			}
		}
  		
  		int score = 5;

  		/*
  		 * Go through the pronouns and check their person
  		 * If they are first person, no points are given or taken
  		 * If they are second person, 1 point is taken away
  		 * If they are third person, 1 point is given
  		 * Scores are rounded to 0 and 5 accordingly for out of bounds numbers
  		 */
  		String thirdPerson[] = new String[100];
  		int temp1 = 0;
  		for(int x=0; pronouns[x] != null; x++){
  			if(first.contains(pronouns[x])) {	//Do nothing
  				;
  			} else if(second.contains(pronouns[x])) {	//take away a point
  				score -= 1;
  			} else if(thirdSingular.contains(pronouns[x]) || thirdPlural.contains(pronouns[x])) {	//Add a point and add it to the list of third person pronouns
  				score += 1;
  				thirdPerson[temp1] = pronouns[x];
  				temp1++;
  			}
  		}
  		
  		for(int y=0; y<temp1; y++){
  			if(thirdSingular.contains(thirdPerson[y])){	//Check for a corresponding singular noun before we reach the pronoun in the file
  				for(int z=0; !(array[z].equals(thirdPerson[y])); z++){
  					
  				}
  			}else{	//Check for a corresponding plural noun before we reach the pronoun in the file
  				for(int z=0; !(array[z].equals(thirdPerson[y])); z++){
  					
  				}
  			}
  		}
  		
  		if(score > 5)
  			score = 5;
  		else if (score < 0)
  			score = 0;
  		
  		scores[4][currentFile] = score;	//Add the score to the corresponding slot in the score array
  		//System.out.println();
  	}

  	/*
  	 * Checks to see if the author wrote about the topic of choice, autobiography
  	 * Uses matching strings to check if they are included in the essay
  	 */
  	private static void checkTopic(String array[], String tags[]){
  		String nouns[] = new String[100];
  		int temp = 0;
  		int total = 0;
  		int properNouns = 0;
  		
  		/*
  		 * Create an array containing all of the nouns in the file
  		 * Checks for proper nouns and makes sure the corresponding pronoun matches first-person
  		 */
  		for(int i=0; i<array.length; i++){
	  		if(tags[i].equals("NN") || tags[i].equals("NNS")) {	//Get the nouns in the file
				nouns[temp] = array[i].toLowerCase();
				temp++;
				total++;
			} else if(tags[i].equals("NNP") && !tags[i-1].equals("NNP")) {	//If it is a proper noun, add it to the list (names count as 1 noun) if the corresponding pronoun is correct
				String helper = tags[i-1];
				int curr = i-1;
				while((curr) != 0 && !(tags[curr].equals("PRP") || tags[curr].equals("PRP$")))
					curr--;
				helper = array[curr];
				//As long as the proper noun is being said by the author and not a third person, add it to the totals
				if(helper.equals("I") || helper.equals("my") || curr == 0)
					properNouns++;
				total++;
			}
  		}
  		
  		//Come From, live, family, work, school
  		//The arrays to check if the topic matches
  		Set<String> family = new HashSet<String>(Arrays.asList(new String[] 
  				{"family", "child", "children", "kid", "kids", "son", "daughter",
  				"parent", "father", "mother", "sibling", "brother", "sister",
  				"girls", "boys", "mom", "dad", "brothers", "sisters", "cousins",
  				"husband", "wife", "husband's", "wife's", "grandson", "granddaughter"}));
  		Set<String> workSchool = new HashSet<String>(Arrays.asList(new String[]
  				{"work", "job", "school", "education", "learn", "educate"}));
  		
  		int contained = properNouns;
  		//If the noun matches a noun in our arrays, add one to the number of contained words
  		for(int x=0; nouns[x] != null; x++) {
  			if(family.contains(nouns[x]) || workSchool.contains(nouns[x])){
  				int curr = x-1;
  				String helper = tags[curr];
  				//Find a previous pronoun to compare to the current noun
  				while((curr) != 0 && !(tags[curr].equals("PRP") || tags[curr].equals("PRP$")))
  					curr--;
  				helper = array[curr];
  				//If the previous pronoun is in first person, or if we are in the beginning of the sentence, count it to the total contained
  				if(helper.equals("I") || helper.equals("my") || curr == 0)
  					contained++;
  			}
  			System.out.print(nouns[x] + ' ');
  		}
  		System.out.println("\n" + contained + ' ' + total);
  		
  		/*
  		 * Calculate the score for this part
  		 * Still being tweaked
  		 * Current model is:
  		 * (total number of words that are part of the topic) / (total number of nouns)
  		 */
  		float score = (float)contained / (float)total;
  		System.out.println(score);
  		if(score >= 0.5)
  			scores[5][currentFile] = 5;
  		else if(score >= 0.4)
  			scores[5][currentFile] = 4;
  		else if(score >= 0.3)
  			scores[5][currentFile] = 3;
  		else if(score >= 0.2)
  			scores[5][currentFile] = 2;
  		else if(score >= 0.1)
  			scores[5][currentFile] = 1;
  		else
  			scores[5][currentFile] = 0;
  	}
  	
  	//Prints the scores of each of the files into the output file, and to the screen
  	private static void printScores(FileWriter output) throws IOException{
  		System.out.println("\nEssay\t1a\t1b\t1c\t1d\t2a\t2b\t3a\tFinal Grade");
  		for(int y=0; y<totalFiles; y++){
  			finalGrade(y);
  			System.out.print(files[y].getName() + "\t");
  			output.write(files[y].getName() + "\t");
  			for(int x=0; x<7; x++){
  				System.out.print(scores[x][y] + "\t");
  				output.write(scores[x][y] + "\t");
  			}
  			System.out.print(finalGrade[0][y]);
  			output.write(finalGrade[0][y]);
  			System.out.println();
  			output.write("\r\n");
  		}
  	}
  	
  	/*
  	 * Calculates the final grade for each essay using the following given formula
  	 * Final Score = (1a + 1b + 1c + 2 * 1d + 2a + 3 * 2b + 3a) / 10
  	 */
  	private static void finalGrade(int current){  		
		float total = scores[0][current] + scores[1][current] + scores[2][current] + (2 * scores[3][current]) + scores[4][current] + ( 3 * scores[5][current]) + scores[6][current];
		total = total / 10;
		double comp = total - Math.floor(total);
		
		if(comp >= 0.75) {	//If the total is closer to the next number, round it to the higher number
			total = (float) (Math.floor(total) + 1.0);
			if(total > 5) total = 5;	//If the total if more than 5, round it down to 5
		} else if(comp >= 0.25) {	//If the total is closer to the midpoint of two numbers, round it to the nearest half
			total = (float) (Math.floor(total) + 0.5);
		} else {	//If the total is closer to the lower number, round it down
			total = (float) (Math.floor(total));
			if(total > 5) total = 5;
		}
		
		finalGrade[0][current] = Float.toString(total);
  	}
  	
  	//Initializes a 2d array of size 7x(number of files) to 0
  	private static void initializeScores(int length){
  		scores = new int[7][length];
  		finalGrade = new String[1][length];
  		for(int y=0; y<length; y++){
  			for(int x=0; x<7; x++)
  				scores[x][y] = 0;
  			finalGrade[0][y] = "";
  		}
  	}
	
	public static void main (String args[]) throws IOException
	{
		File folder = new File(args[0]);	//The given path of the folder of files
		FileWriter output = new FileWriter(args[1]);	//The output file to print the tags
		output.write("Essay\t1a\t1b\t1c\t1d\t2a\t2b\t3a\tFinal Grade\r\n"); //Prints a header to the file
    	files = folder.listFiles();
    	totalFiles = files.length;
    	initializeScores(totalFiles);
		getFile(folder);
		printScores(output);
		output.close();
	} // end main...


	/****************************************************
	 * Below was getting to know the parser.
	 * train() will not actually be used in this code.
	 * It is more for reference to understand our code.
	 ****************************************************/
	
	/*
	private static void train ()
	{
	
		POSModel model = null;
	
		InputStream dataIn = null;
		try {
			dataIn = new FileInputStream("en-pos.train");
			ObjectStream<String> lineStream = new PlainTextByLineStream(dataIn, "UTF-8");
			ObjectStream<POSSample> sampleStream = new WordTagSampleStream(lineStream);

			model = POSTaggerME.train("en", sampleStream, TrainingParameters.defaultParams(), null, null);
		}
		catch (IOException e) {
			// Failed to read or parse training data, training failed
			e.printStackTrace();
		}
		finally {
			if (dataIn != null) {
				try {
					dataIn.close();
				}
				catch (IOException e) {
					// Not an issue, training already finished.
					// The exception should be logged and investigated
					// if part of a production system.
					e.printStackTrace();
				}
			}
		}

		OutputStream modelOut = null;
		try {
			modelOut = new BufferedOutputStream(new FileOutputStream(modelFile));
			model.serialize(modelOut);
		}
		catch (IOException e) {
			// Failed to save model
			e.printStackTrace();
		}
		finally {
			if (modelOut != null) {
				try {
					modelOut.close();
				}
				catch (IOException e) {
					// Failed to correctly save model.
					// Written model might be invalid.
					e.printStackTrace();
				}
			}
		}

	} // end train...
	*/
}
