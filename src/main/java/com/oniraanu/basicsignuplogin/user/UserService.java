package com.oniraanu.basicsignuplogin.user;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.oniraanu.basicsignuplogin.dto.request.RegisterRequest;
import com.oniraanu.basicsignuplogin.dto.response.RegisterResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    RegisterResponse register (RegisterRequest registerRequest) throws UnirestException;
    String confirmToken(String token);
    // LoginResponse login (LoginRequest loginRequest);
}
