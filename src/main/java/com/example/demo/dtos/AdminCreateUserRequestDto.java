package com.example.demo.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCreateUserRequestDto {
    private String email;
    private String password;
    private String fullName;
    private String role;

}
