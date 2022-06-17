import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//TravelAssistantImplementation travelAssistantImplementation = new TravelAssistantImplementation();
		TravelPlanner travelPlanner = new TravelPlanner();
		String addCityCommand = "city";
		String addFlightCommand = "flight";
		String addTrainCommand = "train";
		String planTripCommand = "plan";
		String quitCommand = "quit";
		
		System.out.println("Welcome to Travel Planer system!");
		System.out.println("Please enter the command: ");
		System.out.println(" " + addCityCommand + " <city name> <test required> <time to test> <nightly hotel cost>");
		System.out.println(" " + addFlightCommand + " <start city> <destination city> <flight time> <flight cost>");
		System.out.println(" " + addTrainCommand + " <start city> <destination city> <train time> <train cost>");
		System.out.println(" " + planTripCommand + " <start city> <destination city> <is Vaccinated> <Cost importance(1 or 0)> <travelTimeImportance(1 or 0)> <travelHopImportance(1 or 0)>");
		System.out.println(quitCommand);
		String userCommand="";
		Scanner input = new Scanner(System.in);
		
		do{
			userCommand = input.next(); 
			if (userCommand.equalsIgnoreCase(addCityCommand)){
				
				try{
				String cityName = input.next();
				boolean testRequired = input.nextBoolean();
				int timeToTest = input.nextInt();
				int nightlyHotelCost = input.nextInt();
				
				travelPlanner.addCity(cityName, testRequired, timeToTest, nightlyHotelCost);
				}
				catch (InputMismatchException exception){
					System.out.println("Input mismatch! Please try again");
					continue;
				}
				
			}
			else if(userCommand.equalsIgnoreCase(addFlightCommand)){
				try{
				String startCity = input.next();
				String destinationCity = input.next();
				int flightTime = input.nextInt();
				int flightCost = input.nextInt();
				
				
				
				travelPlanner.addFlight(startCity, destinationCity, flightTime, flightCost);
				}
				catch (InputMismatchException exception){
					System.out.println("Input mismatch! Please try again");
					continue;
				}
			}
			else if(userCommand.equalsIgnoreCase(addTrainCommand)){
				try{
				String startCity = input.next();
				String destinationCity = input.next();
				int trainTime = input.nextInt();
				int trainCost = input.nextInt();
				
				
				
				
				travelPlanner.addTrain(startCity, destinationCity, trainTime, trainCost);
				}
				catch (InputMismatchException exception){
					System.out.println("Input mismatch! Please try again");
					continue;
				}
			}
			else if(userCommand.equalsIgnoreCase(planTripCommand)){
				try{
				String startCity = input.next();
				String destinationCity = input.next();
				boolean isVaccinated = input.nextBoolean();
				int costImportance = input.nextInt();
				int travelTimeImportance = input.nextInt();
				int travelHopImportance = input.nextInt();
				
				travelPlanner.planTrip(startCity, destinationCity, isVaccinated, costImportance, travelTimeImportance, travelHopImportance);
				}
				catch (InputMismatchException exception){
					System.out.println("Input mismatch! Please try again");
					continue;
				}
				}
		}while(!userCommand.equalsIgnoreCase("quit"));
		
	}

}
