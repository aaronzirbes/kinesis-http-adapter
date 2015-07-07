Kenisis Router
==============

This will route data from an AWS Kinesis queue to a configured HTTP endpoint 

The default port is 8383 for the /version and /health endpoints

Running
-------

    java -Dratpack.kinesis.stream=firehose \
         -Dratpack.kinesis.app=my-kinesis-app-name \
         -Dratpack.kinesis.endpoint.url=http://localhost:8080/data-monster \
         -jar build/libs/kinesis-http-adapter-1.0.0-all.jar

or

    java -Dratpack.kinesis.stream=yolo \
         -Dratpack.kinesis.region=us-west-1 \
         -Dratpack.kinesis.app=my-kinesis-app-name \
         -Dratpack.kinesis.endpoint.method=POST \
         -Dratpack.kinesis.endpoint.url=http://www.service.example.com/hollow-leg \
         -Dratpack.kinesis.endpoint.mediaType='application/json; charset=utf-8' \
         -jar build/libs/kinesis-http-adapter-1.0.0-all.jar

Checking that it is running

    http POST localhost:8383/health

Filtering
---------

Filtering should be done on the publisher end since Kinesis is a pay per message service

