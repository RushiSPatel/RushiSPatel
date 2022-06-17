import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author Rushi
 * @description This class is to assist the geneologist by fetching data from
 *              the database based on their requirement.
 *
 */
public class Geneology implements IGeneology {

	/**
	 * @author Rushi
	 * @param String
	 *            name
	 * @return PersonIdentity
	 * @description This method is to find the person in the database.
	 */
	@Override
	public PersonIdentity findPerson(String name) {
		// Input validation for bad input.
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
		// Person identifier object.
		PersonIdentity personIdentity = new PersonIdentity();
		// Query to fetch person information from the database.
		String fetchPersonData = String.format(
				"select id,dateOfBirth,locationOfBirth,gender,occupation,partner,dissolution from familytree where individualName = \"%s\"",
				name);

		try {
			// Fetching the person information from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPersonData);

			// Setting the person identifier variables with the information
			// fetched from the database.
			while (rs.next()) {
				personIdentity.setId(rs.getInt("id"));
				personIdentity.setName(name);

			}

			// Validating if the given person exists in the database.
			if (personIdentity.getId() == 0) {
				try {
					throw new GeneologyException("Person not found in the database!");
				} catch (GeneologyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					e.getMessage();
				}

				return null;

			}
			System.out.println("Person found in the database with name " + name);

			return personIdentity;

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return personIdentity;
	}

	/**
	 * @author Rushi
	 * @param String
	 *            name
	 * @return FileIdentifier
	 * @description This method is to find a media file from the database.
	 */
	@Override
	public FileIdentifier findMediaFile(String name) {
		// Input validation to check for bad input.
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

		// File identifier for the media file.
		FileIdentifier fileIdentifier = new FileIdentifier();
		// Formatting the file location String with a format acceptable by
		// mysql.
		String updatedFileLocation = name.replace("\\", "\\\\");
		// Query to fetch media file information from the database.
		String fetchFileDetails = String.format(
				"select id,fileName,dateOfMedia,location from mediaarchive where fileName = \"%s\"",
				updatedFileLocation);

		try {
			// Fetching media file information from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchFileDetails);
			// Setting the media file identifier object's variable values to the
			// fetched data from the database.
			while (rs.next()) {
				fileIdentifier.setId(rs.getInt("id"));
				fileIdentifier.setFileName(rs.getString("fileName"));
				fileIdentifier.setDate(rs.getString("dateOfMedia"));
				fileIdentifier.setLocation(rs.getString("location"));
			}

			// Validation to check if the file is present in the database.
			if (fileIdentifier.getId() == 0) {
				try {
					throw new GeneologyException("File not found with name : " + name);
				} catch (GeneologyException e) {
					e.printStackTrace();
					e.getMessage();
				}
				return null;
			}
			System.out.println("File found with name : " + name);
			return fileIdentifier;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return fileIdentifier;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            id
	 * @return String
	 * @description Method to find the name of the person.
	 */
	@Override
	public String findName(PersonIdentity id) {

		// Input validation for bad inputs.
		if (id == null) {
			try {
				throw new GeneologyException("Object cannot be null");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}

			return null;
		}
		System.out.println(id.getName());
		return id.getName();
	}

	/**
	 * @author Rushi
	 * @param FileIdentifier
	 *            fileId
	 * @return String
	 * @description Method to get the media file name.
	 */
	@Override
	public String findMediaFile(FileIdentifier fileId) {

		// Input validation for bad input.
		if (fileId == null) {
			try {
				throw new GeneologyException("Object cannot be null");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}

		System.out.println(fileId.getFileName());
		return fileId.getFileName();
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person1, PersonIdentity person2
	 * @return BiologicalRelation
	 * @description Method to find relation between two people.
	 */
	@Override
	public BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {

		// Input validation for bad inputs.
		if (person1 == null || person2 == null) {

			try {
				throw new GeneologyException("Objects cannot be null");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Validating if both person are same.
		if (person1.equals(person2)) {

			try {
				throw new GeneologyException("Cannot find relation of the same person!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Instance of relation class.
		BiologicalRelation br = new BiologicalRelation();

		// storing the id's of both the person.
		int id1 = person1.getId();
		int id2 = person2.getId();

		// Query to fetch the parent children information from the database.
		String fetchParentChildren = "select * from children";

		// Map to store the id and list of their children
		Map<Integer, List<Integer>> relationModel = new HashMap<>();

		// List to store all the children of a parent.
		List<Integer> childList = new ArrayList<>();

		try {
			// Fetching the parent child information from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchParentChildren);

			while (rs.next()) {
				// Storing parent id as key and child id as value.
				int key = rs.getInt("parent");

				int value = rs.getInt("child");
				// Adding the first entry in the map.
				if (relationModel.isEmpty()) {
					List<Integer> firstList = new ArrayList<>();
					firstList.add(value);
					relationModel.put(key, firstList);
				}
				// Adding all the child ids in the map.
				else {
					if (relationModel.containsKey(key)) {
						relationModel.get(key).add(value);
					} else {
						List<Integer> firstList = new ArrayList<>();
						firstList.add(value);
						relationModel.put(key, firstList);
					}

				}

			}
			// List to store the first parent set of id's.
			List<Integer> firstList = new ArrayList<>();
			firstList.add(id1);
			// List to store the second parent set of id's.
			List<Integer> secondList = new ArrayList<>();
			secondList.add(id2);

			// Iterating through the parent list
			for (int i : relationModel.keySet()) {
				// Adding all the matching id's in the first list.
				if (relationModel.get(i).contains(id1)) {
					firstList.add(i);
					id1 = i;
				}
			}

			// Iterating through the parent list
			for (int i : relationModel.keySet()) {
				// Adding all the matching id's in the second list.
				if (relationModel.get(i).contains(id2)) {
					secondList.add(i);
					id2 = i;
				}
			}

			// Creating variables for both person id's and nearest common
			// ancestor.
			int indexFirst = 0;
			int indexSecond = 0;
			String nearestCommonAncestor = new String();
			// Iterating through the first list.
			for (int i : firstList) {
				// Validate if the firstlist id exist in the second list.
				if (secondList.contains(i)) {
					// Storing their indexes in their respective lists.
					indexFirst = firstList.indexOf(i);
					indexSecond = secondList.indexOf(i);
					// Query to fetch name of the individual from the database.
					String fetchIndividualName = String.format("select individualName from FamilyTree where id= %d", i);
					// Fetching the name of the individual from the database.
					ResultSet resultSet = DBConnectionUtil.getInstance().readData(fetchIndividualName);

					// Storing the name of the individual found in the nearest
					// common ancestor variable.
					while (resultSet.next()) {
						nearestCommonAncestor = resultSet.getString("individualName");
					}

					break;
				}
			}
			// Validating if the people are found.
			if (indexFirst == 0 && indexSecond == 0) {
				return null;
			}

			// Setting the cousinship.
			int cousinship = Math.min(indexFirst, indexSecond) - 1;
			// Setting the degree of removal.
			int removal = Math.abs(indexFirst - indexSecond);

			System.out.println("Cosuinship is: " + cousinship);
			System.out.println("Degree of removal is : " + removal);

			// Setting relation name in the biological relation instance.
			if (removal == 0 && cousinship == 0) {
				System.out.println("Relation name: Sibling");
				br.setRelationName("Sibling");
			} else if (cousinship == 1 && removal == 1) {
				System.out.println("Relation name: First Cousins once removed");
				br.setRelationName("First Cousins once removed");
			}

			// Setting the cousinship, degree of removal and nearest common
			// ancestor into the biological relation instance.
			br.setCousinship(cousinship);
			br.setDegreeOfRemoval(removal);
			br.setNearestCommonAncestor(nearestCommonAncestor);

		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return br;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person, Integer generations
	 * @return Set<PersonIdentity>
	 * @description Method to fetch all the descendants of a person given
	 *              generation value.
	 */
	@Override
	public Set<PersonIdentity> descendents(PersonIdentity person, Integer generations) {

		// Input validation for bad inputs.
		if (person == null || generations == null) {
			try {
				throw new GeneologyException("Person or generations object cannot be null!");
			} catch (GeneologyException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}

		// Creating a set to store the descendants.
		Set<PersonIdentity> descendants = new HashSet<>();

		// Variables for checking generation level, count and tempSize (size of
		// the temp list)
		int gen = 0;
		int count = 0;
		int tempSize = 0;

		// id of the person.
		int id = person.getId();

		// List to store the id's of all the descendants.
		List<Integer> permanent = new ArrayList<>();
		// List to store the children of particular generation.
		List<Integer> temp = new ArrayList<>();
		// List to store children of specific person in a particular generation.
		List<Integer> forTemp = new ArrayList<>();

		// Iterating till the local gen variable has reached the pecified
		// generation level.
		while (gen != generations) {
			// Query to fetch child from the database.
			String fetchData = String.format("select child from children where parent = %d", id);

			try {
				// Fetching child id from the database.
				ResultSet rs = DBConnectionUtil.getInstance().readData(fetchData);

				// Adding the child id's of the first generation to permanent
				while (rs.next()) {
					permanent.add(rs.getInt("child"));
					temp.add(rs.getInt("child"));
				}
				// Storing the size of the temp list.
				tempSize = temp.size();

				// Incrementing generation count.
				gen++;
				// breaking if the generation matches the level specified.
				if (gen == generations) {
					break;
				}

				// Iterating till the local gen variable has reached the
				// pecified generation level.
				while (gen != generations) {
					// Iterating through temp list for next generations
					for (Integer i : temp) {
						// changing the id to the next generation person and
						// incrementing count.
						id = i;
						count++;
						// Query to fetch child id for next generation.
						String fetchData1 = String.format("select child from children where parent = %d", id);

						try {
							// Fetching child if for next generation.
							ResultSet rs1 = DBConnectionUtil.getInstance().readData(fetchData1);
							// Adding these to the permanent and forTemp lists.
							while (rs1.next()) {
								permanent.add(rs1.getInt("child"));
								forTemp.add(rs1.getInt("child"));
							}

						} catch (SQLException exception) {
							exception.printStackTrace();
						}

					}
					// Incrementing generation.
					gen++;
					// Validating if the generation matches the level specified.
					if (gen == generations) {
						break;
					}
					// clearing temp and adding all fortemp id's to temp to set
					// it up for the upcoming generation and clearing forTemp
					// list.
					temp.clear();
					temp.addAll(forTemp);
					forTemp.clear();
				}

			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
		// Id's of all the descendants.
		System.out.println(permanent);
		// Iterating through the permanent list to fetch their respective names
		// and adding the person identity instance into the descendants set.
		for (Integer i : permanent) {
			PersonIdentity personIdentity = new PersonIdentity();

			String fetchPersonData = String.format("select individualName from familytree where id = %d", i);

			try {

				ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPersonData);

				while (rs.next()) {
					personIdentity.setName(rs.getString("individualName"));

				}

			} catch (SQLException exception) {
				exception.printStackTrace();
			}

			descendants.add(personIdentity);

		}

		return descendants;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person, Integer generations
	 * @return Set<PersonIdentity>
	 * @description Method to get all the ancestors of a person.
	 */
	@Override
	public Set<PersonIdentity> ancestors(PersonIdentity person, Integer generations) {

		// Input validation for bad inputs.
		if (person == null || generations == null) {
			try {
				throw new GeneologyException("Person or generations object cannot be null!");
			} catch (GeneologyException e) {
				e.printStackTrace();
				e.getMessage();
			}
		}
		// Creating a set to store the ancestors.
		Set<PersonIdentity> ancestores = new HashSet<>();

		// Variables for checking generation level, count and tempSize (size of
		// the temp list)
		int gen = 0;
		int count = 0;

		int tempSize = 0;

		int id = person.getId();
		// List to store the id's of all the ancestors.
		List<Integer> permanent = new ArrayList<>();
		// List to store the parent of particular generation.
		List<Integer> temp = new ArrayList<>();
		// List to store parent of specific person in a particular generation.
		List<Integer> forTemp = new ArrayList<>();

		// Iterating till the local gen variable has reached the pecified
		// generation level.
		while (gen != generations) {
			// Query to fetch child from the database.
			String fetchData = String.format("select parent from children where child = %d", id);

			try {
				// Fetching parent id from the database.
				ResultSet rs = DBConnectionUtil.getInstance().readData(fetchData);
				// Adding the parent id's of the first generation to permanent
				while (rs.next()) {
					permanent.add(rs.getInt("parent"));
					temp.add(rs.getInt("parent"));
				}
				// Storing the size of the temp list.
				tempSize = temp.size();
				// Incrementing generation count.
				gen++;
				// breaking if the generation matches the level specified.
				if (gen == generations) {
					break;
				}
				// Iterating till the local gen variable has reached the
				// specified generation level.
				while (gen != generations) {
					// Iterating through temp list for next generations
					for (Integer i : temp) {
						// changing the id to the next generation person and
						// incrementing count.
						id = i;
						count++;

						// Query to fetch child id for next generation.
						String fetchData1 = String.format("select parent from children where child = %d", id);

						try {
							// Fetching child if for next generation.
							ResultSet rs1 = DBConnectionUtil.getInstance().readData(fetchData1);
							// Adding these to the permanent and forTemp lists.
							while (rs1.next()) {
								permanent.add(rs1.getInt("parent"));
								forTemp.add(rs1.getInt("parent"));
							}

						} catch (SQLException exception) {
							exception.printStackTrace();
						}

					}
					// Incrementing generation.
					gen++;
					// Validating if the generation matches the level specified.
					if (gen == generations) {
						break;
					}
					// clearing temp and adding all fortemp id's to temp to set
					// it up for the upcoming generation and clearing forTemp
					// list.
					temp.clear();
					temp.addAll(forTemp);
					forTemp.clear();
				}

			} catch (SQLException exception) {
				exception.printStackTrace();
			}

		}
		// Id's of all the ancestors.
		System.out.println(permanent);

		// Iterating through the permanent list to fetch their respective names
		// and adding the person identity instance into the descendants set.
		for (Integer i : permanent) {
			PersonIdentity personIdentity = new PersonIdentity();

			String fetchPersonData = String.format("select individualName from familytree where id = %d", i);

			try {

				ResultSet rs = DBConnectionUtil.getInstance().readData(fetchPersonData);

				while (rs.next()) {
					personIdentity.setName(rs.getString("individualName"));

				}

			} catch (SQLException exception) {
				exception.printStackTrace();
			}

			ancestores.add(personIdentity);

		}

		return ancestores;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person
	 * @return List<String>
	 * @description Method to get the notes and references of a person.
	 */
	@Override
	public List<String> notesAndReferences(PersonIdentity person) {
		// Input validation for bad inputs.
		if (person == null) {
			try {
				throw new GeneologyException("Object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// List to store all the notes of the person.
		List<String> notes = new ArrayList<String>();
		// List to store all the references of the person.
		List<String> references = new ArrayList<String>();
		// Query to fetch the notes of the person.
		String fetchNotes = String.format(
				"select notes from notesofindividual where individualName = \"%s\" and id = %d", person.getName(),
				person.getId());
		// Query to fetch the references of the person.
		String fetchReferences = String.format(
				"select referencesToSourceMaterial from referencesofindividual where individualName = \"%s\" and id = %d",
				person.getName(), person.getId());

		try {
			// Fetching the notes of the person from the database.
			ResultSet notesRS = DBConnectionUtil.getInstance().readData(fetchNotes);
			// Adding notes in the list.
			while (notesRS.next()) {
				notes.add(notesRS.getString("notes"));
			}
			// Fetching the references of the person from the database.
			ResultSet referencesRS = DBConnectionUtil.getInstance().readData(fetchReferences);
			// Adding references in the list.
			while (referencesRS.next()) {
				references.add(referencesRS.getString("referencesToSourceMaterial"));
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		// List to store both notes and references.
		List<String> notesAndReferences = new ArrayList<String>();
		// Adding both notes and references of the person in the list.
		notesAndReferences.addAll(notes);
		notesAndReferences.addAll(references);

		// Validating if the person had no notes and references recorded in the
		// database.
		if (notesAndReferences == null || notesAndReferences.isEmpty()) {
			System.out.println("No notes or references found for " + person.getName());
			return null;
		}
		System.out.println("Notes and references found for " + person.getName());
		System.out.println(notesAndReferences);
		return notesAndReferences;

	}

	/**
	 * @author Rushi
	 * @param String
	 *            tag, String startDate, String endDate
	 * @return Set<FileIdentifier>
	 * @description Method to find set of medias by tag within the time range.
	 */
	@Override
	public Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) {
		// Input validation for bad inputs.
		if (tag == null || tag.isEmpty()) {

			try {
				throw new GeneologyException("Location cannot be null or empty!");
			} catch (GeneologyException e) {

				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Formatting the start date if null is encountered.
		if (startDate == null) {
			startDate = "0000-00-00";

		}
		// Formatting the end date if null is encountered.
		if (endDate == null) {
			endDate = "9999-12-31";
		}
		// Input validation for bad input on dates.
		if (startDate.isEmpty() || endDate.isEmpty()) {
			try {
				throw new GeneologyException("Date cannot be empty!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Query to fetch file name from the database.
		String fetchInfo = String.format(
				"select fileName from mediatag where tag = \"%s\" and (dateOfMedia between \"%s\" and \"%s\") ORDER BY dateOfMedia desc",
				tag, startDate, endDate);
		// Set of media.
		Set<FileIdentifier> media = new HashSet<>();

		try {
			// Fetching file name from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchInfo);
			// Setting the file name in the file identifier instance.
			while (rs.next()) {
				FileIdentifier fileIdentifier = new FileIdentifier();
				fileIdentifier.setFileName(rs.getString("fileName"));
				// Adding the file identifier instance to the media set.
				media.add(fileIdentifier);
			}

			return media;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return media;
	}

	/**
	 * @author Rushi
	 * @param String
	 *            location, String startDate, String endDate
	 * @return Set<FileIdentifier>
	 * @description Method to find media by location within the given date
	 *              range.
	 */
	@Override
	public Set<FileIdentifier> findMediaByLocation(String location, String startDate, String endDate) {
		// Input validation for bad inputs.
		if (location == null || location.isEmpty()) {

			try {
				throw new GeneologyException("Location cannot be null or empty!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Formatting startDate value if null.
		if (startDate == null) {
			startDate = "0000-00-00";

		}
		// Formatting endDate value if null.
		if (endDate == null) {
			endDate = "9999-12-31";
		}
		// Input validation for bad inputs for dates.
		if (startDate.isEmpty() || endDate.isEmpty()) {

			try {
				throw new GeneologyException("Date cannot be empty!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		location = "%" + location + "%";
		// Query to fetch file name from database based on location and date
		// range using order by desc to get all the null values at the last.
		String fetchInfo = String.format(
				"select fileName from mediaarchive where location like \"%s\" and (dateOfMedia between \"%s\" and \"%s\") ORDER BY dateOfMedia desc",
				location, startDate, endDate);

		// Set of medias.
		Set<FileIdentifier> media = new HashSet<>();

		try {
			// Fetching file name from database based on location and date
			// range.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchInfo);

			// adding file identifier instances into media set.
			while (rs.next()) {
				FileIdentifier fileIdentifier = new FileIdentifier();

				fileIdentifier.setFileName(rs.getString("fileName"));

				media.add(fileIdentifier);

			}

			return media;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return media;
	}

	/**
	 * @author Rushi
	 * @param Set<PersonIdentity>
	 *            people, String startDate, String endDate
	 * @return List<FileIdentifier>
	 * @description Method to find media files that include any of individuals
	 *              given in the list of people whose dates fall within the date
	 *              range
	 */
	@Override
	public List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String endDate) {
		// Input validation for bad inputs.
		if (people == null) {
			try {
				throw new GeneologyException("People object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// Formatting start date if null is encountered.
		if (startDate == null) {
			startDate = "0000-00-00";
		}
		// Formatting end date if null is encountered.
		if (endDate == null) {
			endDate = "9999-12-31";
		}
		// Input validation for bad inputs for dates.
		if (startDate.isEmpty() || endDate.isEmpty()) {

			try {
				throw new GeneologyException("Date cannot be empty!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// List of name of people.
		List<String> peopleNameList = new ArrayList<String>();
		// Iterating through people to add the name to the list.
		for (PersonIdentity personIdentity : people) {
			peopleNameList.add(personIdentity.getName());
		}

		// Partial query to fetch file name from the database.
		String fetchMedia = "select distinct fileName from mediapeople where (";
		// Iterating through peopleNameList to append to the query.
		for (String string : peopleNameList) {
			fetchMedia += String.format(" individualName = \"%s\" or", string);
		}
		// Removing 'or' from the final query.
		fetchMedia = fetchMedia.substring(0, fetchMedia.length() - 2);
		// Completing the query with order by to get all the null values last.
		fetchMedia += String.format(") and (dateOfMedia between \"%s\" and \"%s\") ORDER BY dateOfMedia desc",
				startDate, endDate);
		// List of file identifier objects.
		List<FileIdentifier> files = new ArrayList<>();

		try {
			// Fetching media file information from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchMedia);

			// Adding the information in the files list.
			while (rs.next()) {
				FileIdentifier fileIdentifier = new FileIdentifier();
				fileIdentifier.setFileName(rs.getString("fileName"));
				files.add(fileIdentifier);
			}
			System.out.println("Found media files in the given date range");
			return files;

		} catch (SQLException exception) {
			exception.printStackTrace();
		}

		return files;
	}

	/**
	 * @author Rushi
	 * @param PersonIdentity
	 *            person
	 * @return List<FileIdentifier>
	 * @description Method to fetch the media files of the children of specified
	 *              person.
	 */
	@Override
	public List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) {
		// Input validation for bad inputs.
		if (person == null) {
			try {
				throw new GeneologyException("Person object cannot be null!");
			} catch (GeneologyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				e.getMessage();
			}
			return null;
		}
		// List of file identifier instances.
		List<FileIdentifier> media = new ArrayList<>();
		// Set of people.
		Set<PersonIdentity> people = new HashSet<>();
		// Storing the id of the person.
		int id = person.getId();

		// Query to fetch child id from the database.
		String fetchChildren = String.format("select child from children where parent = %d", id);

		try {
			// Fetching child id from the database.
			ResultSet rs = DBConnectionUtil.getInstance().readData(fetchChildren);

			while (rs.next()) {
				PersonIdentity personIdentity = new PersonIdentity();
				int childId = rs.getInt("child");
				// Query to fetch the name from the database.
				String fetchName = String.format("select individualName from familytree where id = %d", childId);
				// Fetching name from the database.
				ResultSet rs1 = DBConnectionUtil.getInstance().readData(fetchName);

				// Setting the name to the person identity object.
				while (rs1.next()) {
					personIdentity.setName(rs1.getString("individualName"));
				}
				// Setting the id to the person identity object.
				personIdentity.setId(childId);
				// Adding the person identity object to the people set.
				people.add(personIdentity);
			}

		} catch (SQLException exception) {
			exception.printStackTrace();
		}
		// Fetching the media files of the included people (children of the
		// person mentioned).
		media = findIndividualsMedia(people, null, null);

		return media;
	}

}
