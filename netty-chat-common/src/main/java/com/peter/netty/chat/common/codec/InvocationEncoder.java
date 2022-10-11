package com.peter.netty.chat.common.codec;

import com.alibaba.fastjson2.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvocationEncoder extends MessageToByteEncoder<Invocation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Invocation msg, ByteBuf out) {
        byte[] content = JSON.toJSONBytes(msg);
        out.writeInt(content.length);
        out.writeBytes(content);
        log.info("[encode][连接({}) 编码了一条消息({})]", ctx.channel().id(), msg.toString());
    }
}
