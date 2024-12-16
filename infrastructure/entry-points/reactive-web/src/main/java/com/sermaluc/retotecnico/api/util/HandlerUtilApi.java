package com.sermaluc.retotecnico.api.util;

import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.response.GenerateTokenResponseApi;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import com.sermaluc.retotecnico.api.dto.response.CreateUserResponseApi;
import com.sermaluc.retotecnico.api.dto.response.structure.body.error.ErrorDetail;
import com.sermaluc.retotecnico.model.util.enums.Operation;
import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@UtilityClass
public class HandlerUtilApi {
    public static final String KV_RESPONSE = "RS";
    public static final String NAME_RESPONSE = "Response";
    public static Operation getOperationByName(String operation) {
        try {
            return Operation.findByName(operation);
        } catch (Exception exception) {
            log.info("Get Operation Error Response", kv("getOperationErrorRS", exception), kv("getOperation", operation));
            return Operation.USER_MANAGER;
        }
    }
    public static Mono<ServerResponse> buildResponseService(CreateUserResponseApi createUserResponseApi,
            String nameOperation, List<ErrorDetail> errorDetails, Exception exception,
            BusinessException businessException) {

        Operation operation = HandlerUtilApi.getOperationByName(nameOperation);

        String nameError;
        String kv;

        if (Objects.nonNull(errorDetails) && !errorDetails.isEmpty()) {
            log.info(operation.getNameResponse().replace(NAME_RESPONSE, " Error Details Response"),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, "ErrorDetailsRS"), errorDetails));
        }

        if (Objects.nonNull(businessException)) {
            log.info(operation.getNameResponse().replace(NAME_RESPONSE, "Error Business Response"),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, "ErrorRS"), createUserResponseApi),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, "ErrorBusinessRS"), businessException));
        } else if (Objects.nonNull(exception)) {

            boolean isOpen = exception instanceof CallNotPermittedException;

            nameError = isOpen ? " Error Fallback Open Response" : " Error Fallback Response";
            kv = isOpen ? "ErrorFallbackOpenRS" : "ErrorFallbackRS";

            log.info(operation.getNameResponse().replace(NAME_RESPONSE, nameError),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, kv), createUserResponseApi),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, kv), exception));
        } else {
            log.info(operation.getNameResponse(), kv(operation.getKvResponse(), createUserResponseApi));
        }

        return ServerResponse
                .status(HttpStatus.OK)
                .bodyValue(createUserResponseApi);
    }

    public static Mono<ServerResponse> buildResponseServiceAuth(
            GenerateTokenResponseApi generateTokenResponseApi,
            String nameOperation, List<ErrorDetail> errorDetails, Exception exception,
            BusinessException businessException) {

        Operation operation = HandlerUtilApi.getOperationByName(nameOperation);

        String nameError;
        String kv;

        if (Objects.nonNull(errorDetails) && !errorDetails.isEmpty()) {
            log.info(operation.getNameResponse().replace(NAME_RESPONSE, " Error Details Response"),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, "ErrorDetailsRS"), errorDetails));
        }

        if (Objects.nonNull(businessException)) {
            log.info(operation.getNameResponse().replace(NAME_RESPONSE, "Error Business Response"),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, "ErrorRS"), generateTokenResponseApi),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, "ErrorBusinessRS"), businessException));
        } else if (Objects.nonNull(exception)) {

            boolean isOpen = exception instanceof CallNotPermittedException;

            nameError = isOpen ? " Error Fallback Open Response" : " Error Fallback Response";
            kv = isOpen ? "ErrorFallbackOpenRS" : "ErrorFallbackRS";

            log.info(operation.getNameResponse().replace(NAME_RESPONSE, nameError),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, kv), generateTokenResponseApi),
                    kv(operation.getKvResponse().replace(KV_RESPONSE, kv), exception));
        } else {
            log.info(operation.getNameResponse(), kv(operation.getKvResponse(), generateTokenResponseApi));
        }

        return ServerResponse
                .status(HttpStatus.OK)
                .bodyValue(generateTokenResponseApi);
    }
}
