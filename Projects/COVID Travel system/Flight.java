/*
 * Class to store flight details
 */
public class Flight {
	private String startCity;
	private String destinationCity;
	private int flightTime;
	private int flightCost;
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
	 * @return the flightTime
	 */
	public int getFlightTime() {
		return flightTime;
	}
	/**
	 * @param flightTime the flightTime to set
	 */
	public void setFlightTime(int flightTime) {
		this.flightTime = flightTime;
	}
	/**
	 * @return the flightCost
	 */
	public int getFlightCost() {
		return flightCost;
	}
	/**
	 * @param flightCost the flightCost to set
	 */
	public void setFlightCost(int flightCost) {
		this.flightCost = flightCost;
	}
}
