package com.sermaluc.retotecnico.api.mapper;

import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.response.CreateUserResponseApi;
import com.sermaluc.retotecnico.model.usermanager.Client;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})
public interface ClientResponseMapperApi {

    ClientResponseMapperApi MAPPER = Mappers.getMapper(ClientResponseMapperApi.class);
    @Mapping(target = "message", source = "technicalMessage.message")
    CreateUserResponseApi requestToResponse(
            CreateUserRequestApi createUserRequestApi, TechnicalMessage technicalMessage);

    @Mapping(target = "id", source = "client.id")
    @Mapping(target = "created", source = "client.created")
    @Mapping(target = "modified", source = "client.modified")
    @Mapping(target = "lastLogin", source = "client.lastLogin")
    @Mapping(target = "token", source = "client.token")
    @Mapping(target = "isActive", source = "client.active")
    @Mapping(target = "message", source = "technicalMessage.message")
    CreateUserResponseApi clientToCreateUserResponse(Client client, TechnicalMessage technicalMessage);
}
