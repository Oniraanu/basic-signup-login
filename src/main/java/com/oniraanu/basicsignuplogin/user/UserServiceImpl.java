package com.oniraanu.basicsignuplogin.user;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.oniraanu.basicsignuplogin.dto.request.MessageRequest;
import com.oniraanu.basicsignuplogin.dto.request.RegisterRequest;
import com.oniraanu.basicsignuplogin.dto.response.RegisterResponse;
import com.oniraanu.basicsignuplogin.email.EmailService;
import com.oniraanu.basicsignuplogin.exception.EmailNotFoundException;
import com.oniraanu.basicsignuplogin.token.ConfirmationToken;
import com.oniraanu.basicsignuplogin.token.ConfirmationTokenService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) throws UnirestException {
        validateEmail(registerRequest.getEmailAddress());
        User user = new User();
        mapper.map(registerRequest, user);
        var encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        String tokenGenerated = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(tokenGenerated, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveToken(token);

        String link = "http://localhost:8080/api/v1/register/confirm?token=" + tokenGenerated;
        MessageRequest request = new MessageRequest();
        request.setSender("bakreolubunmi@yahoo.com");
        request.setReceiver(savedUser.getEmailAddress());
        request.setBody("activate your account" + link);
        request.setSubject("Confirm Account");
        emailService.sendSimpleMessage(request);

        RegisterResponse response = new RegisterResponse();
        mapper.map(savedUser, response);
        return response;
    }

    @Transactional
    @Override
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        confirmationTokenService.saveToken(confirmationToken);
        User user = confirmationToken.getUser();
        user.setIsVerified(Boolean.TRUE);
        userRepository.save(user);
        return "confirmed";
    }

    private void validateEmail(String emailAddress) throws EmailNotFoundException {
        User user = userRepository.findByEmailAddress(emailAddress);
        if (user != null) {
            throw new EmailNotFoundException("Email address already taken");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
