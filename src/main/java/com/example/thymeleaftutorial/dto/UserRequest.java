package com.example.thymeleaftutorial.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {

    private Long id;
    @NotBlank(message = "Enter email")
    private String email;
    private String firstName;
    private String lastName;
    @NotBlank(message = "Enter password")
    private String password;
    @AssertFalse
    private boolean isDeleted;
}