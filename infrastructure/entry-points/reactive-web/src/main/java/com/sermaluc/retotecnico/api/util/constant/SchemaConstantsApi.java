package com.sermaluc.retotecnico.api.util.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SchemaConstantsApi {

    // Request
    public static final String REQUEST_TITLE = "Estructura de la petición";

    // Response
    public static final String REQUEST_NAME = "Contiene el nombre del cliente en la petición";
    public static final String REQUEST_EMAIL = "Contiene el email del cliente en la petición";
    public static final String REQUEST_PASSWORD = "Contiene la contraseña del cliente en la petición";
    public static final String REQUEST_PHONES_API = "Estructura genérica de los teléfonos del usuario";
    public static final String REQUEST_NUMBER = "Contiene el número de teléfono en la petición";
    public static final String REQUEST_CITY_CODE = "Contine el código de la ciudad del teléfono en la petición";
    public static final String REQUEST_COUNTRY_CODE = "Contine el código del país del teléfono en la petición";

    public static final String RESPONSE_TITLE = "Estructura de la respuesta";
    public static final String RESPONSE_ID = "Contiene el identificador del cliente generado por Banco en la respuesta";
    public static final String RESPONSE_DATE_CREATED = "Contiene la fecha de creación del cliente en la respuesta";
    public static final String RESPONSE_DATE_MODIFIED = "Contiene la fecha de modificación del cliente en la respuesta";
    public static final String RESPONSE_LAST_LOGIN = "Contiene la última sesión del cliente en la respuesta";
    public static final String RESPONSE_TOKEN = "Contiene el token de autenticación del cliente en la respuesta";
    public static final String RESPONSE_IS_ACTIVE = "Contiene el flag de activo del cliente en la respuesta";
    public static final String RESPONSE_MESSAGE = "Contiene el mensaje de negocio en la respuesta";
    public static final String REQUEST_USER_NAME = "Contiene el usuario en la petición";
}