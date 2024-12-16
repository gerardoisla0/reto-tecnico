package com.sermaluc.retotecnico.api.mapper;

import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.request.structure.PhonesApi;
import com.sermaluc.retotecnico.api.dto.response.CreateUserResponseApi;
import com.sermaluc.retotecnico.model.usermanager.Client;
import com.sermaluc.retotecnico.model.usermanager.Phones;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})
public interface ClientRequestMapperApi {

    ClientRequestMapperApi MAPPER = Mappers.getMapper(ClientRequestMapperApi.class);

    @Mapping(target = "name", source = "createUserRequestApi.name")
    @Mapping(target = "email", source = "createUserRequestApi.email")
    @Mapping(target = "password", source = "createUserRequestApi.password")
    @Mapping(target = "phones", source = "createUserRequestApi.phones" , qualifiedByName = "phoneApiToPhone")
    @Mapping(target = "token", source = "createUserRequestApi.token")
    Client createUserRequestToClient(CreateUserRequestApi createUserRequestApi);

    @Named("phoneApiToPhone")
    Phones phoneApiToPhone(PhonesApi phoneApi);

    @Mapping(target = "id", source = "clientRegistered.id")
    @Mapping(target = "created", source = "clientRegistered.created")
    @Mapping(target = "modified", source = "clientRegistered.modified")
    @Mapping(target = "lastLogin", source = "clientRegistered.lastLogin")
    @Mapping(target = "token", source = "clientRegistered.token")
    @Mapping(target = "isActive", source = "clientRegistered.active")
    CreateUserResponseApi clientToCreateUserResponse(Client clientRegistered);

}
