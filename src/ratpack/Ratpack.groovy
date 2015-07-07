import org.zirbes.kinesis.adapter.config.KinesisConfiguration
import org.zirbes.kinesis.adapter.handlers.KinesisErrorHandler
import org.zirbes.kinesis.adapter.modules.KinesisModule
import org.zirbes.kinesis.adapter.KinesisActionChain

import ratpack.config.ConfigData
import ratpack.error.ServerErrorHandler
import ratpack.jackson.JacksonModule

import static ratpack.groovy.Groovy.ratpack

ratpack {
    serverConfig {
        port 8383
    }
    bindings {
        ConfigData configData = ConfigData.of()
                .yaml(ClassLoader.getSystemResource('application.yml'))
                .env()
                .sysProps()
                .build()
        bindInstance(ConfigData, configData)
        bindInstance(ServerErrorHandler, new KinesisErrorHandler())
        add new KinesisModule(configData.get('/kinesis', KinesisConfiguration))
        add JacksonModule
    }
    handlers {
        handler chain(registry.get(KinesisActionChain))
    }
}
