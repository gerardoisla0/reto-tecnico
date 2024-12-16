package com.sermaluc.retotecnico.api.handler;

import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.mapper.ClientRequestMapperApi;
import com.sermaluc.retotecnico.api.util.constant.validator.RequestValidatorHandlerApi;
import com.sermaluc.retotecnico.model.util.enums.Operation;
import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import com.sermaluc.retotecnico.usecase.usermanager.UserManagerUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import static com.sermaluc.retotecnico.api.util.UserManagerUtilApi.*;
import static com.sermaluc.retotecnico.api.util.UserManagerUtilApi.buildResponseBusinessException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateManagerHandler {

    private final UserManagerUseCase userManagerUseCase;

    private static final String OPERATION_PROCESS_NAME = "createUser";
    private static final String FALLBACK_METHOD_NAME = "fallback";

    @CircuitBreaker(name = OPERATION_PROCESS_NAME, fallbackMethod = FALLBACK_METHOD_NAME)
    public Mono<ServerResponse> process(CreateUserRequestApi createUserRequestApi) {
        return execute(createUserRequestApi, Operation.CREATE_USER);
    }

    public Mono<ServerResponse> fallback(CreateUserRequestApi createUserRequestApi, Exception exception) {
        return buildResponseFallback(createUserRequestApi, TechnicalMessage.ERROR_INTERNAL_SERVER, exception);
    }

    public Mono<ServerResponse> fallback(CreateUserRequestApi createUserRequestApi, CallNotPermittedException callNotPermittedException) {
        return buildResponseFallback(createUserRequestApi, TechnicalMessage.ERROR_SERVICE_UNAVAILABLE, callNotPermittedException);
    }

    public Mono<ServerResponse> execute(
            CreateUserRequestApi createUserRequestApi, Operation operation) {
        return Mono.just(createUserRequestApi)
                .flatMap(createUserRQ -> executeOperation(createUserRQ,operation));
    }

    private Mono<ServerResponse> executeOperation(CreateUserRequestApi createUserRequestApi, Operation operation) {
        return RequestValidatorHandlerApi.validateRequest(createUserRequestApi)
                .filter(errors -> !errors.isEmpty())
                .flatMap(errors -> buildResponseBadRequest(operation, createUserRequestApi, errors))
                .switchIfEmpty(Mono.defer(() -> Mono.just(createUserRequestApi)
                        .flatMap(createUserRQ -> createUserAndExecuteUseCase(createUserRQ, operation))
                        .onErrorResume(BusinessException.class, businessException -> buildResponseBusinessException(createUserRequestApi, businessException))
                        .doOnSubscribe(subscription -> logRequest(operation, createUserRequestApi))));
    }

    protected Mono<Object> createUser(
            CreateUserRequestApi createUserRequestApi) {
        return Mono.just(createUserRequestApi)
                .map(ClientRequestMapperApi.MAPPER::createUserRequestToClient)
                .flatMap(userManagerUseCase::registerClient);
    }
    private Mono<ServerResponse> createUserAndExecuteUseCase(CreateUserRequestApi createUserRequestApi, Operation operation) {
        return createUser(createUserRequestApi)
                .flatMap(clientRS -> buildSuccessResponse(clientRS, operation))
                .onErrorResume(BusinessException.class, businessException -> buildResponseBusinessException(createUserRequestApi, businessException));
    }
}
