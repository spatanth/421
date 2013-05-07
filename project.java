/*
 * authors: Anthony Spatafora, Robert Faigao
 * Spring 2013
 * CS421 Natural Language Processing
 * Project, Part 1
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
	static int scores[][];
	static String finalGrade[][];
	private static File[] files;
	private static int totalFiles;
	private static int currentFile = 0;

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
				POSTag(array);
				countSentences(array);
		    }
		    currentFile++;
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
			
			String fileWords[] = new String [1000];
			String fileTags[] = new String[1000];
			int current = 0;
			String pronouns[] = new String[100];
			int temp1 = 0;
			
			POSTaggerME tagger = new POSTaggerME(model);
			for(int x=0; x<sent.length; x++){
				String array[] = sent[x].split(" ");
				String tags[] = tagger.tag(array);
	
				//Used in part 2
				for(int i=0; i<tags.length; i++){
					if(array[i].length() > 1){
						fileWords[current] = array[i];
						fileTags[current] = tags[i];
						current++;
						
						if(tags[i].equals("PRP") || tags[i].equals("PRP$")){	//Get the pronouns in the file
							pronouns[temp1] = array[i].toLowerCase();
							temp1++;
						}
					}
				}
			}
			
			for(int i=0; i<current; i++)
				System.out.print(fileWords[i] + '\t');
			System.out.println();
			for(int i=0; i<current; i++)
				System.out.print(fileTags[i] + '\t');
			System.out.println();
			
			checkPerson(pronouns);
			checkTopic(fileWords, fileTags);
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
  	 */
  	private static void checkPerson(String pronouns[]){
  		Set<String> first = new HashSet<String>(Arrays.asList(new String[] {"i", "i'm", "me", "my", "mine", "we", "us", "our"}));
  		Set<String> second = new HashSet<String>(Arrays.asList(new String[] {"you", "your"}));
  		Set<String> third = new HashSet<String>(Arrays.asList(new String[] {"he", "she", "him", "her", "his", "hers", "they", "them", "their", "theirs", "it"}));
  		
  		int score = 5;
  		
  		//for(int x=0; pronouns[x] != null; x++)
  			//System.out.print(pronouns[x] + '\t');
  		//System.out.println();
  		for(int x=0; pronouns[x] != null; x++){
  			if(first.contains(pronouns[x])) {
  				//System.out.print("one" + '\t');
  			} else if(second.contains(pronouns[x])) {
  				score -= 1;
  				//System.out.print("two" + '\t');
  			} else if(third.contains(pronouns[x])) {
  				score += 1;
  				//System.out.print("three" + '\t');
  			} else {
  				//System.out.print('\t');
  			}
  		}
  		if(score > 5)
  			score = 5;
  		else if (score < 0)
  			score = 0;
  		
  		scores[4][currentFile] = score;
  		//System.out.println();
  	}
  	
  	private static void checkTopic(String array[], String tags[]){
  		String nouns[] = new String[100];
  		int temp = 0;
  		int total = 0;
  		int properNouns = 0;
  		
  		for(int i=0; array[i] != null; i++){
	  		if(tags[i].equals("NN") || tags[i].equals("NNS")) {	//Get the nouns in the file
				nouns[temp] = array[i].toLowerCase();
				temp++;
				total++;
			} else if(tags[i].equals("NNP") && !tags[i-1].equals("NNP")) {	//If it is a proper noun, add it to the list
				properNouns++;
				total++;
			}
  		}
  		
  		//Come From, live, family, work, school
  		Set<String> family = new HashSet<String>(Arrays.asList(new String[] 
  				{"family", "child", "children", "kid", "kids", "son", "daughter",
  				"parent", "father", "mother", "sibling", "brother", "sister"}));
  		Set<String> workSchool = new HashSet<String>(Arrays.asList(new String[]
  				{"work", "job", "school", "education", "learn", "educate"}));
  		
  		int contained = properNouns;
  		for(int x=0; nouns[x] != null; x++) {
  			if(family.contains(nouns[x]) || workSchool.contains(nouns[x]))
  				contained++;
  			System.out.print(nouns[x] + ' ');
  		}
  		System.out.println("\n" + contained + ' ' + total);
  		
  		float score = (float)contained / (float)total;
  		System.out.println(score);
  		if(score >= 0.4)
  			scores[5][currentFile] = 5;
  		else if(score >= 0.3)
  			scores[5][currentFile] = 4;
  		else if(score >= 0.2)
  			scores[5][currentFile] = 3;
  		else if(score >= 0.1)
  			scores[5][currentFile] = 2;
  		else if(score > 0)
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
