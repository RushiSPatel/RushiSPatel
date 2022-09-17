import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class BuildTransaction {
	
	public void startLocalInstance(){
		
		try {
		System.out.println("Starting connection with my local sql instance...");
		
		DBConnectionUtilLocal.getInstance();
		
		System.out.println("Connected with local sql instance!");
		
		String setAutoCommitOffQuery = "SET autocommit=0;";
		
		DBConnectionUtilLocal.getInstance().updateData(setAutoCommitOffQuery);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startGCPInstance(){
		
		try {
		System.out.println("Starting connection with GCP instance...");
		
		DBConnectionUtilGCP.getInstance();
		
		System.out.println("Connected with GCP instance!");
		
		String setAutoCommitOffQuery = "SET autocommit=0;";
		
		DBConnectionUtilGCP.getInstance().updateData(setAutoCommitOffQuery);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public boolean startDistributedTransaction() {
		boolean validateTransaction = false;
		try {
			
		startLocalInstance();
		
		Instant start = Instant.now();
		System.out.println("Firing queries from local instance affecting 5 tables in database...");
		String localInstanceFirstTableTransactionQuery = "insert into olist_order_payments_dataset values('dummy_order_id_task4', 2, 'dummy_payment_type_task4', 2, 56.23);";
		String localInstanceSecondTableTransactionQuery = "insert into product_category_name_translation values('dummy_product_category_name_task4','dummy_product_category_name_english_task4');";
		String localInstanceThirdTableTransactionQuery = "select * from olist_orders_dataset where order_id='53cdb2fc8bc7dce0b6741e2150273451';";
		String localInstanceFourthTableTransactionQuery = "select * from olist_products_dataset where product_id='3aa071139cb16b67ca9e5dea641aaa2f';";
		String localInstanceFifthTableTransactionQuery = "delete from olist_sellers_dataset where seller_id='d1b65fc7debc3361ea86b5f14c68d2e2';";
		
		DBConnectionUtilLocal.getInstance().updateData(localInstanceFirstTableTransactionQuery);
		DBConnectionUtilLocal.getInstance().updateData(localInstanceSecondTableTransactionQuery);
		DBConnectionUtilLocal.getInstance().readData(localInstanceThirdTableTransactionQuery);
		DBConnectionUtilLocal.getInstance().readData(localInstanceFourthTableTransactionQuery);
		DBConnectionUtilLocal.getInstance().updateData(localInstanceFifthTableTransactionQuery);
		
		System.out.println("Completed 5 table queries without commiting with local instance...");
		
		startGCPInstance();
		
		String gcpInstanceFirstTableTransactionQuery = "update olist_customers_dataset set customer_id='dummy_customer_id_task4' where customer_unique_id='861eff4711a542e4b93843c6dd7febb0'";
		String gcpInstanceSecondTableTransactionQuery = "select * from olist_geolocation_dataset where geolocation_city='sao paulo';";
		String gcpInstanceThirdTableTransactionQuery = "update olist_order_reviews_dataset set review_id='dummy_review_id_task4' where order_id='73fc7af87114b39712e6da79b0a377eb';";
		String gcpInstanceFourthTableTransactionQuery = "delete from olist_order_payments_dataset where order_id='ba78997921bbcdc1373bb41e913ab953';";
		
		DBConnectionUtilGCP.getInstance().updateData(gcpInstanceFirstTableTransactionQuery);
		DBConnectionUtilGCP.getInstance().readData(gcpInstanceSecondTableTransactionQuery);
		DBConnectionUtilGCP.getInstance().updateData(gcpInstanceThirdTableTransactionQuery);
		DBConnectionUtilGCP.getInstance().updateData(gcpInstanceFourthTableTransactionQuery);
		
		System.out.println("Completed 4 table queries without commiting with gcp instance...");
		
		System.out.println("Committing both instances to distributed database.");
		
		DBConnectionUtilLocal.getInstance().updateData("commit;");
		DBConnectionUtilGCP.getInstance().updateData("commit;");
		
		System.out.println("Sucessfully completed distributed transaction via local and gcp instances.");
		
		
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		
		System.out.println("Time taken: "+ timeElapsed.toSeconds() +" seconds");
		
		DBConnectionUtilLocal.getInstance().stop();
		DBConnectionUtilGCP.getInstance().stop();
		validateTransaction = true;
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		return validateTransaction;
	}
	
}
