package com.spring.study.global.model;

import com.spring.study.global.common.model.Name;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NameTest {

    @Test
    @DisplayName("getFullName() 테스트")
    public void getFullName_isEqualToFullName() {
        final Name name = Name.builder()
                .first("lee")
                .last("jungyi")
                .build();
        final String fullName = name.getFullName();
        assertThat(fullName).isEqualTo("lee jungyi");
    }
}