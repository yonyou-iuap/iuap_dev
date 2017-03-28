package com.yonyou.iuap.spring.netty.server.handler;

import com.yonyou.iuap.search.msg.MessageConsumer;
import com.yonyou.iuap.search.query.exception.SearchException;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zengxs on 2016/2/1.
 */
public class MsgServerHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(MsgServerHandler.class);

    private MessageConsumer consumer;

    public MsgServerHandler(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        FullHttpRequest request = (FullHttpRequest) obj;
        String content = request.content().toString(CharsetUtil.UTF_8);
        try {
            consumer.onMessage(content);
        } catch (SearchException e) {
            logger.error("Fail to Consume message " + content, e);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("",cause);
    }
}
