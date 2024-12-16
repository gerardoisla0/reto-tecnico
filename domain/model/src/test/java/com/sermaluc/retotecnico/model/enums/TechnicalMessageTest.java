package com.sermaluc.retotecnico.model.enums;

import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TechnicalMessageTest
{
    @Test
    void shouldReturnSuccessOnFindByExternalCode() {

        TechnicalMessage technicalMessage = TechnicalMessage.findByMessage("SUCCESS");

        assertEquals(TechnicalMessage.SUCCESS.getMessage(), technicalMessage.getMessage());
    }

    @Test
    void shouldReturnSuccessOnFindInRateLimitingByIPIsBlocked() {

        TechnicalMessage technicalMessage = TechnicalMessage.findByMessage("¡Ups! Lo sentimos la petición no es valida");

        assertEquals(TechnicalMessage.FIELD_INVALID_REQUEST_STRUCTURE.getMessage(), technicalMessage.getMessage());
    }

}
