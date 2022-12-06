package com.dealerapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReviewDto {
    private long id;
    @NotBlank(message = "Review title cannot be empty")
    private String title;
    @NotBlank(message = "Review text cannot be empty")
    private String text;
    private String imgPath;
    private String dealerLogin;
    private DealerDto dealer;
}
