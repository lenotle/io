package com.le.nio.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: xll
 * @Date: 2022/6/29 9:36
 */
public class BufferProgram {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("G:\\IntellijIdea\\other\\io\\src\\main\\java\\com\\le\\nio\\buffer\\test.txt");
        FileChannel fileChannel = fis.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(10);
        fileChannel.read(buffer);

        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println((char)buffer.get());
        }

        buffer.clear();
    }
}
