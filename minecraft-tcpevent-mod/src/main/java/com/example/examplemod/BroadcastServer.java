package com.example.examplemod;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Creates a Netty server that can accept connections and then broadcast JSON
 * serialized representations of objects to all connected clients.
 *
 * <p>Messages are zero terminated.</p>
 *
 * @author Eric Bottard
 */
public class BroadcastServer {

	private int port;

	private Map<Integer, ChannelGroup> channelGroups = new LinkedHashMap<>();

	private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private ObjectMapper objectMapper = new ObjectMapper();

	public BroadcastServer(int port) {
		this.port = port;

		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public void broadcast(Object o) {
		try {
			channels.writeAndFlush(Unpooled.copiedBuffer(
					objectMapper.writeValueAsString(o) + "\r\n",
					CharsetUtil.UTF_8)).await();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {

						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							channels.add(ch);
						}

					})
					.option(ChannelOption.SO_BACKLOG, 128)
					.childOption(ChannelOption.SO_KEEPALIVE, true);

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync();

			// Wait until the server socket is closed.
			f.channel().closeFuture().sync();
		}
		finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
