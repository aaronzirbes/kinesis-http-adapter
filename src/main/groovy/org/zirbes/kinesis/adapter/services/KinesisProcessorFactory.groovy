package org.zirbes.kinesis.adapter.services

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory
import com.amazonaws.services.kinesis.model.Record
import com.squareup.okhttp.OkHttpClient

import groovy.transform.CompileStatic

import org.zirbes.kinesis.adapter.domain.EndpointConfiguration

@CompileStatic
class KinesisProcessorFactory implements IRecordProcessorFactory {

    protected final EndpointConfiguration endpointConfig
    protected final OkHttpClient client = new OkHttpClient()

    KinesisProcessorFactory(EndpointConfiguration endpointConfig) {
        this.endpointConfig = endpointConfig
    }

    @Override
    IRecordProcessor createProcessor() {
        return new KinesisDataProcessor(endpointConfig, client)
    }
}

