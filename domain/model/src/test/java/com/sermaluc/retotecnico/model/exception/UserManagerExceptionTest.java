package com.sermaluc.retotecnico.model.exception;

import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import com.sermaluc.retotecnico.model.util.exception.UserManagerException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserManagerExceptionTest {

    @Test
    void shouldReturnInternalServerMessageOnExperianException() {

        TechnicalMessage technicalMessage = TechnicalMessage.ERROR_INTERNAL_SERVER;

        UserManagerException exception = new UserManagerException(technicalMessage);

        assertNotNull(exception);
        assertEquals(technicalMessage.getMessage(), exception.getTechnicalMessage().getMessage());
    }

    @Test
    void shouldReturnUnauthorizedMessageOnExperianException() {

        String message = "This is a mistake";
        TechnicalMessage technicalMessage = TechnicalMessage.ERROR_BAD_REQUEST;

        UserManagerException exception = new UserManagerException(message, technicalMessage);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(technicalMessage.getMessage(), exception.getTechnicalMessage().getMessage());
    }

    @Test
    void shouldReturnIllegalArgumentExceptionOnExperianException() {

        IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Oups error");
        TechnicalMessage technicalMessage = TechnicalMessage.ERROR_INTERNAL_SERVER;

        UserManagerException exception = new UserManagerException(illegalArgumentException, technicalMessage);

        assertNotNull(exception);
        assertEquals(illegalArgumentException, exception.getCause());
        assertEquals(technicalMessage.getMessage(), exception.getTechnicalMessage().getMessage());
    }
}
