package com.cao.shoppingApp.domain.response;

import com.cao.shoppingApp.domain.POJO.OrderPOJO;
import com.cao.shoppingApp.domain.entity.Order;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderResponse {
    ServiceStatus serviceStatus;
    OrderPOJO orderPOJO;
}
