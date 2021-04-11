package com.github.zou8944.scan

import com.github.zou8944.config.Config
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject

/**
 * 扫描器
 */
@KoinApiExtension
class Scanner : KoinComponent {

    private val config by inject(Config::class.java)

    /**
     * 从候选目录中读取文件，当前暂时用不到，但后面会用到
     * TODO 顶起扫描所有候选项，将需要修改的文件加入数据库
     */
    fun scan() {

    }

}
