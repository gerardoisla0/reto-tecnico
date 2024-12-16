package com.sermaluc.retotecnico.model.util.exception;

import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class BusinessException extends UserManagerException {


    public BusinessException(TechnicalMessage technicalMessage) {
        super(technicalMessage);
    }

    public BusinessException(String message, TechnicalMessage technicalMessage) {
        super(message, technicalMessage);
    }

}
