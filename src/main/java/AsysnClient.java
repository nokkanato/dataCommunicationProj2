

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

/**
 * This example demonstrates a fully asynchronous execution of multiple HTTP exchanges
 * where the result of an individual operation is reported using a callback interface.
 */
public class AsysnClient implements Callable<List<HashMap<String, String>>> {
    static List<Manage> mangeLst = new ArrayList<>();

    static int counter =0;
    @Override
    public List<HashMap<String, String>> call() throws Exception {
        return run();
    }

    public static List<HashMap<String,String>> run() throws Exception{
        final List<HashMap<String, String>> result = new ArrayList<>();

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000).build();
        ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy();


        CloseableHttpAsyncClient httpclient = HttpAsyncClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .setKeepAliveStrategy(keepAliveStrat)
                .build();
        try {
            httpclient.start();
            List<HttpGet> http = new ArrayList<>();
            for (int x = 0; x<1000000/200; x++){
                http.add(new HttpGet("http://10.27.8.20:8080"));
            }

            final CountDownLatch latch = new CountDownLatch(http.size());
            Long startTime = System.nanoTime();
            for (final HttpGet request: http) {
                httpclient.execute(request, new FutureCallback<HttpResponse>() {

                    @Override
                    public void completed(final HttpResponse response) {
//                        TODO: Measure Time
//                        TODO: Get Code Status

                        latch.countDown();
                        counter += 1;
                        int code = response.getStatusLine().getStatusCode();
                        mangeLst.add(new Manage(code, System.nanoTime()-startTime));
//
                    }

                    @Override
                    public void failed(final Exception ex) {
                        latch.countDown();
//                        System.out.println("FAILED");


                        mangeLst.add(new Manage(System.nanoTime()-startTime));

//                        TODO: How to measure time
//                        TODO: System.nanoTime();



//                        System.out.println(request.getRequestLine() + "->" + ex);
                    }

                    @Override
                    public void cancelled() {
                        latch.countDown();
//                        System.out.println(request.getRequestLine() + " cancelled");
                        mangeLst.add(new Manage(System.nanoTime()-startTime));
                    }
                });
            }
            latch.await();
            System.out.println("Shutting down");
        } finally {
            httpclient.close();
        }
        System.out.println("Done");
        System.out.println(counter);

        return result;

    }

    public static void main(final String[] args) throws Exception {




    }





}