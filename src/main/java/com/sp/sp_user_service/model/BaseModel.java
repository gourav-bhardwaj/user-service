package com.sp.sp_user_service.model;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseModel {


    @CreatedBy
    private String createdUser;

    @LastModifiedBy
    private String lastUpdatedUser;

    @CreatedDate
    private LocalDateTime createdBy;

    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

}
