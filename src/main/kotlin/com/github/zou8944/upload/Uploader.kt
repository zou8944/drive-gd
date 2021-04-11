package com.github.zou8944.upload

import com.github.zou8944.config.Config
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.*
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.http.*
import io.netty.handler.codec.http.multipart.FileUpload
import io.netty.handler.codec.serialization.ClassResolvers
import io.netty.handler.codec.serialization.ObjectDecoder
import io.netty.handler.codec.serialization.ObjectEncoder
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import java.net.URI

/**
 * 文件上传，基于Netty实现
 * 1. 简单上传
 * 2. 断点续传
 */
class Uploader {

    class FileUploadHandler : ChannelInboundHandlerAdapter() {

        override fun channelActive(ctx: ChannelHandlerContext) {
            println("开始进行请求")
            val uri = URI("/app/shortvideo/videos/recommend")
            val message = "";
            val request = DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1,
                HttpMethod.GET,
                uri.toASCIIString(),
                Unpooled.wrappedBuffer(message.toByteArray())
            )
            request.headers()
                .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes())
            ctx.writeAndFlush(request)
        }

        override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
            println(msg)
        }

    }

    fun upload() {
        val result = connect(80, "apitest.hulaplanet.com").sync()
        result.channel().closeFuture().sync()
    }

    fun connect(port: Int, host: String): ChannelFuture {
        val group = NioEventLoopGroup()
        val bootstrap = Bootstrap()
        bootstrap
            .group(group)
            .channel(NioSocketChannel::class.java)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(object : ChannelInitializer<Channel>() {
                override fun initChannel(ch: Channel) {
                    ch.pipeline()
                        .addLast(HttpClientCodec())
                        .addLast(HttpObjectAggregator(1024 * 10 * 1024))
                        .addLast(HttpContentDecompressor())
                        .addLast(FileUploadHandler())
                }
            })
        return bootstrap.connect(host, port)
    }

}

fun main() {
    Uploader().upload()
}
