package com.segurosbolivar.gestionpolizas.models;

import com.segurosbolivar.gestionpolizas.models.enums.EstadoPoliza;
import com.segurosbolivar.gestionpolizas.models.enums.EstadoRiesgo;
import com.segurosbolivar.gestionpolizas.models.enums.TipoPoliza;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "polizas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPoliza tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPoliza estado;

    @Column(nullable = false)
    private LocalDate fechaInicioVigencia;

    @Column(nullable = false)
    private LocalDate fechaFinVigencia;

    @Column(nullable = false)
    private Integer numeroMeses;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorCanon;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valorPrima;

    @Column(nullable = false)
    private String arrendatario;

    @Column(nullable = false)
    private String arrendador;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Riesgo> riesgos = new ArrayList<>();

    public boolean estaCancelada() {
        return this.estado == EstadoPoliza.CANCELADA;
    }

    public boolean esColectiva() {
        return this.tipo != TipoPoliza.COLECTIVA;
    }

    public void agregarRiesgo(Riesgo riesgo) {
        if (esColectiva() && !this.riesgos.isEmpty()) {
            throw new IllegalStateException("Una póliza individual solo puede tener 1 riesgo");
        }
        riesgo.setPoliza(this);
        this.riesgos.add(riesgo);
    }

    public void cancelarConRiesgos() {
        this.estado = EstadoPoliza.CANCELADA;
        this.riesgos.forEach(r -> r.setEstado(EstadoRiesgo.CANCELADO));
    }

    public void renovar(BigDecimal ipc) {
        if (estaCancelada()) {
            throw new IllegalStateException("No se puede renovar una póliza cancelada");
        }
        BigDecimal incremento = BigDecimal.ONE.add(ipc);
        this.valorCanon = this.valorCanon.multiply(incremento);
        this.valorPrima = this.valorCanon.multiply(BigDecimal.valueOf(numeroMeses));
        this.fechaInicioVigencia = this.fechaFinVigencia.plusDays(1);
        this.fechaFinVigencia = this.fechaInicioVigencia.plusMonths(numeroMeses);
        this.estado = EstadoPoliza.RENOVADA;
    }
}
