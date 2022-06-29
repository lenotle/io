package com.le.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: xll
 * @Date: 2022/6/25 14:50
 */
public class testApi {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);

        while (true) {
            Socket client = server.accept();
            InputStream is = client.getInputStream();
            byte[] buff = new byte[1024];
            int n = is.read(buff);
            if (n > 0) {
                System.out.println(new String(buff));
            }
        }
    }
}
