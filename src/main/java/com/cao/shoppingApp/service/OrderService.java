package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.OrderDAO;
import com.cao.shoppingApp.DAO.OrderProductDAO;
import com.cao.shoppingApp.DAO.ProductDAO;
import com.cao.shoppingApp.domain.entity.Order;
import com.cao.shoppingApp.domain.entity.OrderProduct;
import com.cao.shoppingApp.domain.entity.Product;
import com.cao.shoppingApp.domain.entity.User;
import com.cao.shoppingApp.domain.POJO.OrderPOJO;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.NotEnoughInventoryException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    private OrderProductDAO orderProductDAO;
    private UserService userService;

    @Autowired
    public void setContentDao(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Autowired
    public void setOrderProductDAO(OrderProductDAO orderProductDAO) {
        this.orderProductDAO = orderProductDAO;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Order createNewOrder() throws ZeroOrManyException {
        String username = userService.getCurrentUsername();
        return orderDAO.createNewOrder(userService.getUserByUsername(username));
    }

    public void cancelOrder(Integer order_id) throws ZeroOrManyException, NoPermissionException, NotEnoughInventoryException {
        Order targetOrder = orderDAO.getOrderById(order_id);
        targetOrder.setOrderProducts(orderProductDAO.getAllOrderProductByOrder(targetOrder));
        User user = userService.getUserByUsername(userService.getCurrentUsername());

        if (user.getIs_admin() || user.getId() == targetOrder.getId()) {
            if (targetOrder.getStatus().equals("Processing")) {

                // update stock
                List<OrderProduct> orderProducts = targetOrder.getOrderProducts();
                for (OrderProduct op : orderProducts) {
                    productDAO.updateStock(
                            op.getProduct_id().getId(),
                            productDAO.getStockByProduct(op.getProduct_id().getId()) + op.getQuantity()
                    );
                }
                // change status
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

    public List<OrderPOJO> getOrderByUser() throws ZeroOrManyException {
        String username = userService.getCurrentUsername();
        List<Order> orders = orderDAO.getOrderByUser(userService.getUserByUsername(username));
        List<OrderPOJO> orderPOJOs = new ArrayList<>();
        for (Order o : orders) {
            OrderPOJO orderPOJO = new OrderPOJO(o.getPlacing_time(), o.getStatus(), o.getUser().getUsername());
            orderPOJOs.add(orderPOJO);
        }
        return orderPOJOs;
    }

    public List<OrderPOJO> getAllOrders() {
        List<Order> orders = orderDAO.getAllOrder();
        List<OrderPOJO> orderPOJOs = new ArrayList<>();
        for (Order o : orders) {
            OrderPOJO orderPOJO = new OrderPOJO(o.getPlacing_time(), o.getStatus(), o.getUser().getUsername());
            orderPOJOs.add(orderPOJO);
        }
        return orderPOJOs;
    }

    public Order getOrderById(Integer id) throws ZeroOrManyException {
        return orderDAO.getOrderById(id);
    }

    public List<String> getRecent3ItemByUser() throws ZeroOrManyException {
        String username = userService.getCurrentUsername();
        return orderDAO.getRecent3ItemByUser(userService.getUserByUsername(username));
    }

    public List<String> getFrequent3ItemByUser() throws ZeroOrManyException {
        String username = userService.getCurrentUsername();
        return orderDAO.getFrequent3ItemByUser(userService.getUserByUsername(username));
    }

    public List<String> getTop3Product() {
        return orderDAO.getTop3Product();
    }

    public List<String> getTop3User() {
        return orderDAO.getTop3User();
    }
}
