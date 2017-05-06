package uk.co.thefishlive.lx;

import static io.netty.buffer.Unpooled.copiedBuffer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import uk.co.thefishlive.lx.data.PatchEntry;
import uk.co.thefishlive.lx.data.ShowData;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HttpRequestHandler extends ChannelInboundHandlerAdapter
{
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private ShowData data;

    public HttpRequestHandler(ShowData data)
    {
        this.data = data;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        if (msg instanceof FullHttpRequest)
        {
            final FullHttpRequest request = (FullHttpRequest) msg;

            String path = request.uri();
            path = path.substring(1);

            System.out.println("Received request to " + path);
            if (!path.contains("/"))
            {
                ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.BAD_REQUEST,
                        copiedBuffer(HttpResponseStatus.BAD_REQUEST.toString().getBytes())));
                return;
            }

            String method = path.substring(0, path.indexOf("/"));
            System.out.println("Method: " + method);

            String id;
            String responseMessage = "";

            switch (method)
            {
                case "positions":
                    responseMessage = gson.toJson(
                            this.data.getPositions().stream()
                                .sorted((a, b) -> a.getName().compareTo(b.getName()))
                                .collect(Collectors.toList())
                    );
                    break;

                case "position":
                    id = path.substring(path.indexOf('/') + 1);
                    responseMessage = gson.toJson(this.data.getPositions().get(Integer.parseInt(id)));
                    break;

                case "position_fixtures":
                    id = path.substring(path.indexOf('/') + 1);
                    responseMessage = gson.toJson(this.data.getFixtures().stream()
                            .filter((f) -> f.getPosition() == Integer.parseInt(id))
                            .sorted((a, b) -> a.getUnitNumber() - b.getUnitNumber())
                            .collect(Collectors.toList())
                    );
                    break;

                case "luminaires":
                    responseMessage = gson.toJson(this.data.getLuminaires());
                    break;

                case "luminaire":
                    id = path.substring(path.indexOf('/') + 1);
                    responseMessage = gson.toJson(this.data.getLuminaires().get(Integer.parseInt(id)));
                    break;

                case "fixtures":
                    responseMessage = gson.toJson(this.data.getFixtures());
                    break;

                case "fixture":
                    id = path.substring(path.indexOf('/') + 1);
                    responseMessage = gson.toJson(this.data.getFixtures().get(Integer.parseInt(id)));
                    break;

                case "patch":
                    responseMessage = gson.toJson(
                            this.data.getFixtures().stream()
                                .map(f -> new PatchEntry(
                                        f.getChannel(),
                                        f.getAddress(),
                                        f.getUniverse(),
                                        this.data.getLuminaires().get(f.getLamp()).getMode(f.getMode()).getName()
                                ))
                                .sorted((a, b) -> a.getChannel() - b.getChannel())
                                .collect(Collectors.toList())
                    );
                    // TODO
                    break;

                default:
                    ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                            HttpResponseStatus.NOT_FOUND,
                            copiedBuffer(HttpResponseStatus.NOT_FOUND.toString().getBytes())));
                    return;
            }

            sendRequest(ctx, responseMessage.getBytes(), "application/json", HttpUtil.isKeepAlive(request));
        } else
        {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception
    {
        ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.INTERNAL_SERVER_ERROR,
                copiedBuffer(cause.getMessage().getBytes())));

        cause.printStackTrace();
    }

    private void sendRequest(ChannelHandlerContext ctx, byte[] data, String contentType, boolean keepAlive)
    {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK, copiedBuffer(data));

        if (keepAlive)
        {
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }
        response.headers().set(HttpHeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, data.length);

        ctx.writeAndFlush(response);
    }

}
