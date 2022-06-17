import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Class to structure and write the data received by executing the queries
 */
public class XMLWriter {
	/*
	 * Method to create a file and calling methods to better structure the data in the file
	 */
    public static void createFile(ResultSet rs1, ResultSet rs2,String fileName, String startingDate, String endingDate)
            throws IOException, SQLException {
    	//Creating a file with the file name
        Writer writer = new FileWriter(fileName);
        //Writing the version of the xml
        addXMLVersion(writer);
        //Writing the starting date and ending date 
        addYear(writer,startingDate,endingDate);
        //Writing the manager data
        addManagers(writer, rs1);
        //Writing the product line data
        addProductLine(writer,rs2);
        //Closing the tags in the end
        closeTags(writer);
        
        //Closing the writer object
        writer.flush();
        writer.close();
        System.out.println("XML created with "+fileName);
    }

    /*
     * Method to add the xml version and write it in the file
     */
    private static void addXMLVersion(Writer writer) throws IOException {
        writer.write("<?xml vers1ion='1.0' encoding='UTF-8'?>\n");
    }
    
    /*
     * Method to add the startig tags - starting date and ending date
     */
    private static void addYear(Writer writer, String startingDate, String endingDate) throws IOException{
    	//Structuring the year end report with starting date and ending date.
    	writer.write("<year_end_report>\n");
        writer.write("\t<year>\n");
        writer.write(String.format("\t\t<start_date>%s</ start_date>\n",startingDate));
        writer.write(String.format("\t\t<end_date>%s</ end_date>\n",endingDate));
        writer.write("\t</year>\n");
    }
    
    /*
     * Method to write the manager data in xml
     */
    private static void addManagers(Writer writer, ResultSet rs1)
            throws SQLException, IOException {
    	//Metadata of the manager result set
        ResultSetMetaData metaData = rs1.getMetaData();
        //variable to store the number of columns in the metadata
        int nbColumns = metaData.getColumnCount();
        //Creating a buffer object
        StringBuffer buffer = new StringBuffer();
        //Appending the manager list and manager tags to start the structure
        buffer.setLength(0);
        buffer.append("\t<manager_list>");
        buffer.append("\n\t\t<manager>");
        writer.write(buffer.toString());
        //Structuring all the data for the manager data from the resultset
        while (rs1.next()) {
        	buffer = new StringBuffer();
        	buffer.setLength(0);
        	//Child tags with its data added to the buffer.
            for (int i = 1; i <= nbColumns; i++) {
            	
                buffer.append("\n\t\t\t<" + metaData.getColumnName(i) + ">");
                
                buffer.append(rs1.getString(i));
                
                buffer.append("</" + metaData.getColumnName(i) + ">");
                
            }
            //closing the manager tag in the buffer
            buffer.append("\n\t\t</manager>");
            //writing the buffer to the file
            writer.write(buffer.toString());
        }
        //Initializing the buffer object again to avoid repetition
        buffer = new StringBuffer();
        //Closing the manager list tag in the buffer
        buffer.append("\n\t</manager_list>");
        //Writing the buffer to the file
        writer.write(buffer.toString());
    }

    /*
     * Method to write and structure the product line data in the file
     */
    private static void addProductLine(Writer writer, ResultSet rs2) throws IOException, SQLException{
    	//writing the product line list and product line tags in the file
    	writer.write("\n\t<product_line_list>");
    	writer.write("\n\t\t<product_line>");
    
        //Buffer object used to write the data in the file
        StringBuffer buffer = new StringBuffer();
        buffer.setLength(0);
        
        //List of list to store the database content to in app datastructure
        List<List<String>> myResultSet = new ArrayList<>();
        //Iterating through resultset to store the database content
        while (rs2.next()) {
        	//Adding the values of dataase contents
        	List<String> myListRow = new ArrayList<>();
    		myListRow.add(rs2.getString(1));
			myListRow.add(rs2.getString(2));
			myListRow.add(rs2.getString(3));
			myListRow.add(rs2.getString(4));
			
			myResultSet.add(myListRow);
			
        }
        //String object to check if the data is present or not to segregate the tags
        String check = new String();
        //Itterating through the result set
		for(int i=0;i<myResultSet.size()-1;i++){
			//Checking repetions and skipping the data
			if(check.contains(myResultSet.get(i).get(0))){
				continue;
			}
			else{
			//Flags
			int f=0;
			int k=0;
			int count =0;
			//Itterating through the loop to write the data in the file.
			for(int j=i+1; j<myResultSet.size();j++){
				//Checking and adding the product line name and description first for common tags
				if(myResultSet.get(i).get(0).equals(myResultSet.get(j).get(0))){
					if(k==0){
						buffer = new StringBuffer();
						buffer.append("\n\t\t\t<product_line_name>");
						buffer.append(myResultSet.get(i).get(0));
						buffer.append("</product_line_name>");
						
						buffer.append("\n\t\t\t<product_line_description>");
						buffer.append(myResultSet.get(i).get(1));
						buffer.append("</product_line_description>");
						writer.write(buffer.toString());
						k=1;
					}
					//Adding and writing the customers for common tags
					if(f==0){
						buffer = new StringBuffer();
						buffer.append("\n\t\t\t<customer>");
						buffer.append("\n\t\t\t\t<customer_name>");
						buffer.append(myResultSet.get(i).get(2));
						buffer.append("</customer_name>");
						
						buffer.append("\n\t\t\t\t<order_value>");
						buffer.append(myResultSet.get(i).get(3));
						buffer.append("</order_value>");
						f=1;
						buffer.append("\n\t\t\t</customer>");
						writer.write(buffer.toString());
					}
					//Adding and writing the customers for non common tags
					buffer = new StringBuffer();
					buffer.append("\n\t\t\t<customer>");
					buffer.append("\n\t\t\t\t<customer_name>");
					buffer.append(myResultSet.get(j).get(2));
					buffer.append("</customer_name>");
					//Adding order details and writing in the file
					buffer.append("\n\t\t\t\t<order_value>");
					buffer.append(myResultSet.get(j).get(3));
					buffer.append("</order_value>");
					buffer.append("\n\t\t\t</customer>");
					writer.write(buffer.toString());
					count++;
				}
				
			}
			//closing the product line tag by checking the count
			if(count>0){
				buffer = new StringBuffer();
				buffer.append("\n\t\t</product_line>");
				writer.write(buffer.toString());
			}
			//Adding and writing the product line data if still remaining
			else{
				buffer = new StringBuffer();
				buffer.append("\n\t\t\t<product_line_name>");
				buffer.append(myResultSet.get(i).get(0));
				buffer.append("</product_line_name>");
				
				buffer.append("\n\t\t\t<product_line_description>");
				buffer.append(myResultSet.get(i).get(1));
				buffer.append("</product_line_description>");
				
				buffer.append("\n\t\t\t<customer_name>");
				buffer.append(myResultSet.get(i).get(2));
				buffer.append("</customer_name>");
				
				buffer.append("\n\t\t\t<order_value>");
				buffer.append(myResultSet.get(i).get(3));
				buffer.append("</order_value>");
				
				writer.write(buffer.toString());
			}
			check += myResultSet.get(i).get(0);
		}
			
		}	
    }
    
   
    
    
    private static void closeTags(Writer writer)
            throws IOException {
        writer.write("\n\t</product_line_list>");
        writer.write("\n</year_end_report >");
    }
}