import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Class for database utilities
 */
public class DBUtil {

	//Instance of the class
	private static DBUtil instance;
	//Connection object
	private static Connection connect = null;

	/*
	 * Method to fetch the instance of the class to achieve singleton design pattern.
	 */
	public static DBUtil getInstance() {
		if (instance == null) {
			instance = new DBUtil();
		}
		return instance;
	}

	//Constructor to set the database connection
	private DBUtil() {
		connect = null;
		
		try {

			//load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//create the connection
			String  user = "rspatel";
			String password = "B00886157";
			String url = "jdbc:Mysql://db.cs.dal.ca:3306";
			
			//Calling the getConnection method
			connect = DriverManager.getConnection(url, user, password);
			if (connect == null) {
				connect.close();
				return;
			}
			Statement st = null;
			ResultSet rs = null;
			//selecting the database
			st = connect.createStatement();
			rs = st.executeQuery("use rspatel");
		}catch( Exception e){
			e.printStackTrace();
		}
	}

	/*
	 * Method to read data by executing sql query from the database
	 */
	public ResultSet readData(String query) throws SQLException {
		synchronized (connect) {
			try {
				// Read the data from the table
				PreparedStatement ps = connect.prepareStatement(query);
				//executing the query
				ResultSet rs = ps.executeQuery();
				if (rs == null) {
					connect.close();
					return null;
				}
				return rs;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/*
	 * Method to update data by executing sql query in the database
	 */
	public boolean updateData(String query) throws SQLException {
		synchronized (connect) {
			// Update the data in the table
			try {

				PreparedStatement ps = connect.prepareStatement(query);
				ps.executeUpdate();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}

		}
	}

	/*
	 * Method to close the connection
	 */
	public static void stop() throws SQLException {
		connect.close();
	}

}