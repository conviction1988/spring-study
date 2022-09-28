package com.spring.study.domain.user.dto;

import com.spring.study.domain.user.domain.User;
import com.spring.study.global.common.entity.EntityEnum;
import com.spring.study.global.common.model.Email;
import com.spring.study.global.common.model.Name;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SignUpRequest {

    @NotEmpty(message = "Please enter your Email")
    @javax.validation.constraints.Email
    private String email;

    @NotEmpty(message = "Please enter your Password")
    private String password;

    @NotEmpty(message = "Please enter your first name")
    private String firstName;

    @NotEmpty(message = "Please enter your last name")
    private String lastName;

    SignUpRequest(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User toEntity(EntityEnum.UserRole userRole, PasswordEncoder bCryptPasswordEncoder) {
        return User.builder()
                .name(Name.builder()
                        .first(firstName)
                        .last(lastName)
                        .build())
                .email(Email.of(email))
                .password(bCryptPasswordEncoder.encode(password))
                .role(userRole)
                .build();
    }
}
