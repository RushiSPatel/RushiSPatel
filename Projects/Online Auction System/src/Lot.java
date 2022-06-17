import java.util.ArrayList;

/*
* The class Lot is used to store and retrieve specific Lot details in
* its particular object. 
* Made by: Rushi Patel 
* Last Modified Date: 22-September-2021
*/
public class Lot {

	private int lotNumber;
	private int minBidIncrement = 0;
	private String auctionStatus;
	private int currentBid = 0;
	private int lastValidBid = 0;
	private Bidder bidder;
	private int currentWiningBid = 0;
	private int finalWinnerId = 0;
	private int finalWinner = 0;
	private int bidderId = 0;

	private ArrayList<Integer> lotNumbers;

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
	 * @return the bidderId
	 */
	public int getBidderId() {
		return bidderId;
	}

	/**
	 * @param bidderId
	 *            the bidderId to set
	 */
	public void setBidderId(int bidderId) {
		this.bidderId = bidderId;
	}

}
