package org.zirbes.kinesis.adapter.modules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.google.inject.Singleton

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.zirbes.kinesis.adapter.KinesisActionChain
import org.zirbes.kinesis.adapter.config.KinesisConfiguration
import org.zirbes.kinesis.adapter.handlers.HealthHandler
import org.zirbes.kinesis.adapter.handlers.VersionHandler
import org.zirbes.kinesis.adapter.services.KinesisService

@CompileStatic
@Slf4j
class KinesisModule extends AbstractModule {

    private final KinesisConfiguration kinesisConfig

    KinesisModule(KinesisConfiguration kinesisConfig) {
        this.kinesisConfig = kinesisConfig

    }

    @Override
    protected void configure() {
        bind(KinesisActionChain).in(Scopes.SINGLETON)
        bind(VersionHandler).in(Scopes.SINGLETON)
        bind(HealthHandler).in(Scopes.SINGLETON)
        bind(KinesisService).in(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    KinesisConfiguration providesKinesisConfiguration() {
        kinesisConfig
    }

}
