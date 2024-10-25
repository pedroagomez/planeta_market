package com.planeta.Planeta.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planeta_id")
    private Planeta destino;

    @NotNull(message = "La fecha de viaje es obligatoria")
    @Future(message = "La fecha de reserva debe estar en el futuro")
    private LocalDate fechaViaje;

    @NotNull(message = "El campo capacidad es obligatorio")
    @Min(value=1, message = "Debe haber al menos 1 asiento ")
    private Integer capacidadTotal;
    @NotNull(message = "El campo asientos disponibles es obligatorio")
    private Integer asientosDisponibles;
    @NotNull(message = "El campo precio por pasajero es obligatorio")
    @Min(value=1, message = "El precio tiene que ser superior a 1 ")
    private Double precioPorPasajero;

    @OneToMany(mappedBy = "viaje")
    private List<Reserva> reservas = new ArrayList<>();


    public Viaje(Long id, Planeta destino, LocalDate fechaViaje, Integer capacidadTotal, Integer asientosDisponibles, Double precioPorPasajero, List<Reserva> reservas) {
        this.id = id;
        this.destino = destino;
        this.fechaViaje = fechaViaje;
        this.capacidadTotal = capacidadTotal;
        this.asientosDisponibles = asientosDisponibles;
        this.precioPorPasajero = precioPorPasajero;
        this.reservas = reservas;
    }

    public Viaje() {
    }


}
