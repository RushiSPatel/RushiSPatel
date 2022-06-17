import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

/*
 * The class OnlineAuctionSystem is used for the core implementation of the Online Auction System
 * Made by: Rushi Patel 
 * Last Modified Date: 22-September-2021
 */
@SuppressWarnings("unchecked")
public class OnlineAuctionSystem {

	Lot lot = new Lot();
	ArrayList<Bidder> bidderList = new ArrayList<>();
	ArrayList<Bidder> bidderListAfterBids = new ArrayList<>();
	ArrayList<Auction> auctionList = new ArrayList<>();
	ArrayList<Integer> bidDetailsList = new ArrayList<>();
	ArrayList<Integer> bidDetailsIndividualList = new ArrayList<>();
	ArrayList<int[]> bidDetailsFinal = new ArrayList<>();
	ArrayList<Lot> lotDetailsList = new ArrayList<>();
	ArrayList<Integer> lotNumbers = new ArrayList<>();
	HashMap<Auction, ArrayList<Integer>> lotNumbersAuctionWise = new HashMap<>();
	ArrayList<Lot> lotNumbersOfOneAuction = new ArrayList<>();
	HashMap<Auction, ArrayList<Lot>> lotsOfAuction = new HashMap<>();
	HashMap<Auction, ArrayList<Lot>> lotNumbersOfAuction = new HashMap<>();
	HashMap<Integer, Integer> lotNumberWinners = new HashMap<>();
	ArrayList<Lot> finalLotDetailsWithBids = new ArrayList<>();
	HashMap<Integer, Integer> finalWinnersListBasedOnLots = new HashMap();
	ArrayList<Integer> lotNumbersEntered = new ArrayList<>();
	ArrayList<Auction> auctionChecked = new ArrayList<>();
	int sumOfValidBids;

	/*
	 * Method Description: Method to create an Auction 
	 * Arguments: Auction name, First Lot number, Last Lot number, Minimum Bid Increment Value 
	 * Argument types: String, int, int, int 
	 * Return type: Auction 
	 * Created by: Rushi Patel
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	Auction createAuction(String auctionName, int firstLotNumber, int lastLotNumber, int minBidIncrement) {

		/*To check if there are any special characters as an input I have referred the below tutorial website
		 *http://tutorials.jenkov.com/java-regex/index.html
		 *I have well understood the concept of Patterns and Matchers and have applied that concept to validate the auction name below
		*/
		Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(auctionName);
		boolean specialCharCheck = matcher.find();

		// check if it is supposed to return null
		if (auctionName == null || auctionName.isEmpty()) {
			System.out.println("Auction name cannot be null or empty!");
			return null;
		}
		//Validate special characters entered in auction name
		if (specialCharCheck) {
			System.out.println("Auction name cannot have special character!");
			return null;
		}
		if (firstLotNumber > lastLotNumber) {
			System.out.println("First lot number cannot be less than Last lot number!");
			return null;
		}

		// Validate first lot number and last lot number
		if (firstLotNumber <= 0 || lastLotNumber <= 0 || minBidIncrement <= 0) {
			System.out.println("Lot numbers or Minimum Bid Increment value cannot be 0 or negative in value!");
			return null;
		}

		// Validate lot numbers for auctions
		for (Auction auction : auctionList) {
			if (auction.getAuctionName().equals(auctionName)) {
				System.out.println("The auction with the name " + auctionName + " already exists!");
				return null;
			}
			if ((firstLotNumber <= auction.getLastLotNumber()) && (lastLotNumber >= auction.getFirstLotNumber())) {
				System.out.println("The lot number range is not valid as it is clashing with other auctions!");
				return null;
			}
		}
		//Creates an instance of Auction
		Auction auction = new Auction();
		
		//Setting appropriate data in the object created
		auction.setAuctionName(auctionName);
		auction.setFirstLotNumber(firstLotNumber);
		auction.setLastLotNumber(lastLotNumber);
		auction.setMinBidIncrement(minBidIncrement);
		auction.setAuctionStatus("new");
		auction.setAuctionNumber(auctionList.size() + 1);
		//Add the object in a list of Auctions
		auctionList.add(auction);
		//Create an instance of Lot
		Lot lot = new Lot();
		
