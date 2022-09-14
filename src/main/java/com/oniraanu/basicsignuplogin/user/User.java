package com.oniraanu.basicsignuplogin.user;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    public User(String firstName, String lastName,
                String emailAddress,
                String password,
                UserRole role
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.role = role;
    }

    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 3
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Long id;
    @NotEmpty
    @NotBlank
    private String firstName;
    @NotEmpty
    @NotBlank
    private String lastName;
    @NotEmpty
    @NotBlank
    private String emailAddress;
    @NotEmpty
    @NotBlank
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Boolean isVerified = false;
}
