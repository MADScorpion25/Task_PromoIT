package com.dealerapp.services;

import com.dealerapp.dto.OrderDto;
import com.dealerapp.dto.UserDto;
import com.dealerapp.email.EmailServiceImpl;
import com.dealerapp.models.Client;
import com.dealerapp.models.Configuration;
import com.dealerapp.models.Order;
import com.dealerapp.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final ConfigurationService configurationService;
    @Autowired
    private final MappingUtils mappingUtils;

    public OrderDto getOrderById(long id){
        Order referenceById = orderRepository.getReferenceById(id);
        return orderAddition(referenceById);
    }

    public List<OrderDto> getOrdersList(){
        List<Order> all = orderRepository.findAll();

        return all.stream().map(this::orderAddition).collect(Collectors.toList());
    }

    public OrderDto saveOrder(@Valid OrderDto orderDto) throws IOException {
        orderDto.setSendDate(new Date());
        Order order = mappingUtils.mapToReviewEntity(orderDto);

        UserDto userDto = userService.getUserByLogin(orderDto.getCustomerLogin());
        Client client = (Client) mappingUtils.mapToReviewEntity(userDto);
        order.setClient(client);

        order.setConfiguration(mappingUtils.mapToReviewEntity(
                configurationService.getConfigurationByName(orderDto.getConfigurationName())));

        Order newOrder = orderRepository.save(order);
        return mappingUtils.mapToReviewDto(newOrder);
    }

    public List<OrderDto> getOrdersByClient(Client client){
        return orderRepository.findByClient(client)
                .stream().map(this::orderAddition)
                .collect(Collectors.toList());
    }

    public Order updateOrder(Long id){
        Order order = orderRepository.getReferenceById(id);
        order.setDeliveryDate(new Date());
        return orderRepository.save(order);
    }

    public Order setOrderNotified(Long id){
        Order order = orderRepository.getReferenceById(id);
        order.setNotified(true);
        return orderRepository.save(order);
    }

    private OrderDto orderAddition(Order order){
        OrderDto orderDto = mappingUtils.mapToReviewDto(order);
        orderDto.setCustomerLogin(order.getClient().getLogin());
        Configuration conf = mappingUtils.mapToReviewEntity(
                configurationService.getConfigurationById(order.getConfiguration().getId()));
        orderDto.setConfiguration(mappingUtils.mapToReviewDto(conf));
        return orderDto;
    }
}
