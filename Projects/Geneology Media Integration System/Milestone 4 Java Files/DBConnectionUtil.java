import java.sql.*;

public class DBConnectionUtil {

	private static DBConnectionUtil instance;
	private static Connection connect = null;

	public static DBConnectionUtil getInstance() {
		if (instance == null) {
			instance = new DBConnectionUtil();
		}
		return instance;
	}

	private DBConnectionUtil() {
		connect = null;
		
		try {

			//load the driver
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//create the connection
			String  user = "root";
			String password = "RUS09rus.";
			String url = "jdbc:Mysql://127.0.0.1";
			
		//	String url = "jdbc:Mysql://db.cs.dal.ca:3306";

			connect = DriverManager.getConnection(url, user, password);
			if (connect == null) {
				connect.close();
				return;
			}
			Statement st = null;
			ResultSet rs = null;
			st = connect.createStatement();
			rs = st.executeQuery("use finalproject");
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

	public void stop() throws SQLException {
		connect.close();
	}

}