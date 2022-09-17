

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/*
 * Class to create a database connection with utilities to read or update queries.
 * Citation of my own work from first term to connect with the database. 
 * Done by Rushi Samirbhai Patel
 */
public class DBConnectionUtilGCP {

	// instance variable of the class to follow singleton design pattern.
	private static DBConnectionUtilGCP instance;
	
	public static Connection connect = null;



	/*
	 * Method to get the instance of the class which can be used throughout the
	 * program.
	 */
	public static DBConnectionUtilGCP getInstance() {
		if (instance == null) {
			instance = new DBConnectionUtilGCP();
		}
		return instance;
	}

	/*
	 * Constructor of the class where the connection is created.
	 */
	public DBConnectionUtilGCP() {
		connect = null;
		Properties properties = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream("config.properties");
			properties.load(input);
			
			// load the driver
			Class.forName(properties.getProperty("driver_gcp"));

			// create the connection
			String user = properties.getProperty("user_gcp");
			String password = properties.getProperty("password_gcp");
			String url = properties.getProperty("url_gcp");

			connect = DriverManager.getConnection(url, user, password);
			if (connect == null) {
				connect.close();
				return;
			}
			Statement st = null;
			ResultSet rs = null;
			st = connect.createStatement();
			rs = st.executeQuery(properties.getProperty("use_statement_gcp"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Method for all the read queries.
	 */
	public ResultSet readData(String query) throws SQLException {
		// Keeping the connection synchronized.
		synchronized (connect) {
			if(query == null || query.isEmpty()){
				System.out.println("Query not present!");
				return null;
			}
			try {
				// Read the data from the table
				PreparedStatement ps = connect.prepareStatement(query);
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
	 * Method for all the update queries.
	 */
	public boolean updateData(String query) throws SQLException {
		// Keeping the connection synchronized.
		synchronized (connect) {
			if(query == null || query.isEmpty()){
				System.out.println("Query not present!");
				return false;
			}
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
	 * Method to close the connection.
	 */
	public boolean stop() throws SQLException {
		connect.close();
		if(connect.isClosed())
		return false;
		else
			return true;
	}

}