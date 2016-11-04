import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Apple on 11/3/2016 AD.
 */
public class Manage {
    static List<Long> success = new ArrayList<>();
    static int failed;
    static int complete;
    private static List<Long> percentile = new ArrayList<>();

    static List<Long> avgTime = new ArrayList<>();

    public static synchronized void reportResponse(long time) {
        failed += 1;
        avgTime.add(time);
        percentile.add(time);
//        System.out.println("FAILEDDD");
    }

    public static synchronized void reportResponse(int error,long time) {
        String a = String.valueOf(error);
        avgTime.add(time);
        percentile.add(time);


        if (a.startsWith("2")){
//            System.out.println(error);

            success.add(time);
            complete+= 1;
        }else{
//            System.out.println("FALIEDDD");
            failed += 1;

        }
    }

    public static List<Long> getSortedResponseTimes() {
        List<Long> responseTimes = new ArrayList<>(percentile);
        Collections.sort(responseTimes);

        return responseTimes;
    }
//    static void printPArray() {
//        System.out.printf("size: %d\n", percentile.size());
//        System.out.println(percentile.toString());
//    }
}
