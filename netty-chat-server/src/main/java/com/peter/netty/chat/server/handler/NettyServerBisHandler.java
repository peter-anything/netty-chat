package com.peter.netty.chat.server.handler;

import com.alibaba.fastjson2.JSON;
import com.peter.netty.chat.common.codec.Invocation;
import com.peter.netty.chat.common.model.chat.RpcMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerBisHandler extends SimpleChannelInboundHandler<RpcMsg.Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcMsg.Msg msg) throws Exception {
        System.out.println("收到 "+msg.getFromUid()+" 的消息：" + msg.getBody());
        Invocation invocation = JSON.parseObject(msg.getBody(), Invocation.class);
        ctx.fireChannelRead(invocation);
    }
}
