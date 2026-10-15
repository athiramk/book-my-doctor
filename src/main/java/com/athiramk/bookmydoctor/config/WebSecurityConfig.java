package com.athiramk.bookmydoctor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    private static final String[] PUBLIC_RESOURCES = {
        "/css/**",
        "/js/**",
        "/images/**",
        "/webjars/**",
        "/favicon.ico",
        "/actuator/health/**",
        "/actuator/info/**",
        "/error"
    };

    private static final String[] PUBLIC_PAGES = {"/", "/book/**", "/queue/**", "/cancel/**", "/login", "/registration"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.securityMatcher("/**");

        http.authorizeHttpRequests(r -> r.requestMatchers(PUBLIC_RESOURCES)
                .permitAll()
                .requestMatchers(PUBLIC_PAGES)
                .permitAll()
                .requestMatchers("/doctor/**")
                .authenticated()
                .anyRequest()
                .authenticated());

        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/doctor/queue", false)
                .permitAll());

        http.logout(logout -> logout.logoutRequestMatcher(PathPatternRequestMatcher.pathPattern("/logout"))
                .logoutSuccessUrl("/")
                .permitAll());

        return http.build();
    }
}
