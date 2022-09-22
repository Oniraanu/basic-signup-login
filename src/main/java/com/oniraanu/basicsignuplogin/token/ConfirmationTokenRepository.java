package com.oniraanu.basicsignuplogin.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConfirmationTokenRepository extends JpaRepository <ConfirmationToken, Long> {
    ConfirmationToken getConfirmationToken (String token);
}
