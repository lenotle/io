package com.le.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @Auther: xll
 * @Date: 2022/6/29 9:50
 * @Desc: 只读缓冲区
 */
public class ReadAbleBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // 0..9
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        // 创建只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        // 改变原缓冲区内容
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }

        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());

        while (readOnlyBuffer.hasRemaining()) {
            System.out.print(readOnlyBuffer.get() + " ");
            // 0 10 20 30 40 50 60 70 80 90
            // 修改原缓冲区，导致只读缓冲区数据也发生变化，数据共享
        }
    }
}
