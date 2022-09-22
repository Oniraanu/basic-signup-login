package com.oniraanu.basicsignuplogin.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken findByToken(String token){
        return confirmationTokenRepository.getConfirmationToken(token);
    }

}
