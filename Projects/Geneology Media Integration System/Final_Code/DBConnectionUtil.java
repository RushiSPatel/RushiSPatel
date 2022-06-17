import java.sql.*;

/*
 * Class to create a database connection with utilities to read or update queries.
 */
public class DBConnectionUtil {

	// instance variable of the class to follow singleton design pattern.
	private static DBConnectionUtil instance;
	// connection object and initializing it to null.
	private static Connection connect = null;

	/*
	 * Method to get the instance of the class which can be used throughout the
	 * program.
	 */
	public static DBConnectionUtil getInstance() {
		if (instance == null) {
			instance = new DBConnectionUtil();
		}
		return instance;
	}

	/*
	 * Constructor of the class where the connection is created.
	 */
	private DBConnectionUtil() {
		connect = null;

		try {

			// load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// create the connection
			String user = "root";
			String password = "RUS09rus.";
			String url = "jdbc:Mysql://127.0.0.1";

			connect = DriverManager.getConnection(url, user, password);
			if (connect == null) {
				connect.close();
				return;
			}
			Statement st = null;
			ResultSet rs = null;
			st = connect.createStatement();
			rs = st.executeQuery("use finalproject");
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
	public void stop() throws SQLException {
		connect.close();
	}

}