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
 * 
 */

import java.io.*;
import java.util.*;
import java.nio.charset.Charset;

import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelType;

public class project {

	//Grades the file by going through each sentence and comparing tags
	private static void grader(File output) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(output);
		String[] arr = null;
		int temp = 0;
		
		while ( scanner.hasNext() ) {
			arr[temp] = scanner.next();
			temp++;
		}
		scanner.close();
		
		while (temp != 0) {
			
			grading(arr[temp]);
			temp--;
		}
	}
	
	//Grades the functions based on tense and possession
	private static void grading(String x)
	{
		
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
			break;
		case "NNS": // noun, plural
			break;
		case "NNP": // Proper noun, singular
			break;
		case "NNPS": // Proper noun, plural
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
			break;
		case "VBG": // Verb, gerund or present participle
			break;
		case "VBN": // Verb, past participle
			break;
		case "VBP": // Verb, non-3rd person singular present
			break;
		case "VBZ": // Verb, 3rd person singular present
			break;
		case "WDT": // Wh-determiner
			break;
		case "WP": // Wh-pronoun
			break;
		case "WP$": // Possessive wh-pronoun
			break;
		case "WRB": // Wh-adverb
			break;
		
		
		}
		
	}
	
	//Function to read a file, or read through a folder and tag each of the files
	private static void getFile(File folder, FileWriter output) throws FileNotFoundException
	{
		for (final File fileEntry : folder.listFiles()) {
		    if (fileEntry.isDirectory()) {	//If the given input is a folder, search through the folder
		        getFile(fileEntry, output);
		    } else { //If the given input is a file, read through the file and POS tag it
		    	//Reads from the file and splits it by line
		        System.out.println(fileEntry.getName());
		    	@SuppressWarnings("resource")
				String content = new Scanner(new File(fileEntry.getAbsolutePath())).useDelimiter("\\Z").next();
		    	System.out.println(content + '\n');
		        //Splits the scanned file into individual words
				String array[] = content.split("[.\n\r\n]");
				for(int i=0; i<array.length; i++){
					System.out.println(array[i]);
				}
				//Calls the following functions to tag and grade the current file
				POSTag(array, output);
				countSentences(array, output);
		    }
		}
		
	} // end getFile...

	/*
	 * Parse a sentence array and show the tags to each word
	 */
	private static void POSTag(String sent[], FileWriter output){
		InputStream modelIn = null;

		try {
			//File required to use the tagger
			modelIn = new FileInputStream("en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);

			POSTaggerME tagger = new POSTaggerME(model);
			//Tag the current file
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
  
  	//Count the number of sentences in the given file
  	private static void countSentences(String sent[], FileWriter output) throws IOException{
		int sentences = 0;
		float score = 0;
		for(int i=0; i<sent.length; i++){
			String temp[] = sent[i].split("[ \n]");
			if(temp.length > 1)	//If the sentence length is more than one word it may be a proper sentence
				sentences++;
		}
		score = (float)sentences / 6;
		//Calculate the scores of each of the files rounded down to the nearest half
		if(score > 1){
			score = 5;
			int s = (int) score;
			output.write(s + "\n");
		}else{
			score = score * 5;
			if((score - Math.floor(score)) > 0.5){
				score = (float) (Math.floor(score) + 0.5);
				int s = (int) score;
				output.write(s + ".5\n");
			}else{
				score = (float) (Math.floor(score));
				int s = (int) score;
				output.write(s + "\n");
			}
		}
		System.out.println(sentences);
		System.out.println(score);
  	}//End countSentences

	public static void main (String args[]) throws IOException
	{
		File folder = new File(args[0]);	//The given path of the folder of files
		FileWriter output = new FileWriter(args[1]);	//The output file to print the tags
		getFile(folder, output);
		output.close();
	} // end main...


	/*
	 * Below was getting to know the parser.
	 * train() will not actually be used in this code.
	 * It is more for reference to understand our code.
	 */
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
