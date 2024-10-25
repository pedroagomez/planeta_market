package com.planeta.Planeta.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ReservaDTO {
    private Long id;
    private Long clienteId;
    private ViajeDTO viaje;
    private LocalDate fechaReserva;
    private List<PasajeroDTO> pasajeros;
    private Double precioTotal;

    public ReservaDTO(Long id, Long clienteId, ViajeDTO viaje, LocalDate fechaReserva, List<PasajeroDTO> pasajeros, Double precioTotal) {
        this.id = id;
        this.clienteId = clienteId;
        this.viaje = viaje;
        this.fechaReserva = fechaReserva;
        this.pasajeros = pasajeros;
        this.precioTotal = precioTotal;
    }

    public ReservaDTO() {
    }
}