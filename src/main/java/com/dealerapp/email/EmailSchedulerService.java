package com.dealerapp.email;

import com.dealerapp.dto.OrderDto;
import com.dealerapp.models.Order;
import com.dealerapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmailSchedulerService {
    private static final String CRON = "*/10 * * * * *";

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final EmailService emailService;

    @Scheduled(cron = CRON)
    @Transactional
    public void sendMailToUsers() {
        List<OrderDto> ordersList = orderService.getOrdersList();
        if(ordersList == null) return;

        List<OrderDto> orders = ordersList.stream().filter(order -> !order.isNotified() && order.getDeliveryDate() != null).collect(Collectors.toList());
        for(OrderDto ord : orders){
            emailService.send(ord.getCustomerLogin(), "MARSCARS Company", "Delivery on configuration " + ord.getConfigurationName() + " ready");
            orderService.setOrderNotified(ord.getId());
        }
    }
}
