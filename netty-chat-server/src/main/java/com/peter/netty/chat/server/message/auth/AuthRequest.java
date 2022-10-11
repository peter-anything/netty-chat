package com.peter.netty.chat.server.message.auth;

import com.peter.netty.chat.common.dispatcher.Message;

public class AuthRequest implements Message {
    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证 Token
     */
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public AuthRequest setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}
