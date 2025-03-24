package com.sp.sp_user_service.controller;

import com.sp.sp_user_service.model.GenericResponse;
import com.sp.sp_user_service.model.LoginRequest;
import com.sp.sp_user_service.model.SignUpRequest;
import com.sp.sp_user_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }


    @PostMapping("/signup")
    public ResponseEntity<GenericResponse<Void>> signUpUser(@RequestBody SignUpRequest signUpRequest) {
        service.signUpUser(signUpRequest);
        return ResponseEntity.ok(new GenericResponse<>(HttpStatus.OK.value(), "User created successfully") );
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<Void>> loginUser(@RequestBody LoginRequest loginRequest) {
        service.loginUser(loginRequest);
        return ResponseEntity.ok(new GenericResponse<>(HttpStatus.OK.value(), "User login successfully") );
    }

    @PostMapping("/logout")
    public ResponseEntity<GenericResponse<Void>> logoutUser(@RequestParam String userId) {
        service.logoutUser(userId);
        return ResponseEntity.ok(new GenericResponse<>(HttpStatus.OK.value(), "User logout successfully") );
    }


}
