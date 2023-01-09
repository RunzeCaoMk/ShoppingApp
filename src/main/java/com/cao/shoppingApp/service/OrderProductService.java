package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.OrderDAO;
import com.cao.shoppingApp.DAO.OrderProductDAO;
import com.cao.shoppingApp.DAO.ProductDAO;
import com.cao.shoppingApp.DAO.UserDAO;
import com.cao.shoppingApp.domain.Order;
import com.cao.shoppingApp.domain.OrderProduct;
import com.cao.shoppingApp.domain.Product;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.domain.request.PurchaseRequest;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.NotEnoughInventoryException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {
    private OrderProductDAO orderProductDAO;
    private ProductDAO productDAO;

    @Autowired
    public void setOrderProductDAO(OrderProductDAO orderProductDAO) {
        this.orderProductDAO = orderProductDAO;
    }

    @Autowired
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void createOrderProduct(PurchaseRequest request, Order order) throws NotEnoughInventoryException {
        List<OrderProduct> orderProducts = request.getOrderProducts();

        for (OrderProduct op : orderProducts) {
            Product product = op.getProduct_id();
            int quantity = op.getQuantity();
            if (productDAO.getStockByProduct(product.getId()) < quantity ) {
                throw new NotEnoughInventoryException("Not enough items for " + product.getName());
            }
            orderProductDAO.createNewOrderProduct(order, product, quantity);
            productDAO.updateStock(product.getId(), productDAO.getStockByProduct(product.getId()) - quantity);
        }

    }
}
