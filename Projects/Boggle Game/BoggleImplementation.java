import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Implementation class where all the features (loading the puzzle, loading the dictionary, solving the puzzle and printing the puzzle)
 * are implemented.
 */
public class BoggleImplementation implements Boggle {

	//String array used to store the dictionary containing candidates of words used to find in the puzzle.
	static String dictionary[] = {};

	//2-D char array to store the puzzle grid.
	static char puzzle[][];
	
	//Store the total number of rows in the puzzle based on the input of the stream.
	static int finalLength;
	
	//Store the width of the puzzle based on the input of the stream.
	static int finalWidth;
	
	//Variable to validate solve and print methods if the puzzle is ready to use or not.
	boolean isPuzzleReady = false;
	
	//Instance of buffered reader to reset the stream using marker.
	BufferedReader bufferedReader;
	
	//String used to add the string in the list of string as a return value in solve method.
	static String finalString;
	
	//Used to return the list in the solve method.
	static List<String> returnString = new ArrayList<String>();
	
	/*
	 * Description: Method used to load the dictionary with possible candidate of words to find from the puzzle.
	 * Created By: Rushi Patel
	 * @see Boggle#getDictionary(java.io.BufferedReader)
	 */
	@Override
	public boolean getDictionary(BufferedReader stream){
		//Variable to check the return value.
		boolean check= false;
		try{
			//Validate if the stream is null.
		    if(stream == null){
			   throw new BoggleException("Stream cannot be null!");
		    }
		    //Reads the word in contentLine.
		    String contentLine = stream.readLine();
		    
		    //Used to store words in this String's object.
		    String words = new String();
		    words= contentLine;
		   
		    //Reads all the words from the stream until eof is reached.
		    while (contentLine != null) {
		       //If one character word is found stops the execution. Dictionary is not loaded. 	
			   if(contentLine.length() == 1){
				   return false;
			   }
			   
		      contentLine = stream.readLine();
		      //Stops the executuon if null is encountered in between.
		      if(contentLine == null){
		    	  break;
		      }
		      //Appends all the words.
		      words = words + " " + contentLine;
		      
		   }
		    
		   //Seperating all the words in the array.
		   dictionary = words.split(" ");
		   //List to remove one character word.
		   List<String> removeOneCharacter = new ArrayList<String>(Arrays.asList(dictionary));
		   //Removes the one character word.
		   for(int i=0;i<dictionary.length;i++){
			   if(dictionary[i].length()<2){
				   removeOneCharacter.remove(dictionary[i]);
			   }
		   }
		   //Array without a one character word.
		   String[] finalDictionary = removeOneCharacter.toArray(new String[0]);
		   //Finally storing the final dictionary array.
		   dictionary = finalDictionary;
		   System.out.println(finalDictionary);
		   
		   System.out.println("Succesfully created the dictionary!");
		   check = true;
		   
		}
		   catch(Exception exception){
			   exception.getMessage();
			   return false;
		   }
		
		return check;
	}
	
	/*
	 * Description: Method to initailize the 2-D puzzle variable
	 * Created by: Rushi Patel
	 */
	public int initializePuzzle(BufferedReader stream){
		try{
			//Marking the stream used to reset later.
			stream.mark(40);
			String contentLine = stream.readLine();
			
			int width = contentLine.length();
			int length=0;
			//Validate if the dimension of the puzzle is not correct.
			while (contentLine != null ) {
				if(contentLine.equals("")){
					break;
				}
				if(contentLine.length()!=width){
					throw new BoggleException("Cannot create puzzle due to dimension mismatch!");
				}
				contentLine = stream.readLine();
				
				length++;
				
			}
			//Setting the length of the puzzle in the global variable.
			finalLength = length;
			//Setting the width of the puzzle in the global variable.
			finalWidth = width;
			//Initializing the puzzle with the length and width.
			puzzle = new char[length][width];
			return length;
		}
		catch(Exception ex){
			//Returned 100 to check if the value is 100 then should not load the puzzle.
			return 100;
		}
	}
	
