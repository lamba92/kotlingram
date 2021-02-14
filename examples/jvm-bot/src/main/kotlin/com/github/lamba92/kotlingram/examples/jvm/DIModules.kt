package com.github.lamba92.kotlingram.examples.jvm

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

object DIModules {
    val strings
        get() = DI.Module("strings") {
            bind<String>(tag = "message_response") with singleton {
                buildString {
                    append(System.getProperty("java.vm.name"))
                    append(", version ")
                    append(System.getProperty("java.vm.version"))
                }
            }
            bind<String>(tag = "media") with singleton {
                "https://www.tc-web.it/wp-content/uploads/2019/01/java.jpg"
            }
        }
}
