import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Rushi
 * @description This class acts as a utility class to perform all the operations
 *              on a person or group of people.
 *
 */
public class PersonIdentityUtil implements Person {

	// People list for the entire program.
	static List<PersonIdentity> peopleList = new ArrayList<>();

	/**
	 * @author Rushi
	 * @param String
	 *            name
	 * @return PersonIdentity
	 * @description Method to add a person in the database.
	 */
	public PersonIdentity addPerson(String name) {
		// Input validation for bad inputs.
		if (name == null || name.isEmpty()) {
			try {
				throw new GeneologyException("Name cannot be null or empty!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Person identity instance.
		PersonIdentity person = new PersonIdentity();
		// Query to fetch last id from the database.
		String fertchLastId = String.format("select id from FamilyTree ORDER BY id DESC LIMIT 1");

		try {
			// Fetching the last id from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fertchLastId);
			int lastID = 0;
			while (rs.next()) {
				lastID = rs.getInt("id");
			}

			// Query to insert the data into the database.
			String sqlQuery = String.format("insert into FamilyTree (id,individualName) values(%d,\"%s\")", lastID + 1,
					name);

			// Inserting person data into the database.
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			// Setting the person identity variables and adding the instance to
			// the people list.
			if (checkStatus) {
				System.out.println(name + " added as person in the system!");
				person.setName(name);
				person.setId(lastID + 1);
				peopleList.add(person);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return person;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person, Map<String, String> attributes
	 * @return Boolean
	 * @description Method to record attributes of a person in the database.
	 */
	public Boolean recordAttributes(PersonIdentity person, Map<String, String> attributes) {
		// Input validation for bad inputs.
		if (person == null) {
			try {
				throw new GeneologyException("Person object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation for bad inputs.
		if (attributes == null) {
			try {
				throw new GeneologyException("Attributes cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}

		Boolean check = false;
		// Possible inputs for a specified attribute.
		Map<String, List<String>> possibleAttributeValues = new HashMap<String, List<String>>();
		// Possible date of birth values
		List<String> possibleBirthDateValues = new ArrayList<>();
		possibleBirthDateValues.add("date of birth");
		possibleBirthDateValues.add("dob");
		possibleBirthDateValues.add("Date of Birth");
		possibleBirthDateValues.add("DOB");
		possibleBirthDateValues.add("date_of_birth");
		possibleBirthDateValues.add("birthdate");
		possibleBirthDateValues.add("birthday");
		possibleBirthDateValues.add("dateOfBirth");

		possibleAttributeValues.put("dateOfBirth", possibleBirthDateValues);

		// Possible location of birth values
		List<String> possibleBirthLocationValues = new ArrayList<>();
		possibleBirthLocationValues.add("locationOfBirth");
		possibleBirthLocationValues.add("birthLocation");
		possibleBirthLocationValues.add("location_of_birth");

		possibleAttributeValues.put("locationOfBirth", possibleBirthLocationValues);

		// Possible date of death values
		List<String> possibleDeathDateValues = new ArrayList<>();
		possibleDeathDateValues.add("date of death");

		possibleDeathDateValues.add("date_of_death");
		possibleDeathDateValues.add("deathdate");

		possibleDeathDateValues.add("dateOfDeath");

		possibleAttributeValues.put("dateOfDeath", possibleDeathDateValues);

		// Possible loation of death values
		List<String> possibleDeathLocationValues = new ArrayList<>();
		possibleDeathLocationValues.add("locationOfDeath");
		possibleDeathLocationValues.add("deathLocation");
		possibleDeathLocationValues.add("location_of_death");

		possibleAttributeValues.put("locationOfDeath", possibleDeathLocationValues);

		List<String> possibleGenderValues = new ArrayList<>();
		possibleGenderValues.add("gender");
		possibleGenderValues.add("Gender");
		possibleGenderValues.add("GENDER");
		possibleAttributeValues.put("gender", possibleGenderValues);

		List<String> possibleOccupationValues = new ArrayList<>();
		possibleOccupationValues.add("occupation");
		possibleOccupationValues.add("Occupation");
		possibleOccupationValues.add("OCCUPATION");
		possibleAttributeValues.put("occupation", possibleOccupationValues);

		// Query to fetch people data from the database.
		String fetchPeopleValues = "select individualName, dateOfBirth, dateOfDeath from familytree";
		// People name list.
		List<String> peopleNameList = new ArrayList<>();
		// Date of birth list.
		List<String> dateOfBirthList = new ArrayList<>();
		// Date of death list.
		List<String> dateOfDeathList = new ArrayList<>();

		try {
			// Fetching people data from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPeopleValues);

			// Adding the data to the lists.
			while (rs.next()) {
				peopleNameList.add(rs.getString("individualName"));
				dateOfBirthList.add(rs.getString("dateOfBirth"));
				dateOfDeathList.add(rs.getString("dateOfDeath"));
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		// Validating if the person is added in the database.
		if (!peopleNameList.contains(person.getName())) {
			System.out.println("This person is not added in the database.");
			return false;
		}

		// Iterating through date of birth list to check null values and
		// formatting the string if found.
		for (String string : dateOfBirthList) {
			if (string == null) {
				int index = dateOfBirthList.indexOf(string);
				dateOfBirthList.set(index, "0000-00-00");
			}
		}

		// Iterating through date of death list to check null values and
		// formatting the string if found.
		for (String string : dateOfDeathList) {
			if (string == null) {
				int index = dateOfDeathList.indexOf(string);
				dateOfDeathList.set(index, "0000-00-00");
			}
		}

		String sqlQuery = new String();
		// Partial query to update the database
		sqlQuery = "update FamilyTree set ";
		for (Map.Entry<String, String> entry : attributes.entrySet()) {

			String key = entry.getKey();
			String value = entry.getValue();

			// Iterating through the possible attribute values and check if the
			// possible value match the input.
			for (Map.Entry<String, List<String>> possibleEntryValues : possibleAttributeValues.entrySet()) {

				String columnNameKey = possibleEntryValues.getKey();
				List<String> possibleNames = possibleEntryValues.getValue();

				// Verifying if the possible names has the input.
				if (possibleNames.contains(key)) {

					// Validating attribute name
					if (columnNameKey.equals("dateOfBirth")) {
						// Splitting year, month and date to check if the
						// partial date contains year.
						String[] dateValue = value.split("-");
						if (dateValue.length < 3) {
							if (dateValue[0].length() != 4) {
								System.out.println("Cannot store date without year!");
								return false;
							}
							// Formatting the date based on the input.
							value += "-00";
						}

					}
					// Validating attribute name
					if (columnNameKey.equals("dateOfDeath")) {
						// Splitting year, month and date to check if the
						// partial date contains year.
						String[] dateValue = value.split("-");
						if (dateValue.length < 3) {
							if (dateValue[1].length() != 4) {
								System.out.println("Cannot store date without year!");
								return false;
							}
						}
					}
					// Appending the query with the attribute and values.
					sqlQuery = sqlQuery + String.format("%s = \"%s\",", columnNameKey, value);

				}

			}

		}

		// Removing comma from the query.
		sqlQuery = removeLastChar(sqlQuery);

		// Completing the query.
		sqlQuery += String.format("where individualName = \"%s\"", person.getName());

		try {
			// Updating the database with the formed query (recording
			// attributes).
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			if (checkStatus) {
				System.out.println("Attributes added for : " + person.getName());
				return true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person, String reference
	 * @return Boolean
	 * @description Method to record reference of the person in the database.
	 */
	public Boolean recordReference(PersonIdentity person, String reference) {
		Boolean check = false;
		// Input validation for bad inputs.
		if (person == null) {

			try {
				throw new GeneologyException("Person object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation for bad inputs.
		if (reference == null) {

			try {
				throw new GeneologyException("Reference cannot be null!");
			} catch (GeneologyException e) {

				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}

		// Query to fetch individual name from the database.
		String fetchPeopleValues = "select individualName from familytree";
		// People name list.
		List<String> peopleNameList = new ArrayList<>();

		try {
			// Fetching individual name from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPeopleValues);

			// Adding the names in the list.
			while (rs.next()) {
				peopleNameList.add(rs.getString("individualName"));

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		// Validating if the given person exist in the database.
		if (!peopleNameList.contains(person.getName())) {
			System.out.println("This person is not added in the database.");
			return false;
		}

		String sqlQuery = new String();

		// Query to insert references of individual to the database.
		sqlQuery = String.format(
				"insert into ReferencesOfIndividual (id,individualName,referencesToSourceMaterial) values (%d,\"%s\", \"%s\")",
				person.getId(), person.getName(), reference);

		try {
			// Inserting references of individual to the database.
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			// Setting references to the variable of the person identity
			// variable value.
			if (checkStatus) {
				System.out.println("References added for : " + person.getName());
				if (person.getReferences() == null) {
					List<String> newReference = new ArrayList<String>();
					newReference.add(reference);
					person.setReferences(newReference);
				} else {
					person.getReferences().add(reference);
				}
				return true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person, String note
	 * @return Boolean
	 * @description Method to record notes of a person
	 */
	public Boolean recordNote(PersonIdentity person, String note) {
		Boolean check = false;
		// Input validation for bad inputs.
		if (person == null) {
			try {
				throw new GeneologyException("Person object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation for bad inputs.
		if (note == null) {

			try {
				throw new GeneologyException("Note cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}

		// Query to fetch individual name from the database.
		String fetchPeopleValues = "select individualName from familytree";
		// People name list.
		List<String> peopleNameList = new ArrayList<>();

		try {
			// Fetching individual name from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPeopleValues);

			// Adding the names to the list.
			while (rs.next()) {
				peopleNameList.add(rs.getString("individualName"));

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		// Validating if the person exist in the database.
		if (!peopleNameList.contains(person.getName())) {
			System.out.println("This person is not added in the database.");
			return false;
		}

		String sqlQuery = new String();
		// Query to insert notes of the person in the database.
		sqlQuery = String.format("insert into NotesOfIndividual (id,individualName,notes) values (%d,\"%s\", \"%s\")",
				person.getId(), person.getName(), note);

		try {
			// Inserting notes of the person in the database.
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);

			// Adding note to the person instance variable value.
			if (checkStatus) {
				System.out.println("Note added for : " + person.getName());

				if (person.getNotes() == null) {
					List<String> newNote = new ArrayList<String>();
					newNote.add(note);
					person.setNotes(newNote);
				} else {
					person.getNotes().add(note);
				}

				return true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            parent,PersonIdentity child
	 * @return Boolean
	 * @description Method to record child of a person.
	 */
	public Boolean recordChild(PersonIdentity parent, PersonIdentity child) {
		Boolean check = false;
		// Input validation for bad inputs.
		if (parent == null) {
			try {
				throw new GeneologyException("Parent object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation for bad inputs.
		if (child == null) {

			try {
				throw new GeneologyException("Child object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation if parent is the same as child.
		if (parent.equals(child)) {

			try {
				throw new GeneologyException("Both Parent and Child cannot be same!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}

		// Validating Child birth date less than parents birth date.
		try {
			SimpleDateFormat DateFormat = new SimpleDateFormat("YYYY-MM-DD");
			if (child.getDateOfBirth() == null || parent.getDateOfBirth() == null) {

			} else {

				Date d1 = DateFormat.parse(child.getDateOfBirth());
				Date d2 = DateFormat.parse(parent.getDateOfBirth());
				if (d1.compareTo(d2) < 0) {
					System.out.println("Child date of birth cannot be greater than perents date of birth");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String fetchNumberOfParents = String.format("select distinct parent from children where child=%d;",
				child.getId());

		List<Integer> parentList = new ArrayList<>();

		try {
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchNumberOfParents);
			while (rs.next()) {
				parentList.add(rs.getInt("parent"));
			}

			if (!(parentList.size() == 0 || parentList.size() == 1 || parentList.size() == 2)) {
				try {
					throw new GeneologyException("A child can have at max two parents. Cant record this relation!");
				} catch (GeneologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					e.getMessage();
				}
				return false;
			}
			if (parentList.contains(parent.getId())) {

				try {
					throw new GeneologyException("Alreday recorded parent child relation.");
				} catch (GeneologyException e) {

					e.printStackTrace();
					e.getMessage();
				}
				return false;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		// Query to fetch the partner of the person.
		String getPartner = String.format("select partner from FamilyTree where individualName = \"%s\"",
				parent.getName());

		String partner = new String();
		try {
			// Fetching the partner of the person from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(getPartner);
			String partnerOfIndividual = new String();
			while (rs.next()) {
				if (rs.wasNull()) {
					System.out.println("");
				}
				partnerOfIndividual = rs.getString("partner");

			}

			if (partnerOfIndividual == null || partnerOfIndividual == "") {
				System.out.println("Partner of the person is null or empty");
			} else {

				// Validating if the partner can be child.
				if (child.getName().equalsIgnoreCase(partnerOfIndividual)) {
					System.out.println("A partner cannot be a child!");
					return false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Query to fetch the individual name from the database.
		String fetchPeopleValues = "select individualName from familytree";
		// People name list.
		List<String> peopleNameList = new ArrayList<>();

		try {
			// Fetching the individual name from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPeopleValues);

			// Adding names to the list.
			while (rs.next()) {
				peopleNameList.add(rs.getString("individualName"));

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		// Validating if the person exist in the database.
		if (!peopleNameList.contains(parent.getName())) {
			System.out.println("This person is not added in the database.");
			return false;
		}
		// Validating if the child exist in the database.
		if (!peopleNameList.contains(child.getName())) {
			System.out.println("Child" + child.getName() + " is not added in the database as a person");
			return false;
		}

		String sqlQuery = new String();
		// Query to insert child parent data in the database.
		sqlQuery = String.format("insert into Children (parent,child) values (\"%d\", \"%d\")", parent.getId(),
				child.getId());

		try {
			// Inserting child parent data in the database.
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);

			// Adding the child data in the variable of the instance.
			if (checkStatus) {
				System.out.println("Recorded child for " + parent.getName());

				if (parent.getChildren() == null) {
					List<PersonIdentity> newChild = new ArrayList<PersonIdentity>();
					newChild.add(child);
					parent.setChildren(newChild);
				} else {
					parent.getChildren().add(child);
				}

				return true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            partner1, PersonIdentity partner2
	 * @return Boolean
	 * @description Method to record partners in the database.
	 */
	public Boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2) {
		Boolean check = false;

		// Input validation for bad inputs.
		if (partner1 == null) {

			try {
				throw new GeneologyException("Partner1 object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation for bad inputs.
		if (partner2 == null) {

			try {
				throw new GeneologyException("Partner2 object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Validation to check if both partners are the same person.
		if (partner1.equals(partner2)) {
			try {
				throw new GeneologyException("Both Partners cannot be same!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Query to fetch individual name from database.
		String fetchPeopleValues = "select individualName from familytree";
		// People name list.
		List<String> peopleNameList = new ArrayList<>();

		try {
			// Fetching individual name from database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPeopleValues);

			// Adding names to the list.
			while (rs.next()) {
				peopleNameList.add(rs.getString("individualName"));

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		// Validating if both partners exist in the database.
		if (!peopleNameList.contains(partner1.getName()) || !peopleNameList.contains(partner2.getName())) {
			System.out.println("Partner is not added in the database as a person");
			return false;
		}
		String sqlQuery = new String();
		String sqlQuery1 = new String();

		String updateDissolutionQuery = new String();
		String updateDissolutionQuery1 = new String();

		// Query to update partner1 in the database.
		sqlQuery = String.format("update FamilyTree set partner = \"%s\" where individualName = \"%s\"",
				partner2.getName(), partner1.getName());
		// Query to update partner2 in the database.
		sqlQuery1 = String.format("update FamilyTree set partner = \"%s\" where individualName = \"%s\"",
				partner1.getName(), partner2.getName());

		// Query to update dissolution partner1 in the database.
		updateDissolutionQuery = String.format(
				"update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"", null,
				partner1.getName(), partner2.getName());
		// Query to update dissolution partner2 in the database.
		updateDissolutionQuery1 = String.format(
				"update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"", null,
				partner2.getName(), partner1.getName());

		try {
			// Updating partner1 in the database.
			boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
			// Updating partner2 in the database.
			boolean checkStatus1 = DBConnectionUtil.getInstance().updateData(sqlQuery1);
			// Updating dissolution partner1 in the database.
			boolean checkStatus2 = DBConnectionUtil.getInstance().updateData(updateDissolutionQuery);
			// Updating dissolution partner2 in the database.
			boolean checkStatus3 = DBConnectionUtil.getInstance().updateData(updateDissolutionQuery1);

			// Setting the partner and dissolution data of the partner
			// instances.
			if (checkStatus && checkStatus1 && checkStatus2 && checkStatus3) {
				System.out.println("Partnering recorded for " + partner1.getName() + " and " + partner2.getName());
				partner1.setPartner(partner2);
				partner2.setPartner(partner1);
				partner1.setDissolution(null);
				partner2.setDissolution(null);
				check = true;
			}

			String insertPartnering = String.format("insert into partnering (partner1, partner2) values (%d,%d)",
					partner1.getId(), partner2.getId());

			boolean checkPartnering = DBConnectionUtil.getInstance().updateData(insertPartnering);

			if (checkPartnering) {
				check = true;
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            partner1, PersonIdentity partner2
	 * @return Boolean
	 * @description Method to record dissolution between two partners.
	 */
	@Override
	public Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2) {
		Boolean check = false;
		// Input validation for bad inputs.
		if (partner1 == null) {
			try {
				throw new GeneologyException("Partner1 object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Input validation for bad inputs.
		if (partner2 == null) {

			try {
				throw new GeneologyException("Partner2 object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}

		// Validation for two partners if they are same.
		if (partner1.equals(partner2)) {
			try {
				throw new GeneologyException("Both Partners cannot be same!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return false;
		}
		// Query to fetch individual name from database.
		String fetchPeopleValues = "select individualName from familytree";
		// People name list.
		List<String> peopleNameList = new ArrayList<>();

		try {
			// Fetching individual name from database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPeopleValues);

			// Adding names to the list.
			while (rs.next()) {
				peopleNameList.add(rs.getString("individualName"));

			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		// Validating both partners exist in the database.
		if (!peopleNameList.contains(partner1.getName()) || !peopleNameList.contains(partner2.getName())) {
			System.out.println("Partner is not added in the database as a person");
			return false;
		}

		// Validating if the names match.
		if (partner1.getPartner().getName().equals(partner2.getName())
				&& partner2.getPartner().getName().equals(partner1.getName())) {
			String sqlQuery = new String();
			String sqlQuery1 = new String();
			String sqlQueryUpdatePartner = new String();
			String sqlQueryUpdatePartner2 = new String();

			String yes = "yes";

			// Query to update the dissolution in the database partner 1.
			sqlQuery = String.format(
					"update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"",
					yes, partner1.getName(), partner2.getName());
			// Query to update the dissolution in the database partner 2.
			sqlQuery1 = String.format(
					"update FamilyTree set dissolution = \"%s\" where individualName = \"%s\" and partner = \"%s\"",
					yes, partner2.getName(), partner1.getName());
			// Query to update the pertner1 in the database partner 1.
			sqlQueryUpdatePartner = String.format(
					"update FamilyTree set partner = \"%s\" where individualName = \"%s\"", null, partner1.getName());
			// Query to update the pertners2 in the database partner 1.
			sqlQueryUpdatePartner2 = String.format(
					"update FamilyTree set partner = \"%s\" where individualName = \"%s\"", null, partner2.getName());

			try {
				// Updating the dissolution in the database partner 1.
				boolean checkStatus = DBConnectionUtil.getInstance().updateData(sqlQuery);
				// Updating the dissolution in the database partner 2.
				boolean checkStatus1 = DBConnectionUtil.getInstance().updateData(sqlQuery1);
				// Updating the partner1 in the database.
				boolean checkStatus2 = DBConnectionUtil.getInstance().updateData(sqlQueryUpdatePartner);
				// Updating the partner2 in the database.
				boolean checkStatus3 = DBConnectionUtil.getInstance().updateData(sqlQueryUpdatePartner2);

				// Setting dissolution of the instance variable.
				if (checkStatus && checkStatus1 && checkStatus2 && checkStatus3) {
					System.out.println("Dissolution recorded for " + partner1.getName() + " and " + partner2.getName());

					/*
					 * partner1.setPartner(null); partner2.setPartner(null);
					 */
					partner1.setDissolution("yes");
					partner2.setDissolution("yes");
					check = true;
				}

				// Query to insert dissolution relation in database.
				String dissolutionQuery = String.format("insert into dissolutions (partner1, partner2) values (%d,%d)",
						partner1.getId(), partner2.getId());

				try {
					// Inserting dissolution relation in the database.
					boolean checkDissolution = DBConnectionUtil.getInstance().updateData(dissolutionQuery);

					if (checkDissolution) {
						check = true;
					}

				} catch (SQLException exception) {
					exception.printStackTrace();
				}

			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}

		return check;
	}

	/**
	 * @author Rushi
	 * @param finalString
	 * @return String
	 * @description Method to remove the last character from a string
	 */
	public static String removeLastChar(String finalString) {
		if (finalString != null && finalString.length() > 0 && finalString.charAt(finalString.length() - 1) == ',') {
			finalString = finalString.substring(0, finalString.length() - 1);
		}
		return finalString;
	}

}
