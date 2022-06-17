import java.util.List;
/*
 * Interface for the Travel Planner system
 */
public interface TravelAssistant {
	boolean addCity( String cityName, boolean testRequired, int timeToTest, int
			nightlyHotelCost ) throws IllegalArgumentException;
			boolean addFlight( String startCity, String destinationCity, int flightTime, int flightCost) throws IllegalArgumentException;
			boolean addTrain( String startCity, String destinationCity, int trainTime, int trainCost) throws IllegalArgumentException;
			List<String> planTrip ( String startCity, String destinationCity, boolean isVaccinated, int	costImportance, int travelTimeImportance, int travelHopImportance ) throws	IllegalArgumentException;
}
