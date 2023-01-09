package com.cao.shoppingApp.controller;


import com.cao.shoppingApp.domain.ServiceStatus;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setContentService(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("create")
//    @PreAuthorize("hasAuthority('write')")
//    public MessageResponse createContent(@RequestBody ContentCreationRequest request){
//        userService.createContent(request);
//
//        return MessageResponse.builder()
//                .serviceStatus(
//                        ServiceStatus.builder()
//                                .success(true)
//                                .build()
//                )
//                .message("New content created")
//                .build();
//    }

}
