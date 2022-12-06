package com.dealerapp.controllers;

import com.dealerapp.dto.CarDto;
import com.dealerapp.models.Car;
import com.dealerapp.services.CarService;
import com.dealerapp.validation.exceptions.CarModelAlreadyExistsException;
import com.dealerapp.validation.exceptions.CarNotFoundException;
import com.dealerapp.validation.exceptions.ConfigurationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    
    private final CarService carService;

    @GetMapping
    public List<CarDto> getCars(){
        return carService.getCarsList();
    }

    @GetMapping("/info/{id}")
    public CarDto getCar(@PathVariable long id) throws CarNotFoundException {
        return carService.getCarById(id);
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) throws URISyntaxException, IOException, CarModelAlreadyExistsException, ConfigurationNotFoundException {
        CarDto newCar = carService.saveCar(carDto);
        return ResponseEntity.created(new URI("/cars/" + newCar.getId())).body(newCar);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CarDto> updateCar(@RequestBody CarDto car) throws ConfigurationNotFoundException {
        CarDto currentCar = carService.updateCar(car);
        return ResponseEntity.ok(currentCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable long id) throws CarNotFoundException {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
