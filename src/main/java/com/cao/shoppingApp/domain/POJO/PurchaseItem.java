package com.cao.shoppingApp.domain.POJO;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseItem {
    private int product_id;
    private int quantity;
}
