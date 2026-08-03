package com.horizon.bank.user.controller;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @Mock
    private ResponseStructure response;

    @InjectMocks
    private UserController controller;

    @Test
    void createUser_shouldSucceedEvenWhenCreatedByIsProvidedButInvalid() throws Exception {
        CreateUserRequestDto request = new CreateUserRequestDto();
        request.setName("John Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("j1111111");
        request.setGender("Male");
        request.setPhoneNumber("9876543210");
        request.setCreatedBy("missing-user-id");

        HashMap<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("statusCode", 200);
        expectedResponse.put("message", "User created successfull1y");
        when(service.createUser(request)).thenReturn(new HashMap<String, Object>());
        when(response.send()).thenReturn(expectedResponse);

        Object responseBody = controller.registerUser(request);

        HashMap<String, Object> body = (HashMap<String, Object>) responseBody;
        assertEquals(200, body.get("statusCode"));
        assertEquals("User created successfull1y", body.get("message"));
    }
}
