package com.sermaluc.retotecnico.api.config;

import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.HttpStatusServerAccessDeniedHandler;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Collections;

@Configuration
@EnableWebFluxSecurity
public class JwtSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
            ServerHttpSecurity http,
            ReactiveAuthenticationManager authenticationManager) {

        AuthenticationWebFilter authenticationWebFilter = buildAuthenticationWebFilter(authenticationManager);

        return http
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(exception -> exception.accessDeniedHandler(buildAccessDenied()))
                .authorizeExchange(auth -> auth
                        .pathMatchers("/actuator/**","/api-docs/**", "/h2-console**", "/api/v1/user-manager/generate-token").permitAll()
                        .pathMatchers("/api/v1/user-manager/create-user").hasRole("USER")
                        .anyExchange().authenticated())
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }


    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(JwtTokenProvider jwtTokenProvider) {
        return new JwtAuthenticationManager(jwtTokenProvider);
    }

    private AuthenticationWebFilter buildAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setServerAuthenticationConverter(new JwtServerAuthenticationConverter(jwtTokenProvider));
        return authenticationWebFilter;
    }

    private HttpStatusServerAccessDeniedHandler buildAccessDenied() {
        return new HttpStatusServerAccessDeniedHandler(HttpStatus.FORBIDDEN);
    }
}
