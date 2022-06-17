import java.awt.Adjustable;
import java.util.*;

/*
 * Implementation class for Travel Planner System
 */
public class TravelPlanner implements TravelAssistant{

	//List for all the cities objects added.
	public static ArrayList<City> cityList = new ArrayList<>();
	//List for all the city names added.
	public static ArrayList<String> cityNamesAddedList = new ArrayList<>();
	//List for all the flights object added.
	public static ArrayList<Flight> flightList = new ArrayList<>();
	//List for all the trains object added.
	public static ArrayList<Train> trainList = new ArrayList<>();
	
	//List of all the flights or trains added.
	public ArrayList<VertexClass> edgeWithDetails = new ArrayList<>();
	//Adjecency list used to denote the graph.
	public static ArrayList<ArrayList<VertexClass>> adjecencyList = new ArrayList<>();
	public static ArrayList<String> startCityList = new ArrayList<>();
	//List of destination city's names linked with a starting city used to plan trip.
	public static ArrayList<String> destinationCityLinkedWithStartCity = new ArrayList<>();
	//List of destination city's names linked with a starting city used to plan trip for better route instance.
	public static ArrayList<String> destinationCityLinkedWithStartCityForBetterRoute = new ArrayList<>();
	//List to store the vertex class instances.
	public static ArrayList<VertexClass> list = new ArrayList<>();
	//Final return list to be used.
	public static List<String> finalList = new ArrayList<>();
	
	//priority weights declared.
	public static int cost;
	public static int time;
	public static int hop=2;
	public static int weight;
	
	
	 
	/*
	 * Method to add a city in the system
	 * @see TravelAssistant#addCity(java.lang.String, boolean, int, int)
	 */
	@Override
	public boolean addCity(String cityName, boolean testRequired, int timeToTest, int nightlyHotelCost)
			throws IllegalArgumentException {
		boolean addCityTest = false;
		try{
			//Validations
			if(cityName == null || cityName.isEmpty() || cityName.equals(" ") || cityName.equals("")){
				throw new IllegalArgumentException("City name cannot be null or empty!");
			}
			
			if(nightlyHotelCost<=0){
				throw new IllegalArgumentException("Nightly hotel cost cannot be 0 or have negative value!");
			}
		//To check if the city is already or not.
		for (City city : cityList) {
			if(city.getCityName().equalsIgnoreCase(cityName)){
				System.out.println(cityName + "City is already added in the system!");
				return addCityTest;
			}
		}
		
		
		//Adding the city to the city list created.
		City city = new City();
		city.setCityName(cityName);
		cityNamesAddedList.add(cityName);
		city.setTestRequired(testRequired);
		city.setTimeToTest(timeToTest);
		city.setNightlyHotelCosts(nightlyHotelCost);
		addCityTest = true;
		cityList.add(city);
		System.out.println(cityName + " city added!");
		}
		catch(IllegalArgumentException illegalArgumentException){
			illegalArgumentException.printStackTrace();
		}
		
		return addCityTest;
	}

