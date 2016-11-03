import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apple on 11/3/2016 AD.
 */
public class Manage {
    static List<Long> success = new ArrayList<>();
    static int failed;
    static int complete;
    static List<Long> avgTime = new ArrayList<>();
    public Manage(long time){
        failed += 1;
        avgTime.add(time);
//        System.out.println("FAILEDDD");
    }
    public Manage(int error,long time){
        String a = String.valueOf(error);
        avgTime.add(time);


        if (a.startsWith("2")){
//            System.out.println(error);

            success.add(time);
            complete+= 1;
        }else{
//            System.out.println("FALIEDDD");
            failed += 1;

        }


    }


    public static void main(String[] args) {
    }
}
