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
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

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
    @DisplayName("should_return_201_when_valid_signup_request_provided")
    void should_return_201_when_valid_signup_request_provided() throws Exception {
        // Given: Valid signup request
        doNothing().when(authService).signUpUser(any(SignUpRequest.class));

        // When: POST request to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                // Then: Response status is 201 CREATED
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // Verify service method was called once
        verify(authService, times(1)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_signup_email_is_blank")
    void should_return_400_when_signup_email_is_blank() throws Exception {
        // Given: Signup request with blank email
        signUpRequest.setEmail("");

        // When: POST request to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service method was never called
        verify(authService, never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_signup_email_is_invalid")
    void should_return_400_when_signup_email_is_invalid() throws Exception {
        // Given: Signup request with invalid email format
        signUpRequest.setEmail("invalid-email");

        // When: POST request to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // Verify service method was never called
        verify(authService, never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_signup_firstName_is_blank")
    void should_return_400_when_signup_firstName_is_blank() throws Exception {
        // Given: Signup request with blank firstName
        signUpRequest.setFirstName("");

        // When: POST request to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        // Verify service method was never called
        verify(authService, never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_signup_lastName_is_blank")
    void should_return_400_when_signup_lastName_is_blank() throws Exception {
        // Given: Signup request with blank lastName
        signUpRequest.setLastName("");

        // When: POST request to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        // Verify service method was never called
        verify(authService, never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_signup_password_is_too_short")
    void should_return_400_when_signup_password_is_too_short() throws Exception {
        // Given: Signup request with password less than 8 characters
        signUpRequest.setPassword("short");

        // When: POST request to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        // Verify service method was never called
        verify(authService, never()).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_200_when_valid_login_request_provided")
    void should_return_200_when_valid_login_request_provided() throws Exception {
        // Given: Valid login request
        doNothing().when(authService).loginUser(any(LoginRequest.class));

        // When: POST request to /user/login
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                // Then: Response status is 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User login successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // Verify service method was called once
        verify(authService, times(1)).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_login_email_is_blank")
    void should_return_400_when_login_email_is_blank() throws Exception {
        // Given: Login request with blank email
        loginRequest.setEmail("");

        // When: POST request to /user/login
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        // Verify service method was never called
        verify(authService, never()).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_login_email_is_invalid")
    void should_return_400_when_login_email_is_invalid() throws Exception {
        // Given: Login request with invalid email format
        loginRequest.setEmail("invalid-email");

        // When: POST request to /user/login
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        // Verify service method was never called
        verify(authService, never()).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_400_when_login_password_is_blank")
    void should_return_400_when_login_password_is_blank() throws Exception {
        // Given: Login request with blank password
        loginRequest.setPassword("");

        // When: POST request to /user/login
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                // Then: Response status is 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));

        // Verify service method was never called
        verify(authService, never()).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_200_when_valid_userId_provided_for_logout")
    void should_return_200_when_valid_userId_provided_for_logout() throws Exception {
        // Given: Valid userId
        String userId = "user123";
        doNothing().when(authService).logoutUser(userId);

        // When: POST request to /user/logout with userId parameter
        mockMvc.perform(post("/user/logout")
                        .param("userId", userId))
                .andDo(print())
                // Then: Response status is 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User logout successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // Verify service method was called once with the userId
        verify(authService, times(1)).logoutUser(userId);
    }

    @Test
    @DisplayName("should_return_500_when_userId_is_missing_for_logout")
    void should_return_500_when_userId_is_missing_for_logout() throws Exception {
        // Given: No userId parameter

        // When: POST request to /user/logout without userId parameter
        mockMvc.perform(post("/user/logout"))
                .andDo(print())
                // Then: Response status is 500 INTERNAL SERVER ERROR (handled by global exception handler)
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));

        // Verify service method was never called
        verify(authService, never()).logoutUser(any());
    }

    @Test
    @DisplayName("should_handle_service_exception_and_return_500")
    void should_handle_service_exception_and_return_500() throws Exception {
        // Given: Service throws an exception
        doThrow(new RuntimeException("Service error")).when(authService).signUpUser(any(SignUpRequest.class));

        // When: POST request to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andDo(print())
                // Then: Response status is 500 INTERNAL SERVER ERROR
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));

        // Verify service method was called
        verify(authService, times(1)).signUpUser(any(SignUpRequest.class));
    }
}
