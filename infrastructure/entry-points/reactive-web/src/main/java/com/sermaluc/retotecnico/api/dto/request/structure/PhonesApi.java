package com.sermaluc.retotecnico.api.dto.request.structure;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sermaluc.retotecnico.api.util.constant.SchemaConstantsApi;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = SchemaConstantsApi.REQUEST_TITLE)
public class PhonesApi {

    @JsonProperty("number")
    @Schema(description = SchemaConstantsApi.REQUEST_NUMBER)
    String number;

    @JsonProperty("citycode")
    @Schema(description = SchemaConstantsApi.REQUEST_CITY_CODE)
    String citycode;

    @JsonProperty("countrycode")
    @Schema(description = SchemaConstantsApi.REQUEST_COUNTRY_CODE)
    String countrycode;
}