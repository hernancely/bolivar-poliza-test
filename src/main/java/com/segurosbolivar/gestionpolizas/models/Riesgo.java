package com.segurosbolivar.gestionpolizas.models;

import com.segurosbolivar.gestionpolizas.models.enums.EstadoRiesgo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "riesgos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Riesgo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String direccionInmueble;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRiesgo estado;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "poliza_id", nullable = false)
    private Poliza poliza;

    public void cancelar(){
        this.estado = EstadoRiesgo.CANCELADO;
    }

}
