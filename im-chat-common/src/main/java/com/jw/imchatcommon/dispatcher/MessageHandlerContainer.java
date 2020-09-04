package com.jw.imchatcommon.dispatcher;

import com.jw.imchatcommon.message.Message;
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

    private final Map<String, MessageHandler> handlerMap = new HashMap<>();

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(MessageHandler.class).values().forEach(
                messageHandler -> handlerMap.put(messageHandler.getType(), messageHandler)
        );
        log.info("[afterPropertiesSet][message handlers number: {}]", handlerMap.size());
    }

    MessageHandler getMessageHandler(String type) {
        MessageHandler handler = handlerMap.get(type);
        if (handler == null) {
            throw new IllegalArgumentException(String.format("cannot find MessageHandler for type (%s)", type));
        }
        return handler;
    }

    /**
     * Reference:
     * @see <a href="https://github.com/apache/rocketmq-spring/blob/a334685bd98886f669f42a205376cb7327ad6b96/rocketmq-spring-boot/src/main/java/org/apache/rocketmq/spring/support/DefaultRocketMQListenerContainer.java#L510">
     * https://github.com/apache/rocketmq-spring
     * </a>
     */
    static Class<? extends Message> getMessageClass(MessageHandler handler) {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        Type[] interfaces = targetClass.getGenericInterfaces();
        Class<?> superclass = targetClass.getSuperclass();

        while (interfaces.length == 0 && Objects.nonNull(superclass)) {
            interfaces = superclass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        }

        for (Type type : interfaces) {
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                        return (Class<Message>) actualTypeArguments[0];
                    } else {
                        throw new IllegalStateException(String.format("cannot find message type for type (%s)", handler));
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("cannot find message type for type (%s)", handler));
    }
}
