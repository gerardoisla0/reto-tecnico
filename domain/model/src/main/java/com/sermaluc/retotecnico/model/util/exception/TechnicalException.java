package com.sermaluc.retotecnico.model.util.exception;

import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import lombok.Getter;

@Getter
public class TechnicalException extends UserManagerException {

    public TechnicalException(TechnicalMessage technicalMessage) {
        super(technicalMessage);
    }

    public TechnicalException(Throwable cause, TechnicalMessage technicalMessage) {
        super(cause, technicalMessage);
    }

}
