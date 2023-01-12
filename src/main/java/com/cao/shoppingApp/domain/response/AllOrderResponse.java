package com.cao.shoppingApp.domain.response;

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
