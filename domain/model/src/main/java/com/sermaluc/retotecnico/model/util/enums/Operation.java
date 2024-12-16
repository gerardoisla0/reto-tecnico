package com.sermaluc.retotecnico.model.util.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum Operation {

    USER_MANAGER("userManager", null, "User Manager Request", "userManagerRQ", "User Manager Response", "userManagerRS"),
    CREATE_USER("createUser", "/api/v1/user-manager/create-user", "Create User Request", "createUserRQ", "Create User Response", "createUserRS"),
    GENERATE_TOKEN("generateToken", "/api/v1/user-manager/generate-token", "Generate Token Request", "generateTokenRQ", "Generate Token Response", "generateTokenRS");

    private final String name;
    private final String path;
    private final String nameRequest;
    private final String kvRequest;
    private final String nameResponse;
    private final String kvResponse;

    private static final Map<String, Operation> BY_OPERATION = new HashMap<>();

    static {
        for (Operation operation : values()) {
            BY_OPERATION.put(operation.name, operation);
        }
    }

    public static Operation findByName(String name) {
        return BY_OPERATION.computeIfAbsent(name, key -> {
            throw new IllegalArgumentException(String.format("La operación %s no se encuentra registrada", key));
        });
    }
}
