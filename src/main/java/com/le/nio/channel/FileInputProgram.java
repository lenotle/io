package com.le.nio.channel;

import com.sun.org.apache.xpath.internal.operations.String;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: xll
 * @Date: 2022/6/29 10:10
 * @Desc:
 * 任何时候读取数据，都不是直接从通道读取，而是从通道读取到缓冲区。
 * 所以使用 NIO 读取数据可以分为下面三个步骤：
 * 1. 从 FileInputStream 获取 Channel
 * 2. 创建 Buffer
 * 3. 将数据从 Channel
 */
public class FileInputProgram {
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