	@Override
	public boolean getPuzzle(BufferedReader stream) {
		//Return variable.
		boolean check = false;
		
		try{
			//Validate if the stream is null.
			if(stream == null){
				throw new BoggleException("Stream cannot be null!");
			}
			//Initializing the puzzle.
			int initializePuzzleOutcome = initializePuzzle(stream);
			//Validate if the puzzle has incorrect dimensions.
			if(initializePuzzleOutcome == 100){
				return false;
			}
			//Resets the stream to the marked location.
			stream.reset();

			String line = stream.readLine();
			
			//
			int counter=0;
			while(line!=null){
				//Breaks the loop if encounters empty line 
				if(line.equals("")){
					break;
				}
				//Stores the row in the puzzle.
				puzzle[counter] = line.toCharArray();
				

				counter++;
			line = stream.readLine();
			
			
			}
			//Setting the variable as true indicating puzzle is ready.
			isPuzzleReady = true;
			System.out.println("Succesfully created the puzzle and is ready to use!");
			check = true;
		
		}
		catch(Exception exception){
			//Setting the variable as false indicating puzzle is not ready.
			isPuzzleReady = false;
			exception.getMessage();
			return false;
		}
		

		isPuzzleReady = true;
		return check;
	}
	
	/*
	 * Description: Method to solve the puzzle and find words of the dictionary in the puzzle.
	 * Created by: Rushi Patel
	 * (non-Javadoc)
	 * @see Boggle#solve()
	 */
	public List<String> solve() {

		try{
		
			if(isPuzzleReady == false){
			throw new BoggleException("Puzzle is not ready to solve due to dimension mismatch!");
			}
			Finder finder = new Finder();
		
			//Used to keep track of visited puzzle array
			boolean isVisited[][] = new boolean[finalLength][finalWidth];

			//String used to store matched string and initialized as empty
			String checkString = "";
			//String used to store path string and initialized as empty
			String path ="";
			//Loop to find each character and search character sequence to find words
			for (int i = 0; i < finalLength; i++){
				for (int j = 0; j < finalWidth; j++){
					finder.boggleWordFinder(isVisited, i, j, checkString,path);
				}
			}
			//Used to remove duplicates from the list.
			Set<String> removeDuplicatesList = new HashSet<>(returnString);
			returnString.clear();
			returnString.addAll(removeDuplicatesList);
			//Sorting the list.
			Collections.sort(returnString);
		
		
			//Used to remove the duplicate words
			List<String> words = new ArrayList<>();
			java.util.Iterator<String> itr = returnString.iterator();
			while(itr.hasNext()){
				String[] st = itr.next().split("\t");
			
				if(words.contains(st[0])){
					itr.remove();

					continue;
				}
				words.add(st[0]);
			}

			System.out.println(returnString);
		}
		catch(BoggleException exception){
			exception.getMessage();
			return null;
		}
		return returnString;
	}
	
	/*
	 * Description: Method to display the puzzle as a grid.
	 * Created by: Rushi Patel
	 * (non-Javadoc)
	 * @see Boggle#print()
	 */
	@Override
	public String print() {
		//Validates if the puzzle is ready to be printed.
		if(isPuzzleReady == false){
			System.out.println("Puzzle is not ready to print due to dimension mismatch!");
			return "";
		}
		//Validates if the puzzle is null or does not contain any characters.
		if(puzzle == null || puzzle.length==0){
			return "";
		}
		//Variable used to return the puzzle grid.
		String returnString = new String();
		//Iterating all the characters based on the row.
		for (char[] allCharacter : puzzle)
		{
			//Appending all the characters from the row
		   for (char character : allCharacter)
		   {
		        returnString += character + "\t";
		   }
		   
		   returnString  +=  "\n";
		}
		
		System.out.println(returnString);
		
		
		return returnString;
	}
	


}
