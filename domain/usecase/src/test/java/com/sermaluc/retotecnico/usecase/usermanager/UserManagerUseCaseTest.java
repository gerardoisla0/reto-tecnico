package com.sermaluc.retotecnico.usecase.usermanager;

import com.sermaluc.retotecnico.model.gateway.ClientDataGateway;
import com.sermaluc.retotecnico.model.usermanager.Client;
import com.sermaluc.retotecnico.model.usermanager.Phones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserManagerUseCaseTest {

    @Mock
    private ClientDataGateway clientDataGateway;

    private UserManagerUseCase userManagerUseCase;

    private Mono<Client> processClient;
    private Client processClientCrud;
    @BeforeEach
    public void setUp() {
        userManagerUseCase = new UserManagerUseCase(clientDataGateway);
        processClient = buildMonoClient();
        processClientCrud = buildClient();

    }

    @Test
    void shouldUserAlreadyRegister() {

        given(clientDataGateway.findUserExist(any())).willReturn(processClient);

        Mono<Client> response = userManagerUseCase.registerClient(processClientCrud);

        StepVerifier.create(response)
                .assertNext(client -> {
                    assertEquals("gerar@domain.cl", client.getEmail());
                }).verifyComplete();

        verify(clientDataGateway, times(1)).findUserExist(any());
    }

    @Test
    void shouldUserRegisterSuccess() {

        given(clientDataGateway.findUserExist(any())).willReturn(Mono.empty());
        given(clientDataGateway.saveUser(any())).willReturn(processClient);

        Mono<Client> response = userManagerUseCase.registerClient(processClientCrud);

        StepVerifier.create(response)
                .assertNext(client -> {
                    assertEquals("gerar@domain.cl", client.getEmail());
                }).verifyComplete();

        verify(clientDataGateway, times(1)).findUserExist(any());
        verify(clientDataGateway, times(1)).saveUser(any());
    }

    private Mono<Client> buildMonoClient(){
        return Mono.just(Client.builder()
                .name("Peter")
                .email("gerar@domain.cl")
                .password("123456")
                .phones(buildPhones())
                .build());
    }

    private Client buildClient(){
        return Client.builder()
                .name("Peter")
                .email("gerar@domain.cl")
                .password("123456")
                .phones(buildPhones())
                .build();
    }

    private Phones[] buildPhones() {
        List<Phones> phoneList = new ArrayList<>();

        phoneList.add(Phones.builder()
                .number("123123")
                .citycode("12")
                .countrycode("CO")
                .build());

        phoneList.add(Phones.builder()
                .number("456456")
                .citycode("34")
                .countrycode("US")
                .build());

        Phones[] phones = phoneList.toArray(new Phones[0]);
        return phones;
    }
}
