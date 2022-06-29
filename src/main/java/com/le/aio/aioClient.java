package com.le.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

/**
 * @Auther: xll
 * @Date: 2022/6/29 15:13
 * @Desc:
 */
public class aioClient {
    private final AsynchronousSocketChannel client;

    public aioClient() throws IOException {
        client = AsynchronousSocketChannel.open();
    }

    public void connect(String host, int port) {
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                try {
                    client.write(ByteBuffer.wrap(("this is a test word\t" + System.currentTimeMillis()).getBytes())).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        // 下面这一段代码是只读数据
        final ByteBuffer bb = ByteBuffer.allocate(1024);
        client.read(bb, null, new CompletionHandler<Integer,Object>(){
                    @Override
                    public void completed(Integer result, Object attachment) {
                        //System.out.println("IO操作完成" + result);
                        System.out.println("获取反馈结果" + new String(bb.array()));
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        exc.printStackTrace();
                    }
                }
        );

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        final CountDownLatch latch = new CountDownLatch(count);

        for (int i = 0; i < count; i ++) {
            latch.countDown();
            new Thread(){
                public void run(){
                    try{
                        latch.await();
                        new aioClient().connect("localhost",8080);
                    }catch(Exception e){

                    }
                }
            }.start();
        }

        Thread.sleep(1000 * 60 * 10);
    }
}
