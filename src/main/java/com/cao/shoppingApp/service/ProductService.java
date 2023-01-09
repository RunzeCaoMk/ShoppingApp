package com.cao.shoppingApp.service;

import com.cao.shoppingApp.DAO.OrderDAO;
import com.cao.shoppingApp.DAO.ProductDAO;
import com.cao.shoppingApp.domain.Order;
import com.cao.shoppingApp.domain.Product;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.domain.request.CreateProductRequest;
import com.cao.shoppingApp.domain.request.PurchaseRequest;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductDAO productDAO;

    @Autowired
    public void setContentDao(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void createNewProduct(CreateProductRequest request) {
        productDAO.createNewProduct(request.getName(), request.getDescription(), request.getPrice(), request.getStock());
    }

    public List<Product> getAllProduct() {
        return productDAO.getAllProduct();
    }

    public void updatePrice(Integer product_id, double price) {
        productDAO.updatePrice(product_id, price);
    }

    public void updateStock(Integer product_id, Integer stock) {
        productDAO.updateStock(product_id, stock);
    }

    public Product getProductById(Integer id) throws ZeroOrManyException {
        return productDAO.getProductById(id);
    }
}
