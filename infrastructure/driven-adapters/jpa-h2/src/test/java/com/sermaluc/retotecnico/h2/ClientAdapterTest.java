package com.sermaluc.retotecnico.h2;

import com.sermaluc.retotecnico.h2.model.ClientData;
import com.sermaluc.retotecnico.model.usermanager.Client;
import com.sermaluc.retotecnico.model.util.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientAdapterTest {

    @InjectMocks
    private ClientAdapter ClientAdapter;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void shouldRegisterClient() {
        when(clientRepository.save(any())).thenReturn(null);

        Mono<Client> result = ClientAdapter.saveUser(buildClient());

        StepVerifier.create(result)
                .verifyComplete();
    }
    @Test
    void shouldFindClient() {
        when(clientRepository.findByEmail(anyString())).thenReturn(
                buildMonoClientData());
        Mono<Client> signatureDetailSaved = ClientAdapter.findUserExist("127.0.0.1");

        StepVerifier.create(signatureDetailSaved)
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().equals("El cliente ya se encuentra registrado"))
                .verify();
    }

    @Test
    void shouldFindNotClient() {
        when(clientRepository.findByEmail(anyString())).thenReturn(
                null);
        Mono<Client> signatureDetailSaved = ClientAdapter.findUserExist("127.0.0.1");

        StepVerifier.create(signatureDetailSaved)
                .expectNext()
                .verifyComplete();
    }

    private ClientData buildMonoClientData(){
        return ClientData.builder()
                .name("Peter")
                .email("gerar@domain.cl")
                .password("123456")
                .build();
    }

    private Client buildClient(){
        return Client.builder()
                .name("Peter")
                .email("gerar@domain.cl")
                .password("123456")
                .build();
    }

    private Mono<Client> buildMonoClient(){
        return Mono.just(Client.builder()
                .name("Peter")
                .email("gerar@domain.cl")
                .password("123456")
                .build());
    }
}
