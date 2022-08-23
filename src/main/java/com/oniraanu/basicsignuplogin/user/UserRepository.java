package com.oniraanu.basicsignuplogin.user;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository{

    Optional <User> findByEmailAddress (String emailAddress);
}
