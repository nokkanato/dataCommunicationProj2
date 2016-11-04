import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Apple on 11/2/2016 AD.
 */
public class ThreadPool {

    static Long startTime = System.nanoTime();
    static int numm;
    static int conn;
    static int newCon ;

    public static void manageDisplay(){


        String a = String.valueOf((System.nanoTime() - startTime)/ 1000000000. );
        String b = String.valueOf(Manage.complete);
        Float bb = Float.valueOf(b);
        String c = String.valueOf(Manage.failed);
        Long aa = (System.nanoTime() - startTime)/ 1000000000 ;
        int yay = numm - Integer.valueOf(b);


        List<Long> ptiles = Manage.getSortedResponseTimes();


        System.out.println("Time Taken for Tests :" + a);
        System.out.println("Completed Request :"+ b);
        System.out.println("Failed Requests :" + String.valueOf(c));
        System.out.println("Avg Request per second" + bb/aa);
        System.out.println("Percentage of the requests served within a certain time (ms)");

        double[] targetPercents = {0.5, 0.6, 0.7, 0.8, 0.9, 1.0};

        for (double target: targetPercents) {
            int location = (int) (target * (ptiles.size() - 1));
            System.out.println((String.valueOf((int)(target*100))+"% "+ String.valueOf(ptiles.get(location)/1000000.)));


        }




    }



    public static void main(String[] args) throws InterruptedException, ExecutionException{



        numm = Integer.valueOf(args[1]);

        conn = Integer.valueOf(args[3]);

//        System.out.println(numm);
//        System.out.println(conn);

        Callable<List<HashMap<String, String>>> callable = new AsysnClient();

        newCon = conn;
        if (conn > 10){ newCon = conn/3;}





        ExecutorService executor = Executors.newFixedThreadPool(newCon);



        List<Future<List<HashMap<String, String>>>> lstFuture = new ArrayList<>();

        for (int x = 0; x< conn; x++) {
            Future<List<HashMap<String, String>>> future = executor.submit(callable);
            lstFuture.add(future);
        }

        for (Future<List<HashMap<String, String>>> callbackFuture: lstFuture){
            callbackFuture.get();
        }

        executor.shutdown();
//


        manageDisplay();




    }


}
