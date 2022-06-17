import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

	private static DBConnection instance;
	private static Connection connect = null;

	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	private DBConnection() {
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
			st = connect.createStatement();
			rs = st.executeQuery("use csci3901");
		}catch( Exception e){
			e.printStackTrace();
		}
		
		
		/*try {
			connect = DriverManager.getConnection("jdbc:mysql://db-5308.cs.dal.ca:3306/CSCI5308_1_DEVINT",
					"CSCI5308_1_DEVINT_USER", "piWai3foh6iechee");
			if (connect == null) {
				connect.close();
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}*/
	}

	public ResultSet readData(String query) throws SQLException {
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

	public static void stop() throws SQLException {
		connect.close();
	}

}