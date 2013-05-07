                                                                     
                                                                     
                                                                     
                                            
project 1 for CS421 - University of Illinois at Chicago
Name1 aspata2(at)uic.edu
Name2 rfaigao2(at)uic.edu
-------------------------------------------------------------------------
--->SETUP<---------------------------------------------------------------

Q: Here you should describe how to run your program. Imagine someone (me)
downloads your archive file on a computer and needs step by step
instructions to make everything work. Make sure to specify the relative
positions of files and folders, if needed.

A: This project was created using Eclipse on Ubuntu
	1) Run the .jar file with the following arguments:
		./421project.jar <folder of essays> output.txt

	Note) To grade new text files, add them to the essay-corpus folder
		located within the src folder


-------------------------------------------------------------------------
--->INPUT<---------------------------------------------------------------
  
Q: Use this section only if your program requires some arguments as input.

A: You will need to input two arguments for out program. The first argument
is the folder containing the essays to be checked. The second argument is the
output .txt file where the scores will be printed in.


-------------------------------------------------------------------------
--->OUTPUT<--------------------------------------------------------------

Q: In this section you must specify what your program writes in the
standard or file output.

A: Our output will be the grade that we have given the file. It will include
all of the information asked for by the individual parts of grading. This
will also be outputted into the standard output within the console.
	

-------------------------------------------------------------------------
--->FILES<---------------------------------------------------------------

Q: This sections must include a description of any file your program reads
or writes. Include a description of where the file is located, how it
is formatted, and what its purpose is. Do not describe the files already
provided to you, such as the essays. Just describe the files you create,
if any.

A: Our program uses two files. The first file which may also be a folder,
contains the text to be tagged or the files that will be tagged.
The second file outputs the grades our program calculates and also the
tags of each of the words in a specific file. It is called "output.txt"
and is a simple text file containing letters and numbers.
Both of these files are located within the project directory under the
subfolder "src".
	

-------------------------------------------------------------------------
--->TECHNIQUE<-----------------------------------------------------------

Q: A brief explanation of how you exploited POS tagging to evaluate the essays.
Also state some patterns of errors in terms of POS tags that you found.

A: In order to grade the first part of the project (1a, 1b, and 1c) we used
the fact that the words are tagged in a certain tense and possessive tense.
Such as 3rd person past, and 1st person present. By using these types of tags
we are able to tell of two words that are part of the same sentence match
according to tense and possession. If they do not match then it is an error
on the writers side so they get marked off for that.


-------------------------------------------------------------------------
--->TODO<----------------------------------------------------------------
	
Q: Write here a short list of what you plan to do/fix for the second part of
the project.

A: Use the parser to check on the syntax of a setting.
Make sure that a sentence is composed of an NP and VP.
