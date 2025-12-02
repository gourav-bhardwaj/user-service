package com.sp.sp_user_service.service;

import com.sp.sp_user_service.model.LoginRequest;
import com.sp.sp_user_service.model.SignUpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public void signUpUser(SignUpRequest signUpRequest) {
        logger.info("Processing signup for user with email: {}", signUpRequest.getEmail());
        // TODO: Implement actual user registration logic with User entity and UserRepository
    }

    @Override
    public void loginUser(LoginRequest loginRequest) {
        logger.info("Processing login for user with email: {}", loginRequest.getEmail());
        // TODO: Implement actual login logic with authentication
    }

    @Override
    public void logoutUser(String userId) {
        logger.info("Processing logout for userId: {}", userId);
        // TODO: Implement actual logout logic
    }
}
