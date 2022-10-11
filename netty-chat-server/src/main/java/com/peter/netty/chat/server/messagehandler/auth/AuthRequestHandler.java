package com.peter.netty.chat.server.messagehandler.auth;

import com.peter.netty.chat.common.codec.Invocation;
import com.peter.netty.chat.common.dispatcher.MessageHandler;
import com.peter.netty.chat.server.NettyChannelManager;
import com.peter.netty.chat.server.message.auth.AuthRequest;
import com.peter.netty.chat.server.message.auth.AuthResponse;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestHandler implements MessageHandler<AuthRequest> {
    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public void execute(Channel channel, AuthRequest authRequest) {
        if (StringUtils.isEmpty(authRequest.getAccessToken())) {
            AuthResponse authResponse = new AuthResponse().setCode(1).setMessage("认证 accessToken 未传入");
            channel.writeAndFlush(new Invocation(AuthResponse.TYPE, authResponse));
            return;
        }
        nettyChannelManager.addUser(channel, authRequest.getAccessToken());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setCode(200);
        channel.writeAndFlush(new Invocation(AuthResponse.TYPE, authRequest));
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }
}
