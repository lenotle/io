package com.le.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Auther: xll
 * @Date: 2022/6/29 15:02
 * @Desc:
 */
public class aioServer {
    private int port = 8080;

    public aioServer(int port) throws IOException {
        this.port = port;
        listener();
    }

    private void listener() throws IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 初始化一个线程
        AsynchronousChannelGroup threadPool = AsynchronousChannelGroup.withCachedThreadPool(executorService, 1);

        final AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open(threadPool);
        server.bind(new InetSocketAddress(this.port));
        System.out.println("server has running, listen port: " + port);

        // 客户端连接个数
        final ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        map.put("count", 0);

        // 开始等待客户端连接
        // 实现一个CompletionHandler 的接口，匿名的实现类
        server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            final ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

            /**
             * Invoked when an operation has completed.
             *
             * @param result     The result of the I/O operation.
             * @param attachment
             */
            @Override
            public void completed(AsynchronousSocketChannel result, Object attachment) {
                map.put("count", map.get("count") + 1);
                System.out.println("connect count: " + map.get("count"));

                try {
                    buffer.clear();
                    result.read(buffer).get();
                    buffer.flip();
                    result.write(buffer);
                    buffer.flip();
                } catch (Exception e) {
                    System.out.println(e.toString());
                } finally {
                    try {
                        result.close();
                        server.accept(null, this);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }

            /**
             * Invoked when an operation fails.
             *
             * @param exc        The exception to indicate why the I/O operation failed
             * @param attachment
             */
            @Override
            public void failed(Throwable exc, Object attachment) {
                System.out.println("client has failed!!!!");
            }
        });

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    public static void main(String[] args) throws IOException {
        new aioServer(8080);
    }
}
