/*
package com.oniraanu.basicsignuplogin.user;

import com.oniraanu.basicsignuplogin.dto.request.RegisterRequest;
import com.oniraanu.basicsignuplogin.dto.response.ApiResponse;
import com.oniraanu.basicsignuplogin.dto.response.RegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Test
    public void userCanRegister(){
        try {
            RegisterRequest registerRequest = new RegisterRequest("Olubunmi", "Bakre", "bakreolubunmi@yahoo.com", "password");
            RegisterResponse response = userService.register(registerRequest);
            assertEquals("bakreolubunmi@yahoo.com",response.getEmailAddress());
        }catch(IllegalStateException e){
            System.out.println(e.getMessage());
        }
    }


}*/
