package org.zirbes.kinesis.adapter.services

import com.google.inject.Inject

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import org.zirbes.kinesis.adapter.config.KinesisConfiguration

import ratpack.server.Service
import ratpack.server.StartEvent
import ratpack.server.StopEvent

@CompileStatic
@Slf4j
class KinesisService extends KinesisConsumerService implements Service {

    @Inject
    KinesisService(KinesisConfiguration kinesisConfig) {
        super(kinesisConfig)
    }

    @Override
    void onStart(StartEvent event) {
        log.info "starting."
        start()
    }

    @Override
    void onStop(StopEvent event) {
        log.info "stopping."
        stop()
    }

}
