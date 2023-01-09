package com.cao.shoppingApp.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private Integer id;
    private String name;
    private String description;
    private double price;
    private int stock;
}