		//To add particular lot numbers of an auction
		for (Auction auction2 : auctionList) {
			if (firstLotNumber >= auction2.getFirstLotNumber() && lastLotNumber <= auction2.getLastLotNumber()) {

				ArrayList<Integer> lotNumbers1 = new ArrayList<>();
				for (int i = firstLotNumber; i <= lastLotNumber; i++) {

					lotNumbers1.add(i);
					lotNumbersAuctionWise.put(auction, lotNumbers1);
					auction2.setLotNumbersAuctionWise(lotNumbersAuctionWise);
				}

			}
		}

		
		////Setting appropriate data in the object created
		lot.setLotNumbers(lotNumbers);
		auction.setLotNumbers(lotNumbers);
		Auction.fetchAuctionBasedLots(lotNumbersAuctionWise);
		Auction.fetchAuctionList(auctionList);
		return auction;
	}

	/*
	 * Method Description: Method to create a Bidder 
	 * Arguments: Bidder name
	 * Argument types: String 
	 * Return type: Auction 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public Bidder createBidder(String name) {
		/*To check if there are any special characters as an input I have referred the below tutorial website
		 *http://tutorials.jenkov.com/java-regex/index.html
		 *I have well understood the concept of Patterns and Matchers and have applied that concept to validate the bidder name below
		*/
		Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(name);
		boolean specialCharCheck = matcher.find();

		// Validate Bidder Name
		if (name == null || name.isEmpty()) {
			System.out.println("Bidder name cannot be null or empty!");
			return null;
		}
		//Validate special characters entered in bidder name
		if (specialCharCheck) {
			System.out.println("Bidder name cannot have special characters!");
			return null;
		}
		//Validate if the bidder already exists with the same name
		for (Bidder bidder : bidderList) {
			if (bidder.getBidderName().equalsIgnoreCase(name)) {
				System.out.println("Bidder with name " + name + " already exists!");
				return null;
			}
		}
		//Creating an instance of Bidder class
		Bidder bidder = new Bidder();
		//Adding this bidder to the bidder list after setting the data for this bidder
		int bidderCount = bidderList.size();
		bidder.setBidderName(name);
		bidderCount++;
		bidder.setBidderId(bidderCount);
		bidderList.add(bidder);
		return bidder;
	}
	
	/*
	 * Method Description: Method to view the status of the auctions 
	 * Arguments:No Arguments 
	 * Argument types: No Arguments 
	 * Return type: String 
	 * Created by:Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public String auctionStatus() {
		//Variable to check if the auction is covered in the loop
		int check = 0;
		String returnString = new String();
		// Input Validations
		if (auctionList.isEmpty()) {
			return "";
		}

		for (Auction auction : auctionList) {
			if (check != 0) {
				sumOfValidBids = 0;
				lotNumbersEntered.clear();
			}

			for (Lot lot : finalLotDetailsWithBids) {
				if ((lot.getLotNumber() >= auction.getFirstLotNumber())
						&& (lot.getLotNumber() <= auction.getLastLotNumber())) {
					if (lotNumbersEntered.contains(lot.getLotNumber())) {
						continue;
					}
					sumOfValidBids = sumOfValidBids + lot.getLastValidBid();

					lotNumbersEntered.add(lot.getLotNumber());

				}
			}
			check++;
			
			returnString = returnString + auction.getAuctionName() + "\t" + auction.getAuctionStatus() + "\t"
					+ (sumOfValidBids) + "\n";
		}

		return returnString;
	}

	/*
	 * Method Description: Method to retrieve the number of successfully placed bids 
	 * Arguments: Filename 
	 * Argument types: String 
	 * Return type: Integer
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public int loadBids(String filename) {
		int totalBidsRecorded = 0;
		// Validate Filename
		if (filename == null || filename.isEmpty()) {
			System.out.println("Filename cannot be null or empty!");
			return 0;
		}

		//Create a new file variable
		File file = new File(filename);
		if(!file.exists()){
			System.out.println("File " + filename + " does not exist!");
			return 0;
		}
		if (file.length() == 0) {
			System.out.println("File has 0 lines in it!");
			return 0;
		}
		try {
			//Initialised buffered reader object with the given file
			BufferedReader bidReader = new BufferedReader(new FileReader(file));
			String line;
			//Read the data of the file until the end of line
			while ((line = bidReader.readLine()) != null) {

				String[] tokens = line.split("\t");
				
				//Array to store the data from the file
				int[] storeData = new int[tokens.length];

				int i = 0;
				//Validate if the characters exceed 80 characters
				for (String token : tokens) {
					if(token.length()>80){
						System.out.println("A line in the file of bids can at most have 80 chars!");
						return 0;
					}
					storeData[i++] = Integer.parseInt(token);
				}

				Bidder bidder = new Bidder();
				// storeData[0]->Bidder ID
				// storeData[1]->Lot number
				// storeData[2]->bid
				bidder.setBidderId(storeData[0]);
				bidder.setLotNumber(storeData[1]);
				bidder.setCurrentBid(storeData[2]);
				//Adding the data to the list of incoming bids from the file
				bidDetailsFinal.add(storeData);
				int bidderId = storeData[0];
				int lotNumber = storeData[1];
				int bid = storeData[2];
				ArrayList<Integer> lotNumbersList = new ArrayList<>();
				ArrayList<Integer> bidderIDCreatedList = new ArrayList<>();
				//Store all the bidder id's from the file in a list
				for (Bidder bidderCreated : bidderList) {
					bidderIDCreatedList.add(bidderCreated.getBidderId());
				}
				
				//Initialize the winning bid as 0
				int lastValidBid = 0;
				/*Check for every auction and their particular lot numbers if the lot number falls in that auction
				//If so checks if the bid falls as a successful bid on that lot or not
				*/
				for (Auction auction : auctionList) {
					if(auction.getAuctionStatus()=="open"){
					for (int j = auction.getFirstLotNumber(); j <= auction.getLastLotNumber(); j++) {
						lotNumbersList.add(j);
					}

					if ((lotNumber >= auction.getFirstLotNumber()) && (lotNumber <= auction.getLastLotNumber())) {

						if (lastValidBid == 0) {
							if (bid < auction.getMinBidIncrement() || !(bidderIDCreatedList.contains(bidderId))) {

							} else {
								lastValidBid = auction.getMinBidIncrement();

								totalBidsRecorded++;
							}
						} else {
							if (bid < auction.getMinBidIncrement() || !(bidderIDCreatedList.contains(bidderId))) {

							} else {
								lastValidBid = lastValidBid + auction.getMinBidIncrement();

								totalBidsRecorded++;
							}

							if (!(bid < (auction.getMinBidIncrement() + lastValidBid))) {
								totalBidsRecorded++;
							}

						}

					}
				}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		System.out.println("Total Valid Bids in the file: " + totalBidsRecorded);
		return totalBidsRecorded;
	}

	/*
	 * Method Description: Method to place a bid 
	 * Arguments: Lot number, Bidder ID, Bid 
	 * Argument types: int, int, int 
	 * Return type: Integer 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public int placeBid(int lotNumber, int bidderId, int bid) {

		int returnValue = 0;
		ArrayList<Integer> bidderIdList = new ArrayList<>();
		// Validate inputs
		if (bidderId <= 0) {
			System.out.println("Bidder ID cannot be 0 or negative in value!");
			return 0;
		}
		if (lotNumber <= 0) {
			System.out.println("Lot Number cannot be 0 or negative in value!");
			return 0;
		}
		if (bid <= 0) {
			System.out.println("Bid cannot be 0 or negative in value!");
			return 0;
		}
		//Add the incoming bidder id from the argument in a list
		for (Bidder bidder : bidderList) {
			bidderIdList.add(bidder.getBidderId());
		}
		//Verify in each auction if the lotnumber falls in a particular auction
		for (Auction auction : auctionList) {
			
			if (lotNumber >= auction.getFirstLotNumber() && lotNumber <= auction.getLastLotNumber()) {
				//Validate if the auction status is new or open
				if (auction.getAuctionStatus() == "new" || auction.getAuctionStatus() == "closed") {
					System.out.println("Sorry! Can only place bid for an open auction. The auction "
							+ auction.getAuctionName() + " is " + auction.getAuctionStatus());
					return 0;
				}
				//Store all the lot numbers of a particular auction in a list
				lotNumbersAuctionWise.get(auction);
				for (int i = 0; i < lotNumbersAuctionWise.get(auction).size(); i++) {
					Lot lot = new Lot();

					lot.setLotNumber(lotNumber);
					lot.setMinBidIncrement(auction.getMinBidIncrement());

					lotNumbersOfOneAuction.add(lot);
				}
				for (Lot lot : lotNumbersOfOneAuction) {
					if (lot.getLotNumber() == lotNumber) {
						//If the bid is not the multiple of minimum increment value then the system will automatically assign that bid to be the closest multiple of minimum increment value, which is less than the bid placed currently.
						//example: bid value $7 becomes $5 and bid value $53 becomes $50 where the minimum increment value is $5.
						int bid1 = bid;
						if ((bid1 % (lot.getMinBidIncrement()) != 0)) {
							bid1 = ((int) (bid1 / lot.getMinBidIncrement())) * lot.getMinBidIncrement();
						}
						
						//-------------------------Condition where the bid is not accepted-----------------//
						//The bid cannot be accepted if it is less than the sum of last valid bid and minimum increment value.
						if (bid1 < (lot.getMinBidIncrement() + lot.getLastValidBid())) {
							System.out.println("The bid cannot be accepted.");
							returnValue = 1;
							return 1;
						}
						//------------------Conditions where the bids are accepted--------------------------//
						//To set the remembered bid and the final winner till this point of time.
						else if (bid1 >= (lot.getLastValidBid() + lot.getMinBidIncrement())
								&& bid1 > lot.getCurrentWiningBid()) {
							//Bid is invalid if the bid is less than the sum of minimum bid increment value and last valid winning bid of the lot
							if (bid1 < (lot.getMinBidIncrement() + lot.getLastValidBid())) {
								returnValue = 1;
							}
							//Bid is accepted, but is not the currently leading bid for the lot
							if (bid1 <= lot.getCurrentWiningBid()) {
								returnValue = 2;
							}
							//Bid is accepted, is the leading bid for the lot, and there will be no further automatic bids made for this bidder on the lot.
							if ((bid1 == (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid()))) {
								returnValue = 3;
							}
							//Bid is accepted, is the leading bid for the lot, but there is still room for an automatic bid to be made in the future on behalf of this bidder
							if (bid1 > (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid())) {
								returnValue = 4;
							}
							//If the current bidder is not the final winner then remembered bid value will be the current bid value and 
							//current bidder will be the final winner but the last valid bid will be the sum of last remembered bid value and 
							//minimum increment value.
							if (bidderId != lot.getFinalWinner()) {
								lot.setLastValidBid(lot.getCurrentWiningBid() + lot.getMinBidIncrement());
								lot.setFinalWinnerId(bidderId);
								lot.setCurrentWiningBid(bid1);
								lot.setFinalWinner(bidderId);

								for (Bidder bidder : bidderList) {
									if (bidder.getBidderId() == bidderId) {
										bidder.setLastValidBid(lot.getCurrentWiningBid() + lot.getMinBidIncrement());
										bidder.setFinalWinnerId(bidderId);
										bidder.setCurrentWiningBid(bid1);
										bidder.setFinalWinner(bidderId);
									}
								}

							} 
							//If the current bidder is the final winner then remembered bid value will be the current bid value and 
							//current bidder will be the final winner but the new last valid bid will be the sum of last valid bid value and 
							//minimum increment value.
							else {

								lot.setCurrentWiningBid(bid1);
								lot.setFinalWinnerId(bidderId);
								lot.setLastValidBid(lot.getLastValidBid() + lot.getMinBidIncrement());
								lot.setFinalWinner(bidderId);

								for (Bidder bidder : bidderList) {
									if (bidder.getBidderId() == bidderId) {
										bidder.setCurrentWiningBid(bid1);
										bidder.setFinalWinnerId(bidderId);
										bidder.setLastValidBid(lot.getLastValidBid() + lot.getMinBidIncrement());
										bidder.setFinalWinner(bidderId);
										bidder.setNumberOfWinningLots(bidder.getNumberOfWinningLots() + 1);
									}
								}
							}
						}
						//If current bid is greater than the sum of last valid bid and minimum increment value and 
						//less than remembered bid, then the new last valid bid will be the summation of current bid and 
						//minimum increment value and the final winner will remain the same as it was.
						else if (bid1 >= (lot.getLastValidBid() + lot.getMinBidIncrement())
								&& bid1 < lot.getCurrentWiningBid()) {
							//Bid is invalid if the bid is less than the sum of minimum bid increment value and last valid winning bid of the lot
							if (bid1 < (lot.getMinBidIncrement() + lot.getLastValidBid())) {
								returnValue = 1;
							}
							//Bid is accepted, but is not the currently leading bid for the lot
							if (bid1 <= lot.getCurrentWiningBid()) {
								returnValue = 2;
							}
							//Bid is accepted, is the leading bid for the lot, and there will be no further automatic bids made for this bidder on the lot.
							if ((bid1 == (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid()))) {
								returnValue = 3;
							}
							//Bid is accepted, is the leading bid for the lot, but there is still room for an automatic bid to be made in the future on behalf of this bidder
							if (bid1 > (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid())) {
								returnValue = 4;
							}

							lot.setLastValidBid(bid1);
							lot.setFinalWinner(lot.getFinalWinnerId());

							for (Bidder bidder : bidderList) {
								if (bidder.getBidderId() == bidderId) {
									bidder.setLastValidBid(bid1);
									bidder.setFinalWinner(lot.getFinalWinnerId());
								}
							}

						}
						//If current bid is greater than the sum of last valid bid and minimum increment value is equal to 
						//remembered bid, then the new last valid bid will be equal to current bid and the final winner will remain the same as it was.
						else if (bid1 >= (lot.getLastValidBid() + lot.getMinBidIncrement())
								&& bid1 == lot.getCurrentWiningBid()) {
							//Bid is invalid if the bid is less than the sum of minimum bid increment value and last valid winning bid of the lot
							if (bid1 < (lot.getMinBidIncrement() + lot.getLastValidBid())) {
								returnValue = 1;
							}
							//Bid is accepted, but is not the currently leading bid for the lot
							if (bid1 <= lot.getCurrentWiningBid()) {
								returnValue = 2;
							}
							//Bid is accepted, is the leading bid for the lot, and there will be no further automatic bids made for this bidder on the lot.
							if ((bid1 == (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid()))) {
								returnValue = 3;
							}
							//Bid is accepted, is the leading bid for the lot, but there is still room for an automatic bid to be made in the future on behalf of this bidder
							if (bid1 > (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid())) {
								returnValue = 4;
							}
							
							lot.setLastValidBid(bid1);
							lot.setFinalWinner(lot.getFinalWinnerId());

							for (Bidder bidder : bidderList) {
								if (bidder.getBidderId() == bidderId) {
									bidder.setLastValidBid(bid1);
									bidder.setFinalWinner(lot.getFinalWinnerId());
								}
							}

						}
						//If current bid is equal to the sum of last valid bid and minimum increment value is equal to 
						//remembered bid, then the new last valid bid will be equal to current bid and the final winner will remain the same as it was.
						else if (bid1 == (lot.getLastValidBid() + lot.getMinBidIncrement())
								&& bid1 == lot.getCurrentWiningBid()) {
							//Bid is invalid if the bid is less than the sum of minimum bid increment value and last valid winning bid of the lot
							if (bid1 < (lot.getMinBidIncrement() + lot.getLastValidBid())) {
								returnValue = 1;
							}
							//Bid is accepted, but is not the currently leading bid for the lot
							if (bid1 <= lot.getCurrentWiningBid()) {
								returnValue = 2;
							}
							//Bid is accepted, is the leading bid for the lot, and there will be no further automatic bids made for this bidder on the lot.
							if ((bid1 == (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid()))) {
								returnValue = 3;
							}
							//Bid is accepted, is the leading bid for the lot, but there is still room for an automatic bid to be made in the future on behalf of this bidder
							if (bid1 > (lot.getLastValidBid() + lot.getMinBidIncrement())
									&& (bid1 > lot.getCurrentWiningBid())) {
								returnValue = 4;
							}
							
							lot.setLastValidBid(bid1);
							lot.setFinalWinner(lot.getFinalWinnerId());

							for (Bidder bidder : bidderList) {
								if (bidder.getBidderId() == bidderId) {
									bidder.setLastValidBid(bid1);
									bidder.setFinalWinner(lot.getFinalWinnerId());
								}
							}

						}
						
						//Printing the output for the current placed bid based on the above business logic
						System.out.println("The winning bid as of now for the lot " + lot.getLotNumber() + " of Auction "
								+ auction.getAuctionName() + " is: " + lot.getLastValidBid());
						System.out.println("The winner as of now of the lot " + lot.getLotNumber() + " of Auction "
								+ auction.getAuctionName() + " is" + lot.getFinalWinnerId());
						
						//Add the lot object with all the bid details
						finalLotDetailsWithBids.add(lot);

						Auction.fetchDetailsOfLots(finalLotDetailsWithBids);
						
						finalWinnersListBasedOnLots.put(lot.getLotNumber(), lot.getFinalWinnerId());
						
						break;
					}
				}

			}

		}
		return returnValue;
	}

	/*
	 * Method Description: Method to report on the state of each bidder on closed auctions 
	 * Arguments: No Arguments 
	 * Argument types: No Arguments
	 * Return type: String 
	 * Created by: Rushi Patel 
	 * Last Modified Date:22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public String feesOwed() {
		//Verify if the there are any bidders already created.
		if (bidderList.isEmpty()) {
			System.out.println("There are no bidders listed!");
			return "";
		}
		
		//Initializing objects for data management
		String winningDetails = new String();
		HashMap<Auction, ArrayList<Integer>> lotNumbersAuctionWise = new HashMap<>();
		ArrayList<Integer> lotNumbers = new ArrayList<>();
		ArrayList<Integer> lotNumberChecked = new ArrayList<>();
		ArrayList<Integer> numberOfWinningLots = new ArrayList<>();

		//Verify for all the auctions
		for (Auction auction : auctionList) {
			//Checks if the auction is already processed
			if (auctionChecked.contains(auction)) {
				continue;
			}
			//Verify if the status of the current auction is closed
			if (auction.getAuctionStatus() == "closed") {

				lotNumbersAuctionWise = auction.getLotNumbersAuctionWise();
				lotNumbers = lotNumbersAuctionWise.get(auction);
				//For all the bids made so far on multiple lots will process the sum of bids
				for (Lot lot : finalLotDetailsWithBids) {
					//Checks if the Lot number is already processed
					if (lotNumberChecked.contains(lot.getLotNumber())) {

						continue;
					} else {
						/*After satisfying the above validations, 
						/will check all the bidders and add the sum of the 
						/winning amount along with the number of winning lots
						 */
						for (Bidder bidder : bidderList) {

							if (lotNumbers.contains(lot.getLotNumber())) {
								if (bidder.getBidderId() == lot.getFinalWinner()) {
									if (auction.getAuctionStatus() == "closed") {
										int sumOfBids = 0;
										sumOfBids = bidder.getClosedAuctionSumOfWinningBids() + lot.getLastValidBid();
										bidder.setClosedAuctionSumOfWinningBids(sumOfBids);
										numberOfWinningLots.add(lot.getLotNumber());
										bidder.setWinningLotsTotal(bidder.getWinningLotsTotal() + 1);
										bidder.setWinningLots(numberOfWinningLots);
										lotNumberChecked.add(lot.getLotNumber());
									}
								}
							}
						}
					}
				}
				//Once an auction is processed, will add that auction to the list of checked auctions
				auctionChecked.add(auction);

			}
		}
		//Used to append the return string based on all the bidders created
		for (Bidder bidder : bidderList) {
			
			winningDetails = winningDetails + bidder.getBidderName() + "\t" + bidder.getWinningLotsTotal() + "\t"
					+ bidder.getClosedAuctionSumOfWinningBids() + "\n";

		}

		return winningDetails;
	}

	/**
	 * @return the finalWinnersListBasedOnLots
	 */
	public HashMap<Integer, Integer> getFinalWinnersListBasedOnLots() {
		return finalWinnersListBasedOnLots;
	}

	/**
	 * @param finalWinnersListBasedOnLots
	 *            the finalWinnersListBasedOnLots to set
	 */
	public void setFinalWinnersListBasedOnLots(HashMap<Integer, Integer> finalWinnersListBasedOnLots) {
		this.finalWinnersListBasedOnLots = finalWinnersListBasedOnLots;
	}

	/**
	 * @return the bidderList
	 */
	public ArrayList<Bidder> getBidderList() {
		return bidderList;
	}

	/**
	 * @param bidderList
	 *            the bidderList to set
	 */
	public void setBidderList(ArrayList<Bidder> bidderList) {
		this.bidderList = bidderList;
	}

	/**
	 * @return the auctionList
	 */
	public ArrayList<Auction> getAuctionList() {
		return auctionList;
	}

	/**
	 * @param auctionList
	 *            the auctionList to set
	 */
	public void setAuctionList(ArrayList<Auction> auctionList) {
		this.auctionList = auctionList;
	}

	/**
	 * @return the bidDetailsList
	 */
	public ArrayList<Integer> getBidDetailsList() {
		return bidDetailsList;
	}

	/**
	 * @param bidDetailsList
	 *            the bidDetailsList to set
	 */
	public void setBidDetailsList(ArrayList<Integer> bidDetailsList) {
		this.bidDetailsList = bidDetailsList;
	}

	/**
	 * @return the bidDetailsIndividualList
	 */
	public ArrayList<Integer> getBidDetailsIndividualList() {
		return bidDetailsIndividualList;
	}

	/**
	 * @param bidDetailsIndividualList
	 *            the bidDetailsIndividualList to set
	 */
	public void setBidDetailsIndividualList(ArrayList<Integer> bidDetailsIndividualList) {
		this.bidDetailsIndividualList = bidDetailsIndividualList;
	}

	/**
	 * @return the bidDetailsFinal
	 */
	public ArrayList<int[]> getBidDetailsFinal() {
		return bidDetailsFinal;
	}

	/**
	 * @param bidDetailsFinal
	 *            the bidDetailsFinal to set
	 */
	public void setBidDetailsFinal(ArrayList<int[]> bidDetailsFinal) {
		this.bidDetailsFinal = bidDetailsFinal;
	}

	/**
	 * @return the lotDetailsList
	 */
	public ArrayList<Lot> getLotDetailsList() {
		return lotDetailsList;
	}

	/**
	 * @param lotDetailsList
	 *            the lotDetailsList to set
	 */
	public void setLotDetailsList(ArrayList<Lot> lotDetailsList) {
		this.lotDetailsList = lotDetailsList;
	}

	/**
	 * @return the lotNumbers
	 */
	public ArrayList<Integer> getLotNumbers() {
		return lotNumbers;
	}

	/**
	 * @param lotNumbers
	 *            the lotNumbers to set
	 */
	public void setLotNumbers(ArrayList<Integer> lotNumbers) {
		this.lotNumbers = lotNumbers;
	}

	/**
	 * @return the lotNumbersAuctionWise
	 */
	public HashMap<Auction, ArrayList<Integer>> getLotNumbersAuctionWise() {
		return lotNumbersAuctionWise;
	}

	/**
	 * @param lotNumbersAuctionWise
	 *            the lotNumbersAuctionWise to set
	 */
	public void setLotNumbersAuctionWise(HashMap<Auction, ArrayList<Integer>> lotNumbersAuctionWise) {
		this.lotNumbersAuctionWise = lotNumbersAuctionWise;
	}

	/**
	 * @return the lotNumbersOfOneAuction
	 */
	public ArrayList<Lot> getLotNumbersOfOneAuction() {
		return lotNumbersOfOneAuction;
	}

	/**
	 * @param lotNumbersOfOneAuction
	 *            the lotNumbersOfOneAuction to set
	 */
	public void setLotNumbersOfOneAuction(ArrayList<Lot> lotNumbersOfOneAuction) {
		this.lotNumbersOfOneAuction = lotNumbersOfOneAuction;
	}

	/**
	 * @return the lotNumbersOfAuction
	 */
	public HashMap<Auction, ArrayList<Lot>> getLotNumbersOfAuction() {
		return lotNumbersOfAuction;
	}

	/**
	 * @param lotNumbersOfAuction
	 *            the lotNumbersOfAuction to set
	 */
	public void setLotNumbersOfAuction(HashMap<Auction, ArrayList<Lot>> lotNumbersOfAuction) {
		this.lotNumbersOfAuction = lotNumbersOfAuction;
	}

	/**
	 * @return the lotNumberWinners
	 */
	public HashMap<Integer, Integer> getLotNumberWinners() {
		return lotNumberWinners;
	}

	/**
	 * @param lotNumberWinners
	 *            the lotNumberWinners to set
	 */
	public void setLotNumberWinners(HashMap<Integer, Integer> lotNumberWinners) {
		this.lotNumberWinners = lotNumberWinners;
	}

	/**
	 * @return the finalLotDetailsWithBids
	 */
	public ArrayList<Lot> getFinalLotDetailsWithBids() {
		return finalLotDetailsWithBids;
	}

	/**
	 * @param finalLotDetailsWithBids
	 *            the finalLotDetailsWithBids to set
	 */
	public void setFinalLotDetailsWithBids(ArrayList<Lot> finalLotDetailsWithBids) {
		this.finalLotDetailsWithBids = finalLotDetailsWithBids;
	}

	/**
	 * @return the lot
	 */
	public Lot getLot() {
		return lot;
	}

	/**
	 * @param lot
	 *            the lot to set
	 */
	public void setLot(Lot lot) {
		this.lot = lot;
	}

}

