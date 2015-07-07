package org.zirbes.kinesis.adapter.services

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.profile.ProfileCredentialsProvider
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker

import groovy.transform.CompileStatic

import org.zirbes.kinesis.adapter.config.KinesisConfiguration
import org.zirbes.kinesis.adapter.domain.EndpointConfiguration

@CompileStatic
class KinesisConsumerService {

    protected final KinesisConfiguration kinesisConfig
    protected Worker worker

    KinesisConsumerService(KinesisConfiguration kinesisConfig) {
        this.kinesisConfig = kinesisConfig
    }

    void start() {
        EndpointConfiguration endpointConfig = new EndpointConfiguration(
            kinesisConfig.endpoint.method,
            kinesisConfig.endpoint.url,
            kinesisConfig.endpoint.mediaType,
            kinesisConfig.endpoint.discardStatuses
        )
        IRecordProcessorFactory recordProcessorFactory = new KinesisProcessorFactory(endpointConfig)
        worker = new Worker(recordProcessorFactory, configureConsumer())
        Thread.start {
            worker.run()
        }
    }

    void stop() {
        worker.shutdown()
    }

    protected KinesisClientLibConfiguration configureConsumer() {
        AWSCredentialsProvider credProvider = new ProfileCredentialsProvider()
        return new KinesisClientLibConfiguration(kinesisConfig.app, kinesisConfig.stream, credProvider, workerId)
                .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON)
                .withRegionName(kinesisConfig.region)
    }

    protected String getWorkerId() {
        String workerId = InetAddress.getLocalHost().getCanonicalHostName() + ":" + UUID.randomUUID();
    }

}
