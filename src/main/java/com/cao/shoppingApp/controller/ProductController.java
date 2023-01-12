package com.cao.shoppingApp.controller;


import com.cao.shoppingApp.domain.entity.Product;
import com.cao.shoppingApp.domain.response.ServiceStatus;
import com.cao.shoppingApp.domain.request.CreateProductRequest;
import com.cao.shoppingApp.domain.response.AllProductResponse;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.domain.response.ProductResponse;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import com.cao.shoppingApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/admin/product/{product_id}")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public ProductResponse getAdminProduct(@PathVariable Integer product_id) throws ZeroOrManyException {
        Product p = productService.getProductById(product_id);
        p.setOrderProducts(null);

        return ProductResponse.builder()
                .serviceStatus( ServiceStatus.builder().success(true).build())
                .product(p)
                .build();
    }

    @GetMapping("/user/product/{product_id}")
    @PreAuthorize("hasAuthority('User_Permission')")
    public ProductResponse getUserProduct(@PathVariable Integer product_id) throws ZeroOrManyException {
        Product p = productService.getProductById(product_id);
        p.setOrderProducts(null);
        p.setStock(null);

        return ProductResponse.builder()
                .serviceStatus( ServiceStatus.builder().success(true).build())
                .product(p)
                .build();
    }

    @PostMapping("/admin/addProduct")
    @PreAuthorize("hasAuthority('Admin_Permission')")
    public MessageResponse addProduct(@RequestBody CreateProductRequest request) {
        productService.createNewProduct(request);

        return MessageResponse.builder()
                .serviceStatus( ServiceStatus.builder().success(true).build())
                .message("Product created")
                .build();
    }

}
