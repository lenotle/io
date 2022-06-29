package com.le.nio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: xll
 * @Date: 2022/6/29 9:56
 * @Desc: 直接缓冲区
 *
 * 直接缓冲区是为加快 I/O 速度，使用一种特殊方式为其分配内存的缓冲区，JDK 文档中的描述为：给
 * 定一个直接字节缓冲区，Java 虚拟机将尽最大努力直接对它执行本机 I/O 操作。也就是说，它会在
 * 每一次调用底层操作系统的本机 I/O 操作之前(或之后)，尝试避免将缓冲区的内容拷贝到一个中间缓
 * 冲区中或者从一个中间缓冲区中拷贝数据
 */
public class DirectBuffer {
    // 实现文件拷贝
    public static void main(String[] args) throws IOException {
        String filename = "G:\\IntellijIdea\\other\\io\\src\\main\\java\\com\\le\\nio\\buffer\\test.txt";
        FileInputStream fis = new FileInputStream(filename);
        FileChannel fic = fis.getChannel();

        filename = "G:\\IntellijIdea\\other\\io\\src\\main\\java\\com\\le\\nio\\buffer\\test_copy.txt";
        FileOutputStream fos = new FileOutputStream(filename);
        FileChannel foc = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        int n = 0;

        while (true) {
            buffer.clear();
            n = fic.read(buffer);

            if (n == -1)
                break;
            buffer.flip();

            foc.write(buffer);
        }

        foc.close();
        fos.close();
        fic.close();
        fis.close();

    }
}
