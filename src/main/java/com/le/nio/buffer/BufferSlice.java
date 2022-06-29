package com.le.nio.buffer;

import java.nio.IntBuffer;

/**
 * @Auther: xll
 * @Date: 2022/6/29 9:44
 * 缓冲区分片，主缓冲区和子缓冲区在底层数据共享
 */
public class BufferSlice {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            // 1-10
            buffer.put(i + 1);
        }

        // 子缓冲区
        buffer.position(3);
        buffer.limit(7);
        IntBuffer slice = buffer.slice();

        // 更改子缓冲区内容
        for (int i = 0; i < slice.capacity(); i++) {
            int val = slice.get(i);
            val *= 2;
            slice.put(i, val);
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());

        while (buffer.hasRemaining()) {
            System.out.print(buffer.get() + " ");
        }
    }
}
