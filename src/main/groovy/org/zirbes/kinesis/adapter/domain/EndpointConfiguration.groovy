package org.zirbes.kinesis.adapter.domain

class EndpointConfiguration {

    final String method = 'POST'
    final String url
    final Set<Integer> discardStatuses = [400, 408, 422] as Set
    final String mediaType = 'application/json; charset=utf-8'

    EndpointConfiguration(String method,
                          String url,
                          String mediaType,
                          Set<Integer> discardStatuses) {
        this.method = method
        this.url = url
        this.mediaType = mediaType
        this.discardStatuses = discardStatuses
    }

}

