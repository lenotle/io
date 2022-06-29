package com.le.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: xll
 * @Date: 2022/6/28 16:51
 */
public class BIOServer {
    private ServerSocket server;

    public BIOServer(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("server has already run, port is " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listener() {
        while (true) {
            try {
                // 提取新连接
                Socket client = server.accept();

                // 获取输入流，读取数据
                InputStream is = client.getInputStream();

                byte[] buff = new byte[1024];
                int len = is.read(buff);
                if (len > 0) {
                    String msg = new String(buff, 0, len);
                    System.out.println("recv: " + msg);
                }
                is.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new BIOServer(8080).listener();
    }
}
