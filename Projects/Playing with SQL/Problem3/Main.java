import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		//Scanner for user input
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter order number:");
		int orderNumber = sc.nextInt();
		System.out.println("Please enter shipper id:");
		int shipperId = sc.nextInt();
		
		OrderManager orderManager = new OrderManager();
		//Calling checkout method to update values in the invoice table
		orderManager.checkout(orderNumber,shipperId);
		//Calling orderValue method to fetch the final value from the invoice table
		orderManager.orderValue(orderNumber);
		
	}

}
