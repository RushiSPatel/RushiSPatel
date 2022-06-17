import java.io.BufferedReader;
import java.util.List;
/*
 * Interface with method signatures which are used to solve the boggle puzzle game.
 */
public interface Boggle {
	/*
	 * Method signature to load the dictionary.
	 */
	boolean getDictionary(BufferedReader stream);
	
	/*
	 * Method signature to load the puzzle used for the game.
	 */
	boolean getPuzzle(BufferedReader stream);
	
	/*
	 * Method signature to solve the boggle puzzle game.
	 */
	List<String> solve();
	
	/*
	 * Method signature to print the boggle puzzle grid.
	 */
	String print();
}
