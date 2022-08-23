package com.oniraanu.basicsignuplogin.register;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/register")
@AllArgsConstructor
public class RegisterController {

    private RegisterService registerService;

    public ResponseEntity <?> register(@RequestBody RegisterRequest registerRequest){
        return registerService.register(registerRequest);

    }
}
