package com.bachinalabs.pubsub.demo.subscriber;

import com.google.cloud.pubsub.v1.AckReplyConsumer;
import com.google.cloud.pubsub.v1.MessageReceiver;
import com.google.pubsub.v1.PubsubMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Subscriber {

    @Autowired
    private PubSubTemplate pubSubTemplate;

    @Value("${pubsub.subscription}")
    private String subscription;

    @EventListener(ApplicationReadyEvent.class)
    public void subscribe() {
        MessageReceiver messageReceiver = new MessageReceiver() {
            @Override
            public void receiveMessage(PubsubMessage pubsubMessage, AckReplyConsumer ackReplyConsumer) {
                System.out.println(pubsubMessage.getData().toStringUtf8());
                log.info("Subscribed message:{}",pubsubMessage.getData().toStringUtf8());
                ackReplyConsumer.ack();
                log.info("Message has been acknowledged");
            }
        };
        log.info("Subscribing {} to {} ", this.getClass().getSimpleName(), subscription);
        pubSubTemplate.subscribe(subscription, messageReceiver);
    }
}
