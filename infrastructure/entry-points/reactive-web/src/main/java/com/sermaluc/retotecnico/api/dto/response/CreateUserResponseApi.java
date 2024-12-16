package com.sermaluc.retotecnico.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sermaluc.retotecnico.api.dto.request.structure.PhonesApi;
import com.sermaluc.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateUserResponseApi {

    @JsonProperty("id")
    @Schema(description = SchemaConstantsApi.RESPONSE_ID)
    String id;

    @JsonProperty("created")
    @Schema(description = SchemaConstantsApi.RESPONSE_DATE_CREATED)
    String created;

    @JsonProperty("modified")
    @Schema(description = SchemaConstantsApi.RESPONSE_DATE_MODIFIED)
    String modified;

    @JsonProperty("lastLogin")
    @Schema(description = SchemaConstantsApi.RESPONSE_LAST_LOGIN)
    String lastLogin;

    @JsonProperty("token")
    @Schema(description = SchemaConstantsApi.RESPONSE_TOKEN)
    String token;

    @JsonProperty("isActive")
    @Schema(description = SchemaConstantsApi.RESPONSE_IS_ACTIVE)
    String isActive;

    @JsonProperty("message")
    @Schema(description = SchemaConstantsApi.RESPONSE_MESSAGE)
    String message;
}