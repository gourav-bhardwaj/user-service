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
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("should_return_201_created_when_valid_signup_request_given")
    void should_return_201_created_when_valid_signup_request_given() throws Exception {
        // Given: Service is configured to process signup successfully
        willDoNothing().given(authService).signUpUser(any(SignUpRequest.class));

        // When: POST request is made to /user/signup with valid data
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                // Then: Response status should be 201 CREATED
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(201))
                .andExpect(jsonPath("$.message").value("User created successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // And: Service method should be called once
        then(authService).should(times(1)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_email_is_missing_in_signup")
    void should_return_400_bad_request_when_email_is_missing_in_signup() throws Exception {
        // Given: Signup request with missing email
        validSignUpRequest.setEmail(null);

        // When: POST request is made to /user/signup with invalid data
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_email_is_invalid_in_signup")
    void should_return_400_bad_request_when_email_is_invalid_in_signup() throws Exception {
        // Given: Signup request with invalid email format
        validSignUpRequest.setEmail("invalid-email");

        // When: POST request is made to /user/signup with invalid data
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_password_is_too_short_in_signup")
    void should_return_400_bad_request_when_password_is_too_short_in_signup() throws Exception {
        // Given: Signup request with password less than 8 characters
        validSignUpRequest.setPassword("short");

        // When: POST request is made to /user/signup with invalid data
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_firstName_is_missing_in_signup")
    void should_return_400_bad_request_when_firstName_is_missing_in_signup() throws Exception {
        // Given: Signup request with missing firstName
        validSignUpRequest.setFirstName(null);

        // When: POST request is made to /user/signup with invalid data
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_lastName_is_missing_in_signup")
    void should_return_400_bad_request_when_lastName_is_missing_in_signup() throws Exception {
        // Given: Signup request with missing lastName
        validSignUpRequest.setLastName(null);

        // When: POST request is made to /user/signup with invalid data
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).signUpUser(any(SignUpRequest.class));
    }

    @Test
    @DisplayName("should_return_200_ok_when_valid_login_request_given")
    void should_return_200_ok_when_valid_login_request_given() throws Exception {
        // Given: Service is configured to process login successfully
        willDoNothing().given(authService).loginUser(any(LoginRequest.class));

        // When: POST request is made to /user/login with valid data
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                // Then: Response status should be 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User login successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // And: Service method should be called once
        then(authService).should(times(1)).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_email_is_missing_in_login")
    void should_return_400_bad_request_when_email_is_missing_in_login() throws Exception {
        // Given: Login request with missing email
        validLoginRequest.setEmail(null);

        // When: POST request is made to /user/login with invalid data
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_email_is_invalid_in_login")
    void should_return_400_bad_request_when_email_is_invalid_in_login() throws Exception {
        // Given: Login request with invalid email format
        validLoginRequest.setEmail("invalid-email");

        // When: POST request is made to /user/login with invalid data
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_400_bad_request_when_password_is_missing_in_login")
    void should_return_400_bad_request_when_password_is_missing_in_login() throws Exception {
        // Given: Login request with missing password
        validLoginRequest.setPassword(null);

        // When: POST request is made to /user/login with invalid data
        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                // Then: Response status should be 400 BAD REQUEST
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        // And: Service method should not be called
        then(authService).should(times(0)).loginUser(any(LoginRequest.class));
    }

    @Test
    @DisplayName("should_return_200_ok_when_valid_userId_given_for_logout")
    void should_return_200_ok_when_valid_userId_given_for_logout() throws Exception {
        // Given: Service is configured to process logout successfully
        String validUserId = "user123";
        willDoNothing().given(authService).logoutUser(validUserId);

        // When: POST request is made to /user/logout with valid userId
        mockMvc.perform(post("/user/logout")
                        .param("userId", validUserId))
                // Then: Response status should be 200 OK
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("User logout successfully"))
                .andExpect(jsonPath("$.timestamp").exists());

        // And: Service method should be called once
        then(authService).should(times(1)).logoutUser(validUserId);
    }

    @Test
    @DisplayName("should_return_500_when_userId_is_missing_in_logout")
    void should_return_500_when_userId_is_missing_in_logout() throws Exception {
        // Given: No userId parameter provided

        // When: POST request is made to /user/logout without userId
        mockMvc.perform(post("/user/logout"))
                // Then: Response status should be 500 INTERNAL SERVER ERROR (missing required parameter)
                .andExpect(status().isInternalServerError());

        // And: Service method should not be called
        then(authService).should(times(0)).logoutUser(any(String.class));
    }

    @Test
    @DisplayName("should_return_500_internal_server_error_when_service_throws_exception")
    void should_return_500_internal_server_error_when_service_throws_exception() throws Exception {
        // Given: Service throws RuntimeException
        willThrow(new RuntimeException("Database connection failed"))
                .given(authService).signUpUser(any(SignUpRequest.class));

        // When: POST request is made to /user/signup
        mockMvc.perform(post("/user/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validSignUpRequest)))
                // Then: Response status should be 500 INTERNAL SERVER ERROR
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));

        // And: Service method should be called once
        then(authService).should(times(1)).signUpUser(any(SignUpRequest.class));
    }
}
