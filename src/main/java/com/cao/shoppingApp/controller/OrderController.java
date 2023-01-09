package com.cao.shoppingApp.controller;


import com.cao.shoppingApp.domain.ServiceStatus;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import com.cao.shoppingApp.service.OrderService;
import com.cao.shoppingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {
    private UserService userService;
    private OrderService orderService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("cancel/{order_id}/{username}")
    public MessageResponse cancelOrder(@PathVariable Integer order_id, @PathVariable String username) throws ZeroOrManyException, NoPermissionException {
        User user = userService.getUserByUsername(username);
        orderService.cancelOrder(order_id, user);

        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Order canceled")
                .build();
    }

    @PutMapping("complete/{order_id}")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public MessageResponse completeOrder(@PathVariable Integer order_id) throws NoPermissionException, ZeroOrManyException {
        orderService.completeOrder(order_id);

        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Order completed")
                .build();
    }

}
