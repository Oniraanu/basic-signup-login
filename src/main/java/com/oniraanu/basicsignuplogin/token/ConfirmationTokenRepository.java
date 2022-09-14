package com.oniraanu.basicsignuplogin.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository <ConfirmationToken, Long> {

    ConfirmationToken getConfirmationToken (String token);

}
