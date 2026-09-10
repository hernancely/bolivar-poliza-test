package com.segurosbolivar.gestionpolizas.dto.response;


import com.segurosbolivar.gestionpolizas.models.enums.EstadoPoliza;
import com.segurosbolivar.gestionpolizas.models.enums.TipoPoliza;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolizaResponseDTO {

    private Long id;
    private TipoPoliza tipo;
    private EstadoPoliza estado;
    private LocalDate fechaInicioVigencia;
    private LocalDate fechaFinVigencia;
    private Integer numeroMeses;
    private BigDecimal valorCanon;
    private BigDecimal valorPrima;
    private String arrendatario;
    private String arrendador;
}
