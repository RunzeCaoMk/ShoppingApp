package com.cao.shoppingApp.domain.POJO;

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
}
