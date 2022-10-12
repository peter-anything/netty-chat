package com.peter.netty.chat.server.handler;

import com.peter.netty.chat.common.codec.InvocationDecoder;
import com.peter.netty.chat.common.codec.InvocationEncoder;
import com.peter.netty.chat.common.dispatcher.MessageDispatcher;
import com.peter.netty.chat.common.model.chat.RpcMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

    /**
     * 心跳超时时间
     */
    private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyServerHandler nettyServerHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
//                .addLast(new InvocationEncoder())
//                .addLast(new InvocationDecoder())
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(RpcMsg.Msg.getDefaultInstance()))
                // 消息分发器
                .addLast(new NettyServerBisHandler())
                .addLast(messageDispatcher)
                // 客户端处理器
                .addLast(nettyServerHandler)
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder());
    }
}
