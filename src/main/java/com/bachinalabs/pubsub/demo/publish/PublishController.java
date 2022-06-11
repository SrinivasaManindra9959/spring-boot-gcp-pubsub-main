package com.bachinalabs.pubsub.demo.publish;

import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/publisher")
public class PublishController {

    @Autowired Publisher publisher;

    @PostMapping
    public String publishMessage(@RequestParam String message) throws ExecutionException, InterruptedException {
        PubsubMessage pubsubMessage = PubsubMessage.newBuilder()
                .setData(ByteString.copyFromUtf8(message))
                .build();
        publisher.publish(pubsubMessage);
        return "Successfully published";
    }
}
