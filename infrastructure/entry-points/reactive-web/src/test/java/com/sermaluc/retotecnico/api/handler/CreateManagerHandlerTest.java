package com.sermaluc.retotecnico.api.handler;

import com.sermaluc.retotecnico.api.UserManagerRouteRest;
import com.sermaluc.retotecnico.api.config.JwtSecurityConfig;
import com.sermaluc.retotecnico.api.config.JwtTokenProvider;
import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.response.CreateUserResponseApi;
import com.sermaluc.retotecnico.model.util.enums.Operation;
import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.fallback.FallbackMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ContextConfiguration(classes = { UserManagerRouteRest.class, JwtSecurityConfig.class, CreateManagerHandler.class, AuthenticationHandler.class, JwtTokenProvider.class})
class CreateManagerHandlerTest extends GenericHandleTest {
    private static final Operation OPERATION = Operation.CREATE_USER;
    private static final Operation OPERATION_GT = Operation.GENERATE_TOKEN;
    private CreateUserRequestApi createUserRequestApi;
    private GenerateTokenRequestApi generateTokenRequestApi;

    @BeforeEach
    public void setUp() {

        createUserRequestApi = buildCreateUserRequest();
        generateTokenRequestApi = buildCreateGenerateToken();

        webTestClient = WebTestClient.bindToApplicationContext(context)
                .configureClient().build();

    }

    @Test
    @WithMockUser
    void shouldPostCreateUserWhenClientExists() {

        given(userManagerUseCase.registerClient(any())).willReturn(Mono.error(
                new BusinessException(TechnicalMessage.ERROR_CLIENT_EXIST)));

        webTestClient.post().uri(OPERATION.getPath())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(createUserRequestApi),
                        CreateUserRequestApi.class).exchange()
                .expectStatus().isOk()
                .expectBody(CreateUserResponseApi.class)
                .value(response -> {
                    assertNotNull(response);
                    assertNotNull(response.getMessage());

                    assertEquals(TechnicalMessage.ERROR_CLIENT_EXIST.getMessage(),
                            response.getMessage());
                });

        verify(userManagerUseCase, times(1)).registerClient(any());
    }

    @Test
    @WithMockUser
    void shouldPostCreateUserWhenBadRequest() {

        createUserRequestApi = buildBadRequest();

        given(userManagerUseCase.registerClient(any())).willReturn(Mono.error(
                new BusinessException(TechnicalMessage.ERROR_BAD_REQUEST)));

        webTestClient.post().uri(OPERATION.getPath())
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(createUserRequestApi),
                        CreateUserRequestApi.class).exchange()
                .expectStatus().isOk()
                .expectBody(CreateUserResponseApi.class)
                .value(response -> {
                    assertNotNull(response);
                    assertNotNull(response.getMessage());

                    assertEquals(TechnicalMessage.ERROR_BAD_REQUEST.getMessage(),
                            response.getMessage());
                });

        verify(userManagerUseCase, times(0)).registerClient(any());
    }

    @Test
    @WithMockUser
    void shouldPostCreateUserWhenFallbackCircuitBreaker() throws Throwable {

        CreateManagerHandler target = buildCreateManagerHandler();
        Method testMethod = target.getClass().getMethod("process",
                CreateUserRequestApi.class);

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(OPERATION.getName());

        FallbackMethod fallbackMethod = FallbackMethod.create(
                FALLBACK_METHOD_NAME, testMethod,
                new Object[] { createUserRequestApi }, target);

        Mono<ServerResponse> responseError = (Mono<ServerResponse>) fallbackMethod.fallback(
                CallNotPermittedException.createCallNotPermittedException(
                        circuitBreaker));

        StepVerifier.create(responseError).assertNext(response -> assertThat(response.statusCode()).isEqualTo(
                HttpStatus.OK)).verifyComplete();
    }

    @Test
    @WithMockUser
    void shouldPostGenerateTokenWhenFallbackCircuitBreaker() throws Throwable {

        AuthenticationHandler target = buildAuthenticationHandler();
        Method testMethod = target.getClass().getMethod("process",
                GenerateTokenRequestApi.class);

        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults(OPERATION_GT.getName());

        FallbackMethod fallbackMethod = FallbackMethod.create(
                FALLBACK_METHOD_NAME, testMethod,
                new Object[] { generateTokenRequestApi }, target);

        Mono<ServerResponse> responseError = (Mono<ServerResponse>) fallbackMethod.fallback(
                CallNotPermittedException.createCallNotPermittedException(
                        circuitBreaker));

        StepVerifier.create(responseError).assertNext(response -> assertThat(response.statusCode()).isEqualTo(
                HttpStatus.OK)).verifyComplete();
    }
}
