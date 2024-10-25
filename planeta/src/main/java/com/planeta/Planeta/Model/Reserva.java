package com.planeta.Planeta.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "El cliente no puede ser nulo")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "viaje_id")
    @NotNull(message = "El viaje no puede ser nulo")
    private Viaje viaje;

    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReserva;

    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pasajero> pasajeros = new ArrayList<>();

    @NotNull(message = "El campo es obligatorio")
    @Min(value = 1, message = "El precio tiene que ser superior a 1 ")
    private Double precioTotal;

    public Reserva(Long id, Cliente cliente, Viaje viaje, LocalDate fechaReserva, List<Pasajero> pasajeros, Double precioTotal) {
        this.id = id;
        this.cliente = cliente;
        this.viaje = viaje;
        this.fechaReserva = fechaReserva;
        this.pasajeros = pasajeros;
        this.precioTotal = precioTotal;
    }

    public Reserva() {
    }
}
