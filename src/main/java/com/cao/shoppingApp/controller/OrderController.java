package com.cao.shoppingApp.controller;


import com.cao.shoppingApp.domain.entity.Order;
import com.cao.shoppingApp.domain.response.ServiceStatus;
import com.cao.shoppingApp.domain.request.PurchaseRequest;
import com.cao.shoppingApp.domain.response.AllOrderResponse;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.domain.response.OrderPOJO;
import com.cao.shoppingApp.domain.response.OrderResponse;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.NotEnoughInventoryException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import com.cao.shoppingApp.service.OrderProductService;
import com.cao.shoppingApp.service.OrderService;
import com.cao.shoppingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class OrderController {
    private UserService userService;
    private OrderService orderService;
    private OrderProductService orderProductService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setOrderProductService(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @PutMapping("/cancel/{order_id}")
    public MessageResponse cancelOrder(@PathVariable Integer order_id) throws ZeroOrManyException, NoPermissionException {
        orderService.cancelOrder(order_id);

        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message("Order canceled")
                .build();
    }

    @PutMapping("/complete/{order_id}")
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

    @GetMapping("/user/orders")
    @PreAuthorize("hasAuthority('User_Permission')")
    public AllOrderResponse getOrderByUser() throws ZeroOrManyException {
        List<OrderPOJO> orders = orderService.getOrderByUser();

        return AllOrderResponse.builder()
                .serviceStatus(ServiceStatus.builder().success(true).build())
                .orders(orders)
                .build();
    }

    @GetMapping("/user/order/{order_id}")
    @PreAuthorize("hasAuthority('User_Permission')")
    public Order getOrderById(@PathVariable Integer order_id) throws ZeroOrManyException {
        return orderService.getOrderById(order_id);
    }

    @PostMapping("/purchase")
    @PreAuthorize("hasAuthority('User_Permission')")
    public OrderResponse purchase(@RequestBody PurchaseRequest request) throws ZeroOrManyException, NotEnoughInventoryException {
        Order order = orderService.createNewOrder();
        orderProductService.createOrderProduct(request, order);
        order.setOrderProducts(request.getOrderProducts());
        return OrderResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .order(order)
                .build();
    }

    @GetMapping("/user/recent3")
    @PreAuthorize("hasAuthority('User_Permission')")
    public MessageResponse getRecent3ItemByUser() throws ZeroOrManyException {
        List<String> items = orderService.getRecent3ItemByUser();
        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message(items.toString())
                .build();
    }

    @GetMapping("/user/frequent3")
    @PreAuthorize("hasAuthority('User_Permission')")
    public MessageResponse getFrequent3ItemByUser() throws ZeroOrManyException {
        List<String> items = orderService.getFrequent3ItemByUser();
        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message(items.toString())
                .build();
    }

    @GetMapping("/admin/top3product")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public MessageResponse getTop3Product() {
        List<String> products = orderService.getTop3Product();
        return MessageResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .message(products.toString())
                .build();
    }

    @GetMapping("/admin/top3user")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public MessageResponse getTop3User() {
        List<String> users = orderService.getTop3User();
        return MessageResponse.builder()
                .serviceStatus(ServiceStatus.builder().success(true).build())
                .message(users.toString())
                .build();
    }

    @GetMapping("/admin/orders")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public AllOrderResponse getAllOrders() throws ZeroOrManyException {
        List<OrderPOJO> orders = orderService.getAllOrders();

        return AllOrderResponse.builder()
                .serviceStatus(ServiceStatus.builder().success(true).build())
                .orders(orders)
                .build();
    }
}
