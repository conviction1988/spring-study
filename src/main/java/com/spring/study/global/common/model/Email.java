package com.spring.study.global.common.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"value"})
public class Email {

    @javax.validation.constraints.Email
    @Column(name = "EMAIL", length = 50)
    @NotEmpty
    private String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    public String getHost() {
        final int index = value.indexOf("@");
        if (index == -1) {
            return null;
        }
        return value.substring(index + 1);
    }

    public String getId() {
        final int index = value.indexOf("@");
        if (index == -1) {
            return null;
        }
        return value.substring(0, index);
    }
}
