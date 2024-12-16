package com.sermaluc.retotecnico.h2.mapper;

import com.sermaluc.retotecnico.h2.model.ClientData;
import com.sermaluc.retotecnico.model.usermanager.Client;
import com.sermaluc.retotecnico.model.usermanager.Phones;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SecurityMapperTest {

    ClientMapper mapper = ClientMapper.MAPPER;

    @Test
    void shouldMapClientDomainToClientData() {
        Client cliente = Client
                .builder()
                .email("gerar@gmail.com")
                .password("mypublickey")
                .name("names")
                .phones(buildPhones())
                .build();

        ClientData data = mapper.domainToData(cliente);

        assertEquals("gerar@gmail.com", data.getEmail());
        assertEquals("mypublickey", data.getPassword());
        assertEquals("names", data.getName());
    }

    @Test
    void shouldGetErrorWithNullSecurity() {

        ClientData data = mapper.domainToData(null);

        assertNull(data);
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
