import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		//Scanner for user input
		Scanner sc = new Scanner(System.in);
		//Variables for starting date,ending date and filepath
		String startingDate;
		String endingDate;
		String filePath;
		//Creating DataExport object
		DataExport data = new DataExport();
		//user inputs
		System.out.println("Please enter the starting date for the period (Format :  yyyy-mm-dd)");
		startingDate = sc.next();
		System.out.println("Please enter the ending date for the period (Format : yyyy-mm-dd)");
		endingDate = sc.next();
		System.out.println("Please enter full path of the output file with filename");
		filePath = sc.next();
		//Processing the data to write it in a xml file
		data.processData(startingDate,endingDate,filePath);
		
		try {
			//Closing the database conection
			DBConnection.stop();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
