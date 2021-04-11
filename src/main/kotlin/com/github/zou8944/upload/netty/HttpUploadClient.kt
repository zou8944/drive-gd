package com.github.zou8944.upload.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.http.DefaultHttpRequest
import io.netty.handler.codec.http.HttpMethod
import io.netty.handler.codec.http.HttpVersion
import io.netty.handler.codec.http.multipart.*
import io.netty.util.internal.SocketUtils
import java.io.File
import java.lang.Exception

class HttpUploadClient {

    fun constructHeader() {

    }

    fun sign() {

    }

    /**
     * 简单上传
     * https://help.aliyun.com/document_detail/31978.html
     */
    fun putObject(b: Bootstrap, uri: String, factory: HttpDataFactory, file: File) {

    }

    fun formPost(b: Bootstrap, uri: String, factory: HttpDataFactory, file: File) {
        val future = b.connect(SocketUtils.socketAddress(OSS_HOST, OSS_PORT))
        val channel = future.sync().channel()

        val request = DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, uri)

        val bodyRequestEncoder = HttpPostRequestEncoder(factory, request, true).apply {
            addBodyAttribute("getform", "POST")
            addBodyAttribute("info", "first value")
            addBodyAttribute("secondinfo", "secondvalue ���&")
            addBodyAttribute("thirdinfo", "")
            addBodyAttribute("fourthinfo", "")
            addBodyFileUpload("myfile", file, "multipart/form-data", false)
        }

        val finalRequest = bodyRequestEncoder.finalizeRequest()

        channel.write(finalRequest)
        if (bodyRequestEncoder.isChunked) {
            channel.write(bodyRequestEncoder)
        }
        channel.flush()
        channel.closeFuture().sync()
    }

    fun upload() {

        val eventLoopGroup = NioEventLoopGroup()

        val factory: HttpDataFactory = DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE)
        DiskFileUpload.deleteOnExitTemporaryFile = true // should delete file on exit (in normal exit)
        DiskFileUpload.baseDirectory = null // system temp directory
        DiskAttribute.deleteOnExitTemporaryFile = true // should delete file on exit (in normal exit)
        DiskAttribute.baseDirectory = null // system temp directory

        try {

            val bootstrap = Bootstrap()
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel::class.java).handler(HttpUploadInitializer())
            formPost(bootstrap, "", factory, File("/data/home/floyd/PersonalCode/test.txt"))

        } catch (e: Exception) {
            eventLoopGroup.shutdownGracefully()
            factory.cleanAllHttpData()
        }

    }

}

private const val OSS_HOST = "drive-gd.oss-cn-hangzhou.aliyuncs.com"
private const val OSS_PORT = 80


fun main() {
    HttpUploadClient().upload()
}
