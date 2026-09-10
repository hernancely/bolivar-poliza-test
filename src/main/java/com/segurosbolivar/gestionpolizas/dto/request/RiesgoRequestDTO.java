package com.segurosbolivar.gestionpolizas.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiesgoRequestDTO {

    @NotBlank(message = "La descripcion del riesgo es obligatoria")
    private String descripcion;

    @NotBlank(message = "La direccion del inmueble es obligatoria")
    private String direccionInmueble;
}
