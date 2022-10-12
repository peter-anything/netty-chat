package com.peter.netty.chat.client.handler;

import com.peter.netty.chat.common.model.chat.RpcMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientBisHandler extends SimpleChannelInboundHandler<RpcMsg.Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMsg.Msg msg) throws Exception {
        System.out.println("收到 "+msg.getFromUid()+" 的消息：" + msg.getBody());
        ctx.fireChannelRead(msg.getBody());
    }
}