	/*
	 * Method to add Flight
	 * @see TravelAssistant#addFlight(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public boolean addFlight(String startCity, String destinationCity, int flightTime, int flightCost)
			throws IllegalArgumentException {
		boolean addFlightTest = false;
		
		Flight flight = new Flight();
		
		//Validations
		if(startCity == null || startCity.isEmpty() || startCity.equals("") || startCity.equals(" ")){
			throw new IllegalArgumentException("Start City cannot be null or empty!");
		}
		
		if( destinationCity == null || destinationCity.isEmpty() ||destinationCity.equals("") || destinationCity.equals(" ")){
			throw new IllegalArgumentException("Destination City cannot be null or empty!");
		}
		if(flightTime <=0){
			throw new IllegalArgumentException("Flight time cannot be 0 minutes or have negative value!");
		}
		if(flightCost<=0){
			throw new IllegalArgumentException("Flight cost cannot be 0 or have negative value!");
		}
		
		
		//Will only operate if both the cities are already registered.
		if(isCityAdded(startCity) && isCityAdded(destinationCity)){

			if(startCity.equals(destinationCity)){
				System.out.println("You are already in the same city!");
				addFlightTest = false;
				return addFlightTest;
			}
			
			//Add flight in flightList.
			flight.setStartCity(startCity);
			flight.setDestinationCity(destinationCity);
			flight.setFlightTime(flightTime);
			flight.setFlightCost(flightCost);
			flightList.add(flight);
			
			//Validate if the flight is added or not.
			for (VertexClass vertexInstance : edgeWithDetails) {
				if(vertexInstance.getStartCity().equalsIgnoreCase(startCity) && vertexInstance.getDestinationCity().equalsIgnoreCase(destinationCity) && vertexInstance.getModeOfTransport().equals("flight")){
					System.out.println("Flight already added for the given set of cities!");
					addFlightTest = false;
					return addFlightTest;
				}
				
			}
			
			//Adding vertex used for edge.
			VertexClass vertex = new VertexClass(startCity,destinationCity, flightCost, flightTime, "fly");
			//Creating List in Adjency List
			ArrayList<VertexClass> listInAdjecencyList = new ArrayList<>();
			if(adjecencyList.isEmpty()){
				edgeWithDetails.add(vertex);
				listInAdjecencyList.add(vertex);

				adjecencyList.add(listInAdjecencyList);
				for(List<VertexClass> a1 : adjecencyList){
					for(VertexClass a2 : a1)
						System.out.println(a2.startCity + " "+a2.destinationCity + " "+a2.cost+" "+a2.modeOfTransport+" "+a2.time );
				}
				addFlightTest = true;
				System.out.println("Flight added from " + startCity + " and "+destinationCity);
				//Returning if flight is added
				return addFlightTest;
			}
			
			ArrayList<VertexClass> listInAdjecencyList1 = new ArrayList<>();
			int index=0;
			//Storing the flight details used for edge building in edgeWithDetails and creating adjecenncy list.
			for(ArrayList<VertexClass> instance:adjecencyList){
				if(instance.get(0).getStartCity().equalsIgnoreCase(startCity)){
					index=adjecencyList.indexOf(instance);
					
					if(!adjecencyList.isEmpty()){
						adjecencyList.get(index).add(vertex);
						edgeWithDetails.add(vertex);
					}
					else{
						edgeWithDetails.add(vertex);
						listInAdjecencyList1.add(vertex);
						//allVertex.add(vertex);
						adjecencyList.add(listInAdjecencyList1);
					}
					for(List<VertexClass> a1 : adjecencyList){
						for(VertexClass a2 : a1)
							System.out.println(a2.startCity + " "+a2.destinationCity + " "+a2.cost+" "+a2.modeOfTransport+" "+a2.time );
					}
					addFlightTest = true;

					System.out.println("Flight added from " + startCity + " and "+destinationCity);
					return addFlightTest;
				}
				index++;
			}
			
			
			
			//Adding the vertex object to my edge with details
			edgeWithDetails.add(vertex);
			//Used to add in adjecency list
			ArrayList<VertexClass> al = new ArrayList<>();
			al.add(vertex);

			adjecencyList.add(al);
			
			for(List<VertexClass> a1 : adjecencyList){
				for(VertexClass a2 : a1)
				System.out.println(a2.startCity + " "+a2.destinationCity + " "+a2.cost+" "+a2.modeOfTransport+" "+a2.time );
			}
			//System.out.println(Arrays.toString(adjecencyList.toArray()));
			addFlightTest = true;
			System.out.println("Flight added from " + startCity + " and "+destinationCity);
		}
		else{
			throw new IllegalArgumentException("City is not added in the system!");
		}
		
		
		
		return addFlightTest;
	}
	
	/*
	 * Method to convert Adjecency list to adjecency matrix
	 */
	public int[][] convertAdjencyListToMatrix(){
		
		//Fetching start city list
		for (ArrayList<VertexClass> instance : adjecencyList) {
			for(int i=0;i<instance.size();i++){
				startCityList.add(instance.get(i).getStartCity());
			}
		}
		
		//Adding empty row in adjecency list for matrix creation
		for(int i=0;i<cityNamesAddedList.size();i++){
			if(!startCityList.contains(cityNamesAddedList.get(i))){
				startCityList.add(cityNamesAddedList.get(i));
				adjecencyList.add(new ArrayList<>());
			}
		}
		
		
		ArrayList<String> cityName = new ArrayList<>();
		//Adding the city name in one arraylist
		for(City n1 : cityList){
			cityName.add(n1.getCityName());
		}
		//Creating the matrix with city name size
		int[][] adjencyMatrix = new int[cityName.size()][cityName.size()];
		for(int i=0;i<cityName.size();i++){
			if(adjecencyList.size()!=0){
				
				ArrayList<VertexClass> vertexList = adjecencyList.get(i);
				ArrayList<Integer> position = new ArrayList<>();
				ArrayList<Integer> cost = new ArrayList<>();
				for (VertexClass vertex : vertexList) {
					//Keeping in mind the cost and time importance I have stored the value beforehand
					int total = vertex.getCost() + vertex.getTime();
					
					position.add(cityName.indexOf(vertex.getDestinationCity()));
					
					cost.add(total);
					
					
				}
				
				System.out.println(position + " " + cost);
				
				//Building the matrix
				for(int k=0;k<adjecencyList.size();k++){
					if(position.contains(k)){
						adjencyMatrix[i][k] = cost.get(position.indexOf(k));
						System.out.println((adjencyMatrix[i][k]));
					}
				}
			}
		}
		
		System.out.println(Arrays.deepToString(adjencyMatrix));
		return adjencyMatrix;
	}
	
