package com.dealerapp.dto;

import com.dealerapp.models.Configuration;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CarDto {
    private long id;
    @NotBlank
    private String brandName;
    @NotBlank
    private String modelName;
    @Min(value = 1900)
    @Max(value = 2022)
    private short productionYear;
    private String[] curConfigs;
    private String[] freeConfigs;
    private Set<ConfigurationDto> configurations;
}