/*
 * The class Auction is used to store and retrieve specific Auction details in
 * its particular object. 
 * Made by: Rushi Patel 
 * Last Modified Date: 22-September-2021
 */
@SuppressWarnings("unchecked")
class Auction extends OnlineAuctionSystem {

	private String auctionName;
	public int auctionNumber;
	private int firstLotNumber = 0;
	private int lastLotNumber = 0;
	private int minBidIncrement = 0;
	private String auctionStatus;
	private int currentBid = 0;
	private int lastValidBid = 0;
	private Bidder bidder;
	private int currentWiningBid = 0;
	private int finalWinnerId = 0;
	// To keep flag on Winner in code
	private int finalWinner = 0;
	private ArrayList<Integer> lotNumbers;
	HashMap<Auction, ArrayList<Integer>> lotNumbersAuctionWise;
	static ArrayList<Lot> lotWithBidDetailsList;
	static ArrayList<Bidder> allDetailsWithBidderList;
	static HashMap<Auction, ArrayList<Integer>> lotNumbersAuctionBased;
	static ArrayList<Auction> auctionListFetch;
	static Map<Integer, Integer> bidAndBidderId;
	static Map<Integer, Map<Integer, Integer>> winningBid;

	/**
	 * @return the lotNumbersAuctionWise
	 */
	public HashMap<Auction, ArrayList<Integer>> getLotNumbersAuctionWise() {
		return lotNumbersAuctionWise;
	}

