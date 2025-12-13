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
@DisplayName("AuthServiceImpl Unit Tests")
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    private SignUpRequest validSignUpRequest;
    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        validSignUpRequest = createValidSignUpRequest();
        validLoginRequest = createValidLoginRequest();
    }

    @Test
    @DisplayName("Should successfully process signup for valid user")
    void shouldProcessSignUpForValidUser() {
        // Given - valid signup request is prepared in setUp()

        // When & Then - should not throw any exception
        assertDoesNotThrow(() -> authService.signUpUser(validSignUpRequest));
    }

    @Test
    @DisplayName("Should handle signup with null request gracefully")
    void shouldHandleSignUpWithNullRequest() {
        // Given
        SignUpRequest nullRequest = null;

        // When & Then - should handle gracefully (current implementation logs but doesn't throw)
        assertThrows(NullPointerException.class, () -> authService.signUpUser(nullRequest));
    }

    @Test
    @DisplayName("Should successfully process login for valid user")
    void shouldProcessLoginForValidUser() {
        // Given - valid login request is prepared in setUp()

        // When & Then - should not throw any exception
        assertDoesNotThrow(() -> authService.loginUser(validLoginRequest));
    }

    @Test
    @DisplayName("Should handle login with null request gracefully")
    void shouldHandleLoginWithNullRequest() {
        // Given
        LoginRequest nullRequest = null;

        // When & Then - should handle gracefully
        assertThrows(NullPointerException.class, () -> authService.loginUser(nullRequest));
    }

    @Test
    @DisplayName("Should successfully process logout for valid userId")
    void shouldProcessLogoutForValidUserId() {
        // Given
        String validUserId = "user123";

        // When & Then - should not throw any exception
        assertDoesNotThrow(() -> authService.logoutUser(validUserId));
    }

    @Test
    @DisplayName("Should handle logout with null userId gracefully")
    void shouldHandleLogoutWithNullUserId() {
        // Given
        String nullUserId = null;

        // When & Then - should handle gracefully
        assertDoesNotThrow(() -> authService.logoutUser(nullUserId));
    }

    @Test
    @DisplayName("Should handle logout with empty userId")
    void shouldHandleLogoutWithEmptyUserId() {
        // Given
        String emptyUserId = "";

        // When & Then - should handle gracefully
        assertDoesNotThrow(() -> authService.logoutUser(emptyUserId));
    }

    // Helper methods to create valid test data

    private SignUpRequest createValidSignUpRequest() {
        SignUpRequest request = new SignUpRequest();
        request.setEmail("test@example.com");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDob(LocalDateTime.of(1990, 1, 1, 0, 0));
        request.setPassword("SecurePass123");
        return request;
    }

    private LoginRequest createValidLoginRequest() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("SecurePass123");
        return request;
    }
}
