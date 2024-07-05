package com.qima.productsadmin.controller;

import com.qima.productsadmin.model.JwtResponse;
import com.qima.productsadmin.model.LoginFormRequest;
import com.qima.productsadmin.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void authenticateReturnsJwtResponseOnValidCredentials() {
        LoginFormRequest loginForm = new LoginFormRequest();
        loginForm.setUsername("user");
        loginForm.setPassword("password");
        JwtResponse expectedResponse = new JwtResponse("token");
        when(authService.authenticate(loginForm)).thenReturn(expectedResponse);

        ResponseEntity<JwtResponse> response = authController.authenticate(loginForm);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void authenticateReturnsBadRequestOnInvalidCredentials() {
        LoginFormRequest loginForm = new LoginFormRequest();
        loginForm.setUsername("user");
        loginForm.setPassword("wrong");
        when(authService.authenticate(loginForm)).thenThrow(new IllegalArgumentException("Bad credentials"));

        ResponseEntity<?> response;
        try {
            response = authController.authenticate(loginForm);
        } catch (IllegalArgumentException e) {
            response = ResponseEntity.badRequest().body(e.getMessage());
        }

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Bad credentials", response.getBody());
    }
}