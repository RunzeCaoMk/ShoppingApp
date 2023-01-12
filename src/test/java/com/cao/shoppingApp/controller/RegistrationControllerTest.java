package com.cao.shoppingApp.controller;

import com.cao.shoppingApp.domain.request.RegistrationRequest;
import com.cao.shoppingApp.domain.response.MessageResponse;
import com.cao.shoppingApp.service.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(RegistrationController.class)
@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @BeforeAll
    public static void prepare() {
//        System.out.println("preparing resource");
    }

    @BeforeEach
    public void init() {
//        System.out.println("before each");
    }

//    @Test
//    public void groupAssertions() {
//        int[] numbers = {0, 1, 2, 3, 4};
//        assertAll("numbers",
//                () -> assertEquals(numbers[0], 1),
//                () -> assertEquals(numbers[3], 3),
//                () -> assertEquals(numbers[4], 1)
//        );
//    }

    @Test
    public void testAddEmployee_success() throws Exception {
        RegistrationRequest registrationRequest = RegistrationRequest.builder().id(1).username("lan").password("kk").email("tracy@gmail.com").build();

//        when(userService.createNewUser(registrationRequest));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(registrationRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        MessageResponse addUserResponse = new Gson().fromJson(result.getResponse().getContentAsString(), MessageResponse.class);
        assertEquals("New user account created", addUserResponse.getMessage());
        assertTrue(addUserResponse.getServiceStatus().isSuccess());
    }

//    @Test
//    public void testAddEmployee_failedWhenNoRequestBody() throws Exception {
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/employee")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//        ServiceStatus serviceStatus = new Gson().fromJson(result.getResponse().getContentAsString(), ServiceStatus.class);
//        assertFalse(serviceStatus.getSuccess());
//        assertNotNull(serviceStatus.getErrorMessage());
//    }
//
//    @Test
//    public void testAddEmployee_failedWhenInvalidEmployee() throws Exception {
//        EmployeeRequest employeeRequest = EmployeeRequest.builder().firstname("tracy").lastname("lan").build();
//
//        when(employeeService.addEmployee(any())).thenReturn(1);
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/employee")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new Gson().toJson(employeeRequest))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest())
//                .andReturn();
//        ServiceStatus serviceStatus = new Gson().fromJson(result.getResponse().getContentAsString(), ServiceStatus.class);
//        assertFalse(serviceStatus.getSuccess());
//        assertNotNull(serviceStatus.getErrorMessage());
//
//    }
}
