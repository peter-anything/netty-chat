package com.peter.netty.chat.server.messagehandler.heartbeat;


import com.peter.netty.chat.common.codec.Invocation;
import com.peter.netty.chat.common.dispatcher.MessageHandler;
import com.peter.netty.chat.server.message.heartbeat.HeartbeatRequest;
import com.peter.netty.chat.server.message.heartbeat.HeartbeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {
    @Override
    public void execute(Channel channel, HeartbeatRequest message) {
        log.info("[execute][收到连接({}) 的心跳请求]", channel.id());
        // 响应心跳
        HeartbeatResponse response = new HeartbeatResponse();
        channel.writeAndFlush(new Invocation(HeartbeatResponse.TYPE, response));
    }

    @Override
    public String getType() {
        return HeartbeatRequest.TYPE;
    }

}