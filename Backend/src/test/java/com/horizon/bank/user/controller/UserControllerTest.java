package com.horizon.bank.user.controller;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.horizon.bank.common.component.ResponseStructure;
import com.horizon.bank.user.dto.CreateUserRequestDto;
import com.horizon.bank.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService service;

    @Test
    void createUser_shouldSucceedEvenWhenCreatedByIsProvidedButInvalid() throws Exception {
        CreateUserRequestDto request = new CreateUserRequestDto();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("j1111111");
        request.setGender("Male");
        request.setPhoneNumber("9876543210");
        request.setCreatedBy("missing-user-id");

        when(service.createUser(request)).thenReturn(new HashMap<String, Object>());

        ResponseStructure response = new ResponseStructure();
        UserController controller = new UserController(service, response);

        ResponseStructure responseBody = controller.registerUser(request);

        assertEquals(200, responseBody.getStatusCode());
        assertEquals("User created successfully", responseBody.getMessage());
    }
}
