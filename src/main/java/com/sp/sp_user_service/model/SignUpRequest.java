package com.sp.sp_user_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest extends BaseModel {

    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime dob;
    private String password;
}
