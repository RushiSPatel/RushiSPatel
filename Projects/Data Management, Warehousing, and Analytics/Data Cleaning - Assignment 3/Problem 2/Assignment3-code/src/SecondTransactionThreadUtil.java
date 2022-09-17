import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SecondTransactionThreadUtil implements Runnable{
    Thread secondThread;
    TransactionUtil transactionUtil = new TransactionUtil();
    SecondTransactionThreadUtil(){
        secondThread = new Thread("Second Transaction");
        System.out.println("Second Thread created!");
    }

    @Override
    public void run() {
        try {
            List<String> queries = new ArrayList<String>();

            DBConnectionUtil.getInstance().updateData("set autocommit=0;");


            String firstQuery = "Update annualticketsales set TICKETS_SOLD = 0 where YEAR=2011;";
            //Locking this transaction since update query is encountered.
            transactionUtil.acquire_locks(2);

            String secondQuery = "Select TICKETS_SOLD from annualticketsales where YEAR=2011;";

            queries.add(firstQuery);
            queries.add(secondQuery);
            transactionUtil.transactions(2, queries);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
