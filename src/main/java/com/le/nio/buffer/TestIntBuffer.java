package com.le.nio.buffer;

import java.nio.IntBuffer;

/**
 * @Auther: xll
 * @Date: 2022/6/29 9:27
 */
public class TestIntBuffer {
    public static void main(String[] args) {
        // 初始化缓冲区
        IntBuffer buffer = IntBuffer.allocate(8);

        // 添加数据
        for (int i = 0; i < 8; i++) {
            buffer.put(i);
        }

        // 固定 [position, limit]区域
        buffer.flip();

        // 读取数据
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
