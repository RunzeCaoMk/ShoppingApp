package com.cao.shoppingApp.domain.request;

import com.cao.shoppingApp.domain.POJO.PurchaseItem;
import com.cao.shoppingApp.domain.entity.OrderProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseRequest {
    List<PurchaseItem> purchaseItems;
}
