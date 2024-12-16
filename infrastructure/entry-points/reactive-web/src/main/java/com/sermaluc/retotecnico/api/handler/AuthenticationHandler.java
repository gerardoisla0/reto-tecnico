package com.sermaluc.retotecnico.api.handler;

import com.sermaluc.retotecnico.api.config.JwtTokenProvider;
import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.response.GenerateTokenResponseApi;
import com.sermaluc.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.sermaluc.retotecnico.model.util.enums.Operation;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.sermaluc.retotecnico.api.util.UserManagerUtilApi.*;
import static com.sermaluc.retotecnico.api.util.UserManagerUtilApi.buildResponseBusinessExceptionAuth;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private static final String OPERATION_PROCESS_NAME = "generateToken";
    private static final String FALLBACK_METHOD_NAME = "fallback";

    @CircuitBreaker(name = OPERATION_PROCESS_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
    public Mono<ServerResponse> process(
            GenerateTokenRequestApi generateTokenRequestApi) {
        return execute(generateTokenRequestApi, Operation.GENERATE_TOKEN);
    }

    public Mono<ServerResponse> fallback(GenerateTokenRequestApi generateTokenRequestApi, Exception exception) {
        return buildResponseFallbackAuth(generateTokenRequestApi, TechnicalMessage.ERROR_INTERNAL_SERVER, exception);
    }

    public Mono<ServerResponse> fallback(GenerateTokenRequestApi generateTokenRequestApi, CallNotPermittedException callNotPermittedException) {
        return buildResponseFallbackAuth(generateTokenRequestApi, TechnicalMessage.ERROR_SERVICE_UNAVAILABLE, callNotPermittedException);
    }

    public Mono<ServerResponse> execute(
            GenerateTokenRequestApi generateTokenRequestApi, Operation operation) {
        return Mono.just(generateTokenRequestApi)
                .flatMap(generateTokenRQ -> executeOperation(generateTokenRQ,operation));
    }

    private Mono<ServerResponse> executeOperation(GenerateTokenRequestApi generateTokenRequestApi, Operation operation) {
        return RequestValidatorHandlerApi.validateRequestAuth(generateTokenRequestApi)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> buildResponseBadRequestAuth(operation, generateTokenRequestApi, errors))
                .switchIfEmpty(Mono.defer(() -> Mono.just(generateTokenRequestApi)
                        .flatMap(createUserRQ -> generateTokenAndExecuteUseCase(createUserRQ, operation))
                        .onErrorResume(BusinessException.class, businessException -> buildResponseBusinessExceptionAuth(generateTokenRequestApi, businessException))
                        .doOnSubscribe(subscription -> logRequest(operation, generateTokenRequestApi))));
    }

    private Mono<ServerResponse> generateTokenAndExecuteUseCase(GenerateTokenRequestApi generateTokenRequestApi, Operation operation) {
        return jwtTokenProvider.generateToken(generateTokenRequestApi.getUsername())
                .map(this::parsedToResponse)
                .flatMap(clientRS -> buildSuccessResponseAuth(generateTokenRequestApi, clientRS, operation))
                .onErrorResume(BusinessException.class, businessException -> buildResponseBusinessExceptionAuth(generateTokenRequestApi, businessException));
    }

    private GenerateTokenResponseApi parsedToResponse (String token){
        return GenerateTokenResponseApi.builder().token(token)
                .build();
    }
}
