import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.io.StringWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
/*
 * Class to process data received as user inputs
 */
public class DataExport {
	/*
	 * Method to process the user inputs and fetching the data by running the query
	 */
	public void processData(String startingDate, String endingDate, String filePath) {
		if(startingDate == null||endingDate==null||filePath==null){
			System.out.println("Null values cannot be accepted!");
			System.exit(0);
		}
		//formatting the date to validate the dates.
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		try {
			//Converting starting date into date datatype to validate
			startDate = formatter.parse(startingDate);
			//Converting end date into date datatype to validate
			Date endDate = formatter.parse(endingDate);
			System.out.println(startDate);
			System.out.println(endDate);
			//Validating start date and end date
			if(endDate.before(startDate)){
				System.out.println("End date cannot be before the starting date!");
				System.exit(0);
			}
	
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			//variable to store the manager fetch sql query
			String managerData = String.format("with ReportToEmployeeTable as (select reportsTo from employees), EmployeeTeable as (select lastName, firstName, employeeNumber, officeCode from employees join ReportToEmployeeTable), EmployeeOfficeTable as (select EmployeeTeable.employeeNumber, EmployeeTeable.lastName, EmployeeTeable.firstName, offices.city from offices join EmployeeTeable on EmployeeTeable.officeCode = offices.officeCode), SalesTable as (select orderNumber, sum((quantityOrdered*priceEach)) totalSales from orderdetails group by orderNumber), TotalSalesSumTable as (select orders.customerNumber, sum(SalesTable.totalSales) totalSales from orders join SalesTable on orders.orderNumber = SalesTable.orderNumber where orders.orderDate >= \'%s\' and orders.orderDate <= \'%s\' group by orders.customerNumber), CustomerSalesTable as (select salesRepEmployeeNumber, sum(TotalSalesSumTable.totalSales) totalSales, count(customers.customerNumber) as customerCount from customers join TotalSalesSumTable on customers.customerNumber = TotalSalesSumTable.customerNumber group by salesRepEmployeeNumber), FinalSalesTable as (select reportsTo, sum(CustomerSalesTable.totalSales) totalSales, sum(CustomerSalesTable.customerCount) customerCount, count(employeeNumber) as employeeCount from employees join CustomerSalesTable on employees.employeeNumber = CustomerSalesTable.salesRepEmployeeNumber group by reportsTo) select distinct concat(EmployeeOfficeTable.firstName,' ',EmployeeOfficeTable.lastName) manager_name, EmployeeOfficeTable.city, FinalSalesTable.employeeCount employee_count, FinalSalesTable.customerCount customer_count, FinalSalesTable.totalSales totalSales from EmployeeOfficeTable join FinalSalesTable on EmployeeOfficeTable.employeeNumber = FinalSalesTable.reportsTo order by EmployeeOfficeTable.employeeNumber;", startingDate,endingDate);
			//executing the query
			ResultSet rs1 = DBConnection.getInstance().readData(managerData);
			//variable to store the product line fetch sql query
			String productLineData = String.format("with RangeTable as (select orderNumber, customerNumber from orders where orders.orderDate >= \'%s\' and orders.orderDate <= \'%s\'), OrderDetailSalesTable as (select productCode, quantityOrdered*priceEach totalSales, RangeTable.orderNumber, RangeTable.customerNumber from orderdetails join RangeTable where RangeTable.orderNumber = orderdetails.orderNumber order by RangeTable.customerNumber), CustomerOrderDetailsTable as (select customers.customerNumber, customers.customerName, OrderDetailSalesTable.totalSales, OrderDetailSalesTable.productCode from customers join OrderDetailSalesTable on customers.customerNumber = OrderDetailSalesTable.customerNumber), finalCustomersSalesTable as (select products.productLine, CustomerOrderDetailsTable.customerName, sum(CustomerOrderDetailsTable.totalSales) as totalSales from products join CustomerOrderDetailsTable on products.productCode = CustomerOrderDetailsTable.productCode group by CustomerOrderDetailsTable.customerNumber order by productLine) select finalCustomersSalesTable.productLine, productlines.textDescription, finalCustomersSalesTable.customerName, finalCustomersSalesTable.totalSales total_sales from productlines join finalCustomersSalesTable on productlines.productLine = finalCustomersSalesTable.productLine;", startingDate,endingDate);			
			//executing the query
			ResultSet rs2 = DBConnection.getInstance().readData(productLineData);
			//reading the data and structuring it by writing into a file
			XMLWriter.createFile(rs1,rs2,filePath,startingDate,endingDate);
			   
			//Closing the result set objects
		    rs1.close();
		    rs2.close();
		    //Closing the database connection 
		    DBConnection.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

		
		
		
		
		
		
		
		
	}

}
