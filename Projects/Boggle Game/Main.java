import java.io.BufferedReader;
import java.io.FileReader;

public class Main {

	public static void main(String[] args){
		
		BoggleImplementation boggleImplementation = new BoggleImplementation();
		try{
			BufferedReader stream = new BufferedReader(new FileReader("D:\\Assignment4\\Dictionary.txt"));
			boggleImplementation.getDictionary(stream);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		
		try{
			BufferedReader stream = new BufferedReader(new FileReader("D:\\Assignment4\\Puzzle.txt"));
			boggleImplementation.getPuzzle(stream);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		boggleImplementation.print();
		
		boggleImplementation.solve();
	}
}