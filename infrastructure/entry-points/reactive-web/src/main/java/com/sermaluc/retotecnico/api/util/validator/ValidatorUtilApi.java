package com.sermaluc.retotecnico.api.util.validator;

import com.sermaluc.retotecnico.api.dto.response.structure.body.error.ErrorDetail;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ValidatorUtilApi {
    public static void validateField(Boolean isValid, List<ErrorDetail> errors, TechnicalMessage technicalMessage) {
        if (Boolean.FALSE.equals(isValid)) {
            errors.add(ValidatorUtilApi.buildErrorDetail(technicalMessage));
        }
    }

    public static ErrorDetail buildErrorDetail(TechnicalMessage technicalMessage) {
        return ErrorDetail.builder()
                .message(technicalMessage.getMessage())
                .build();
    }
}
