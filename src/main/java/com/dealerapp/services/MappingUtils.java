package com.dealerapp.services;

import com.dealerapp.dto.*;
import com.dealerapp.models.*;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {

    public ReviewDto mapToReviewDto(Review entity){
        ReviewDto dto = new ReviewDto();
        dto.setId(entity.getId());
        dto.setText(entity.getText());
        dto.setTitle(entity.getTitle());
        dto.setImgPath(entity.getImgPath());
        return dto;
    }

    public Review mapToReviewEntity(ReviewDto dto){
        Review entity = new Review();
        entity.setId(dto.getId());
        entity.setText(dto.getText());
        entity.setTitle(dto.getTitle());
        entity.setImgPath(dto.getImgPath());
        return entity;
    }

    public OrderDto mapToReviewDto(Order entity){
        OrderDto dto = new OrderDto();
        dto.setId(entity.getId());
        dto.setSendDate(entity.getSendDate());
        dto.setDeliveryDate(entity.getDeliveryDate());
        dto.setNotified(entity.isNotified());
        return dto;
    }

    public Order mapToReviewEntity(OrderDto dto){
        Order entity = new Order();
        entity.setId(dto.getId());
        entity.setSendDate(dto.getSendDate());
        entity.setDeliveryDate(dto.getDeliveryDate());
        entity.setNotified(dto.isNotified());
        return entity;
    }

    public ConfigurationDto mapToReviewDto(Configuration entity){
        ConfigurationDto dto = new ConfigurationDto();
        dto.setId(entity.getId());
        dto.setConfigurationName(entity.getConfigurationName());
        dto.setBodyType(entity.getBodyType());
        dto.setCarClass(entity.getCarClass());
        dto.setDescription(entity.getDescription());
        dto.setDriveType(entity.getDriveType());
        dto.setTransmissionType(entity.getTransmissionType());
        dto.setImgPath(entity.getImgPath());
        dto.setPower(entity.getPower());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public Configuration mapToReviewEntity(ConfigurationDto dto){
        Configuration entity = new Configuration();
        entity.setId(dto.getId());
        entity.setConfigurationName(dto.getConfigurationName());
        entity.setBodyType(dto.getBodyType());
        entity.setCarClass(dto.getCarClass());
        entity.setDescription(dto.getDescription());
        entity.setDriveType(dto.getDriveType());
        entity.setTransmissionType(dto.getTransmissionType());
        entity.setImgPath(dto.getImgPath());
        entity.setPower(dto.getPower());
        entity.setPrice(dto.getPrice());
        return entity;
    }

    public CarDto mapToReviewDto(Car entity){
        CarDto dto = new CarDto();
        dto.setId(entity.getId());
        dto.setBrandName(entity.getBrandName());
        dto.setModelName(entity.getModelName());
        dto.setProductionYear(entity.getProductionYear());
        return dto;
    }

    public Car mapToReviewEntity(CarDto dto){
        Car entity = new Car();
        entity.setId(dto.getId());
        entity.setBrandName(dto.getBrandName());
        entity.setModelName(dto.getModelName());
        entity.setProductionYear(dto.getProductionYear());
        return entity;
    }

    public UserDto mapToReviewDto(User entity){
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        return dto;
    }

    public User mapToReviewEntity(UserDto dto){
        User entity = new User();
        switch (dto.getRole()){
            case ADMIN:
                entity = new Admin();
                break;
            case DEALER:
                entity = new Dealer();
                break;
            case CLIENT:
                entity = new Client();
                break;
        }
        entity.setId(dto.getId());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        return entity;
    }
}
