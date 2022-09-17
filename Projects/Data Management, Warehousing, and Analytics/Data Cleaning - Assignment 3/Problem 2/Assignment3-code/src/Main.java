import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    //https://www.baeldung.com/java-concurrent-locks
    /*ReadWriteLock lock = new ReentrantReadWriteLock();

    Lock readLock = lock.readLock();

    Lock writeLock = lock.writeLock();*/

    public static List<Thread> threads = new ArrayList<Thread>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("How many transactions do you want to start?");
        int numberOfTransactions = sc.nextInt();



        //For simplicity assuming there are only two transactions considered as per the assignment description
        for(int i=0;i<2;i++){
            if(i==0) {
                FirstTransactionThreadUtil firstThreadThreadUtil = new FirstTransactionThreadUtil();
                Thread firstThread = new Thread(firstThreadThreadUtil,"First Transaction");
                threads.add(firstThread);
            }
            else{
                SecondTransactionThreadUtil secondTransactionThreadUtil = new SecondTransactionThreadUtil();
                Thread secondThread = new Thread(secondTransactionThreadUtil,"Second Transaction");
                threads.add(secondThread);
            }
        }

        //Starting first transaction

        System.out.println("Since the first update query is from second transaction, locking 2nd transaction..");
        TransactionUtil.transactionLockID = 2;
        System.out.println("Starting first transaction..");
        threads.get(0).start();
        System.out.println("Starting second transaction..");
        threads.get(1).start();


      /*  ThreadUtil  firstTransaction = new ThreadUtil();
        Thread firstTransactionThread = new Thread(firstTransaction);
        firstTransactionThread.start();

        ThreadUtil secondTransaction = new ThreadUtil();
        Thread secondTransactionThread = new Thread(secondTransaction);
        secondTransactionThread.start();*/





        /*Thread firstTransaction = threadUtil.createThreads();
        firstTransaction.start();
        firstTransaction.run();
        System.out.println(firstTransaction.getState());*/



    }
}