package com.spring.study.global.common.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"first", "last"})
public class Name {

    @NotEmpty
    @Column(name = "FIRST_NAME", length = 50)
    private String first;

    @NotEmpty
    @Column(name = "LAST_NAME", length = 50)
    private String last;

    @Builder
    public Name(final String first, final String last) {
        this.first = first;
        this.last = last;
    }

    public String getFullName() {
        return String.format("%s %s", this.first, this.last);
    }
}
