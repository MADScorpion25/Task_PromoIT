package com.dealerapp.services;

import com.dealerapp.dto.CarDto;
import com.dealerapp.models.Car;
import com.dealerapp.models.Configuration;
import com.dealerapp.repo.CarRepository;
import com.dealerapp.validation.CarModelAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class CarService {
    @Autowired
    private final CarRepository carRepository;
    @Autowired
    private final ConfigurationService configurationService;
    @Autowired
    private final MappingUtils mappingUtils;

    public CarDto saveCar(@Valid CarDto carDto) throws IOException, CarModelAlreadyExistsException {
        if(carRepository.existsByModelName(carDto.getModelName())) throw new CarModelAlreadyExistsException(carDto.getModelName());
        Car car = mappingUtils.mapToReviewEntity(carDto);

        if(carDto.getCurConfigs() != null){
            Set<Configuration> configs = new HashSet<>();
            for(String name : carDto.getCurConfigs()){
                Configuration configurationById = mappingUtils.mapToReviewEntity(
                        configurationService.getConfigurationByName(name));
                configs.add(configurationById);
                configurationById.setCar(car);
            }
            car.setConfigurations(configs);
        }

        Car newCar = carRepository.save(car);
        return mappingUtils.mapToReviewDto(newCar);
    }
    public CarDto updateCar(@Valid CarDto carDto){
        Car currentCar = carRepository.getReferenceById(carDto.getId());
        currentCar.setBrandName(carDto.getBrandName());
        currentCar.setModelName(carDto.getModelName());
        currentCar.setProductionYear(carDto.getProductionYear());

        Set<Configuration> configs = new HashSet<>();
        for(String name : carDto.getCurConfigs()){
            Configuration configurationById = mappingUtils.mapToReviewEntity(
                    configurationService.getConfigurationByName(name));
            configs.add(configurationById);
            configurationById.setCar(currentCar);
        }
        for(Configuration cur : currentCar.getConfigurations()){
            if(!configs.contains(cur)){
                cur.setCar(null);
            }
        }
        currentCar.setConfigurations(configs);
        currentCar = carRepository.save(currentCar);
        return mappingUtils.mapToReviewDto(currentCar);
    }
    public void deleteCar(long id){
        Car carById = carRepository.findById(id).get();
        carById.getConfigurations().forEach(c -> c.setCar(null));
        carById.setConfigurations(null);
        carRepository.save(carById);
        carRepository.deleteById(id);
    }
    public CarDto getCarById(long id){
        Car referenceById = carRepository.findById(id).get();
        CarDto carDto = carDtoAddition(referenceById);

        carDto.setFreeConfigs(configurationService.getFreeConfigurationsList());

        String[] list = new String[referenceById.getConfigurations().size()];
        Configuration[] configs = referenceById.getConfigurations().toArray(new Configuration[0]);
        for (int i = 0; i < list.length; i++) {

            list[i] = configs[i].getConfigurationName();
        }
        carDto.setCurConfigs(list);
        return carDto;
    }

    public List<CarDto> getCarsList(){
        return carRepository.findAll()
                .stream().map(this::carDtoAddition)
                .collect(Collectors.toList());
    }

    private CarDto carDtoAddition(Car car){
        CarDto carDto = mappingUtils.mapToReviewDto(car);
        carDto.setConfigurations(car.getConfigurations().stream()
                .map(mappingUtils::mapToReviewDto)
                .collect(Collectors.toSet()));
        return carDto;
    }
}
