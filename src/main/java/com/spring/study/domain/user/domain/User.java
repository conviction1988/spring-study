package com.spring.study.domain.user.domain;

import com.spring.study.global.common.entity.BaseEntity;
import com.spring.study.global.common.entity.EntityEnum;
import com.spring.study.global.common.model.Email;
import com.spring.study.global.common.model.Name;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Embedded
    @AttributeOverride(name = "value",
            column = @Column(name = "EMAIL", nullable = false, unique = true, updatable = false, length = 50))
    private Email email;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "first", column = @Column(name = "FIRST_NAME", nullable = false)),
            @AttributeOverride(name = "last", column = @Column(name = "LAST_NAME", nullable = false))
    })
    private Name name;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="ROLE")
    private EntityEnum.UserRole role;

    @Builder
    public User(Email email, Name name, String password, EntityEnum.UserRole role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

}
