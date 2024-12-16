package com.sermaluc.retotecnico.api;

import com.sermaluc.retotecnico.api.dto.request.CreateUserRequestApi;
import com.sermaluc.retotecnico.api.dto.request.GenerateTokenRequestApi;
import com.sermaluc.retotecnico.api.dto.response.CreateUserResponseApi;
import com.sermaluc.retotecnico.api.dto.response.GenerateTokenResponseApi;
import com.sermaluc.retotecnico.api.handler.AuthenticationHandler;
import com.sermaluc.retotecnico.api.handler.CreateManagerHandler;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.sermaluc.retotecnico.model.util.enums.Operation;

import java.util.function.Consumer;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
@Configuration
@OpenAPIDefinition(info = @Info(title = "Swagger User Manager", version = "1.0",
        description = "Documentación para la gestión de Clientes v1.0"))
public class UserManagerRouteRest {

    private static final String SUCCESSFUL = "Successful Operation";
    private static final String AUTHORIZATION = "Authorization";
    private static final String MESSAGE_DESCRIPTION = "Authorization: Bearer";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(CreateManagerHandler createManagerHandler, AuthenticationHandler authenticationHandler){
        return route()
                .POST(Operation.CREATE_USER.getPath(), request -> request
                        .bodyToMono(CreateUserRequestApi.class)
                        .flatMap(createRS -> {
                            String authorizationHeader = request.headers().header("Authorization").stream().findFirst().orElse(null);
                            String token = null;
                            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                                token = authorizationHeader.substring(7);
                                createRS.setToken(token);
                            }
                            return createManagerHandler.process(createRS);
                        }), userManager(Operation.CREATE_USER))
                .POST(Operation.GENERATE_TOKEN.getPath(), request -> request
                        .bodyToMono(GenerateTokenRequestApi.class)
                        .flatMap(authenticationHandler::process), generateTokenOperation(Operation.GENERATE_TOKEN))
                .build();
    }
    private Consumer<Builder> generateTokenOperation(Operation operation) {
        return ops -> ops.tag(operation.getKvRequest())
                .operationId(operation.getName()).summary(operation.getNameRequest())
                .tags(new String[] { "GenerateToken" }).requestBody(
                        requestBodyBuilder().implementation(
                                GenerateTokenRequestApi.class)).response(
                        responseBuilder().responseCode("200").description(SUCCESSFUL)
                                .implementation(GenerateTokenResponseApi.class))
                .response(responseBuilder().responseCode("404"));
    }
    private Consumer<Builder> userManager(Operation operation) {
        return ops -> ops.tag(operation.getKvRequest())
                .operationId(operation.getName()).summary(operation.getNameRequest())
                .tags(new String[] { "UserManager" }).requestBody(
                        requestBodyBuilder().implementation(
                                CreateUserRequestApi.class))
                .parameter(parameterBuilder()
                        .in(ParameterIn.HEADER)
                        .name(AUTHORIZATION)
                        .description(MESSAGE_DESCRIPTION)
                        .required(true))
                .response(
                        responseBuilder().responseCode("200").description(SUCCESSFUL)
                                .implementation(CreateUserResponseApi.class))
                .response(responseBuilder().responseCode("404"));
    }
}
