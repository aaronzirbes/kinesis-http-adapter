package org.zirbes.kinesis.adapter.services

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason
import com.amazonaws.services.kinesis.model.Record
import com.squareup.okhttp.OkHttpClient

import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import groovy.util.logging.Slf4j

import org.zirbes.kinesis.adapter.domain.EndpointConfiguration
import org.zirbes.kinesis.adapter.domain.HttpResult

import rx.Observable
import rx.Subscriber

@CompileStatic
@Slf4j
class KinesisDataProcessor implements IRecordProcessor {

    String shardId

    protected final HttpForwarder forwarder

    KinesisDataProcessor(EndpointConfiguration endpointConfig, OkHttpClient client) {
        forwarder = new HttpForwarder(endpointConfig, client)
    }

    @Override
    void initialize(String shardId) {
        this.shardId = shardId
    }

    @Override
    void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
        observableForRecords(records).subscribe{ HttpResult result ->
            if (result.success) {
                checkpointer.checkpoint(result.record)
            }
        }
    }

    @Override
    void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason reason) {
        log.warn "Shutting down. ${reason}"
        checkpointer.checkpoint()
    }

    protected Observable<HttpResult> observableForRecords(Collection<Record> records) {
        Observable.create(new Observable.OnSubscribe() {
            @Override
            void call(Subscriber subscriber) {
                Thread.start {
                    records.each{ Record record ->
                        if (subscriber.unsubscribed) { return }
                        subscriber.onNext(forwarder.post((record)))
                    }
                    if (!subscriber.unsubscribed) { subscriber.onCompleted() }
                }
            }
        })
    }
}

