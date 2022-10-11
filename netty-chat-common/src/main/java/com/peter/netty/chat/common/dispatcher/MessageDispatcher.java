package com.peter.netty.chat.common.dispatcher;

import com.alibaba.fastjson2.JSON;
import com.peter.netty.chat.common.codec.Invocation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ChannelHandler.Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {
    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    private final ExecutorService executor =  Executors.newFixedThreadPool(200);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation msg) throws Exception {
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(msg.getType());
        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        // 解析消息
        Message message = JSON.parseObject(msg.getMessage(), messageClass);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                messageHandler.execute(ctx.channel(), message);
            }
        });
    }
}