	/*
	 * Method to check if city is added or not used in multiple methods
	 */
	public boolean isCityAdded(String cityName){
		boolean check = true;
		//Validate if the city is present in City list
		if(!cityNamesAddedList.contains(cityName)){
			System.out.println(cityName+ " not added as a city!");
			check = false;
			return check;
		}
		return check;
	}

	/*
	 * Method to add train in system
	 * @see TravelAssistant#addTrain(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public boolean addTrain(String startCity, String destinationCity, int trainTime, int trainCost)
			throws IllegalArgumentException {
		
		boolean addTrainTest = false;
		
			//Validations
			if(startCity == null || startCity.isEmpty() || startCity.equals("") || startCity.equals(" ")){
				throw new IllegalArgumentException("Start City cannot be null or empty!");
			}
			
			if( destinationCity == null || destinationCity.isEmpty() ||destinationCity.equals("") || destinationCity.equals(" ")){
				throw new IllegalArgumentException("Destination City cannot be null or empty!");
			}
			if(trainTime <=0){
				throw new IllegalArgumentException("Train time cannot be 0 or have negative value!");
			}
			if(trainCost<=0){
				throw new IllegalArgumentException("Train cost cannot be 0 or have negative value!");
			}
			
		Train train = new Train();
		
		//Operate only if both cities are added
		if(isCityAdded(startCity) && isCityAdded(destinationCity)){

			//Validate if both cities are same then return false
			if(startCity.equals(destinationCity)){
				System.out.println("You are already in the same city!");
				addTrainTest = false;
				return addTrainTest;
			}
			//Adding train in train list
			train.setStartCity(startCity);
			train.setDestinationCity(destinationCity);
			train.setTrainTime(trainTime);
			train.setTrainCost(trainCost);
			trainList.add(train);
			//Validating if the train already exists
			for (VertexClass vertexInstance : edgeWithDetails) {
				if(vertexInstance.getStartCity().equalsIgnoreCase(startCity) && vertexInstance.getDestinationCity().equalsIgnoreCase(destinationCity) && vertexInstance.getModeOfTransport().equals("train")){
					System.out.println("Train already added for the given set of cities!");
					addTrainTest = false;
					return addTrainTest;
				}
			}
			
			
			//Setting vertex for train used for edges
			VertexClass vertex = new VertexClass(startCity,destinationCity, trainCost, trainTime, "train");
			//Adding this vertex in adjecency list with use of edge details
			ArrayList<VertexClass> listInAdjecencyList = new ArrayList<>();
			if(adjecencyList.isEmpty()){
				edgeWithDetails.add(vertex);
				listInAdjecencyList.add(vertex);
				//adjecencyList.add(edgeWithDetails);
				adjecencyList.add(listInAdjecencyList);
				for(List<VertexClass> a1 : adjecencyList){
					for(VertexClass a2 : a1)
						System.out.println(a2.startCity + " "+a2.destinationCity + " "+a2.cost+" "+a2.modeOfTransport+" "+a2.time );
				}
				addTrainTest = true;
				System.out.println("Train added from " + startCity + " and "+destinationCity);

				//Finally returning true if added
				return addTrainTest;
			}
			//Creating list of vertexclass to be used in developing edges
			ArrayList<VertexClass> listInAdjecencyList1 = new ArrayList<>();
			int index=0;
			for(ArrayList<VertexClass> instance:adjecencyList){
				//Storing details in edge with details and adjecency list
				if(instance.get(0).getStartCity().equalsIgnoreCase(startCity)){
					index=adjecencyList.indexOf(instance);
				
					if(!adjecencyList.isEmpty()){
						adjecencyList.get(index).add(vertex);
						edgeWithDetails.add(vertex);
					}
					else{
						edgeWithDetails.add(vertex);
						listInAdjecencyList1.add(vertex);
						//allVertex.add(vertex);
						adjecencyList.add(listInAdjecencyList1);
					}
					for(List<VertexClass> a1 : adjecencyList){
						for(VertexClass a2 : a1)
							System.out.println(a2.startCity + " "+a2.destinationCity + " "+a2.cost+" "+a2.modeOfTransport+" "+a2.time );
					}
					addTrainTest = true;
					System.out.println("Train added from " + startCity + " and "+destinationCity);
					//Returning true if train is added
					return addTrainTest;
				}
				index++;
			}
			//Adding this vertex to the edge with details
			edgeWithDetails.add(vertex);
			
			ArrayList<VertexClass> al = new ArrayList<>();
			al.add(vertex);
			//Adding the vertex list to adjecency list
			adjecencyList.add(al);
			
			
			for(List<VertexClass> a1 : adjecencyList){
				for(VertexClass a2 : a1)
					System.out.println(a2.startCity + " "+a2.destinationCity + " "+a2.cost+" "+a2.modeOfTransport+" "+a2.time );
			}
			

			addTrainTest = true;
			System.out.println("Train added from " + startCity + " and "+destinationCity);
		}
		else{
			throw new IllegalArgumentException("City is not added in the system!");
		}
		
		
		//Finally returning if train is added or not
		return addTrainTest;
	}
	
	/*
	 * Method to find route from source city to destination city after one iteration from plan trip
	 */
	public List<String> findRoute(String startCity,String destinationCity,VertexClass checkInstance){
		List<String> returnList = new ArrayList<String>();
		for (VertexClass vertex : list) {
			if(destinationCityLinkedWithStartCity.contains(vertex.getDestinationCity())){
				break;
			}
			destinationCityLinkedWithStartCity.clear();
			destinationCityLinkedWithStartCity.add(vertex.getDestinationCity());
		}
		for (String destinationCityAsStartCity : destinationCityLinkedWithStartCity) {
			for(ArrayList<VertexClass> instance2 : adjecencyList){
				for(int i=0;i<instance2.size();i++){
					if(instance2.get(i).getStartCity().equals(destinationCityAsStartCity)){
						
						if(!finalList.contains("start " + startCity)){
							finalList.add("start " + startCity);
						}
						if(!finalList.contains(checkInstance.getModeOfTransport() + " " + checkInstance.getDestinationCity())){
						finalList.add(checkInstance.getModeOfTransport() + " " + checkInstance.getDestinationCity());
						}
						
						finalList.add(instance2.get(i).getModeOfTransport()+ " "+instance2.get(i).getDestinationCity());
						if(instance2.get(i).getDestinationCity().equals(destinationCity)){
							returnList.add(instance2.get(i).getModeOfTransport() + " " + instance2.get(i).getDestinationCity());
							return returnList;
						}
						else{
							list.add(instance2.get(i));

							returnList = findRoute(startCity,destinationCity,checkInstance);
							return returnList;
						}
					}
				}
			}
		}
		return returnList;
	}
	
