package com.github.zou8944.watcher

import java.io.File
import java.nio.file.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


fun main() {
    val watchService = FileSystems.getDefault().newWatchService()
    val watchDirectories = listOf(
        "/home/floyd",
        "/home/floyd/PersonalCode",
        "/home/floyd/Postman",
        "/home/floyd/Software",
        "/home/floyd/WorkCode",
        "/home/floyd/Documents",
        "/home/floyd/Videos",
        "/home/floyd/Downloads",
        "/home/floyd/Pictures",
    )
    var counter = AtomicInteger(0)
    watchDirectories.forEach { watchDirectory ->
        File(watchDirectory).walkTopDown().filter { it.isDirectory }.forEach { directory ->
            Paths.get(directory.absolutePath).register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.OVERFLOW
            )
            println(counter.incrementAndGet())
        }
    }

    while (true) {
        // 3. 获取准备好的事件，pool() 立即返回、take() 阻塞
        val watchKey = watchService.poll(2, TimeUnit.SECONDS) ?: continue
        // 4. 处理准备好的事件
        val watchEvents: List<WatchEvent<*>> = watchKey.pollEvents()
        for (event in watchEvents) {
            when {
                event.kind().name().equals(StandardWatchEventKinds.ENTRY_CREATE.name()) -> {
                    println("create: " + event.context())
                }
                event.kind().name().equals(StandardWatchEventKinds.ENTRY_MODIFY.name()) -> {
                    println("modify: " + event.context())
                }
                event.kind().name().equals(StandardWatchEventKinds.ENTRY_DELETE.name()) -> {
                    println("delete: " + event.context())
                }
            }
        }
        // 5. 重启该线程，因为处理文件可能是一个耗时的过程，因此调用 pool() 时需要阻塞监控器线程
        val valid: Boolean = watchKey.reset()
        if (!valid) {
            break
        }

    }
}
