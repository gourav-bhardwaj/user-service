package com.sp.sp_user_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.sp_user_service.model.LoginRequest;
import com.sp.sp_user_service.model.SignUpRequest;
import com.sp.sp_user_service.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@DisplayName("AuthController Unit Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    private SignUpRequest validSignUpRequest;
    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        validSignUpRequest = createValidSignUpRequest();
        validLoginRequest = createValidLoginRequest();
    }

    @Test
    @DisplayName("Should successfully sign up user with valid request")
    void shouldSignUpUserWithValidRequest() throws Exception {
        // Given
        doNothing().when(authService).signUpUser(any(SignUpRequest.class));

        // When & Then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // Verify service was called
        then(authService).should(times(1)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when signup request has invalid email")
    void shouldReturnBadRequestWhenSignUpHasInvalidEmail() throws Exception {
        // Given
        SignUpRequest invalidRequest = createValidSignUpRequest();
        invalidRequest.setEmail("invalid-email");

        // When & Then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when signup request has missing email")
    void shouldReturnBadRequestWhenSignUpHasMissingEmail() throws Exception {
        // Given
        SignUpRequest invalidRequest = createValidSignUpRequest();
        invalidRequest.setEmail(null);

        // When & Then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when signup request has missing firstName")
    void shouldReturnBadRequestWhenSignUpHasMissingFirstName() throws Exception {
        // Given
        SignUpRequest invalidRequest = createValidSignUpRequest();
        invalidRequest.setFirstName(null);

        // When & Then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when signup request has missing lastName")
    void shouldReturnBadRequestWhenSignUpHasMissingLastName() throws Exception {
        // Given
        SignUpRequest invalidRequest = createValidSignUpRequest();
        invalidRequest.setLastName(null);

        // When & Then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when signup request has short password")
    void shouldReturnBadRequestWhenSignUpHasShortPassword() throws Exception {
        // Given
        SignUpRequest invalidRequest = createValidSignUpRequest();
        invalidRequest.setPassword("short");

        // When & Then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("Should successfully login user with valid credentials")
    void shouldLoginUserWithValidCredentials() throws Exception {
        // Given
        doNothing().when(authService).loginUser(any(LoginRequest.class));

        // When & Then
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User login successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // Verify service was called
        then(authService).should(times(1)).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when login request has invalid email")
    void shouldReturnBadRequestWhenLoginHasInvalidEmail() throws Exception {
        // Given
        LoginRequest invalidRequest = new LoginRequest();
        invalidRequest.setEmail("invalid-email");
        invalidRequest.setPassword("SecurePass123");

        // When & Then
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when login request has missing email")
    void shouldReturnBadRequestWhenLoginHasMissingEmail() throws Exception {
        // Given
        LoginRequest invalidRequest = new LoginRequest();
        invalidRequest.setEmail(null);
        invalidRequest.setPassword("SecurePass123");

        // When & Then
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("Should return 400 when login request has missing password")
    void shouldReturnBadRequestWhenLoginHasMissingPassword() throws Exception {
        // Given
        LoginRequest invalidRequest = new LoginRequest();
        invalidRequest.setEmail("test@example.com");
        invalidRequest.setPassword(null);

        // When & Then
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service was NOT called
        then(authService).should(never()).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("Should successfully logout user with valid userId")
    void shouldLogoutUserWithValidUserId() throws Exception {
        // Given
        String userId = "user123";
        doNothing().when(authService).logoutUser(userId);

        // When & Then
        mockMvc.perform(post("/user/logout")
                        .param("userId", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User logout successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // Verify service was called
        then(authService).should(times(1)).logoutUser(userId);
    }

    @Test
    @DisplayName("Should handle logout with empty userId")
    void shouldHandleLogoutWithEmptyUserId() throws Exception {
        // Given
        String emptyUserId = "";
        doNothing().when(authService).logoutUser(emptyUserId);

        // When & Then
        mockMvc.perform(post("/user/logout")
                        .param("userId", emptyUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User logout successfully"));

        // Verify service was called
        then(authService).should(times(1)).logoutUser(emptyUserId);
    }

    @Test
    @DisplayName("Should handle service exception during signup")
    void shouldHandleServiceExceptionDuringSignUp() throws Exception {
        // Given
        doThrow(new RuntimeException("Database error")).when(authService).signUpUser(any(SignUpRequest.class));

        // When & Then
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));

        // Verify service was called
        then(authService).should(times(1)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("Should handle service exception during login")
    void shouldHandleServiceExceptionDuringLogin() throws Exception {
        // Given
        doThrow(new RuntimeException("Authentication error")).when(authService).loginUser(any(LoginRequest.class));

        // When & Then
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));

        // Verify service was called
        then(authService).should(times(1)).loginUser(any(LoginRequest.class));
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
