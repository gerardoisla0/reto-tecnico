package com.sermaluc.retotecnico.model.enums;

import com.sermaluc.retotecnico.model.util.enums.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OperationTest {

    @Test
    void shouldReturnNameOperationOnOperation() {

        assertEquals("createUser", Operation.CREATE_USER.getName());
        assertEquals("generateToken", Operation.GENERATE_TOKEN.getName());

    }

    @Test
    void shouldReturnSuccessOnFindByNameWheOperationIsCreateUser() {

        String expected = "createUser";
        Operation actual = Operation.findByName(expected);

        assertNotNull(actual);
        assertEquals(expected, actual.getName());
    }

    @Test
    void shouldReturnIllegalArgumentExceptionOnFindByNameWhenOperationNotFound() {

        String operation = "create User Error";

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Operation.findByName(operation)
        );

        assertNotNull(exception);
        assertEquals(IllegalArgumentException.class.getSimpleName(), exception.getClass().getSimpleName());
        assertEquals(String.format("La operación %s no se encuentra registrada", operation), exception.getMessage());
    }
}
