package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.OrderDAO;
import com.cao.shoppingApp.DAO.UserDAO;
import com.cao.shoppingApp.domain.Order;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.domain.request.PurchaseRequest;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private UserDAO userDAO;
    private OrderDAO orderDAO;

    @Autowired
    public void setContentDao(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public Order createNewOrder() throws ZeroOrManyException {
        String username = userDAO.getCurrentUsername();
        return orderDAO.createNewOrder(userDAO.getUserByUsername(username));
    }

    public void cancelOrder(Integer order_id) throws ZeroOrManyException, NoPermissionException {
        Order targetOrder = orderDAO.getOrderById(order_id);
        User user = userDAO.getUserByUsername(userDAO.getCurrentUsername());

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

    public List<Order> getOrderByUser() throws ZeroOrManyException {
        String username = userDAO.getCurrentUsername();
        return orderDAO.getOrderByUser(userDAO.getUserByUsername(username));
    }

    public Order getOrderById(Integer id) throws ZeroOrManyException {
        return orderDAO.getOrderById(id);
    }
}
