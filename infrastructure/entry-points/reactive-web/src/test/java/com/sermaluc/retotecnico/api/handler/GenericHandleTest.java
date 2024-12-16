package com.sermaluc.retotecnico.api.handler;

import com.sermaluc.retotecnico.api.config.JwtTokenProvider;
import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.request.structure.PhonesApi;
import com.sermaluc.retotecnico.model.usermanager.Phones;
import com.sermaluc.retotecnico.usecase.usermanager.UserManagerUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.sermaluc.retotecnico.model.util.enums.Operation;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
public class GenericHandleTest {

    @Autowired
    protected ApplicationContext context;

    @MockBean
    protected UserManagerUseCase userManagerUseCase;

    @MockBean
    protected JwtTokenProvider jwtTokenProvider;
    protected WebTestClient webTestClient;

    protected static final String FALLBACK_METHOD_NAME = "fallback";
    public static final String SERVICE_NAME = "UserLocksService";

    protected CreateManagerHandler buildCreateManagerHandler() {
        return new CreateManagerHandler(userManagerUseCase);
    }

    protected AuthenticationHandler buildAuthenticationHandler() {
        return new AuthenticationHandler(jwtTokenProvider);
    }

    @Test
    void shouldBuildCustomerIdentityRequestWhenBodyIsCreateUse() {

        CreateUserRequestApi  createUserRequestApi = buildCreateUserRequest();

        assertNotNull(createUserRequestApi);
        assertNotNull(createUserRequestApi.getEmail());
        assertNotNull(createUserRequestApi.getName());
        assertNotNull(createUserRequestApi.getPassword());
        assertNotNull(createUserRequestApi.getPhones());
    }

    @Test
    void shouldBuildUnderAgeRequestWhenBodyIsGenerateToken() {

        GenerateTokenRequestApi generateTokenRequestApi = buildGenerateTokenRequestApi();
        assertNotNull(generateTokenRequestApi);
        assertNotNull(generateTokenRequestApi.getUsername());
    }

    protected CreateUserRequestApi buildCreateUserRequest() {
        return CreateUserRequestApi
                .builder()
                .email("email@domain.cl")
                .name("hola")
                .password("G4kll2ni19hhh")
                .phones(buildPhones())
                .build();
    }

    protected GenerateTokenRequestApi buildCreateGenerateToken() {
        return GenerateTokenRequestApi
                .builder()
                .username("Julio")
                .build();
    }


    protected CreateUserRequestApi buildBadRequest() {
        return CreateUserRequestApi
                .builder()
                .email("email@email.com")
                .name("hola")
                .password("12312clave")
                .phones(buildPhones())
                .build();
    }

    protected GenerateTokenRequestApi buildGenerateTokenRequestApi() {
        return GenerateTokenRequestApi
                .builder().username("username")
                .build();
    }

    private PhonesApi[] buildPhones() {
        List<PhonesApi> phoneList = new ArrayList<>();

        phoneList.add(PhonesApi.builder()
                .number("123123")
                .citycode("12")
                .countrycode("CO")
                .build());

        phoneList.add(PhonesApi.builder()
                .number("456456")
                .citycode("34")
                .countrycode("US")
                .build());

        PhonesApi[] phones = phoneList.toArray(new PhonesApi[0]);
        return phones;
    }
}
