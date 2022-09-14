package com.oniraanu.basicsignuplogin.token;

import com.oniraanu.basicsignuplogin.user.User;
import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class ConfirmationToken {

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, User user) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.user = user;
    }

    @Id
    @SequenceGenerator(
            name = "confirmation_sequence",
            sequenceName = "confirmation_sequence",
            allocationSize = 3
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
