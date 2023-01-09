package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.OrderDAO;
import com.cao.shoppingApp.domain.Order;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.domain.request.PurchaseRequest;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderDAO orderDAO;

    @Autowired
    public void setContentDao(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public void createNewOrder(PurchaseRequest request) {
    }

    public void cancelOrder(Integer order_id, User user) throws ZeroOrManyException, NoPermissionException {
        Order targetOrder = orderDAO.getOrderById(order_id);
        if (user.getIs_admin() || user.getId() == targetOrder.getId()) {
            if (targetOrder.getStatus().equals("Processing")) {
                orderDAO.cancelOrder(order_id);
            } else {
                throw new NoPermissionException("Order is not processing and can not be canceled.");
            }
        } else {
            throw new NoPermissionException("You don't have permission to cancel this order.");
        }
    }

    public void completeOrder(Integer order_id) throws NoPermissionException, ZeroOrManyException {
        Order targetOrder = orderDAO.getOrderById(order_id);
        if (targetOrder.getStatus().equals("Processing")) {
            orderDAO.completeOrder(order_id);
        } else {
            throw new NoPermissionException("Order is not processing and can not be completed.");
        }
    }
}
