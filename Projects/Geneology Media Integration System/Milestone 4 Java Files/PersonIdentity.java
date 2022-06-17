import java.util.List;

public class PersonIdentity {
	
	private String name;
	private String dateOfBirth;
	private String locationOfBirth;
	private String dateOfDeath;
	private String locationOfDeath;
	private String gender;
	private String occupation;
	private List<String> references;
	private List<String> notes;
	private PersonIdentity partner;
	private String dissolution;
	private List<PersonIdentity> children;
	
	/**
	 * @return the partner
	 */
	public PersonIdentity getPartner() {
		return partner;
	}
	/**
	 * @param partner the partner to set
	 */
	public void setPartner(PersonIdentity partner) {
		this.partner = partner;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the locationOfBirth
	 */
	public String getLocationOfBirth() {
		return locationOfBirth;
	}
	/**
	 * @param locationOfBirth the locationOfBirth to set
	 */
	public void setLocationOfBirth(String locationOfBirth) {
		this.locationOfBirth = locationOfBirth;
	}
	/**
	 * @return the dateOfDeath
	 */
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	/**
	 * @param dateOfDeath the dateOfDeath to set
	 */
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	/**
	 * @return the locationOfDeath
	 */
	public String getLocationOfDeath() {
		return locationOfDeath;
	}
	/**
	 * @param locationOfDeath the locationOfDeath to set
	 */
	public void setLocationOfDeath(String locationOfDeath) {
		this.locationOfDeath = locationOfDeath;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the occupation
	 */
	public String getOccupation() {
		return occupation;
	}
	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	/**
	 * @return the references
	 */
	public List<String> getReferences() {
		return references;
	}
	/**
	 * @param references the references to set
	 */
	public void setReferences(List<String> references) {
		this.references = references;
	}
	/**
	 * @return the notes
	 */
	public List<String> getNotes() {
		return notes;
	}
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
	/**
	 * @return the dissolution
	 */
	public String getDissolution() {
		return dissolution;
	}
	/**
	 * @param dissolution the dissolution to set
	 */
	public void setDissolution(String dissolution) {
		this.dissolution = dissolution;
	}
	/**
	 * @return the children
	 */
	public List<PersonIdentity> getChildren() {
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<PersonIdentity> children) {
		this.children = children;
	}
	
	
	
	

}
