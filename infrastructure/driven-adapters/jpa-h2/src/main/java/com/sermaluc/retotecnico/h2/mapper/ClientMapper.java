package com.sermaluc.retotecnico.h2.mapper;

import com.sermaluc.retotecnico.h2.model.ClientData;
import com.sermaluc.retotecnico.model.usermanager.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Mapper
public interface ClientMapper {

    ClientMapper MAPPER = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "email", source = "client.email")
    @Mapping(target = "name", source = "client.name")
    @Mapping(target = "password", source = "client.password")
    @Mapping(target = "created", source = "client.created")
    @Mapping(target = "modified", source = "client.modified")
    @Mapping(target = "lastLogin", source = "client.lastLogin")
    @Mapping(target = "token", source = "client.token")
    @Mapping(target = "active", source = "client.active")
    @Mapping(target = "phoneList", source = "client.phones")
    ClientData domainToData(Client client);

    @Mapping(target = "email", source = "clientData.email")
    @Mapping(target = "name", source = "clientData.name")
    @Mapping(target = "password", source = "clientData.password")
    @Mapping(target = "created", source = "clientData.created")
    @Mapping(target = "modified", source = "clientData.modified")
    @Mapping(target = "lastLogin", source = "clientData.lastLogin")
    @Mapping(target = "token", source = "clientData.token")
    @Mapping(target = "active", source = "clientData.active")
    @Mapping(target = "phones", source = "clientData.phoneList")
    Client dataToDomain(ClientData clientData);

    default LocalDateTime map(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    default Date map(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
