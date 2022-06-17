import java.util.ArrayList;
import java.util.List;

public class FileIdentifier {

	private String fileName;
	private String date = "0000-00-00";
	private String location;
	private List<String> tags = new ArrayList<>();
	private List<PersonIdentity> peopleInMedia = new ArrayList<>();
	
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
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
	 * @param date the date to set
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
	 * @param location the location to set
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
	 * @param tags the tags to set
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
	 * @param peopleInMedia the peopleInMedia to set
	 */
	public void setPeopleInMedia(List<PersonIdentity> peopleInMedia) {
		this.peopleInMedia = peopleInMedia;
	}
	
}
