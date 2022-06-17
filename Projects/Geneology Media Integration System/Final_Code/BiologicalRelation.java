/**
 * This class is used to denote the relation of two people.
 */
public class BiologicalRelation {

	// Variable to set the cousinship two people
	Integer cousinship;
	// Variable to set the degree of removal.
	Integer degreeOfRemoval;
	// Variable to set the nearest common ancestor.
	String nearestCommonAncestor;
	// Variable to set the relation.
	String relationName;

	// Creating getters and setters for the variables
	/**
	 * @return the nearestCommonAncestor
	 */
	public String getNearestCommonAncestor() {
		return nearestCommonAncestor;
	}

	/**
	 * @param nearestCommonAncestor
	 *            the nearestCommonAncestor to set
	 */
	public void setNearestCommonAncestor(String nearestCommonAncestor) {
		this.nearestCommonAncestor = nearestCommonAncestor;
	}

	/**
	 * @return the relationName
	 */
	public String getRelationName() {
		return relationName;
	}

	/**
	 * @param relationName
	 *            the relationName to set
	 */
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	/**
	 * @param cousinship
	 *            the cousinship to set
	 */
	public void setCousinship(Integer cousinship) {
		this.cousinship = cousinship;
	}

	/**
	 * @param degreeOfRemoval
	 *            the degreeOfRemoval to set
	 */
	public void setDegreeOfRemoval(Integer degreeOfRemoval) {
		this.degreeOfRemoval = degreeOfRemoval;
	}

	/**
	 * @return the cousinship
	 */
	public int getCousinship() {
		return cousinship;
	}

	/**
	 * @param cousinship
	 *            the cousinship to set
	 */
	public void setCousinship(int cousinship) {
		this.cousinship = cousinship;
	}

	/**
	 * @return the degreeOfRemoval
	 */
	public int getDegreeOfRemoval() {
		return degreeOfRemoval;
	}

	/**
	 * @param degreeOfRemoval
	 *            the degreeOfRemoval to set
	 */
	public void setDegreeOfRemoval(int degreeOfRemoval) {
		this.degreeOfRemoval = degreeOfRemoval;
	}

}
