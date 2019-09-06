package org.example.query;

import org.apache.http.HttpHost;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.example.coreapi.Thing;
import org.example.coreapi.ThingCreatedEvent;
import org.example.coreapi.ThingQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Component
class ThingsProjection {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThingsProjection.class);

    private RestHighLevelClient client;

    @PostConstruct
    void init(){
        client = new RestHighLevelClient(RestClient.builder(
                new HttpHost("elasticsearch", 9200, "http"),
                new HttpHost("elasticsearch", 9201, "http")
        ));
    }

    @PreDestroy
    void tearDown() throws Exception{
        client.close();
    }

    @EventHandler
    protected void on(ThingCreatedEvent thingCreatedEvent) throws Exception{
        LOGGER.info("handling {}", thingCreatedEvent);
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", thingCreatedEvent.getName());
        IndexRequest indexRequest = new IndexRequest("things").id(thingCreatedEvent.getId().toString()).source(jsonMap);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    @QueryHandler
    Thing on(ThingQuery thingQuery) throws Exception{
        LOGGER.info("handling {}", thingQuery);
        GetRequest getRequest = new GetRequest("things", thingQuery.getId().toString());
        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
        return new Thing(
                UUID.fromString(getResponse.getId()),
                getResponse.getSource().get("name").toString());
    }

}
