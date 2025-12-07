package com.sp.sp_user_service.service;

import com.sp.sp_user_service.model.LoginRequest;
import com.sp.sp_user_service.model.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    private SignUpRequest signUpRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        // Given: Setup test data
        signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");
        signUpRequest.setPassword("password123");
        signUpRequest.setDob(LocalDateTime.of(1990, 1, 1, 0, 0));

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");
    }

    @Test
    @DisplayName("should_process_signup_when_valid_request_provided")
    void should_process_signup_when_valid_request_provided() {
        // Given: Valid signup request is prepared in setUp()

        // When: signUpUser is called
        assertDoesNotThrow(() -> authService.signUpUser(signUpRequest));

        // Then: Method executes without throwing exception
        // Note: This is a basic test as the service currently only logs the operation
    }

    @Test
    @DisplayName("should_handle_signup_when_email_is_provided")
    void should_handle_signup_when_email_is_provided() {
        // Given: Valid signup request with email
        assertNotNull(signUpRequest.getEmail());

        // When: signUpUser is called
        assertDoesNotThrow(() -> authService.signUpUser(signUpRequest));

        // Then: No exception is thrown
    }

    @Test
    @DisplayName("should_process_login_when_valid_credentials_provided")
    void should_process_login_when_valid_credentials_provided() {
        // Given: Valid login request is prepared in setUp()

        // When: loginUser is called
        assertDoesNotThrow(() -> authService.loginUser(loginRequest));

        // Then: Method executes without throwing exception
        // Note: This is a basic test as the service currently only logs the operation
    }

    @Test
    @DisplayName("should_handle_login_when_email_is_provided")
    void should_handle_login_when_email_is_provided() {
        // Given: Valid login request with email
        assertNotNull(loginRequest.getEmail());

        // When: loginUser is called
        assertDoesNotThrow(() -> authService.loginUser(loginRequest));

        // Then: No exception is thrown
    }

    @Test
    @DisplayName("should_process_logout_when_valid_userId_provided")
    void should_process_logout_when_valid_userId_provided() {
        // Given: Valid userId
        String userId = "user123";

        // When: logoutUser is called
        assertDoesNotThrow(() -> authService.logoutUser(userId));

        // Then: Method executes without throwing exception
        // Note: This is a basic test as the service currently only logs the operation
    }

    @Test
    @DisplayName("should_handle_logout_when_null_userId_provided")
    void should_handle_logout_when_null_userId_provided() {
        // Given: Null userId
        String userId = null;

        // When: logoutUser is called with null
        assertDoesNotThrow(() -> authService.logoutUser(userId));

        // Then: Method executes without throwing exception
        // Note: The current implementation doesn't validate userId
    }

    @Test
    @DisplayName("should_handle_logout_when_empty_userId_provided")
    void should_handle_logout_when_empty_userId_provided() {
        // Given: Empty userId
        String userId = "";

        // When: logoutUser is called with empty string
        assertDoesNotThrow(() -> authService.logoutUser(userId));

        // Then: Method executes without throwing exception
    }
}
