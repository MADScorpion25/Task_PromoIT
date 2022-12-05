package com.dealerapp.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private long id;
    private Date sendDate;
    private Date deliveryDate;
    private boolean isNotified;
    private String customerLogin;
    private String configurationName;
    private ConfigurationDto configuration;
    private ClientDto client;
}
