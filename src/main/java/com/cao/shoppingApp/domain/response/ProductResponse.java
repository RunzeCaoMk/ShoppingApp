package com.cao.shoppingApp.domain.response;

import com.cao.shoppingApp.domain.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse {
    ServiceStatus serviceStatus;
    Product product;
}