	/**
	 * @param lotNumbersAuctionWise
	 *            the lotNumbersAuctionWise to set
	 */
	public void setLotNumbersAuctionWise(HashMap<Auction, ArrayList<Integer>> lotNumbersAuctionWise) {
		this.lotNumbersAuctionWise = lotNumbersAuctionWise;
	}

	/**
	 * @return the lotNumbers
	 */
	public ArrayList<Integer> getLotNumbers() {
		return lotNumbers;
	}

	/**
	 * @param lotNumbers
	 *            the lotNumbers to set
	 */
	public void setLotNumbers(ArrayList<Integer> lotNumbers) {
		this.lotNumbers = lotNumbers;
	}

	/**
	 * @return the auctionNumber
	 */
	public int getAuctionNumber() {
		return auctionNumber;
	}

	/**
	 * @param auctionNumber
	 *            the auctionNumber to set
	 */
	public void setAuctionNumber(int auctionNumber) {
		this.auctionNumber = auctionNumber;
	}

	public String getAuctionName() {
		return auctionName;
	}

	public void setAuctionName(String auctionName) {
		this.auctionName = auctionName;
	}

	public int getFirstLotNumber() {
		return firstLotNumber;
	}

	public void setFirstLotNumber(int firstLotNumber) {
		this.firstLotNumber = firstLotNumber;
	}

