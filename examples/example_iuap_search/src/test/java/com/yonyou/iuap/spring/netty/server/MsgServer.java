package com.yonyou.iuap.spring.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.iuap.search.msg.MessageConsumer;
import com.yonyou.iuap.spring.netty.server.handler.MsgServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;

/**
 * Created by zengxs on 2016/2/1.
 */
public class MsgServer {
	
	private static final Logger logger = LoggerFactory.getLogger(MsgServer.class);

    private static int port = 10001;

    private MessageConsumer consumer;


    public void init() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,
                1024).childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel channel) throws Exception {
                channel.pipeline().addLast(new HttpRequestDecoder());
                channel.pipeline().addLast(new HttpObjectAggregator(1048576));
                channel.pipeline().addLast(new MsgServerHandler(consumer));
            }
        });
        b.bind(port);
    }

    public MessageConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(MessageConsumer consumer) {
        this.consumer = consumer;
    }

    //TODO
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG,
                    1024).childHandler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
//                    channel.pipeline().addLast(new HttpObjectAggregator(1048576));
                    channel.pipeline().addLast(new HttpRequestDecoder());
                    channel.pipeline().addLast(new MsgServerHandler(null));
                }
            });
            ChannelFuture future = b.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        	 logger.error("msg server sync error",e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
