package com.cao.shoppingApp.controller;

import com.cao.shoppingApp.domain.ServiceStatus;
import com.cao.shoppingApp.domain.request.RegistrationRequest;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.exception.ConstraintViolationException;
import com.cao.shoppingApp.exception.EmailExistedException;
import com.cao.shoppingApp.exception.UsernameExistedException;
import com.cao.shoppingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private UserService userService;

    @Autowired
    public void setContentService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public MessageResponse registration(@RequestBody RegistrationRequest request) throws UsernameExistedException, EmailExistedException, ConstraintViolationException {
        userService.createNewUser(request);

        if (request.is_admin()) {
            return MessageResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(true)
                                    .build()
                    )
                    .message("New admin account created")
                    .build();
        } else {
            return MessageResponse.builder()
                    .serviceStatus(
                            ServiceStatus.builder()
                                    .success(true)
                                    .build()
                    )
                    .message("New user account created")
                    .build();
        }
    }

}