	public int getLastLotNumber() {
		return lastLotNumber;
	}

	public void setLastLotNumber(int lastLotNumber) {
		this.lastLotNumber = lastLotNumber;
	}

	public int getMinBidIncrement() {
		return minBidIncrement;
	}

	public void setMinBidIncrement(int minBidIncrement) {
		this.minBidIncrement = minBidIncrement;
	}

	public boolean openAuction() {
		// TODO Auto-generated method stub
		boolean check = false;

		if (this.getAuctionStatus().equals("new")) {
			this.setAuctionStatus("open");
			System.out.println("Auction " + this.getAuctionName() + " open");
			check = true;
		} else if (this.getAuctionStatus() == "closed") {
			System.out.println("Cannot reopen closed auction!");

		} else if (this.getAuctionStatus().equals("open")) {
			System.out.println("Cannot open an already open auction!");
		}

		return check;
	}

	public boolean closeAuction() {
		// TODO Auto-generated method stub
		boolean check = false;
		if (this.getAuctionStatus().equals("open")) {
			System.out.println("Auction " + this.getAuctionName() + " closed");
			this.setAuctionStatus("closed");
			check = true;
		} else {
			System.out.println("Cannot Close autions that aren't open!");
		}
		return check;
	}

	/*
	 * Method Description: Method to fetch Winning Bid Details from Online Auction System class 
	 * Arguments: Lot number, Last Valid Bid, Bidder ID
	 * Argument types: int, int, int Return type:
	 * Map<Integer,Map<Integer,Integer>> 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	static Map<Integer, Map<Integer, Integer>> fetchWinningBidsDetails(int lotNumber, int lastValidBid, int bidderId) {
		bidAndBidderId.put(lotNumber, bidderId);
		winningBid.put(lotNumber, bidAndBidderId);
		return winningBid;
	}

	/*
	 * Method Description: Method to fetch Lots of particular Auction from Online Auction System class 
	 * Arguments: Auction Based Lots 
	 * Argument types: HashMap<Auction, ArrayList<Integer>> 
	 * Return type: HashMap<Auction, ArrayList<Integer>> 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	static HashMap<Auction, ArrayList<Integer>> fetchAuctionBasedLots(
			HashMap<Auction, ArrayList<Integer>> fetchAuctionBasedLots) {
		lotNumbersAuctionBased = (HashMap<Auction, ArrayList<Integer>>) fetchAuctionBasedLots.clone();

		return lotNumbersAuctionBased;
	}

	/*
	 * Method Description: Method to fetch Auction List from Online Auction System class 
	 * Arguments: Auction List 
	 * Argument types: ArrayList<Auction>
	 * Return type: ArrayList<Auction> 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	static ArrayList<Auction> fetchAuctionList(ArrayList<Auction> fetchAuctionList) {
		auctionListFetch = (ArrayList<Auction>) fetchAuctionList.clone();

		return fetchAuctionList;
	}

	/*
	 * Method Description: Method to fetch Details of Lots With Bids from Online Auction System class 
	 * Arguments: Lots with Bid Details 
	 * Argument types: ArrayList<Lot> 
	 * Return type: ArrayList<Lot> 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	static ArrayList<Lot> fetchDetailsOfLots(ArrayList<Lot> lotWithBids) {
		lotWithBidDetailsList = (ArrayList<Lot>) lotWithBids.clone();

		return lotWithBidDetailsList;
	}

	/*
	 * Method Description: Method to print all the Lots of the current Auction
	 * with their respective winning bids 
	 * Arguments: No Arguments 
	 * Argument types: No Arguments 
	 * Return type: String 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public String winningBids() {
		//Created a string object used to append the return strings based on the number of bids made on lots
		String returnString = new String();
		//Created a lot to segregate only unique lots
		ArrayList<Lot> uniqueLots = new ArrayList<>();
		//Created a lot to segregate only unique lot numbers
		ArrayList<Integer> uniqueLotNumbers = new ArrayList<>();
		int check = 0;
		//Validate if the list is already empty and there are no bids placed so far
		if(lotWithBidDetailsList == null){
			System.out.println("No Bids placed so far!");
			return null;
		}
		//Loop in and would add unique lots to the list
		for (Lot lot : lotWithBidDetailsList) {
			if (uniqueLots.isEmpty()) {
				uniqueLots.add(lot);
				check++;
			} else if (lot.getLotNumber() == uniqueLots.get(check - 1).getLotNumber()) {
				// check++;
				continue;
			} else {
				uniqueLots.add(lot);
				check++;
			}
		}
		int firstLotNumber = this.firstLotNumber;
		int lastLotNumber = this.lastLotNumber;
		//Loop in and would add unique lot numbers to the list
		for (Lot lot : uniqueLots) {
			uniqueLotNumbers.add(lot.getLotNumber());
		}
		//Loop to store the lot numbers of a particular auction
		ArrayList<Integer> lotNumberAuctionWise = new ArrayList<>();
		for (int i = 0; i < uniqueLots.size(); i++) {
			if ((firstLotNumber <= uniqueLotNumbers.get(i)) && (lastLotNumber >= uniqueLotNumbers.get(i))) {
				lotNumberAuctionWise.add(uniqueLotNumbers.get(i));
			}
			
		}
		
		//Store the required data in a HashMap
		HashMap<Integer, ArrayList<Integer>> lotDetails = new HashMap<Integer, ArrayList<Integer>>();
		for (Lot lot : uniqueLots) {
			ArrayList<Integer> bidDetails = new ArrayList<>();
			bidDetails.add(lot.getLastValidBid());
			bidDetails.add(lot.getFinalWinnerId());
			lotDetails.put(lot.getLotNumber(), bidDetails);
		}
		
		//Verify if the hashmap contains the lot numbers fetched and if so add the lot number to the list
		int lotNumberListIncrementor = 0;
		ArrayList<Integer> lotNumbers = new ArrayList<>();
		for (int i = this.getFirstLotNumber(); i <= this.getLastLotNumber(); i++) {
			ArrayList<Integer> keySet = new ArrayList<>(lotDetails.keySet());
			if (keySet.contains(i)) {
				lotNumbers.add(i);
				lotNumberListIncrementor++;

			}
		}
		
		//Loop and check if the given range of lot numbers from the auction matches with the lot numbers in the list of lot numbers
		int incrementValue = 0;
		for (int i = this.getFirstLotNumber(); i <= this.lastLotNumber; i++) {
			ArrayList<Integer> a1 = new ArrayList<>(lotDetails.keySet());
			if (lotNumbers.contains(i)) {
				ArrayList<Integer> abc = new ArrayList<>();
				abc = (lotDetails.get(lotNumbers.get(incrementValue)));
				int lastValidBid = abc.get(0);
				int winnerId = abc.get(1);
				returnString = returnString + i + "\t" + lastValidBid + "\t" + winnerId + "\n";
				incrementValue++;
			} else {
				returnString = returnString + i + "\t0\t0\n";
			}
		}

		return returnString;

	}

	/*
	 * Method Description: Method to print the status of all the Auctions that
	 * are created so far 
	 * Arguments: No Arguments 
	 * Argument types: No Arguments
	 * Return type: String 
	 * Created by: Rushi Patel 
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public String getAuctionStatus() {
		return auctionStatus;
	}

	/*
	 * Method Description: Method to set the status of an auction 
	 * Arguments:Status 
	 * Argument types: String 
	 * Return type: void 
	 * Created by: Rushi Patel
	 * Last Modified Date: 22-September-2021
	 */
	@SuppressWarnings("unchecked")
	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	/**
	 * @return the bidder
	 */
	public Bidder getBidder() {
		return bidder;
	}

