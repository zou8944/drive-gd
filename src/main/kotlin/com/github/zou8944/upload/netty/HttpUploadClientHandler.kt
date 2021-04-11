package com.github.zou8944.upload.netty

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.HttpObject

class HttpUploadClientHandler : SimpleChannelInboundHandler<HttpObject>() {

    override fun channelRead0(ctx: ChannelHandlerContext?, msg: HttpObject?) {

    }

    override fun exceptionCaught(ctx: ChannelHandlerContext?, cause: Throwable?) {

    }
}
