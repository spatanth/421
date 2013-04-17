import java.io.*;

import opennlp.tools.postag.*;

public class project {

  private static void getFile(File folder, File output)
	{
		for (final File fileEntry : folder.listFiles()) {
		    if (fileEntry.isDirectory()) {
		        getFile(fileEntry, output);
		    } else {
		        System.out.println(fileEntry.getName());
		    }
		}
	  
		String temp = "Test one tests test two.";
		String array[] = temp.split(" ");
	} // end getFile...
  
  	/*
  	 * Parse a sentence array and show the tags to each word
  	 */
	private static void POSTag (String sent[], File output){
		InputStream modelIn = null;

		try {
		  modelIn = new FileInputStream("en-pos-maxent.bin");
		  POSModel model = new POSModel(modelIn);
			
			POSTaggerME tagger = new POSTaggerME(model);
			//String sent[] = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
	                //"morning", "and", "afternoon", "newspapers", "."};
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
	
	/*
	 * Below was getting to know the parser.
	 * train() will not actually be used in this code.
	 * It is more for reference to understand our code.
	 */
	private static void train ()

	public static void main (String args[])
	{
		File folder = new File(args[0]);
		getFile(folder, args[1];
	} // end main...

}
