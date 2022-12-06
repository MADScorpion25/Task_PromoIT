package com.dealerapp.controllers;

import com.dealerapp.dto.OrderDto;
import com.dealerapp.dto.UserDto;
import com.dealerapp.models.Client;
import com.dealerapp.services.MappingUtils;
import com.dealerapp.services.OrderService;
import com.dealerapp.services.UserService;
import com.dealerapp.validation.exceptions.ConfigurationNotFoundException;
import com.dealerapp.validation.exceptions.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final UserService userService;

    private final MappingUtils mappingUtils;

    @GetMapping
    public List<OrderDto> getOrders(){
        return orderService.getOrdersList();
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable long id) throws OrderNotFoundException {
        return orderService.getOrderById(id);
    }

    @GetMapping("/client/{login}")
    public List<OrderDto> getOrdersByClient(@PathVariable String login){
        UserDto userDto = userService.getUserByLogin(login);
        Client client = (Client) mappingUtils.mapToReviewEntity(userDto);
        return orderService.getOrdersByClient(client);
    }

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) throws URISyntaxException, IOException, ConfigurationNotFoundException {
        OrderDto saveOrder = orderService.saveOrder(orderDto);
        return ResponseEntity.created(new URI("/orders/" + saveOrder.getId())).body(saveOrder);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable long id){
        orderService.updateOrder(id);
        return ResponseEntity.ok().build();
    }
}
