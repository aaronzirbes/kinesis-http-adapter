package org.zirbes.kinesis.adapter.domain

import com.amazonaws.services.kinesis.model.Record

class HttpResult {

    final Record record
    boolean success = false

    HttpResult(Record record) {
        this.record = record
    }

    HttpResult failed() {
        this.success = false
        return this
    }

    HttpResult succeded() {
        this.success = true
        return this
    }
}

