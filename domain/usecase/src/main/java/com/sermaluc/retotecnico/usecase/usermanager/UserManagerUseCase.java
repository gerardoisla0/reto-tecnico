package com.sermaluc.retotecnico.usecase.usermanager;
import com.sermaluc.retotecnico.model.gateway.ClientDataGateway;
import com.sermaluc.retotecnico.model.usermanager.Client;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.Date;

@RequiredArgsConstructor
public class UserManagerUseCase {

    private final ClientDataGateway clientDataGateway;

    public Mono<Client> registerClient(Client processClient) {
        return clientDataGateway.findUserExist(processClient.getEmail())
                .switchIfEmpty(Mono.defer(() -> registerNewClient(processClient)));
    }

    private Mono<Client> registerNewClient(Client processClient) {
        processClient.setActive(true);
        processClient.setCreated(new Date());
        processClient.setLastLogin(new Date());
        processClient.setModified(null);
        return clientDataGateway.saveUser(processClient);
    }

}

