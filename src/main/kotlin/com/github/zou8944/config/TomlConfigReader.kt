package com.github.zou8944.config

import com.moandjiezana.toml.Toml

class TomlConfigReader : ConfigReader {

    override fun read(): Config = Toml()
        .read(this.javaClass.classLoader.getResourceAsStream("config.toml"))
        .to(Config::class.java)

    companion object {
        fun read(): Config = this.read()
    }
}
