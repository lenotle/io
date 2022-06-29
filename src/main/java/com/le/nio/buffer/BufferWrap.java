package com.le.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @Auther: xll
 * @Date: 2022/6/29 9:41
 * 缓冲区分配方式
 */
public class BufferWrap {
    public static void main(String[] args) {
        // 1. 分配指定大小缓冲区
        ByteBuffer allocate = ByteBuffer.allocate(10);

        // 2. 包装已有数组
        byte[] array = new byte[10];
        ByteBuffer wrap = ByteBuffer.wrap(array);
    }
}
