package com.cao.shoppingApp.controller;


import com.cao.shoppingApp.domain.Product;
import com.cao.shoppingApp.domain.ServiceStatus;
import com.cao.shoppingApp.domain.User;
import com.cao.shoppingApp.domain.response.AllProductResponse;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.exception.NoPermissionException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import com.cao.shoppingApp.service.OrderService;
import com.cao.shoppingApp.service.ProductService;
import com.cao.shoppingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.util.List;


@RestController
public class ProductController {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/user/home")
    @PreAuthorize("hasAuthority('User_Permission')")
    public AllProductResponse getUserHome() {
        List<Product> products = productService.getAllProduct();

        // hide stock from user
        for (Product p : products) {
            p.setStock(0);
            p.setOrderProducts(null);
        }

        return AllProductResponse.builder()
                .serviceStatus(
                        ServiceStatus.builder()
                                .success(true)
                                .build()
                )
                .products(products)
                .build();
    }

    @GetMapping("/admin/home")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public AllProductResponse getAdminHome() {
        List<Product> products = productService.getAllProduct();

        for (Product p : products) {
            p.setOrderProducts(null);
        }

        return AllProductResponse.builder()
                .serviceStatus(ServiceStatus.builder().success(true).build())
                .products(products)
                .build();
    }

    @PutMapping("/admin/updatePrice/{product_id}/{price}")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public MessageResponse updatePrice(@PathVariable Integer product_id, @PathVariable double price) {
        productService.updatePrice(product_id, price);

        return MessageResponse.builder()
                .serviceStatus( ServiceStatus.builder().success(true).build())
                .message("Price updated")
                .build();
    }

    @PutMapping("/admin/updateStock/{product_id}/{stock}")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public MessageResponse updateStock(@PathVariable Integer product_id, @PathVariable Integer stock) {
        productService.updateStock(product_id, stock);

        return MessageResponse.builder()
                .serviceStatus( ServiceStatus.builder().success(true).build())
                .message("Stock updated")
                .build();
    }

}
