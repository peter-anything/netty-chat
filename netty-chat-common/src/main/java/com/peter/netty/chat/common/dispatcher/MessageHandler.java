package com.peter.netty.chat.common.dispatcher;

import io.netty.channel.Channel;

public interface MessageHandler<T extends Message> {
    void execute(Channel channel, T messages);
    String getType();
}