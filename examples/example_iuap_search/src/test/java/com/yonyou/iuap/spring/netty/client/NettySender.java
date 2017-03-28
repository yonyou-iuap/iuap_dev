package com.yonyou.iuap.spring.netty.client;

import com.yonyou.iuap.search.msg.MessageSender;
import com.yonyou.iuap.search.query.exception.SearchException;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * Created by zengxs on 2016/3/4.
 */
public class NettySender implements MessageSender {

    private ChannelHandlerContext ctx;

    @Override
    public void sendDoc(String content, String routeKey) throws SearchException {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.POST, "/", Unpooled
                .wrappedBuffer(content.getBytes(CharsetUtil.UTF_8)));
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH, content.getBytes(CharsetUtil.UTF_8).length + "");
        ctx.writeAndFlush(request);
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }
}
