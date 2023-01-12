package com.cao.shoppingApp.domain.response;

import com.cao.shoppingApp.domain.POJO.OrderPOJO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllOrderResponse {
    ServiceStatus serviceStatus;
    List<OrderPOJO> orders;
}
