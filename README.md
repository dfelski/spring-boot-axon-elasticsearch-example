# Spring Boot Axon Elasticsearch Example
Simple Spring Boot application using Axon Framework for CQRS and event sourcing and Elasticsearch for projection.

## Run

`docker-compose up --build`

This will start elasticsearch and our application.

## Test

First create the `things` index in elasticsearch

`curl -X PUT localhost:9200/things`

No let's switch to our application and create a new `thing`:

`curl -X PUT -H "Content-Type: application/json" -d '{"name":"testThing"}' localhost:8080`

The result of this operation should be a UUID. Use it to retrieve the data

`curl localhost:8080/<uuid>`
