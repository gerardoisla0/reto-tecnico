package com.sermaluc.retotecnico.config;

import com.sermaluc.retotecnico.model.gateway.ClientDataGateway;
import com.sermaluc.retotecnico.usecase.usermanager.UserManagerUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UseCasesConfigTest {

    @Mock
    private ClientDataGateway clientDataGateway;

    @InjectMocks
    private UseCasesConfig useCasesConfig;


    @Test
    void shouldCreateSecurityUseCase() {
        UserManagerUseCase userManagerUseCase =
                useCasesConfig.userManagerUseCase(clientDataGateway);
        Assertions.assertNotNull(userManagerUseCase);
    }

}
