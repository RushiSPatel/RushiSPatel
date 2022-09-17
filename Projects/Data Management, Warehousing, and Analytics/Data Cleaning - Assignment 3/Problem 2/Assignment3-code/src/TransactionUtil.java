import java.sql.SQLException;
import java.util.List;

public class TransactionUtil {

    public static int transactionLockID = 0;
    static int threshold = 0;

    public void acquire_locks(int transactionId){
        if(transactionId == 1){
            transactionLockID = 1;
        }
        else if(transactionId == 2){
            transactionLockID = 2;
        }
    }

    public void transactions(int transactionId, List<String> queries){

        try {

            DBConnectionUtil.getInstance().updateData("START TRANSACTION;");
            for (String query: queries) {
                String arr[] = query.split(" ",2);
                String firstWordQuery = arr[0];
                if(firstWordQuery.equalsIgnoreCase("select")){
                    DBConnectionUtil.getInstance().readData(query);
                    if(transactionId == 1) {
                        System.out.println("Transaction 1 Select query running without lock " + query);
                    }
                    else if(transactionId == 2){
                        System.out.println("Transaction 2 Select query running without lock " + query);
                    }
                }
            }

            if(transactionId == 1){
                if(transactionLockID == 1){
                    System.out.println("First transaction queries executing since lock is enforced on first transaction!");
                    for (String query: queries) {
                        String arr[] = query.split(" ",2);
                        String firstWordQuery = arr[0];
                        if(!firstWordQuery.equalsIgnoreCase("select")){
                            DBConnectionUtil.getInstance().updateData(query);
                            System.out.println("Transaction 1 query executed: "+ query);
                        }
                    }
                    //Releasing the lock after the transaction is completed.
                    release_locks(1,queries);
                }
                else {
                    System.out.println("Cant execute first transaction query since lock is enforced on other transaction!");
                }

            }
            else if(transactionId == 2){
                if(transactionLockID == 2){
                    System.out.println("Second transaction queries executing since lock is enforced on second transaction!");
                    for (String query: queries) {
                        String arr[] = query.split(" ",2);
                        String firstWordQuery = arr[0];
                        if(!firstWordQuery.equalsIgnoreCase("select")){
                            DBConnectionUtil.getInstance().updateData(query);
                            System.out.println("Transaction 2 query executed: "+ query);
                        }
                    }
                    //Releasing the lock after the transaction is completed.
                    release_locks(2, queries);
                }
                else {
                    System.out.println("Cant execute second transaction query since lock is enforced on other transaction!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }





    }

    public void release_locks(int transactionId, List<String> queries){
        //Setting the lock id to 0 suggesting there are no locks in place now.
        transactionLockID = 0;
        try {
            DBConnectionUtil.getInstance().updateData("commit;");
            System.out.println("Released lock of transaction "+transactionId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Execute other transaction if waiting
        System.out.println("Notifying other transactions..");
        if(transactionId == 1){
            threshold++;
            if(threshold==1) {
                Main.threads.get(0).run();
            }
            else{
                System.out.println("Concurrent transaction with 2-phase locking protocol completed!");
            }

        }
        else if(transactionId == 2){
            threshold++;
            if(threshold==1) {
                Main.threads.get(1).run();
            }
            else{
                System.out.println("Concurrent transaction with 2-phase locking protocol completed!");
            }
        }
    }


}
