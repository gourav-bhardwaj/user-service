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
@DisplayName("AuthServiceImpl Tests")
class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    private SignUpRequest validSignUpRequest;
    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        // Given: Valid test data
        validSignUpRequest = new SignUpRequest();
        validSignUpRequest.setEmail("test@example.com");
        validSignUpRequest.setFirstName("John");
        validSignUpRequest.setLastName("Doe");
        validSignUpRequest.setPassword("password123");
        validSignUpRequest.setDob(LocalDateTime.of(1990, 1, 1, 0, 0));

        validLoginRequest = new LoginRequest();
        validLoginRequest.setEmail("test@example.com");
        validLoginRequest.setPassword("password123");
    }

    @Test
    @DisplayName("should_process_signup_when_valid_signup_request_given")
    void should_process_signup_when_valid_signup_request_given() {
        // Given: Valid signup request is set up in setUp()

        // When: signUpUser is called
        assertDoesNotThrow(() -> authService.signUpUser(validSignUpRequest));

        // Then: No exception should be thrown (method executes successfully)
        // Note: Current implementation only logs, so we verify no exceptions occur
    }

    @Test
    @DisplayName("should_handle_signup_when_null_signup_request_given")
    void should_handle_signup_when_null_signup_request_given() {
        // Given: null signup request
        SignUpRequest nullRequest = null;

        // When & Then: Calling with null should throw NullPointerException
        assertThrows(NullPointerException.class, () -> authService.signUpUser(nullRequest));
    }

    @Test
    @DisplayName("should_process_signup_when_optional_dob_is_null")
    void should_process_signup_when_optional_dob_is_null() {
        // Given: Valid signup request with null DOB
        validSignUpRequest.setDob(null);

        // When: signUpUser is called
        assertDoesNotThrow(() -> authService.signUpUser(validSignUpRequest));

        // Then: No exception should be thrown (DOB is optional)
    }

    @Test
    @DisplayName("should_process_login_when_valid_login_request_given")
    void should_process_login_when_valid_login_request_given() {
        // Given: Valid login request is set up in setUp()

        // When: loginUser is called
        assertDoesNotThrow(() -> authService.loginUser(validLoginRequest));

        // Then: No exception should be thrown (method executes successfully)
    }

    @Test
    @DisplayName("should_handle_login_when_null_login_request_given")
    void should_handle_login_when_null_login_request_given() {
        // Given: null login request
        LoginRequest nullRequest = null;

        // When & Then: Calling with null should throw NullPointerException
        assertThrows(NullPointerException.class, () -> authService.loginUser(nullRequest));
    }

    @Test
    @DisplayName("should_process_logout_when_valid_userId_given")
    void should_process_logout_when_valid_userId_given() {
        // Given: Valid userId
        String validUserId = "user123";

        // When: logoutUser is called
        assertDoesNotThrow(() -> authService.logoutUser(validUserId));

        // Then: No exception should be thrown (method executes successfully)
    }

    @Test
    @DisplayName("should_handle_logout_when_null_userId_given")
    void should_handle_logout_when_null_userId_given() {
        // Given: null userId
        String nullUserId = null;

        // When: logoutUser is called
        assertDoesNotThrow(() -> authService.logoutUser(nullUserId));

        // Then: No exception should be thrown
        // Note: Current implementation doesn't validate null userId, only logs it
    }

    @Test
    @DisplayName("should_handle_logout_when_empty_userId_given")
    void should_handle_logout_when_empty_userId_given() {
        // Given: empty userId
        String emptyUserId = "";

        // When: logoutUser is called
        assertDoesNotThrow(() -> authService.logoutUser(emptyUserId));

        // Then: No exception should be thrown
        // Note: Current implementation doesn't validate empty userId, only logs it
    }
}
