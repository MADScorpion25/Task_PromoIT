package com.dealerapp.dto;

import com.dealerapp.models.enums.BodyType;
import com.dealerapp.models.enums.CarClass;
import com.dealerapp.models.enums.DriveType;
import com.dealerapp.models.enums.TransmissionType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class ConfigurationDto {
    private long id;
    @NotBlank
    private String configurationName;
    @Min(value = 50)
    @Max(value = 500)
    private short power;
    @Min(value = 10_000)
    @Max(value = 10_000_000)
    private int price;
    @NotNull
    private BodyType bodyType;
    @NotNull
    private CarClass carClass;
    @NotNull
    private DriveType driveType;
    @NotNull
    private TransmissionType transmissionType;
    @NotBlank
    private String description;
    private String imgPath;
    private MultipartFile file;
    private Set<ConfigurationDto> configurations;
    private CarDto car;
    private Set<OrderDto> orders;
}