	/**
	 * @param bidder
	 *            the bidder to set
	 */
	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	/**
	 * @return the currentBid
	 */
	public int getCurrentBid() {
		return currentBid;
	}

	/**
	 * @param currentBid
	 *            the currentBid to set
	 */
	public void setCurrentBid(int currentBid) {
		this.currentBid = currentBid;
	}

	/**
	 * @return the currentWiningBid
	 */
	public int getCurrentWiningBid() {
		return currentWiningBid;
	}

	/**
	 * @param currentWiningBid
	 *            the currentWiningBid to set
	 */
	public void setCurrentWiningBid(int currentWiningBid) {
		this.currentWiningBid = currentWiningBid;
	}

	/**
	 * @return the lastValidBid
	 */
	public int getLastValidBid() {
		return lastValidBid;
	}

	/**
	 * @param lastValidBid
	 *            the lastValidBid to set
	 */
	public void setLastValidBid(int lastValidBid) {
		this.lastValidBid = lastValidBid;
	}

	/**
	 * @return the finalWinnerId
	 */
	public int getFinalWinnerId() {
		return finalWinnerId;
	}

	/**
	 * @param finalWinnerId
	 *            the finalWinnerId to set
	 */
	public void setFinalWinnerId(int finalWinnerId) {
		this.finalWinnerId = finalWinnerId;
	}

