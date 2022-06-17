import java.util.ArrayList;
import java.util.List;

/*
 * Class that stores the media file details.
 */
public class FileIdentifier {

	// Variable for the id of the media file.
	private int id = 0;
	
	// Variable for the name of the media file.
	private String fileName;
	
	// Variable for the date of the media file.
	private String date = "0000-00-00";
	
	// Variable for the location of the media file.
	private String location;
	
	// Variable for the tags of the media file.
	private List<String> tags = new ArrayList<>();
	
	// Variable for the people in the media file.
	private List<PersonIdentity> peopleInMedia = new ArrayList<>();

	// Creating getter and setter methods for the class variables.
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the tags
	 */
	public List<String> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	/**
	 * @return the peopleInMedia
	 */
	public List<PersonIdentity> getPeopleInMedia() {
		return peopleInMedia;
	}

	/**
	 * @param peopleInMedia
	 *            the peopleInMedia to set
	 */
	public void setPeopleInMedia(List<PersonIdentity> peopleInMedia) {
		this.peopleInMedia = peopleInMedia;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
