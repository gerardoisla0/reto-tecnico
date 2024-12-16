package com.sermaluc.retotecnico.model.gateway;

import com.sermaluc.retotecnico.model.usermanager.Client;
import reactor.core.publisher.Mono;

public interface ClientDataGateway {
    Mono<Client> findUserExist(String email);
    Mono<Client> saveUser(Client cliente);
}
