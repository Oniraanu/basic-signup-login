package com.oniraanu.basicsignuplogin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

}
