package com.oniraanu.basicsignuplogin.user;

import com.oniraanu.basicsignuplogin.exception.EmailNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        return userRepository.findByEmailAddress(emailAddress).orElseThrow(()-> new EmailNotFoundException("Email address not found"));
    }
}
