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
	static float scores[][];
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
		    	//System.out.println(content + '\n');
		        //Splits the scanned file into individual words
				String array[] = content.split("[.\n\r\n]");
				//for(int i=0; i<array.length; i++){
					//System.out.println(array[i]);
				//}
				//POSTag(array);
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

			POSTaggerME tagger = new POSTaggerME(model);
			String tags[] = tagger.tag(sent);

			for(int i=0; i<tags.length; i++)
				System.out.print(tags[i] + ' ');
			System.out.println('\n');
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

  	// Count the number of sentences in the given file
  	private static void countSentences(String sent[]) throws IOException{
		float sentences = 0;
		float score = 0;
		for(int i=0; i<sent.length; i++){
			String temp[] = sent[i].split("[ \n]");
			if(temp.length > 1)	//If the sentence length is more than one word it may be a proper sentence
				sentences++;
		}
		score = sentences / 6;
		//Calculate the scores of each of the files rounded down to the nearest half
		if(score > 1){
			score = 5;
			scores[6][currentFile] = score;
		}else{
			score = score * 5;
			double comp = score - Math.floor(score);
			if(comp >= 0.75){	//If the score is closer to the next number, round it to the higher number
				score = (float) (Math.floor(score) + 1.0);
				if(score > 5) score = 5;	//If the score if more than 5, round it down to 5
				scores[6][currentFile] = score;
			}else if(comp >= 0.25){	//If the score is closer to the midpoint of two numbers, round it to the nearest half
				score = (float) (Math.floor(score) + 1.0);
				score += 0.5;
				scores[6][currentFile] = score;
			}else{ //If the score is closer to the lower number, round it down
				score = (float) (Math.floor(score));
				scores[6][currentFile] = score;
			}
		}
		System.out.println(sentences);
		System.out.println(score);
  	} // end countSentences...
  	
  	//Prints the scores of each of the files into the output file, and to the screen
  	private static void printScores(FileWriter output) throws IOException{
  		for(int y=0; y<totalFiles; y++){
  			System.out.print(files[y].getName() + "\t");
  			output.write(files[y].getName() + "\t");
  			for(int x=0; x<7; x++){
  				System.out.print(scores[x][y] + "\t");
  				output.write(scores[x][y] + "\t");
  			}
  			System.out.println();
  			output.write("\r\n");
  		}
  	}
  	
  	//Initializes a 2d array of size 7x(number of files) to 0
  	private static void initializeScores(int length){
  		scores = new float[7][length];
  		for(int x=0; x<7; x++){
  			for(int y=0; y<length; y++){
  				scores[x][y] = 0;
  			}
  		}
  	}
	
	public static void main (String args[]) throws IOException
	{
		File folder = new File(args[0]);	//The given path of the folder of files
		FileWriter output = new FileWriter(args[1]);	//The output file to print the tags
		output.write("Essay\t1a\t1b\t1c\t1d\t2a\t2b\t3a\r\n"); //Prints a header to the file
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
