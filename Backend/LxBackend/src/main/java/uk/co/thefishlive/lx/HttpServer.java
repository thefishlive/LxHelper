package uk.co.thefishlive.lx;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import uk.co.thefishlive.lx.data.ShowData;

public class HttpServer
{
    private ChannelFuture channel;
    private final EventLoopGroup masterGroup;
    private final EventLoopGroup slaveGroup;

    private final int port;

    public HttpServer(int port)
    {
        masterGroup = new NioEventLoopGroup();
        slaveGroup = new NioEventLoopGroup();

        this.port = port;
    }

    public void start(ShowData data) // #1
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                shutdown();
            }
        });

        try
        {
            // #3
            final ServerBootstrap bootstrap = new ServerBootstrap().group(masterGroup, slaveGroup)
                    .channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() // #4
                    {
                        @Override
                        public void initChannel(final SocketChannel ch) throws Exception
                        {
                            ch.pipeline().addLast("codec", new HttpServerCodec());
                            ch.pipeline().addLast("aggregator", new HttpObjectAggregator(512 * 1024));
                            ch.pipeline().addLast("request", new HttpRequestHandler(data));
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
            channel = bootstrap.bind(port).sync();
            System.out.println("Bound to port " + port);
        } catch (final InterruptedException e)
        {
        }
    }

    public void shutdown() // #2
    {
        slaveGroup.shutdownGracefully();
        masterGroup.shutdownGracefully();

        try
        {
            channel.channel().closeFuture().sync();
        } catch (InterruptedException e)
        {
        }
    }
}