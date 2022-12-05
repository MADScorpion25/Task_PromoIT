package com.dealerapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReviewDto {
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private String imgPath;
    private String dealerLogin;
    private DealerDto dealer;
}
