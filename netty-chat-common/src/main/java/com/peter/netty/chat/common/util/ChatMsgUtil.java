package com.peter.netty.chat.common.util;

import com.alibaba.fastjson2.JSON;
import com.peter.netty.chat.common.codec.Invocation;
import com.peter.netty.chat.common.model.chat.RpcMsg;

import java.util.UUID;

public class ChatMsgUtil {
    public static RpcMsg.Msg builLoginMsg(Invocation invocation) {
        RpcMsg.Msg msg = RpcMsg.Msg.newBuilder()
                .setMsgId(UUID.randomUUID().toString())
                .setTimestamp(System.currentTimeMillis())
                .setMsgType(invocation.getType())
                .setBody(JSON.toJSONString(invocation))
                .build();
        return msg;
    }
}
