package com.own.apicollectorapp.metric.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.own.apicollectorapp.metric.controller.request.MetricTO;
import com.own.apicollectorapp.metric.infra.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/devices")
public class DevicesController {

    private final MessageSender messageSender;
    private final ObjectMapper objectMapper;

    public DevicesController(MessageSender messageSender, ObjectMapper objectMapper) {
        this.messageSender = messageSender;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody MetricTO metricTO) throws JsonProcessingException {
        log.info("New metric received {}", metricTO);
        messageSender.send(metricTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapper.writeValueAsString(metricTO));
    }

}

