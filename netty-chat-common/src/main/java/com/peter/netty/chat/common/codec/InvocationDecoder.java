package com.peter.netty.chat.common.codec;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class InvocationDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        if (in.readableBytes() <= 4) return;
        int length = in.readInt();
        if (length < 0) {
            throw new CorruptedFrameException("negative length: " + length);
        }
        if (in.readableBytes() < length) {
            in.resetReaderIndex();
            return;
        }
        byte[] content = new byte[length];
        in.readBytes(content);
        Invocation msg = JSON.parseObject(content, Invocation.class);
        out.add(msg);
        log.info("[decode][连接({}) 解析到一条消息({})]", ctx.channel().id(), msg.toString());
    }
}