	/**
	 * @return the finalWinner
	 */
	public int getFinalWinner() {
		return finalWinner;
	}

	/**
	 * @param finalWinner
	 *            the finalWinner to set
	 */
	public void setFinalWinner(int finalWinner) {
		this.finalWinner = finalWinner;
	}

}

/*
 * The class Bidder is used to store and retrieve specific Bidder details in its
 * particular object. 
 * Made by: Rushi Patel 
 * Last Modified Date: 22-September-2021
 */
class Bidder {

	private String bidderName;
	private int bidderId;
	private int currentBid = 0;
	private int lotNumber;
	private int numberOfWinningLots = 0;
	private ArrayList<Integer> winningLots;
	private Auction auction;
	private int closedAuctionSumOfWinningBids = 0;
	private int winningLotsTotal = 0;
	private int minBidIncrement = 0;
	private String auctionStatus;
	private int lastValidBid = 0;
	private Bidder bidder;
	private int currentWiningBid = 0;
	private int finalWinnerId = 0;
	private int finalWinner = 0;
	private int totalWinningBidsPerLots = 0;

	/**
	 * @return the closedAuctionSumOfWinningBids
	 */
	public int getClosedAuctionSumOfWinningBids() {
		return closedAuctionSumOfWinningBids;
	}

	/**
	 * @param closedAuctionSumOfWinningBids
	 *            the closedAuctionSumOfWinningBids to set
	 */
	public void setClosedAuctionSumOfWinningBids(int closedAuctionSumOfWinningBids) {
		this.closedAuctionSumOfWinningBids = closedAuctionSumOfWinningBids;
	}

