package com.own.apicollectorapp.metric.infra;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.apicollectorapp.metric.controller.request.MetricTO;
import io.awspring.cloud.messaging.core.QueueMessageChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageSender {
    private final AmazonSQSAsync amazonSqs;
    private final ObjectMapper objectMapper;
    // TODO - EXTRACT TO ENV VARIABLE
    private static final String QUEUE_NAME = "http://localhost:4566/000000000000/metric-received";

    @Autowired
    public MessageSender(final AmazonSQSAsync amazonSQSAsync, ObjectMapper objectMapper) {
        this.amazonSqs = amazonSQSAsync;
        this.objectMapper = objectMapper;
    }

    public void send(MetricTO messagePayload) {
        var messageChannel = new QueueMessageChannel(amazonSqs, QUEUE_NAME);

        try {
            var message = MessageBuilder.withPayload(objectMapper.writeValueAsString(messagePayload))
                    .setHeader("origin", "api-collector")
                    .build();

            var waitTimeoutMillis = 5000;
            var sentStatus = messageChannel.send( message, waitTimeoutMillis );

            log.info("Message sent with success? : {}", sentStatus);
        } catch (JsonProcessingException e) {
            // TODO - CREATE A EXCEPTION HANDLER TO PROVIDE A GOOD MESSAGE FOR THE CLIENT
            log.error("Error to send message to queue {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}