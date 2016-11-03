import sun.font.TrueTypeFont;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by Apple on 11/2/2016 AD.
 */
public class ThreadPool {
    static Long startTime = System.nanoTime();

    public static void manageDisplay(){

        String a = String.valueOf((System.nanoTime() - startTime)/ 1000000000. );
        String b = String.valueOf(Manage.complete);
        Float bb = Float.valueOf(b);
        String c = String.valueOf(Manage.failed);
        Long aa = (System.nanoTime() - startTime)/ 1000000000 ;

        System.out.println("Time Taken for Tests :" + a);
        System.out.println("Completed Request :"+ b);
        System.out.println("Failed Requests :" + c);
        System.out.println("Avg Request per second" + bb/aa);


    }


    public static void main(String[] args) throws InterruptedException, ExecutionException{



        int num = 1000000;
        int con = 200;
        List<String> boo = new ArrayList<>();

        Callable<List<HashMap<String, String>>> callable = new AsysnClient();

        ExecutorService executor = Executors.newFixedThreadPool(num/con);



        List<Future<List<HashMap<String, String>>>> lstFuture = new ArrayList<>();

        for (int x = 0; x< con; x++) {
            Future<List<HashMap<String, String>>> future = executor.submit(callable);
            lstFuture.add(future);
        }

        for (Future<List<HashMap<String, String>>> callbackFuture: lstFuture){
            callbackFuture.get();
//            System.out.println(callbackFuture.get());
        }

        executor.shutdown();
        if (executor.isShutdown()){
            boo.add("True");
            System.out.println("HHey");


        }


        System.out.println(boo.size());
        if (boo.size() == con){
            manageDisplay();


        }


        manageDisplay();



    }


}
