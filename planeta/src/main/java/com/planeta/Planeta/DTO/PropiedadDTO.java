package com.planeta.Planeta.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PropiedadDTO {
    private Long id;
    private Long clienteId;
    private Long planetaId;
    private Double kilometrosCuadrados;


    public PropiedadDTO(Long id, Long clienteId, Long planetaId, Double kilometrosCuadrados) {
        this.id = id;
        this.clienteId = clienteId;
        this.planetaId = planetaId;
        this.kilometrosCuadrados = kilometrosCuadrados;
    }

    public PropiedadDTO() {
    }
}
