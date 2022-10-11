package com.peter.netty.chat.client.messagehandler.chat;

import com.peter.netty.chat.client.message.chat.ChatSendResponse;
import com.peter.netty.chat.common.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ChatSendResponseHandler implements MessageHandler<ChatSendResponse> {

    @Override
    public void execute(Channel channel, ChatSendResponse message) {
        log.info("[execute][发送结果：{}]", message);
    }

    @Override
    public String getType() {
        return ChatSendResponse.TYPE;
    }

}
