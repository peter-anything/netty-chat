package com.peter.netty.chat.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class TestSlice {
    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        System.out.println(buffer);
        buffer.writeBytes(new byte[] {1, 2, 3, 4});
        System.out.println(buffer);
        ByteBuf dupBuf = buffer.slice();
        System.out.println(dupBuf);
    }
}
