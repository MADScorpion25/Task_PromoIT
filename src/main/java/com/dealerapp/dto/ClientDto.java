package com.dealerapp.dto;

import com.dealerapp.models.Order;
import lombok.Data;

import java.util.Set;

@Data
public class ClientDto extends UserDto{
    private Set<Order> orders;
}
