package com.oniraanu.basicsignuplogin.register;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;

}
