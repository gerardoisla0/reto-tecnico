package com.sermaluc.retotecnico.h2;

import ch.qos.logback.classic.Level;
import com.sermaluc.retotecnico.h2.mapper.ClientMapper;
import com.sermaluc.retotecnico.h2.model.ClientData;
import com.sermaluc.retotecnico.model.gateway.ClientDataGateway;
import com.sermaluc.retotecnico.model.usermanager.Client;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import com.sermaluc.retotecnico.model.util.exception.TechnicalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.function.Predicate;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ClientAdapter implements ClientDataGateway {

    private final ClientRepository clientRepository;

    @Override
    public Mono<Client> findUserExist(String email) {
        return Mono.defer(() ->
                        Mono.justOrEmpty(clientRepository.findByEmail(email))
                                .doOnSubscribe(subscription -> log.info("H2 Find by Email Request", kv("h2findEmailQueryRQ", email)))
                                .doOnSuccess(client -> {
                                    if(Objects.nonNull(client)) {
                                        throw new BusinessException(TechnicalMessage.ERROR_CLIENT_EXIST);
                                    }
                                })
                                .doOnError(error -> log.error("H2 Find by Email Error Response: ", error))
                                .onErrorMap(
                                        exception -> !(exception instanceof BusinessException),
                                        exception -> new TechnicalException(exception, TechnicalMessage.ERROR_INTERNAL_SERVER)
                                )
                )
                .flatMap(clientData -> {
                    if (clientData != null) {
                        Client client = ClientMapper.MAPPER.dataToDomain(clientData);
                        return Mono.just(client);
                    }

                    return Mono.empty();
                });
    }

    @Override
    public Mono<Client> saveUser(Client processClient) {
        ClientData clientData = ClientMapper.MAPPER.domainToData(processClient);

        return Mono.defer(() ->
                        Mono.justOrEmpty(clientRepository.save(clientData))
                                .doOnSubscribe(subscription -> log.info("H2 Save by Username Request", kv("h2saveUsernameQuerySaveRQ", processClient)))
                                .doOnError(error -> log.error("H2 Save Username Error Response: ", error))
                                .onErrorMap(
                                        exception -> !(exception instanceof BusinessException),
                                        exception -> new TechnicalException(exception, TechnicalMessage.ERROR_INTERNAL_SERVER)
                                )
                )
                .flatMap(savedClientData -> {
                    if (savedClientData != null) {
                        Client client = ClientMapper.MAPPER.dataToDomain(savedClientData);
                        return Mono.just(client);
                    }
                    return Mono.empty();
                });
    }
}
