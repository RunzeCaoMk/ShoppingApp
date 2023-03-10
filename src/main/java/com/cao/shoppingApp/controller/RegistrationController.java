package com.cao.shoppingApp.controller;

import com.cao.shoppingApp.domain.response.ServiceStatus;
import com.cao.shoppingApp.domain.request.RegistrationRequest;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.exception.ConstraintViolationException;
import com.cao.shoppingApp.exception.EmailExistedException;
import com.cao.shoppingApp.exception.UsernameExistedException;
import com.cao.shoppingApp.exception.ZeroOrManyException;
import com.cao.shoppingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private UserService userService;

    @Autowired
    public void setContentService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public MessageResponse registration(@RequestBody RegistrationRequest request) throws UsernameExistedException, EmailExistedException, ConstraintViolationException, ZeroOrManyException {
        userService.createNewUser(request);
        return MessageResponse.builder()
                .serviceStatus(ServiceStatus.builder().success(true).build())
                .message("New user account created")
                .build();
    }

}
