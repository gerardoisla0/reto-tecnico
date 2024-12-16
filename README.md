# Reto técnico Implementado con Arquitectura Limpia
## Antes de Iniciar

El proyecto fue desarrollado siguiendo un modelo de arquitectura limpia y programación reactiva, lo que permite manejar grandes volúmenes de transacciones de manera eficiente. Se utilizó Spring WebFlux con Java 8 y se estructuró la lógica en capas bien definidas: dominio, infraestructura y aplicación, aplicando el patrón de puertos y adaptadores. Esta arquitectura modular no solo mejora la mantenibilidad del sistema, sino que también facilita la implementación de pruebas unitarias, asegurando la calidad del software. Adicional se tiene el patrón de CircuitBreaker en caso de fallos repetidos. Por último se mantiene una cobertura sin excepciones de 38%.
# Arquitectura

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

## Instalación

- Instalar Java 1.8
- Abrir el proyecto con IDE Intellij Idea de preferencia.
- Base de datos en H2 in memory autogenerado, se adjunta Script de tablas CLIENT y PHONE.
- Utilizar el comando bootRun de gradle
- El puerto se expone en 8083
- OpenApi: http://localhost:8083/api-docs/reto-tecnico/docs/swagger-ui/index.html
- Se debe generar un Token con la operación GenerateToken - POSTMAN
```json
curl --location 'http://localhost:8083/api/v1/user-manager/generate-token' \
--header 'Content-Type: application/json' \
--data '{
"username" : "gerardo"
}'
```
- Con ese Token JWT generado se puede proceder a ejecutar la operación Create-User - POSTMAN
```json
curl --location 'http://localhost:8083/api/v1/user-manager/create-user' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnZXJhcmRvIiwiZXhwIjoxNzM0MzM3OTk2LCJpYXQiOjE3MzQzMzQzOTZ9.y1mZci7M9Ek96J0qewz7xvO7L6nSn4Tc3GJFHkkyM9VeVIbe2C83PyAWNzXHOD0SjtuKZAaoR0OgRWH5E1Ucqw' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Julio Isla",
"email": "gerard@domain.cl",
"password": "G4kll2ni19h",
"phones": [{
"number": "123123",
"citycode": "03",
"countrycode": "CO"
}
]
}'
```
- Se puede validar la cobertura con el comando  ./gradlew jacocoMergedReport , se mantiene en 38% sin excepciones en cada uno de los subproyectos.



