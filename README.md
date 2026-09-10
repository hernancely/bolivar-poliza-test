# API de Gestión de Pólizas

Prueba técnica — Desarrollador TI/Sénior. **Módulo 2**: API funcional con Spring Boot.

## Stack

- Java 17
- Spring Boot 4.1.1 (starters modulares)
- Spring Data JPA, Spring Web MVC, Spring Validation, Lombok
- H2 (base de datos en memoria)
- Gradle

## Estructura del proyecto

```
com.segurosbolivar.gestionpolizas
├── models/
│   ├── Poliza.java
│   ├── Riesgo.java
│   └── enums/
│       ├── TipoPoliza.java
│       ├── EstadoPoliza.java
│       └── EstadoRiesgo.java
├── dto/
│   ├── request/
│   │   └── RiesgoRequestDTO.java
│   ├── response/
│   │   ├── PolizaResponseDTO.java
│   │   └── RiesgoResponseDTO.java
│   └── CoreEventoDTO.java
├── mapper/
│   ├── PolizaMapper.java
│   └── RiesgoMapper.java
├── repository/
│   ├── PolizaRepository.java
│   └── RiesgoRepository.java
├── service/
│   ├── PolizaService.java
│   ├── RiesgoService.java
│   ├── CoreNotificationService.java
│   └── serviceimpl/
│       ├── PolizaServiceImpl.java
│       ├── RiesgoServiceImpl.java
│       └── CoreNotificationServiceImpl.java
├── controller/
│   ├── PolizaController.java
│   ├── RiesgoController.java
│   └── CoreMockController.java
├── config/
│   ├── ApiKeyInterceptor.java
│   ├── WebConfig.java
│   └── GlobalExceptionHandler.java
└── exception/
    ├── PolizaNoEncontradaException.java
    ├── RiesgoNoEncontradoException.java
    ├── PolizaCanceladaException.java
    ├── ReglaNegocioException.java
    └── ErrorResponse.java
```

## Cómo ejecutar

```bash
./gradlew bootRun
```

La API queda disponible en `http://localhost:8080`.

Consola H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:polizasdb`
- Usuario: `sa`
- Password: *(vacío)*

## Autenticación

Todos los endpoints bajo `/polizas/**` y `/riesgos/**` requieren el header:

```
x-api-key: 123456
```

El endpoint `/core-mock/evento` no requiere autenticación (simula ser invocado internamente por el CORE vía capa media weblogic).

## Endpoints

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/polizas?tipo=&estado=` | Lista pólizas, filtros opcionales (`INDIVIDUAL`\|`COLECTIVA`, `ACTIVA`\|`RENOVADA`\|`CANCELADA`) |
| GET | `/polizas/{id}/riesgos` | Lista riesgos de una póliza |
| POST | `/polizas/{id}/renovar` | Renueva póliza, incrementa canon/prima por IPC |
| POST | `/polizas/{id}/cancelar` | Cancela póliza y todos sus riesgos en cascada |
| POST | `/polizas/{id}/riesgos` | Agrega riesgo (solo pólizas Colectivas) |
| POST | `/riesgos/{id}/cancelar` | Cancela un riesgo puntual |
| POST | `/core-mock/evento` | Mock del CORE, registra el intento de sincronización en logs |

## Ejemplos de uso

```bash
# Listar pólizas colectivas
curl -X GET "http://localhost:8080/polizas?tipo=COLECTIVA" \
  -H "x-api-key: 123456"

# Agregar riesgo a una póliza colectiva
curl -X POST "http://localhost:8080/polizas/2/riesgos" \
  -H "x-api-key: 123456" \
  -H "Content-Type: application/json" \
  -d '{"descripcion": "Bodega", "direccionInmueble": "Calle 20 #10-15"}'

# Renovar póliza
curl -X POST "http://localhost:8080/polizas/1/renovar" \
  -H "x-api-key: 123456"

# Cancelar póliza (cancela riesgos en cascada)
curl -X POST "http://localhost:8080/polizas/1/cancelar" \
  -H "x-api-key: 123456"
```

## Reglas de negocio implementadas

- Una póliza **individual** solo admite 1 riesgo.
- No se puede renovar una póliza cancelada (`409 Conflict`).
- Cancelar una póliza cancela en cascada todos sus riesgos asociados.
- Agregar riesgos solo está permitido para pólizas **colectivas** (`400 Bad Request` en caso contrario).
- Toda operación que modifica el estado de una póliza o riesgo notifica al mock del CORE (`CoreNotificationService`).

## Manejo de errores

Respuesta estándar para cualquier error controlado:

```json
{
    "timestamp": "2026-09-10T01:43:19.614443",
    "status": 400,
    "error": "Bad Request",
    "mensaje": "direccionInmueble: La direccion del inmueble es obligatoria, descripcion: La descripcion del riesgo es obligatoria"
}
```

| Código | Caso |
|---|---|
| 400 | Validación de campos (`@NotBlank`) o violación de regla de negocio |
| 401 | `x-api-key` ausente o inválida |
| 404 | Póliza o riesgo no encontrado |
| 409 | Operación no permitida por el estado actual (ej. renovar una cancelada) |

