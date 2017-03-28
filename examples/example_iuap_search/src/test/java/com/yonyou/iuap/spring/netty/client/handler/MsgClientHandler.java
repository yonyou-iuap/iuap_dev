package com.yonyou.iuap.spring.netty.client.handler;

import com.yonyou.iuap.spring.netty.client.NettySender;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zengxs on 2016/2/1.
 */
public class MsgClientHandler extends ChannelHandlerAdapter {
	static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MsgClientHandler.class); // auto append.


    private NettySender sender;

    public MsgClientHandler(NettySender sender) {
        this.sender = sender;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        sender.setCtx(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("",cause);
        sender.setCtx(null);
    }
}