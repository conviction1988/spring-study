package com.spring.study.domain.user.dto;

import com.spring.study.domain.user.domain.User;
import com.spring.study.global.common.model.Email;
import com.spring.study.global.common.model.Name;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private Email email;

    private Name name;

    public UserResponse(final User user) {

        if (!ObjectUtils.isEmpty(user)) {
            this.email = user.getEmail();
            this.name = user.getName();
        }
    }
}
