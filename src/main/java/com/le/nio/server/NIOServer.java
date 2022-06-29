package com.le.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Auther: xll
 * @Date: 2022/6/29 11:09
 * @Desc:
 */
public class NIOServer {
    // 底层采用Epoll Reactor反应堆模型
    private Selector selector;

    public NIOServer(int port) throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        server.configureBlocking(false);

        selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server has running, listen port is: " + port);
    }

    public void listener() throws IOException {
        while (true) {
            // epoll: 返回就绪事件个数
            int ready = selector.select();
            if (ready == 0) continue;

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                process(key);
                iterator.remove();
            }
        }
    }

    private void process(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 监听不同事件
        if (key.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
            SocketChannel client = channel.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } else if (key.isReadable()) {
            SocketChannel client;
            try {
                client = (SocketChannel) key.channel();
                int n = client.read(buffer);
                if (n > 0) {
                    buffer.flip();
                    String content = new String(buffer.array(), 0, n);
                    System.out.println("recv: " + content);
                    client.register(selector, SelectionKey.OP_WRITE);
                }
                buffer.clear();
            } catch (IOException e) {
                key.cancel();
                if (key.channel() != null) {
                    System.out.println(((SocketChannel) key.channel()).getRemoteAddress() + " closed");
                    key.channel().close();
                }
            }
        } else if (key.isWritable()) {
            SocketChannel client = (SocketChannel) key.channel();
            client.configureBlocking(false);

            client.write(ByteBuffer.wrap("hello-world".getBytes()));

            client.register(selector, SelectionKey.OP_READ);
        }
    }


    public static void main(String[] args) throws IOException {
        new NIOServer(8080).listener();
    }
}
