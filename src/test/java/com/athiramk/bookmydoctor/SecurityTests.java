package com.athiramk.bookmydoctor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class SecurityTests extends BaseIT {

    @Test
    void shouldPermitPublicAccessToHome() {
        var result = mvc.get().uri("/");
        assertThat(result).hasStatus(HttpStatus.OK);
    }

    @Test
    void shouldPermitPublicAccessToLogin() {
        var result = mvc.get().uri("/login");
        assertThat(result).hasStatus(HttpStatus.OK);
    }

    @Test
    void shouldRedirectAnonymousUserFromDoctorRoutes() {
        var result = mvc.get().uri("/doctor/queue");
        assertThat(result).hasStatus(HttpStatus.FOUND);
    }
}
