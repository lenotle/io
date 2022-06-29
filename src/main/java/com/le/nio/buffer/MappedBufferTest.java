package com.le.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: xll
 * @Date: 2022/6/29 10:03
 * @Desc: 内存映射
 */
public class MappedBufferTest {
    public static void main(String[] args) throws IOException {
        String filename = "G:\\IntellijIdea\\other\\io\\src\\main\\java\\com\\le\\nio\\buffer\\test.txt";
        RandomAccessFile accessFile = new RandomAccessFile(filename, "rw");
        FileChannel channel = accessFile.getChannel();

        // 对文件进行内存映射
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

        mappedByteBuffer.put("hello world".getBytes());

        channel.close();
        accessFile.close();
    }
}
