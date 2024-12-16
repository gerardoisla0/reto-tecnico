package com.sermaluc.retotecnico.api.util;

import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.response.CreateUserResponseApi;
import com.sermaluc.retotecnico.api.dto.response.GenerateTokenResponseApi;
import com.sermaluc.retotecnico.api.dto.response.structure.body.error.ErrorDetail;
import com.sermaluc.retotecnico.api.mapper.ClientResponseMapperApi;
import com.sermaluc.retotecnico.api.mapper.GenerateTokenMapperApi;
import com.sermaluc.retotecnico.model.usermanager.Client;
import com.sermaluc.retotecnico.model.util.enums.Operation;
import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;

import java.util.List;

import static com.sermaluc.retotecnico.api.util.HandlerUtilApi.buildResponseService;
import static com.sermaluc.retotecnico.api.util.HandlerUtilApi.buildResponseServiceAuth;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@UtilityClass
public class UserManagerUtilApi {

    public static Mono<ServerResponse> buildResponseBadRequest(
            Operation operation, CreateUserRequestApi createUserRequestApi, List<ErrorDetail> errors) {
        return buildCreateUserResponse(createUserRequestApi,  operation.getName(), TechnicalMessage.ERROR_BAD_REQUEST, errors, null, null);
    }

    public static Mono<ServerResponse> buildResponseBadRequestAuth(
            Operation operation, GenerateTokenRequestApi generateTokenRequestApi, List<ErrorDetail> errors) {
        return buildCreateUserResponseAuth(generateTokenRequestApi,  operation.getName(), TechnicalMessage.ERROR_BAD_REQUEST, errors, null, null);
    }

    public static Mono<ServerResponse> buildCreateUserResponse(CreateUserRequestApi createUserRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            CreateUserResponseApi createUserResponseApi = ClientResponseMapperApi.MAPPER.requestToResponse(createUserRequestApi, technicalMessage);
            return buildResponseService(createUserResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildCreateUserResponseAuth(CreateUserRequestApi createUserRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            CreateUserResponseApi createUserResponseApi = ClientResponseMapperApi.MAPPER.requestToResponse(createUserRequestApi, technicalMessage);
            return buildResponseService(createUserResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildCreateUserResponseAuth(GenerateTokenRequestApi generateTokenRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            GenerateTokenResponseApi generateTokenResponseApi = GenerateTokenMapperApi.MAPPER.requestToResponse(generateTokenRequestApi, technicalMessage);
            return buildResponseServiceAuth(generateTokenResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildResponseFallback(CreateUserRequestApi createUserRequestApi, TechnicalMessage technicalMessage, Exception exception) {
        return buildCreateUserResponse(createUserRequestApi,  null, technicalMessage, null, null, exception);
    }

    public static Mono<ServerResponse> buildResponseFallbackAuth(
            GenerateTokenRequestApi generateTokenRequestApi, TechnicalMessage technicalMessage, Exception exception) {
        return buildCreateUserResponseAuth(generateTokenRequestApi,  null, technicalMessage, null, null, exception);
    }
    public static Mono<ServerResponse> buildSuccessResponse(Object getUserLocksRS, Operation operation) {
        return Mono.defer(() -> {

            Client cliente = (Client) getUserLocksRS;
            CreateUserResponseApi createUserResponseApi = ClientResponseMapperApi.MAPPER
                    .clientToCreateUserResponse(cliente, TechnicalMessage.SUCCESS);
            return buildResponseService(createUserResponseApi, operation.getName(), null, null, null);
        });
    }

    public static Mono<ServerResponse> buildSuccessResponseAuth(GenerateTokenRequestApi generateTokenRequestApi, GenerateTokenResponseApi getUserLocksRS, Operation operation) {
        return Mono.defer(() -> {

            GenerateTokenResponseApi generateTokenResponseApi = GenerateTokenMapperApi.MAPPER
                    .requestToGetGenerateTokenResponse(generateTokenRequestApi, getUserLocksRS, TechnicalMessage.SUCCESS);
            return buildResponseServiceAuth(generateTokenResponseApi, operation.getName(), null, null, null);
        });
    }

    public static Mono<ServerResponse> buildResponseBusinessException(CreateUserRequestApi createUserRequestApi, BusinessException businessException) {
        return buildUserManagerResponse(createUserRequestApi, null, businessException.getTechnicalMessage(), null, businessException, null);
    }

    public static Mono<ServerResponse> buildResponseBusinessExceptionAuth(GenerateTokenRequestApi generateTokenRequestApi, BusinessException businessException) {
        return buildUserManagerResponseAuth(generateTokenRequestApi, null, businessException.getTechnicalMessage(), null, businessException, null);
    }

    public static Mono<ServerResponse> buildUserManagerResponse(CreateUserRequestApi createUserRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            CreateUserResponseApi createUserResponseApi  = ClientResponseMapperApi.MAPPER.requestToResponse(createUserRequestApi, technicalMessage);
            return buildResponseService(createUserResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static Mono<ServerResponse> buildUserManagerResponseAuth(GenerateTokenRequestApi generateTokenRequestApi,
            String nameOperation,  TechnicalMessage technicalMessage, List<ErrorDetail> errorDetails,
            BusinessException businessException, Exception exception) {
        return Mono.defer(() -> {
            GenerateTokenResponseApi generateTokenResponseApi  = GenerateTokenMapperApi.MAPPER.requestToResponse(generateTokenRequestApi, technicalMessage);
            return buildResponseServiceAuth(generateTokenResponseApi, nameOperation, errorDetails, exception, businessException);
        });
    }

    public static void logRequest(Operation operation, Object userLockRequest) {
        log.info(operation.getNameRequest(), kv(operation.getKvRequest(), userLockRequest));
    }
}
