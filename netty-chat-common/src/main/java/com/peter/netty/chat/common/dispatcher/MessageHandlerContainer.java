package com.peter.netty.chat.common.dispatcher;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class MessageHandlerContainer implements InitializingBean {
    private final Map<String, MessageHandler> handlers = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(MessageHandler.class).values()
                .forEach(messageHandler -> handlers.put(messageHandler.getType(), messageHandler));
        log.info("[afterPropertiesSet][消息处理器数量：{}]", handlers.size());;
    }

    /**
     * 获得类型对应的 MessageHandler
     *
     * @param type 类型
     * @return MessageHandler
     */
    MessageHandler getMessageHandler(String type) {
        MessageHandler handler = handlers.get(type);
        if (handler == null) {
            throw new IllegalArgumentException(String.format("类型(%s) 找不到匹配的 MessageHandler 处理器", type));
        }
        return handler;
    }

    static Class<? extends Message> getMessageClass(MessageHandler handler) {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superClass = targetClass.getSuperclass();
        while ((Objects.isNull(interfaces) || 0 == interfaces.length) && Objects.nonNull(superClass)) {
            interfaces = superClass.getGenericInterfaces();
            superClass = targetClass.getSuperclass();
        }
        if (Objects.nonNull(interfaces)) {
            for (Type type: interfaces) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                        Type[] actualTypeArgs = parameterizedType.getActualTypeArguments();
                        if (Objects.nonNull(actualTypeArgs) && actualTypeArgs.length > 0) {
                            return (Class<Message>) actualTypeArgs[0];
                        } else {
                            throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
                        }
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
    }
}
