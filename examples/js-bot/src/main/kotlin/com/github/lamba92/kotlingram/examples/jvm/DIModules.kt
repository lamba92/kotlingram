package com.github.lamba92.kotlingram.examples.jvm

import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton
import process

object DIModules {
    val strings
        get() = DI.Module("strings") {
            bind<String>(tag = "message_response") with singleton { process.versions.v8 }
            bind<String>(tag = "media") with singleton {
                "https://coralogix.com/wp-content/uploads/2018/04/Coralogix-Nodejs-integration.jpg"
            }
        }
}
