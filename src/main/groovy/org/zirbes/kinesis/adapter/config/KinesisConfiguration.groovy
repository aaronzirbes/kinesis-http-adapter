package org.zirbes.kinesis.adapter.config

class KinesisConfiguration {

    String region = 'us-east-1'
    String stream = 'default'
    String app = 'kinesis-adapter'

    Endpoint endpoint = new Endpoint()

    static class Endpoint {
        String method = 'POST'
        String url
        String mediaType = 'application/json; charset=utf-8'
        Set<Integer> discardStatuses = [400, 408, 422] as Set
    }

}
