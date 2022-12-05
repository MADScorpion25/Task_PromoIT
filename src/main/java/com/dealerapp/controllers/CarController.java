package com.dealerapp.controllers;

import com.dealerapp.dto.CarDto;
import com.dealerapp.models.Car;
import com.dealerapp.models.Configuration;
import com.dealerapp.services.CarService;
import com.dealerapp.validation.CarModelAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/cars")
public class CarController {
    @Autowired
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<CarDto> getCars(){
        return carService.getCarsList();
    }

    @GetMapping("/info/{id}")
    public CarDto getCar(@PathVariable long id){
        return carService.getCarById(id);
    }

    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CarDto carDto) throws URISyntaxException, IOException, CarModelAlreadyExistsException {
        CarDto newCar = carService.saveCar(carDto);
        return ResponseEntity.created(new URI("/cars/" + newCar.getId())).body(newCar);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<CarDto> updateCar(@RequestBody CarDto car){
        CarDto currentCar = carService.updateCar(car);
        return ResponseEntity.ok(currentCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Car> deleteCar(@PathVariable long id){
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
