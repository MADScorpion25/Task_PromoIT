package com.dealerapp.services;

import com.dealerapp.dto.CarDto;
import com.dealerapp.dto.ConfigurationDto;
import com.dealerapp.dto.OrderDto;
import com.dealerapp.models.Configuration;
import com.dealerapp.models.enums.BodyType;
import com.dealerapp.models.enums.CarClass;
import com.dealerapp.models.enums.DriveType;
import com.dealerapp.models.enums.TransmissionType;
import com.dealerapp.repo.ConfigurationRepository;
import com.dealerapp.validation.exceptions.ConfigurationAlreadyExists;
import com.dealerapp.validation.exceptions.ConfigurationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.dealerapp.services.ImgService.deleteImg;
import static com.dealerapp.services.ImgService.uploadImg;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@Validated
@RequiredArgsConstructor
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    private final MappingUtils mappingUtils;

    @Value("${upload.path}")
    private String path;

    public ConfigurationDto saveConfiguration(@Valid ConfigurationDto configurationDto, MultipartFile file) throws IOException, ConfigurationAlreadyExists {
        if(configurationRepository.existsByConfigurationName(configurationDto.getConfigurationName()))
            throw new ConfigurationAlreadyExists(configurationDto.getConfigurationName());
        Configuration configuration = mappingUtils.mapToReviewEntity(configurationDto);
        String imgFileName = uploadImg(file, path);
        if(StringUtils.hasLength(imgFileName)) configuration.setImgPath(imgFileName);
        return mappingUtils.mapToReviewDto(configurationRepository.save(configuration));
    }

    public ConfigurationDto updateConfiguration(@Valid ConfigurationDto configurationDto, MultipartFile fileUp) throws IOException {
        Configuration car = mappingUtils.mapToReviewEntity(configurationDto);
        Configuration currentConfiguration = configurationRepository.getReferenceById(car.getId());

        currentConfiguration.setConfigurationName(car.getConfigurationName());
        currentConfiguration.setBodyType(car.getBodyType());
        currentConfiguration.setCarClass(car.getCarClass());
        currentConfiguration.setTransmissionType(car.getTransmissionType());
        currentConfiguration.setPower(car.getPower());
        currentConfiguration.setPrice(car.getPrice());
        currentConfiguration.setDescription(car.getDescription());
        currentConfiguration.setDriveType(car.getDriveType());

        String imgFileName = "";
        if(fileUp != null)imgFileName = uploadImg(fileUp, path);
        else car.setImgPath(currentConfiguration.getImgPath());
        if(StringUtils.hasLength(imgFileName)) {
            deleteImg(currentConfiguration.getImgPath(), path);
            car.setImgPath(imgFileName);
            currentConfiguration.setImgPath(imgFileName);
        }

        currentConfiguration = configurationRepository.save(car);
        return mappingUtils.mapToReviewDto(currentConfiguration);
    }

    public ConfigurationDto getConfigurationByName(String name) throws ConfigurationNotFoundException {
      return configurationDtoAddition(
                configurationRepository.findByConfigurationName(name)
                        .orElseThrow(() ->  new ConfigurationNotFoundException(name)));
    }

    public void deleteConfiguration(long id) throws ConfigurationNotFoundException {
        Configuration configuration = configurationRepository.findById(id).orElseThrow(() ->  new ConfigurationNotFoundException(id));
        configuration.setCar(null);

        deleteImg(configuration.getImgPath(), path);
        configurationRepository.deleteById(id);
    }

    public ConfigurationDto getConfigurationById(long id) throws ConfigurationNotFoundException {
        return configurationDtoAddition(
                configurationRepository.findById(id)
                        .orElseThrow(() ->  new ConfigurationNotFoundException(id)));
    }

    public String[] getFreeConfigurationsList(){
        return configurationRepository.findAll().stream().filter(conf -> conf.getCar() == null)
                .map(Configuration::getConfigurationName).toArray(String[]::new);
    }
    public List<String> getBodyTypes(){
        return Arrays.stream(BodyType.values()).map(BodyType::toString).collect(toList());
    }
    public List<String> getCarClasses(){
        return Arrays.stream(CarClass.values()).map(CarClass::toString).collect(toList());
    }
    public List<String> getDriveTypes(){
        return Arrays.stream(DriveType.values()).map(DriveType::toString).collect(toList());
    }
    public List<String> getTransmissionTypes(){
        return Arrays.stream(TransmissionType.values()).map(TransmissionType::toString).collect(toList());
    }
    public List<ConfigurationDto> getConfigurationsList(){
        return configurationRepository.findAll()
                .stream().map(this::configurationDtoAddition)
                .collect(toList());
    }

    private ConfigurationDto configurationDtoAddition(Configuration configuration){
        ConfigurationDto configurationDto = mappingUtils.mapToReviewDto(configuration);
        if(configuration.getCar() != null) {
            CarDto carDto = mappingUtils.mapToReviewDto(configuration.getCar());
            configurationDto.setCar(carDto);
        }
        Set<OrderDto> orderDtos = configuration.getOrders()
                .stream().map(mappingUtils::mapToReviewDto)
                .collect(toSet());

        configurationDto.setOrders(orderDtos);
        return configurationDto;
    }
}
