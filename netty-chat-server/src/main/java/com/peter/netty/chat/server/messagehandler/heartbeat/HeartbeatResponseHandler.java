package com.peter.netty.chat.server.messagehandler.heartbeat;

import com.peter.netty.chat.common.dispatcher.Message;
import com.peter.netty.chat.common.dispatcher.MessageHandler;
import com.peter.netty.chat.server.message.heartbeat.HeartbeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 消息 - 心跳响应
 */
@Slf4j
@Component
public class HeartbeatResponseHandler implements MessageHandler<HeartbeatResponse> {

    @Override
    public void execute(Channel channel, HeartbeatResponse message) {
        log.info("[execute][收到连接({}) 的心跳响应]", channel.id());
    }

    @Override
    public String getType() {
        return HeartbeatResponse.TYPE;
    }
}