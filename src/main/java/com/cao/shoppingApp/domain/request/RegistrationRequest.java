package com.cao.shoppingApp.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {
    private Integer id;
    private String username;
    private String password;
    private String email;
}
