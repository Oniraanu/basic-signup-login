package com.oniraanu.basicsignuplogin.user;

import com.oniraanu.basicsignuplogin.dto.request.RegisterRequest;
import com.oniraanu.basicsignuplogin.dto.response.RegisterResponse;
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
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        validateEmail(registerRequest.getEmailAddress());
        User user = new User();
        mapper.map(registerRequest, user);
        var encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());
        user.setPassword(encodedPassword);
        User savedUser = userRepository.save(user);

        String tokenGenerated = UUID.randomUUID().toString();
        ConfirmationToken token = new ConfirmationToken(tokenGenerated, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), user);
        confirmationTokenService.saveToken(token);

        RegisterResponse response = new RegisterResponse();
        mapper.map(savedUser, response);
        return response;
    }

    private String confirmToken(String token) {
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