	/**
	 * @return the winningLotsTotal
	 */
	public int getWinningLotsTotal() {
		return winningLotsTotal;
	}

	/**
	 * @param winningLotsTotal
	 *            the winningLotsTotal to set
	 */
	public void setWinningLotsTotal(int winningLotsTotal) {
		this.winningLotsTotal = winningLotsTotal;
	}

	/**
	 * @return the totalWinningBidsPerLots
	 */
	public int getTotalWinningBidsPerLots() {
		return totalWinningBidsPerLots;
	}

	/**
	 * @param totalWinningBidsPerLots
	 *            the totalWinningBidsPerLots to set
	 */
	public void setTotalWinningBidsPerLots(int totalWinningBidsPerLots) {
		this.totalWinningBidsPerLots = totalWinningBidsPerLots;
	}

	/**
	 * @return the minBidIncrement
	 */
	public int getMinBidIncrement() {
		return minBidIncrement;
	}

	/**
	 * @param minBidIncrement
	 *            the minBidIncrement to set
	 */
	public void setMinBidIncrement(int minBidIncrement) {
		this.minBidIncrement = minBidIncrement;
	}

	/**
	 * @return the auctionStatus
	 */
	public String getAuctionStatus() {
		return auctionStatus;
	}

	/**
	 * @param auctionStatus
	 *            the auctionStatus to set
	 */
	public void setAuctionStatus(String auctionStatus) {
		this.auctionStatus = auctionStatus;
	}

	/**
	 * @return the lastValidBid
	 */
	public int getLastValidBid() {
		return lastValidBid;
	}

	/**
	 * @param lastValidBid
	 *            the lastValidBid to set
	 */
	public void setLastValidBid(int lastValidBid) {
		this.lastValidBid = lastValidBid;
	}

	/**
	 * @return the bidder
	 */
	public Bidder getBidder() {
		return bidder;
	}

	/**
	 * @param bidder
	 *            the bidder to set
	 */
	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	/**
	 * @return the currentWiningBid
	 */
	public int getCurrentWiningBid() {
		return currentWiningBid;
	}

	/**
	 * @param currentWiningBid
	 *            the currentWiningBid to set
	 */
	public void setCurrentWiningBid(int currentWiningBid) {
		this.currentWiningBid = currentWiningBid;
	}

	/**
	 * @return the finalWinnerId
	 */
	public int getFinalWinnerId() {
		return finalWinnerId;
	}

	/**
	 * @param finalWinnerId
	 *            the finalWinnerId to set
	 */
	public void setFinalWinnerId(int finalWinnerId) {
		this.finalWinnerId = finalWinnerId;
	}

	/**
	 * @return the finalWinner
	 */
	public int getFinalWinner() {
		return finalWinner;
	}

	/**
	 * @param finalWinner
	 *            the finalWinner to set
	 */
	public void setFinalWinner(int finalWinner) {
		this.finalWinner = finalWinner;
	}

	public String getBidderName() {
		return bidderName;
	}

	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}

	public int getBidderId() {
		// TODO Auto-generated method stub
		return bidderId;

	}

	public void setBidderId(int bidderId) {
		this.bidderId = bidderId;
	}

	/**
	 * @return the auction
	 */
	public Auction getAuction() {
		return auction;
	}

	/**
	 * @param auction
	 *            the auction to set
	 */
	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	/**
	 * @return the lotNumber
	 */
	public int getLotNumber() {
		return lotNumber;
	}

	/**
	 * @param lotNumber
	 *            the lotNumber to set
	 */
	public void setLotNumber(int lotNumber) {
		this.lotNumber = lotNumber;
	}

	/**
	 * @return the numberOfWinningLots
	 */
	public int getNumberOfWinningLots() {
		return numberOfWinningLots;
	}

	/**
	 * @param numberOfWinningLots
	 *            the numberOfWinningLots to set
	 */
	public void setNumberOfWinningLots(int numberOfWinningLots) {
		this.numberOfWinningLots = numberOfWinningLots;
	}

	/**
	 * @return the currentBid
	 */
	public int getCurrentBid() {
		return currentBid;
	}

	/**
	 * @param currentBid
	 *            the currentBid to set
	 */
	public void setCurrentBid(int currentBid) {
		this.currentBid = currentBid;
	}

	/**
	 * @return the winningLots
	 */
	public ArrayList<Integer> getWinningLots() {
		return winningLots;
	}

	/**
	 * @param winningLots
	 *            the winningLots to set
	 */
	public void setWinningLots(ArrayList<Integer> winningLots) {
		this.winningLots = winningLots;
	}

}
