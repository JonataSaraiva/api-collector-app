package com.own.apicollectorapp.metric.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.apicollectorapp.metric.controller.request.MetricTO;
import com.own.apicollectorapp.metric.infra.MessageSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DevicesControllerTest {

    private DevicesController devicesController;
    private final MessageSender messageSender = mock(MessageSender.class);
    private final ObjectMapper objectMapper = mock(ObjectMapper.class);

    @BeforeEach
    public void setup(){
        devicesController = new DevicesController(messageSender, objectMapper);
    }

    @Test
    public void mustSendEventToMessageBroker() throws JsonProcessingException {
        var metric = new MetricTO();

        devicesController.create(metric);

        verify(messageSender, atLeast(1)).send(metric);
    }

}