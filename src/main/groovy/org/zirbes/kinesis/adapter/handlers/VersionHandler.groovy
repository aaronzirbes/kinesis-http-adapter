package org.zirbes.kinesis.adapter.handlers

import groovy.transform.CompileStatic

import ratpack.groovy.handling.GroovyContext
import ratpack.groovy.handling.GroovyHandler

import static ratpack.jackson.Jackson.json

@CompileStatic
class VersionHandler extends GroovyHandler {


    @Override
    protected void handle(GroovyContext context) {
        context.response.status(200)
        context.render(json([version: '1.0.0']))
    }

}

