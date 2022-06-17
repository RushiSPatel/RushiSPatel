/*
 * Class used as an extension to BoggleImplementation to find the word, check the word in dictionary and optimize the code.
 */
public class Finder {

	
	/*
	 * Description: Method to optimize the code and reduce the time complexity of the program.
	 * Created by: Rushi Patel
	 */
	protected boolean optimizer(String checkString){
		//Return variable
		boolean check = false;
		//Checks if the dictionary contains any word that starts with the checkString passed. If not it wont check further  
		//in the boggleWordFinder method.
		for(int i=0; i< BoggleImplementation.dictionary.length;i++){
			if(BoggleImplementation.dictionary[i].startsWith(checkString)){
				check =true;
			}
		}
		
		return check;
	}
	
	
	/*
	 * Description: Method to find all the words present in the given dictionary.
	 * Created by: Rushi Patel
	 */
	protected void boggleWordFinder( boolean isVisited[][], int i, int j, String checkString, String path)
		{
		
		//Setting the current puzzle index as visited
		isVisited[i][j] = true;
		//Appending character from the current puzzle index to the string to check in dictionary.
		checkString = checkString + BoggleImplementation.puzzle[i][j];
		
			//Validates the string if it is marked as a word in the dictionary
			if (checkWordInDictionary(checkString)){
				//Used to fetch the coordinates
				char startingCharacter = checkString.charAt(0);
				//Initializing the x and y coordinates to 0.
				int yAxis=0;
				int xAxis=0;
				//Looping to see the coordinates of the starting character of checkString based on XY plane.
				for(int k =0;k<BoggleImplementation.finalLength;k++){
					for(int l=0;l<BoggleImplementation.finalWidth;l++){
						if(BoggleImplementation.puzzle[k][l] == startingCharacter){
								yAxis=BoggleImplementation.finalLength-k;
								xAxis=l+1;
						}
					}
				}
				
				//Appending the matched word along with the x coordinate and y coordinate.
				BoggleImplementation.finalString = checkString + "\t" + xAxis + "\t" + yAxis + "\t"+path;
				
				//Adding the finalString to the return string.
				BoggleImplementation.returnString.add(BoggleImplementation.finalString);
			}

			//Recursively check adjacent indexes of the current index.
			for (int length = i - 1; length <= i + 1 && length < BoggleImplementation.finalLength; length++){
				for (int width = j - 1; width <= j + 1 && width < BoggleImplementation.finalWidth; width++){
					if (length >= 0 && width >= 0 && !isVisited[length][width] && optimizer(checkString)){
						
						//Path = diagonalTopLeft
						if(length == i-1 && width == j-1){
						 path+="N";
						}
						//Path = Up
						else if(length == i-1 && width == j){
							path+="U";
						}
						//Path = diagonalTopRight
						else if(length == i-1 && width == j+1){
							path+="E";
						}
						//Path = Left
						else if(length == i && width == j-1){
							path+="L";
						}
						//Path = Right
						else if(length == i && width == j+1){
							path+="R";
						}
						//Path = diagonalDownLeft
						else if(length == i+1 && width == j-1){
							path+="W";
						}
						//Path = diagonalDownRight
						else if(length == i+1 && width == j+1){
							path+="S";
						}
						//Path = Down
						else if(length == i+1 && width == j){
							path+="D";
						}
						//Recursively calls the method to check the characters of words.
						boggleWordFinder(isVisited, length, width, checkString, path);
						//Reinitialising path and removing the latest path to avoid conflicts.
						path = path.substring(0,path.length()-1);
						//System.out.println(path);
					}
				}
			}

			//Resets the checkString and marks the index as not visited
			checkString = "" + checkString.charAt(checkString.length() - 1);
			isVisited[i][j] = false;
		}
	
	
	/*
	 * Description: Method to verify if the string is described as a word in the dictionary.
	 * Created by: Rushi Patel
	 */
	private boolean checkWordInDictionary(String checkWord)
	{
		//Return variable
		boolean check = false;
		//Loop to find the string in the dictionary array.
		for (int i = 0; i < BoggleImplementation.dictionary.length; i++){
			if (checkWord.equalsIgnoreCase(BoggleImplementation.dictionary[i])){
				check = true;
				break;
			}
		}
		return check;
	}

}
