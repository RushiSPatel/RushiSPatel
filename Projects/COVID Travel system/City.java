import java.util.ArrayList;
/*
 * Class to store city objects
 */
public class City {
	private String cityName;
	private boolean testRequired;
	private int timeToTest;
	private int nightlyHotelCosts;
	
	private ArrayList<City> connectedCities;
	
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the testRequired
	 */
	public boolean isTestRequired() {
		return testRequired;
	}
	/**
	 * @param testRequired the testRequired to set
	 */
	public void setTestRequired(boolean testRequired) {
		this.testRequired = testRequired;
	}
	/**
	 * @return the timeToTest
	 */
	public int getTimeToTest() {
		return timeToTest;
	}
	/**
	 * @param timeToTest the timeToTest to set
	 */
	public void setTimeToTest(int timeToTest) {
		this.timeToTest = timeToTest;
	}
	/**
	 * @return the nightlyHotelCosts
	 */
	public int getNightlyHotelCosts() {
		return nightlyHotelCosts;
	}
	/**
	 * @param nightlyHotelCosts the nightlyHotelCosts to set
	 */
	public void setNightlyHotelCosts(int nightlyHotelCosts) {
		this.nightlyHotelCosts = nightlyHotelCosts;
	}

}
