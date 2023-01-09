package com.cao.shoppingApp.domain.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private boolean is_admin;
}
