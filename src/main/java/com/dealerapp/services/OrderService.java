package com.dealerapp.services;

import com.dealerapp.dto.OrderDto;
import com.dealerapp.dto.UserDto;
import com.dealerapp.models.Client;
import com.dealerapp.models.Configuration;
import com.dealerapp.models.Order;
import com.dealerapp.repo.OrderRepository;
import com.dealerapp.validation.exceptions.ConfigurationNotFoundException;
import com.dealerapp.validation.exceptions.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
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

    private final OrderRepository orderRepository;

    private final UserService userService;

    private final ConfigurationService configurationService;

    private final MappingUtils mappingUtils;

    public OrderDto getOrderById(long id) throws OrderNotFoundException {
        Order referenceById = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return orderAddition(referenceById);
    }

    public List<OrderDto> getOrdersList(){
        List<Order> all = orderRepository.findAll();

        return all.stream().map(this::orderAddition).collect(Collectors.toList());
    }

    public OrderDto saveOrder(@Valid OrderDto orderDto) throws IOException, ConfigurationNotFoundException {
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

    private OrderDto orderAddition(Order order) {
        OrderDto orderDto = mappingUtils.mapToReviewDto(order);
        orderDto.setCustomerLogin(order.getClient().getLogin());
        try {
            Configuration conf = mappingUtils.mapToReviewEntity(
                    configurationService.getConfigurationById(order.getConfiguration().getId()));
            orderDto.setConfiguration(mappingUtils.mapToReviewDto(conf));
        } catch (ConfigurationNotFoundException ignored) {
        }
        return orderDto;
    }
}
