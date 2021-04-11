package com.github.zou8944.upload.oss

import com.github.zou8944.config.Config

class OSSRequestSigner(config: Config) : RequestSigner {

    private val accessKeyId = config.accessKeyId
    private val accessKeySecret = config.accessKeySecret


    override fun sign(): String {
        if (accessKeyId.isNotBlank() && accessKeySecret.isNotBlank()) {

        }
        return ""
    }
}
