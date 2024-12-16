package com.sermaluc.retotecnico.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sermaluc.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(toBuilder = true)
public class GenerateTokenResponseApi {

    @JsonProperty("token")
    @Schema(description = SchemaConstantsApi.RESPONSE_TOKEN)
    String token;
    @JsonProperty("message")
    @Schema(description = SchemaConstantsApi.RESPONSE_MESSAGE)
    String message;
}
