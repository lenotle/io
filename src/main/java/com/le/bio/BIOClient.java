package com.le.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther: xll
 * @Date: 2022/6/28 16:56
 */
public class BIOClient {
    public static void main(String[] args) {
        int count = 100;
        final CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    latch.await();

                    Socket client = new Socket("localhost", 8080);
                    OutputStream os = client.getOutputStream();

                    String msg = UUID.randomUUID().toString();
                    os.write(msg.getBytes());

                    os.close();
                    client.close();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            latch.countDown();
        }
    }
}
