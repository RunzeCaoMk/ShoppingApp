package com.cao.shoppingApp.domain.response;

import com.cao.shoppingApp.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllProductResponse {
    ServiceStatus serviceStatus;
    List<Product> products;
}
