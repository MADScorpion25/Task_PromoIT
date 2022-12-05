package com.dealerapp.dto;

import lombok.Data;

import java.util.Set;

@Data
public class DealerDto extends UserDto{
    private Set<ReviewDto> reviews;
}
