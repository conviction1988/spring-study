package com.spring.study.global.model;

import com.spring.study.global.common.model.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmailTest {

    @Test
    @DisplayName("이메일 테스트")
    public void Email_Test() {
        final String emailValue = "leejungyi88@gmail.com";
        final Email email = Email.of(emailValue);
        assertThat(email.getValue()).isEqualTo(emailValue);
        assertThat(email.getId()).isEqualTo("leejungyi88");
        assertThat(email.getHost()).isEqualTo("gmail.com");
    }

    @Test
    @DisplayName("유효하지 않은 이메일 테스트01")
    public void Email_Invalidation_Test_01() {
        final String emailValue = "leejungyi88!gmail.com";
        final Email email = Email.of(emailValue);
        assertThat(email.getValue()).isEqualTo(emailValue);
        assertThat(email.getId()).isNullOrEmpty();
        assertThat(email.getHost()).isNullOrEmpty();
    }

    @Test
    @DisplayName("유효하지 않은 이메일 테스트02")
    public void Email_Invalidation_Test_02() {
        final String emailValue = "leejungyi88@gmail";
        final Email email = Email.of(emailValue);
        assertThat(email.getValue()).isEqualTo(emailValue);
        assertThat(email.getId()).isEqualTo("leejungyi88");
        assertThat(email.getHost()).isEqualTo("gmail");
    }

    @Test
    @DisplayName("유효하지 않은 이메일 테스트03")
    public void Email_Invalidation_Test_03() {
        final String emailValue = "@gmail.com";
        final Email email = Email.of(emailValue);
        assertThat(email.getValue()).isEqualTo(emailValue);
        assertThat(email.getId()).isEqualTo("");
        assertThat(email.getHost()).isEqualTo("gmail.com");
    }
}