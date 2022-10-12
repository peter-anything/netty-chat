package com.peter.netty.chat.client.handler;

import com.peter.netty.chat.common.codec.InvocationDecoder;
import com.peter.netty.chat.common.codec.InvocationEncoder;
import com.peter.netty.chat.common.dispatcher.MessageDispatcher;
import com.peter.netty.chat.common.model.chat.RpcMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {
    /**
     * 心跳超时时间
     */
    private static final Integer READ_TIMEOUT_SECONDS = 60;

    @Autowired
    private MessageDispatcher messageDispatcher;

    @Autowired
    private NettyClientHandler nettyClientHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                // 空闲检测
                .addLast(new IdleStateHandler(READ_TIMEOUT_SECONDS, 0, 0))
                .addLast(new ReadTimeoutHandler(3 * READ_TIMEOUT_SECONDS))
                // 编码器
//                .addLast(new InvocationEncoder())
                // 解码器
//                .addLast(new InvocationDecoder())
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(RpcMsg.Msg.getDefaultInstance()))
                .addLast(new NettyClientBisHandler())
                // 消息分发器
                .addLast(messageDispatcher)
                // 客户端处理器
                .addLast(nettyClientHandler)
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder());
    }
}
