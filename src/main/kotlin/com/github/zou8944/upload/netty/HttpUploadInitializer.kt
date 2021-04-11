package com.github.zou8944.upload.netty

import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.handler.codec.http.HttpClientCodec
import io.netty.handler.codec.http.HttpContentCompressor
import io.netty.handler.ssl.SslContext
import io.netty.handler.stream.ChunkedWriteHandler

class HttpUploadInitializer(
    private val sslCtx: SslContext? = null
) : ChannelInitializer<Channel>() {

    override fun initChannel(ch: Channel) {

        val pipeline = ch.pipeline()

        if (sslCtx != null) {
            pipeline.addLast("ssl", sslCtx.newHandler(ch.alloc()))
        }

        pipeline
            .addLast("http codec", HttpClientCodec())
            .addLast("inflater", HttpContentCompressor())
            .addLast("chunk writer", ChunkedWriteHandler())
            .addLast("logic handler", HttpUploadClientHandler())
    }

}
