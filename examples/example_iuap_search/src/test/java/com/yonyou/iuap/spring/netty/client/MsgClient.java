package com.yonyou.iuap.spring.netty.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.spring.netty.client.handler.MsgClientHandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpRequestEncoder;

/**
 * Created by zengxs on 2016/2/1.
 */
public class MsgClient {
	
	private static final Logger logger = LoggerFactory.getLogger(MsgClient.class);

    private static int port = 10001;

    private NettySender sender;

    public void init() {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                 .handler(new ChannelInitializer() {
                     @Override
                     protected void initChannel(Channel channel) throws Exception {
                         channel.pipeline().addLast(new HttpRequestEncoder());
                         channel.pipeline().addLast(new MsgClientHandler(sender));
                     }
                 });
        bootstrap.connect("127.0.0.1", port);
    }

    public NettySender getSender() {
        return sender;
    }

    public void setSender(NettySender sender) {
        this.sender = sender;
    }

    public static void main(String[] args) {
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
                     .handler(new ChannelInitializer() {
                         @Override
                         protected void initChannel(Channel channel) throws Exception {
                             channel.pipeline().addLast(new HttpClientCodec());
                             channel.pipeline().addLast(new MsgClientHandler(null));
                         }
                     });
            ChannelFuture future = bootstrap.connect("127.0.0.1", port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
           logger.error("msg client sync error",e);
        } finally {
            loopGroup.shutdownGracefully();
        }

    }
}
