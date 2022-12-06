package com.dealerapp.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CarDto {
    private long id;
    @NotBlank(message = "Car's brand name cannot be empty")
    private String brandName;
    @NotBlank(message = "Car's model name cannot be empty")
    private String modelName;
    @Min(value = 1900)
    @Max(value = 2022)
    private short productionYear;
    private String[] curConfigs;
    private String[] freeConfigs;
    private Set<ConfigurationDto> configurations;
}
