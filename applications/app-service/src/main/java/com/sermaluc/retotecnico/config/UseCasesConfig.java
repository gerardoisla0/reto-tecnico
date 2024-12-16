package com.sermaluc.retotecnico.config;

import com.sermaluc.retotecnico.model.gateway.ClientDataGateway;
import com.sermaluc.retotecnico.usecase.usermanager.UserManagerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

        @Bean
        public UserManagerUseCase userManagerUseCase(
                ClientDataGateway clientDataGateway) {
                return new UserManagerUseCase(clientDataGateway);
        }
}
