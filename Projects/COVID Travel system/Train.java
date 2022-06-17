/*
 * Class to store Train objects
 */
public class Train {
	private String startCity;
	private String destinationCity;
	private int trainTime;
	private int trainCost;
	/**
	 * @return the startCity
	 */
	public String getStartCity() {
		return startCity;
	}
	/**
	 * @param startCity the startCity to set
	 */
	public void setStartCity(String startCity) {
		this.startCity = startCity;
	}
	/**
	 * @return the destinationCity
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	/**
	 * @param destinationCity the destinationCity to set
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	/**
	 * @return the trainTime
	 */
	public int getTrainTime() {
		return trainTime;
	}
	/**
	 * @param trainTime the trainTime to set
	 */
	public void setTrainTime(int trainTime) {
		this.trainTime = trainTime;
	}
	/**
	 * @return the trainCost
	 */
	public int getTrainCost() {
		return trainCost;
	}
	/**
	 * @param trainCost the trainCost to set
	 */
	public void setTrainCost(int trainCost) {
		this.trainCost = trainCost;
	}
}