	/*
	 * Method to check for better route in case edges are less than 3 and not using dijkstras algorithm
	 */
	public List<String> checkForBetterRoute(String startCity,String destinationCity,int costImportance, int travelTimeImportance, int travelHopImportance, int weightBasedOnPriority,VertexClass checkInstance){
		
		int weightLocal=0;
		List<String> betterRoute = new ArrayList<>();
		for (ArrayList<VertexClass> instance : adjecencyList) {
			for(int i=0;i<instance.size();i++){
				if(instance.get(i).getStartCity().equalsIgnoreCase(startCity)){
					destinationCityLinkedWithStartCityForBetterRoute.add(instance.get(i).getDestinationCity());
				}
			}
			
		}
		for (String string : destinationCityLinkedWithStartCityForBetterRoute) {
			for (ArrayList<VertexClass> instance : adjecencyList) {
				for(int i=0;i<instance.size();i++){
					if(instance.get(i).getStartCity().equalsIgnoreCase(string)){
						if(instance.get(i).getDestinationCity().equalsIgnoreCase(destinationCity)){
							
							if(hop==2){
							
							weightLocal = (costImportance * instance.get(i).getCost()) + (travelTimeImportance * instance.get(i).getTime()) + (travelHopImportance*hop);
							if(weightLocal<weightBasedOnPriority){
								betterRoute.addAll(finalList);
								betterRoute.add(instance.get(i).getModeOfTransport() + instance.get(i).getDestinationCity());
								return betterRoute;
							}
							else{
								return null;
							}
							}
							else{
								hop++;
								weightLocal = (costImportance * instance.get(i).getCost()) + (travelTimeImportance * instance.get(i).getTime()) + (travelHopImportance*hop);
								if(weightLocal<weightBasedOnPriority){
									betterRoute.addAll(finalList);
									betterRoute.add(instance.get(i).getModeOfTransport() + instance.get(i).getDestinationCity());
									return betterRoute;
								}
								else{
									return null;
								}
								
							}
							
						}
						else{
							checkForBetterRoute(startCity,destinationCity,costImportance,travelTimeImportance,travelHopImportance,weightLocal,instance.get(i));
						}
					}
				}
			}
		}
		
		
		return betterRoute;
	}
	/*
	 * Method to plan a trip from start city to destination city
	 * @see TravelAssistant#planTrip(java.lang.String, java.lang.String, boolean, int, int, int)
	 */
	@Override
	public List<String> planTrip(String startCity, String destinationCity, boolean isVaccinated, int costImportance,
			int travelTimeImportance, int travelHopImportance) throws IllegalArgumentException {
		
		
		
			List<String> returnString = new ArrayList<>();
			ArrayList<ArrayList<VertexClass>> adjacencyList1 = new ArrayList<>();
			adjacencyList1 = (ArrayList<ArrayList<VertexClass>>)adjecencyList.clone();
			if(startCity.equalsIgnoreCase(destinationCity)){
				returnString.add("Start " + startCity);
				return returnString;
			}
			
			
		if(isCityAdded(startCity) && isCityAdded(destinationCity)){
		
		if(costImportance<0){
			throw new IllegalArgumentException("Cost importance cannot be negative!");
		}
		if(travelTimeImportance<0){
			throw new IllegalArgumentException("Travel time importance cannot be negative!");
		}
		if(travelHopImportance<0){
			throw new IllegalArgumentException("Travel hop importance cannot be negative!");
		}
		
		if(edgeWithDetails.size()<3){
		int flag=0;
		String modeOfTransport = "";
		VertexClass dummy = new VertexClass();
		for (ArrayList<VertexClass> instance : adjecencyList) {
			for(int i=0;i<instance.size();i++){
				if(instance.get(i).getStartCity().equalsIgnoreCase(startCity)){
					if(instance.get(i).getDestinationCity().equalsIgnoreCase(destinationCity)){
						//hop++;
						//weight = (costImportance * instance.get(i).getCost()) + (travelTimeImportance * instance.get(i).getTime()) + (travelHopImportance*1);
						finalList.add("start " + startCity);
						finalList.add(instance.get(i).getModeOfTransport() + " " + destinationCity);
						VertexClass dummyInstance = new VertexClass();
						//checkForBetterRoute(startCity,destinationCity,costImportance,travelTimeImportance,travelHopImportance,weight,dummyInstance);
						
						System.out.println(finalList.toString());
						return finalList;
					}
					else{
						dummy=instance.get(i);
						
						destinationCityLinkedWithStartCity.add(instance.get(i).getDestinationCity());
						
					}
			}
			
			}
			flag++;
		}
		
		
		List<String> list = findRoute(startCity,destinationCity,dummy);
		for (String string : list) {
			finalList.add(string);
		}
		if(!finalList.get(finalList.size()-1).contains(destinationCity)){
			return null;
		}
		
		if(finalList.isEmpty()){
			return null;
		}
		Set<String> s = new LinkedHashSet<>(finalList);
		finalList.clear();
		finalList.addAll(s);
		System.out.println(finalList);

		return finalList;
		}
		else{
		
		DijkstrasImplementation dijkstrasImplementation = new DijkstrasImplementation();
		
		int checkStart=0;
		for (ArrayList<VertexClass> instance : adjacencyList1) {
			if(instance.get(0).getStartCity().equalsIgnoreCase(startCity)){
				break;
			}
			checkStart++;
		}
		
		//Start for adjacency matrix
		for (ArrayList<VertexClass> instance : adjacencyList1) {
			
			
			
			
			for(int j=0;j<instance.size();j++){
				if(!startCityList.contains(instance.get(j).getStartCity()))
				startCityList.add(instance.get(j).getStartCity());
			}
		}
		
		
		for(int k=0;k<cityNamesAddedList.size();k++){
			if(!startCityList.contains(cityNamesAddedList.get(k))){
				startCityList.add(cityNamesAddedList.get(k));
				adjacencyList1.add(new ArrayList<>());
			}
			
		}
		
		ArrayList<String> cityName = new ArrayList<>();
		for(City n1 : cityList){
			cityName.add(n1.getCityName());
		}
		
		int[][] adjencyMatrix = new int[cityName.size()][cityName.size()];
		ArrayList<String> destinationCityList = new ArrayList<>();
		ArrayList<Integer> findMode = new ArrayList<>();
		for(int i =0;i<adjacencyList1.size();i++){
			ArrayList<String> listOfDestinationCity = new ArrayList<>(adjacencyList1.size()+10);
			ArrayList<Integer> totalCost = new ArrayList<>();
			ArrayList<String> travelPath = new ArrayList<>();
			int check=0;
			//for (ArrayList<VertexClass> instance : adjacencyList1) {
				for(VertexClass instance1 : adjacencyList1.get(i)){
				if(!listOfDestinationCity.contains(instance1.getDestinationCity())){
					
					listOfDestinationCity.add(instance1.getDestinationCity());
					if(!destinationCityList.contains(instance1.getDestinationCity())){
					destinationCityList.add(instance1.getDestinationCity());
					}
					totalCost.add((costImportance * instance1.getCost()) + (instance1.getTime() * travelTimeImportance));
					travelPath.add(instance1.getModeOfTransport());
					System.out.println(listOfDestinationCity + " " + totalCost + " " + travelPath);
				}
				else{
					
					int cost = totalCost.get(listOfDestinationCity.indexOf(instance1.getDestinationCity()));
					int latest = (costImportance * instance1.getCost()) + (instance1.getTime() * travelTimeImportance);
					
					if(latest<cost){
						totalCost.set(listOfDestinationCity.indexOf(instance1.getDestinationCity()),latest);
						travelPath.set(listOfDestinationCity.indexOf(instance1.getDestinationCity()), instance1.getModeOfTransport());
						System.out.println(listOfDestinationCity + " " + totalCost + " " + travelPath);
					}
				}
				check++;
				
			}
				
				for(int i33=0; i33<listOfDestinationCity.size(); i33++){
					findMode.add(i);
					findMode.add(startCityList.indexOf(listOfDestinationCity.get(i33)));
					if(travelPath.get(i33) == "train" ){
						findMode.add(6967);
					}else{
						findMode.add(6969);
					}
						
				}
				
				for(int l=0;l<listOfDestinationCity.size();l++){
					int index = startCityList.indexOf(listOfDestinationCity.get(l));
					int cost = totalCost.get(l);
					adjencyMatrix[i][index] = cost;
				}
		}
		
		System.out.println(Arrays.deepToString(adjencyMatrix));

		ArrayList<Integer> mapping = dijkstrasImplementation.dijkstraImplementation(adjencyMatrix, checkStart);

		int indexOfStartCity = startCityList.indexOf(startCity);
		int indexOfDestinationCity = startCityList.indexOf(destinationCity);
		
		ArrayList<Integer> path = new ArrayList<>();
		for(int check=1;check<mapping.size();check=check+3){
			if(mapping.get(check)==indexOfDestinationCity){
				path.add(mapping.get(check-1));
				check = check-3;
				while(mapping.get(check-1) != indexOfStartCity){
					if(path.size() > 0 && mapping.get(check) == path.get(path.size()-1))
						path.add(mapping.get(check-1));
					check = check-3;
				}
				break;
			}
			}
		
		Collections.reverse(path);
		path.add(0,indexOfStartCity);
		path.add(path.size(), indexOfDestinationCity);
		System.out.println(path);
		
		
		ArrayList<String> cityNamesOfPath = new ArrayList<>();
		for(int c=0;c<path.size()+10;c++){
			cityNamesOfPath.add("");
		}
		for (Integer integer : path) {
			cityNamesOfPath.set(integer, cityNamesAddedList.get(integer));
			//cityNamesOfPath.add(cityNamesAddedList.get(integer));
		}
		
		for (VertexClass instance : edgeWithDetails) {
			if(instance.getStartCity().equals(cityNamesOfPath.get(0))){
				if(!returnString.contains("Start " + instance.getStartCity()))
				returnString.add("Start " + instance.getStartCity());
			}
			
		}
		
		ArrayList<City> cityObjectsInPath = new ArrayList<>();
		for (String cityNameInPath : cityNamesOfPath) {
			for(City city:cityList){
				if(city.getCityName().equals(cityNameInPath)){
					cityObjectsInPath.add(city);
				}
			}
		}
		
		for(int i=1;i<cityObjectsInPath.size();i++){
			for (VertexClass instance : edgeWithDetails) {
				if(instance.getDestinationCity().equalsIgnoreCase(cityObjectsInPath.get(i).getCityName()) && instance.getStartCity().equals(cityObjectsInPath.get(i-1).getCityName())){
					/*for (String city : returnString) {
						if(!city.contains(cityObjectsInPath.get(i).getCityName())){
							returnString.add(instance.getModeOfTransport() + " " + cityObjectsInPath.get(i).getCityName());
						}
					}*/
					if(returnString.contains("fly " + cityObjectsInPath.get(i).getCityName()) || returnString.contains("train " + cityObjectsInPath.get(i).getCityName())){
						continue;
					}
					returnString.add(instance.getModeOfTransport() + " " + cityObjectsInPath.get(i).getCityName());
				}
			}
		}
		
		
		System.out.println(returnString);
		
		ArrayList<String> path123 = new ArrayList<>();
		
		path123.add("Start " + startCity);
		System.out.println(findMode.toString()); 
		for(int i = 0; i< path.size()-1;i++){
			for(int j = 0; j< findMode.size();j = j+3){
				if(findMode.get(j) == path.get(i) && findMode.get(j+1)==path.get(i+1) ){
					if(findMode.get(j+2) == 6967){
						path123.add( "train " + startCityList.get(path.get(i+1))  );						
					}else{
						path123.add("fly " + startCityList.get(path.get(i+1)));
					}
				}
			}

		}

		System.out.println(path123);
		if(path123.size()==1){
			return null;
		}
		
	
		return returnString;
		}
	}

		else{
			throw new IllegalArgumentException("City is not added in the system!");
			
		}
	}
}



