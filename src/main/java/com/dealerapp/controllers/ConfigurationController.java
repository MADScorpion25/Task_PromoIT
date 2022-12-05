package com.dealerapp.controllers;

import com.dealerapp.dto.ConfigurationDto;
import com.dealerapp.models.Configuration;
import com.dealerapp.models.enums.BodyType;
import com.dealerapp.models.enums.CarClass;
import com.dealerapp.models.enums.DriveType;
import com.dealerapp.models.enums.TransmissionType;
import com.dealerapp.services.ConfigurationService;
import com.dealerapp.validation.ConfigurationAlreadyExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/configurations")
public class ConfigurationController {
    @Autowired
    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping("/free")
    public String[] getFreeConfigurations(){
        return configurationService.getFreeConfigurationsList();
    }

    @GetMapping
    public List<ConfigurationDto> getConfigurationsList(){
        return configurationService.getConfigurationsList();
    }
    @GetMapping("/body-types")
    public List<String> getBodyTypes(){
        return configurationService.getBodyTypes();
    }
    @GetMapping("/car-classes")
    public List<String> getCarClasses(){
        return configurationService.getCarClasses();
    }
    @GetMapping("/drive-types")
    public List<String> getDriveTypes(){
        return configurationService.getDriveTypes();
    }
    @GetMapping("/transmission-types")
    public List<String> getTransmissionTypes(){
        return configurationService.getTransmissionTypes();
    }
    @GetMapping("/{id}")
    public ConfigurationDto getConfiguration(@PathVariable long id){
        return configurationService.getConfigurationById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ConfigurationDto> createConfiguration(@ModelAttribute ConfigurationDto configuration,
                                                             @RequestParam("fileUp") MultipartFile fileUp) throws IOException, URISyntaxException, ConfigurationAlreadyExists {
        ConfigurationDto newConfiguration = configurationService.saveConfiguration(configuration, fileUp);
        return ResponseEntity.created(new URI("/configurations/" + newConfiguration.getId())).body(newConfiguration);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/edit/{id}")
    public ResponseEntity<ConfigurationDto> updateConfiguration(@ModelAttribute ConfigurationDto configuration,
                                                             @RequestParam(value = "fileUp", required = false) MultipartFile fileUp) throws IOException {
        ConfigurationDto currentConfiguration = configurationService.updateConfiguration(configuration, fileUp);
        return ResponseEntity.ok(currentConfiguration);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ConfigurationDto> deleteConfiguration(@PathVariable Long id){
        configurationService.deleteConfiguration(id);
        return ResponseEntity.ok().build();
    }
}
