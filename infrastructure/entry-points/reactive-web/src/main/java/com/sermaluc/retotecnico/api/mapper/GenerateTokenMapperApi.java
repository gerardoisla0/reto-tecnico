package com.sermaluc.retotecnico.api.mapper;

import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.response.CreateUserResponseApi;
import com.sermaluc.retotecnico.api.dto.response.GenerateTokenResponseApi;
import com.sermaluc.retotecnico.model.util.enums.TechnicalMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(imports = { LocalDateTime.class, DateTimeFormatter.class})

public interface GenerateTokenMapperApi {

    GenerateTokenMapperApi MAPPER = Mappers.getMapper(GenerateTokenMapperApi.class);
    @Mapping(target = "message", source = "technicalMessage.message")
    GenerateTokenResponseApi requestToResponse(
            GenerateTokenRequestApi generateTokenRequestApi, TechnicalMessage technicalMessage);

    @Mapping(target = "message", source = "technicalMessage.message")
    GenerateTokenResponseApi  requestToGetGenerateTokenResponse(
            GenerateTokenRequestApi generateTokenRequestApi, GenerateTokenResponseApi generateTokenResponseApi, TechnicalMessage technicalMessage);

}
