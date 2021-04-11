package com.github.zou8944

import com.github.zou8944.config.TomlConfigReader
import com.github.zou8944.scan.Scanner
import com.github.zou8944.upload.Uploader
import org.koin.core.component.KoinApiExtension
import org.koin.core.context.startKoin
import org.koin.dsl.module

// 声明依赖关系
@OptIn(KoinApiExtension::class)
val applicationModule = module {
    single { TomlConfigReader.read() }
    single { Scanner() }
    single { Uploader() }
}

// 应用启动逻辑
fun start() {

}

fun main() {

    startKoin {
        printLogger()
        modules(applicationModule)
    }

    start()
}
