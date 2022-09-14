package com.oniraanu.basicsignuplogin.token;

import com.oniraanu.basicsignuplogin.exception.TokenException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken findByToken(String token){
        return confirmationTokenRepository.getConfirmationToken(token);
    }

}
