import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Class to manage orders to update and fetch the order information from the database.
 */
public class OrderManager {

	/*
	 * Method to update data in the invoice table regarding the tax amount, promotional discount, shipping cost and final value of the order.S
	 */
	public void checkout(int orderNumber, int shipperId ){
		//Converting the int value of order number to string
		String orderNum = String.valueOf(orderNumber);
		//variable to store the query to update total value of the order before tax, discount and shipping charges
		String updateTotalOrderValue = String.format("update invoice set totalValue = (select distinct sum(quantityOrdered * priceEach) as totalSales from orderdetails where orderNumber = %s) where orderNumber = %s;",orderNum,orderNum);
		//variable to store the query to update promotional discount of the order
		String updatePromotionAmount = String.format("update invoice set promotionAmount = (select promotionAmount from promotion where officeCode= (select officeCode from employees where employeeNumber= (select salesRepEmployeeNumber from customers where customerNumber= (select customerNumber from orders where orderNumber = %s)) )) where orderNumber = %s;", orderNumber,orderNumber);
		//variable to store the query to update shipping amount of the order
		String updateShippingAmount = String.format("update invoice set shippingAmount = (select shippingAmount from shipper where shipperID = (select shipperID from orders where orderNumber=%s)) where orderNumber = %s;", orderNum,orderNum);
		//variable to store the query to update tax amount after calculating all the shipping and promotional charges of the order
		String updateTaxAmount = String.format("update invoice set taxAmount = (select taxAmount from tax where officeCode= (select officeCode from employees where employeeNumber= (select salesRepEmployeeNumber from customers where customerNumber= (select customerNumber from orders where orderNumber = %s)) )) where orderNumber = %s;", orderNum,orderNum);
		//variable to store the query to update final value after extra charges have been added or reduced.
		String updateFinalValue = String.format("update invoice set finalValue = (select ((totalValue-promotionAmount+shippingAmount) * ((taxAmount/100)+1)) as finalValue from invoice where orderNumber = %s  )where orderNumber = %s ;", orderNum,orderNum);
		try {
			//Updating data in the database;
			DBUtil.getInstance().updateData(updateTotalOrderValue);
			
			DBUtil.getInstance().updateData(updatePromotionAmount);
			
			DBUtil.getInstance().updateData(updateShippingAmount);
			
			DBUtil.getInstance().updateData(updateTaxAmount);
			
			DBUtil.getInstance().updateData(updateFinalValue);
			
			System.out.println("Promotion amount, shipping amount and tax amount updated in Invoice table");
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public int orderValue(int orderNumber){
		//Converting the int value of order number to string
		String orderNum = String.valueOf(orderNumber);
		//variable to store the final order value of the order from the invoice table
		String fetchOrderValue = String.format("select finalValue from invoice where orderNumber = %s;", orderNum);
		//initializing the order value variable
		int orderValue = 0;
		try {
			//executing the query to read the data
			ResultSet rs = DBUtil.getInstance().readData(fetchOrderValue);
			String value = new String();
			//Fetching the data and converting it into integer return type
			if(rs.next()){
			value = rs.getString(1);
			orderValue= (int) Float.parseFloat(value);
			}
			
			System.out.println(orderValue);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderValue;
		
	}
	
}
