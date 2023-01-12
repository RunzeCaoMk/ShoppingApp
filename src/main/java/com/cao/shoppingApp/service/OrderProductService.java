package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.OrderProductDAO;
import com.cao.shoppingApp.DAO.ProductDAO;
import com.cao.shoppingApp.domain.POJO.PurchaseItem;
import com.cao.shoppingApp.domain.entity.Order;
import com.cao.shoppingApp.domain.entity.OrderProduct;
import com.cao.shoppingApp.domain.entity.Product;
import com.cao.shoppingApp.domain.request.PurchaseRequest;
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

    public void createOrderProduct(PurchaseRequest request, Order order) throws NotEnoughInventoryException, ZeroOrManyException {
        List<PurchaseItem> purchaseItems = request.getPurchaseItems();

        for (PurchaseItem pi : purchaseItems) {
            Product product = productDAO.getProductById(pi.getProduct_id());
            int quantity = pi.getQuantity();

            orderProductDAO.createNewOrderProduct(order, product, quantity);
            productDAO.updateStock(product.getId(), productDAO.getStockByProduct(product.getId()) - quantity);
        }

    }
}
