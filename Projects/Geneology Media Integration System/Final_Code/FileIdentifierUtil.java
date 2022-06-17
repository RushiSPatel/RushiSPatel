import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rushi Class for all the media file utilities.
 */
public class FileIdentifierUtil implements Media {
	// List that would store all the media file objects.
	static List<FileIdentifier> mediaList = new ArrayList<>();

	/**
	 * @author Rushi
	 * @param String
	 *            File location
	 * @return FileIdentifier
	 * @description Method to add media file in the database
	 */
	@Override
	public FileIdentifier addMediaFile(String fileLocation) {
		// Input validation check for bad data.
		if (fileLocation == null || fileLocation.isEmpty() || fileLocation == "" || fileLocation == " ") {
			try {
				throw new GeneologyException("File location object cannot be null or empty!");
			} catch (GeneologyException e) {
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Creating a media file object.
		FileIdentifier mediaFile = new FileIdentifier();
		// formatting the location so that mysql can accept the string in the
		// given format.
		String updatedFileLocation = fileLocation.replace("\\", "\\\\");
		// query to fetch the id of the last media file in the database.
		String fertchLastId = String.format("select id from mediaarchive ORDER BY id DESC LIMIT 1");

		try {
			// Fetching the id from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fertchLastId);
			// Initializing the last id variable to 0.
			int lastID = 0;
			// Storing the id in the variable.
			while (rs.next()) {
				lastID = rs.getInt("id");
			}
			// Query to update the database with id and file name.
			String sqlQuery = String.format("insert into MediaArchive (id,fileName) values(%d,\"%s\")", lastID + 1,
					updatedFileLocation);
			// Updating the database.
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);

			if (checkStatus) {
				// setting the filename and id of the media file object.
				mediaFile.setFileName(fileLocation);
				mediaFile.setId(lastID + 1);
				// adding the object to the media file list.
				mediaList.add(mediaFile);
				System.out.println("File added as media in the system!");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return mediaFile;
	}

	/**
	 * @author Rushi
	 * @param FileIdentifier
	 *            fileIdentifier, Map<String, String> attributes
	 * @return Boolean
	 * @description Method to record media attributes in the database.
	 */
	@Override
	public Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) {
		// Initializing the variable to false.
		Boolean check = false;
		// Input validation for bad input.
		if (fileIdentifier == null) {
			try {
				throw new GeneologyException("Media object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation for bad input.
		if (attributes == null) {
			try {
				throw new GeneologyException("Attributes are null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Query to fetch filename and date from the database.
		String fetchFiles = "select fileName,dateOfMedia from mediaarchive";

		// Creating lists to store the filenames and dates from the database.
		List<String> fileNameList = new ArrayList<>();
		List<String> dateList = new ArrayList<>();

		try {
			// Fetching file information from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchFiles);
			// Adding the information in the list.
			while (rs.next()) {
				fileNameList.add(rs.getString("fileName"));
				dateList.add(rs.getString("dateOfMedia"));
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		// Validating if the file is present in the database or not.
		if (!fileNameList.contains(fileIdentifier.getFileName())) {
			System.out.println("This file is not added in the database.");
			return null;
		}
		// Validating if the date is null.
		for (String string : dateList) {
			if (string == null) {
				// Setting date in an acceptable format.
				int index = dateList.indexOf(string);
				dateList.set(index, "0000-00-00");
			}
		}

		// Query to update the database.
		String sqlQuery = new String();
		sqlQuery = "update MediaArchive set ";

		// Iterating through map entryset to set the query as per the
		// attributes.
		for (Map.Entry<String, String> entry : attributes.entrySet()) {
			// Variables for key and value.
			String key = entry.getKey();
			String value = entry.getValue();

			// Variable to keep track of dates from the list.
			int flag = 0;
			for (String fileName : fileNameList) {
				// Fetching date from the list.
				String date = dateList.get(flag);
				// Check if the filename matches from the list.
				if (fileIdentifier.getFileName().equals(fileName)) {
					// Check if the attribute is year.
					if (key.equalsIgnoreCase("year")) {
						// Separating year, month and day.
						String[] finalDate = date.split("-");
						finalDate[0] = value;
						// Setting the string again by inputting year
						String sqlValue = String.join("-", finalDate);

						// Validating if the date already exists in the query
						// string.
						if (sqlQuery.contains("dateOfMedia")) {
							// Formatting the date to set the year in the
							// string.
							String[] replace = sqlQuery.split("= \"");
							replace[1] = replace[1].replace("\",", "");
							replace[1] = String.format("\"%s\",", sqlValue);
							sqlQuery = String.join("= ", replace);

						} else {
							// Appending the query with the year value.
							sqlQuery = sqlQuery + String.format("dateOfMedia = \"%s\",", sqlValue);
						}
						dateList.set(flag, sqlValue);

					}
					// Validating if the attribute is date.
					if (key.equalsIgnoreCase("date")) {
						// Separating year, month and day.
						String[] finalDate = date.split("-");
						// Separating month and day.
						String[] monthDate = value.split("-");
						String sqlValue;
						// Validate if the value has partial date without year.
						if (monthDate[0].length() != 4) {
							System.out.println("Cannot enter date with just the day or day and month!");
							return false;
						}
						// Validating and setting the date based on year, month
						// and day.
						if (monthDate.length == 1) {
							finalDate[0] = monthDate[0];

							sqlValue = String.join(",", finalDate);
						} else if (monthDate.length == 3) {
							finalDate = monthDate;
							sqlValue = String.join("-", finalDate);
						} else {
							finalDate[0] = monthDate[0];
							finalDate[1] = monthDate[1];
							sqlValue = String.join("-", finalDate);
						}
						// Validating if the date already exists in the query
						// string.
						if (sqlQuery.contains("dateOfMedia")) {

							String[] replace = sqlQuery.split("= \"");
							replace[1] = replace[1].replace("\",", "");
							replace[1] = String.format("\"%s\",", sqlValue);
							sqlQuery = String.join("= ", replace);

						}
						// Appending to the query the value for date.
						else {
							sqlQuery = sqlQuery + String.format("dateOfMedia = \"%s\",", sqlValue);
						}
						dateList.set(flag, sqlValue);
						// fileIdentity.setDate(sqlValue);
					}
					// Validate if the attribute is the location or city
					if (key == "city" || key == "location" || key.equalsIgnoreCase("location")
							|| key.equalsIgnoreCase("city")) {
						// Appending to the query to set the location.
						sqlQuery = sqlQuery + String.format("location = \"%s\",", value);
						// fileIdentity.setLocation(value);
					}

				}
				// incrementing flag value.
				flag++;
			}

		}
		// Removing the last comma in the query.
		sqlQuery = PersonIdentityUtil.removeLastChar(sqlQuery);
		// Formatting the query which could e accepted by mysql.
		String fileNameForQuery = fileIdentifier.getFileName().replace("\\", "\\\\");
		// Appending the query to set the where clause.
		sqlQuery += String.format(" where fileName = \"%s\";", fileNameForQuery);

		// Updating the attributes for the file in the database based on the
		// query.
		try {
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if (checkStatus) {
				System.out.println("Attributes added for : " + fileIdentifier.getFileName());
				check = true;
				return true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param FileIdentifier
	 *            fileIdentifier, List<PersonIdentity> people
	 * @return Boolean
	 * @description Method to record all the people in database which are
	 *              existing in the file.
	 */
	@Override
	public Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) {
		Boolean check = false;
		// Input validation to check for bad input.
		if (fileIdentifier == null) {

			try {
				throw new GeneologyException("Media object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}

			return false;
		}
		// Input validation to check for bad input.
		if (people == null) {

			try {
				throw new GeneologyException("People list cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Query to fetch the filename, date from the database.
		String fetchFiles = "select fileName,dateOfMedia from mediaarchive";
		// Lists to store file names and dates.
		List<String> fileNameList = new ArrayList<>();
		List<String> dateList = new ArrayList<>();
		// Fetching the file names and dates from the database.
		try {
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchFiles);

			while (rs.next()) {
				// Storing the information in list.
				fileNameList.add(rs.getString("fileName"));
				dateList.add(rs.getString("dateOfMedia"));
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		// Validating if the given file exists in the database or not.
		if (!fileNameList.contains(fileIdentifier.getFileName())) {
			System.out.println("This file is not added in the database.");
			return null;
		}
		// Iterating through all the people to add them in the database.
		for (PersonIdentity person : people) {
			// Formatting the filename which could be accepted by mysql.
			String fileNameForQuery = fileIdentifier.getFileName().replace("\\", "\\\\");
			// Query to fetch date from the database.
			String fetchDate = String.format("select dateOfMedia from mediaarchive where fileName = \"%s\"",
					fileNameForQuery);

			String date = new String();
			try {
				// Fetching the date from the database.
				ResultSet rs = DBConnectionUtil.getInstance().readData(fetchDate);

				while (rs.next()) {
					date = rs.getString("dateOfMedia");
				}
				// Query to insert the information into the database.
				String sqlQuery = String.format(
						"insert into MediaPeople (id,fileName, individualName, dateOfMedia) values(%d,\"%s\", \"%s\", \"%s\")",
						fileIdentifier.getId(), fileIdentifier.getFileName(), person.getName(), date);
				// Inserting th information into the database.
				boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
				if (checkStatus) {
					// List to store all the people in media.
					List<PersonIdentity> peopleInMedia = new ArrayList<>();
					// Adding all the people in the list.
					peopleInMedia.add(person);
					// Adding all the people in media in the list.
					fileIdentifier.getPeopleInMedia().addAll(peopleInMedia);
					// Setting the return value as true.
					check = true;
					System.out.println(person.getName() + " added as person in media!");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param FileIdentifier
	 *            fileIdentifier, String tag
	 * @return Boolean
	 * @description Method to add a tag to the media file.
	 */
	@Override
	public Boolean tagMedia(FileIdentifier fileIdentifier, String tag) {
		Boolean check = false;
		// Input validation to check for bad input.
		if (fileIdentifier == null) {

			try {
				throw new GeneologyException("Media object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}

			return false;
		}
		// Input validation to check for bad input.
		if (tag == null) {

			try {
				throw new GeneologyException("Tag object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}

			return false;
		}
		// Query to fetch the file information.
		String fetchFiles = "select fileName,dateOfMedia from mediaarchive";
		// List to store file names and dates.
		List<String> fileNameList = new ArrayList<>();
		List<String> dateList = new ArrayList<>();

		try {
			// Fetching the file information from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchFiles);

			while (rs.next()) {
				// Storing the data in the lists.
				fileNameList.add(rs.getString("fileName"));
				dateList.add(rs.getString("dateOfMedia"));
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		// Input validation to check for bad input.
		if (!fileNameList.contains(fileIdentifier.getFileName())) {
			System.out.println("This file is not added in the database.");
			return null;
		}
		// Formatting the file name which could be accepted by mysql.
		String fileNameForQuery = fileIdentifier.getFileName().replace("\\", "\\\\");

		// Query to fetch date from the database.
		String fetchDate = String.format("select dateOfMedia from MediaArchive where fileName = \"%s\"",
				fileNameForQuery);

		try {
			// Fetching date from the database.
			ResultSet dateResult = DBConnectionUtil.getInstance().readData(fetchDate);

			String date = new String();

			while (dateResult.next()) {
				date = dateResult.getString("dateOfMedia");
			}

			// Query to insert file information in the database.
			String sqlQuery = String.format(
					"insert into MediaTag (id,fileName, tag,dateOfMedia) values(%d,\"%s\", \"%s\", \"%s\")",
					fileIdentifier.getId(), fileNameForQuery, tag, date);
			// Updating the database based on the query.
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if (checkStatus) {
				// Creating a list to store tags.
				List<String> tags = new ArrayList<>();
				// Storing tag in the list.
				tags.add(tag);
				// Adding the tags to the existing tags.
				fileIdentifier.getTags().addAll(tags);

				System.out.println(tag + " recorded for the media!");
				// Setting the return variable value as true.
				check = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return check;
	}

}
