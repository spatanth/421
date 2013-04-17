import java.io.*;
import java.nio.charset.Charset;

import opennlp.tools.postag.*;
import opennlp.tools.sentdetect.*;
import opennlp.tools.util.*;
import opennlp.tools.util.model.ModelType;

public class project {

  private static void getFile(String inputFileName, String outputFileName) throws IOException
	{



	} // end getFile...

	
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

	private static void POSTag (){
		InputStream modelIn = null;

		try {
		  modelIn = new FileInputStream("en-pos-maxent.bin");
		  POSModel model = new POSModel(modelIn);
			
			POSTaggerME tagger = new POSTaggerME(model);
			String sent[] = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
	                "morning", "and", "afternoon", "newspapers", "."};		  
			String tags[] = tagger.tag(sent);
			
			for(int i=0; i<tags.length; i++)
				System.out.print(tags[i] + '\n');
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



	public static void main (String args[])
	{
		POSTag();
	} // end main...

}
