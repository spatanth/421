import java.io.*;
import java.util.Scanner;

import opennlp.tools.postag.*;

public class project {

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
				POSTag(array, output);
		    }
		}
	} // end getFile...
  
  	/*
  	 * Parse a sentence array and show the tags to each word
  	 */
	private static void POSTag(String sent[], FileWriter output){
		InputStream modelIn = null;

		try {
			modelIn = new FileInputStream("en-pos-maxent.bin");
			POSModel model = new POSModel(modelIn);
			
			POSTaggerME tagger = new POSTaggerME(model);
			for(int j=0; j<sent.length; j++){
				String temp[] = sent[j].split("[ \n]");
				String tags[] = tagger.tag(temp);
				
				for(int i=0; i<tags.length; i++){
					output.write(tags[i] + ' ');
					System.out.print(tags[i] + ' ');
				}
				output.write('\n');
				System.out.println('\n');
			}
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
	}
	
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

	public static void main (String args[]) throws IOException
	{
		File folder = new File(args[0]);	//The given path of the folder of files
		FileWriter output = new FileWriter(args[1]);	//The output file to print the tags
		getFile(folder, output);
		output.close();
	} // end main...

}
