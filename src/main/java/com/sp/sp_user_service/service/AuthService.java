package com.sp.sp_user_service.service;

import com.sp.sp_user_service.model.LoginRequest;
import com.sp.sp_user_service.model.SignUpRequest;

public interface AuthService {

    void signUpUser(SignUpRequest signUpRequest);
    void loginUser(LoginRequest loginRequest);
    void logoutUser(String userId);

}
