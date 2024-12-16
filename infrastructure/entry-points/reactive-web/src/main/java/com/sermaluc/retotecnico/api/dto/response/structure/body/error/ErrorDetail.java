package com.sermaluc.retotecnico.api.dto.response.structure.body.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder(toBuilder = true)
@Jacksonized
public class ErrorDetail {

    @JsonInclude(NON_NULL)
    String message;

}
