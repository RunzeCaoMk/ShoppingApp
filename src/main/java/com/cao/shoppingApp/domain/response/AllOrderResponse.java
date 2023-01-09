package com.cao.shoppingApp.domain.response;

import com.cao.shoppingApp.domain.Order;
import com.cao.shoppingApp.domain.Product;
import com.cao.shoppingApp.domain.ServiceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllOrderResponse {
    ServiceStatus serviceStatus;
    List<Order> orders;
}