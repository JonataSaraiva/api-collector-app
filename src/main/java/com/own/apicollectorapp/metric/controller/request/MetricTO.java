package com.own.apicollectorapp.metric.controller.request;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class MetricTO {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private String group;
    private String value;
    private Date timestamp;
}