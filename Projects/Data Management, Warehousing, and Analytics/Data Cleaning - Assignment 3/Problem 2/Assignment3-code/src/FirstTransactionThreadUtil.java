import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FirstTransactionThreadUtil implements Runnable{
    Thread firstThread;
    TransactionUtil transactionUtil = new TransactionUtil();
    FirstTransactionThreadUtil(){
        firstThread = new Thread("First Transaction");
        System.out.println("First Thread created!");
    }

    @Override
    public void run() {
        try {
            List<String> queries = new ArrayList<String>();
            DBConnectionUtil.getInstance().updateData("set autocommit=0;");

            String firstQuery = "Select TICKETS_SOLD from annualticketsales where YEAR=2011;";
            String secondQuery = "Update annualticketsales set TICKET_SOLD = 0 where YEAR=2011;";

            /*//Checking if the lock is acquired by first transaction. If yes will execute the queries.
            if(TransactionUtil.transactionLockID == 1){

            }*/
            queries.add(firstQuery);
            queries.add(secondQuery);
            if(TransactionUtil.transactionLockID == 0){
                TransactionUtil.transactionLockID = 1;
            }
            transactionUtil.transactions(1, queries);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }
}
