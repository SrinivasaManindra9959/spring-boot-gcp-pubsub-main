package com.bachinalabs.pubsub.demo.publish;

import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
@Component
@Slf4j
public class Publisher {

    @Value("${pubsub.topic}")
    private String topic;

    @Autowired
    private PubSubTemplate pubSubTemplate;

    public void publish(PubsubMessage pubsubMessage) throws ExecutionException, InterruptedException {
        log.info("Publishing to the topic [{}], message [{}]", topic, pubsubMessage);
        pubSubTemplate.publish(topic, pubsubMessage).get();
    }
}
