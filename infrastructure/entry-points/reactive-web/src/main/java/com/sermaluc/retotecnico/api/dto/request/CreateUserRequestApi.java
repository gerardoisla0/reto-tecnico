package com.sermaluc.retotecnico.api.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sermaluc.retotecnico.api.dto.request.structure.PhonesApi;
import com.sermaluc.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Jacksonized
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = SchemaConstantsApi.REQUEST_TITLE)
public class CreateUserRequestApi {

    @JsonProperty("name")
    @Schema(description = SchemaConstantsApi.REQUEST_NAME)
    String name;

    @JsonProperty("email")
    @Schema(description = SchemaConstantsApi.REQUEST_EMAIL)
    String email;

    @JsonProperty("password")
    @Schema(description = SchemaConstantsApi.REQUEST_PASSWORD)
    String password;

    @JsonProperty("phones")
    @Schema(description = SchemaConstantsApi.REQUEST_PHONES_API)
    PhonesApi[] phones;

    String token;
}
