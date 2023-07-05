package com.programming.karaoke.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String emailAddress;
    private String password;
    private String telephoneNumber;
    private String fullName;
}
