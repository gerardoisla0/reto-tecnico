package com.sermaluc.retotecnico.api.util.constant.validator;

import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.response.structure.body.error.ErrorDetail;
import com.sermaluc.retotecnico.helper.validator.ValidatorUtil;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Mono;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;

import java.util.*;

import static com.sermaluc.retotecnico.api.util.validator.ValidatorUtilApi.buildErrorDetail;
import static com.sermaluc.retotecnico.api.util.validator.ValidatorUtilApi.validateField;

@UtilityClass
public class RequestValidatorHandlerApi {

    public static Mono<List<ErrorDetail>> validateRequest(
            CreateUserRequestApi createUserRequestApi) {
        return validateRequestStructure(createUserRequestApi)
                .filter(isValidRequest -> isValidRequest)
                .flatMap(isValidRequest -> validateStructureBody(createUserRequestApi))
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    public static Mono<List<ErrorDetail>> validateRequestAuth(
            GenerateTokenRequestApi generateTokenRequestApi) {
        return Mono.just(generateTokenRequestApi)
                .flatMap(RequestValidatorHandlerApi::validateStructureBodyAuth)
                .switchIfEmpty(Mono.defer(() -> Mono.just(Collections.singletonList(buildErrorDetail(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE)))));
    }

    private static Mono<Boolean> validateRequestStructure(CreateUserRequestApi createUserRequestApi) {
        return Mono.defer(() -> Mono.just(Objects.nonNull(createUserRequestApi)
                && Objects.nonNull(createUserRequestApi.getEmail())
                && Objects.nonNull(createUserRequestApi.getName())
                && Objects.nonNull(createUserRequestApi.getPassword())
                && Objects.nonNull(createUserRequestApi.getPhones())));
    }

    private static Mono<List<ErrorDetail>> validateStructureBody(CreateUserRequestApi createUserRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotBlankAndNotNull(createUserRequestApi.getEmail()),
                        ValidatorUtil.isValidNotBlankAndNotNull(createUserRequestApi.getName()),
                        ValidatorUtil.isValidNotBlankAndNotNull(createUserRequestApi.getPassword()),
                        ValidatorUtil.isValidArray(createUserRequestApi.getPhones()),
                        ValidatorUtil.isValidateFormatEmail(createUserRequestApi.getEmail()),
                        ValidatorUtil.isValidateFormatPassword(createUserRequestApi.getPassword())
                ).map(validations -> {
                    List<ErrorDetail> errors = new ArrayList<>();
                    validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_EMAIL);
                    validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_NAME);
                    validateField(validations.getT3(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_PASSWORD);
                    validateField(validations.getT4(), errors, TechnicalMessage.FIELD_INVALID_ARRAY_PHONES);
                    validateField(validations.getT5(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_FORMAT_EMAIL);
                    validateField(validations.getT6(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_FORMAT_PASSWORD);
                    return errors;
                });
    }

    private static Mono<List<ErrorDetail>> validateStructureBodyAuth(GenerateTokenRequestApi generateTokenRequestApi) {
        return Mono.zip(ValidatorUtil.isValidNotNull(generateTokenRequestApi.getUsername()),
                ValidatorUtil.isValidNotBlank(generateTokenRequestApi.getUsername())
        ).map(validations -> {
            List<ErrorDetail> errors = new ArrayList<>();
            validateField(validations.getT1(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_USERNAME);
            validateField(validations.getT2(), errors, TechnicalMessage.FIELD_INVALID_REQUEST_USERNAME);
            return errors;
        });
    }

}
