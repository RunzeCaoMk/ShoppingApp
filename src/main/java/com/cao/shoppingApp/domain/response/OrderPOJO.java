package com.cao.shoppingApp.domain.response;

import com.cao.shoppingApp.domain.entity.OrderProduct;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPOJO {
    private Timestamp placing_time;
    private String status;
    private String username;

    public OrderPOJO(Timestamp placing_time, String status) {
        this.placing_time = placing_time;
        this.status = status;
    }
}
