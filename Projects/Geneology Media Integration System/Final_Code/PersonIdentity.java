import java.util.List;

/**
 * 
 * @author Rushi
 * @description This class is used as a person identifier which stores the
 *              person information.
 */
public class PersonIdentity {

	// Variable for person id.
	private int id = 0;
	
	// Variable for person name.
	private String name;
	
	// Variable for person date of birth.
	private String dateOfBirth;
	
	// Variable for person location of birth.
	private String locationOfBirth;
	
	// Variable for person date of death.
	private String dateOfDeath;
	
	// Variable for person location of death.
	private String locationOfDeath;
	
	// Variable for person gender.
	private String gender;
	
	// Variable for person occupation.
	private String occupation;
	
	// Variable for person references.
	private List<String> references;
	
	// Variable for person notes.
	private List<String> notes;
	
	// Variable for person partner.
	private PersonIdentity partner;
	
	// Variable for person's dissolution.
	private String dissolution;
	
	// Variable for person's children.
	private List<PersonIdentity> children;

	// Creating getters and setters for the class variables.
	/**
	 * @return the partner
	 */
	public PersonIdentity getPartner() {
		return partner;
	}

	/**
	 * @param partner
	 *            the partner to set
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
	 * @param name
	 *            the name to set
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
	 * @param dateOfBirth
	 *            the dateOfBirth to set
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
	 * @param locationOfBirth
	 *            the locationOfBirth to set
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
	 * @param dateOfDeath
	 *            the dateOfDeath to set
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
	 * @param locationOfDeath
	 *            the locationOfDeath to set
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
	 * @param gender
	 *            the gender to set
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
	 * @param occupation
	 *            the occupation to set
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
	 * @param references
	 *            the references to set
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
	 * @param notes
	 *            the notes to set
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
	 * @param dissolution
	 *            the dissolution to set
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
	 * @param children
	 *            the children to set
	 */
	public void setChildren(List<PersonIdentity> children) {
		this.children = children;
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
