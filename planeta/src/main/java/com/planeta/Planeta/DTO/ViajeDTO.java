package com.planeta.Planeta.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ViajeDTO {

    private Long id;
    private LocalDate fechaSalida;
    private PlanetaDTO destino;
    private Integer asientosDisponibles;
    private Integer capacidadTotal;
    private Double precioPorPasajero;
   // private List<ReservaDTO> reservasDto;

    public ViajeDTO(Long id, LocalDate fechaSalida, PlanetaDTO destino, Integer asientosDisponibles, Integer capacidadTotal, Double precioPorPasajero) {
        this.id = id;
        this.fechaSalida = fechaSalida;
        this.destino = destino;
        this.asientosDisponibles = asientosDisponibles;
        this.capacidadTotal = capacidadTotal;
        this.precioPorPasajero = precioPorPasajero;
        //this.reservasDto = reservasDto;
    }

    public ViajeDTO()
    {

    }
}
