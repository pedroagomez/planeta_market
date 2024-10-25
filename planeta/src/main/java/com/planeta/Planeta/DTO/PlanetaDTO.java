package com.planeta.Planeta.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlanetaDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private int kmCuadrados;

    public PlanetaDTO(Long id, String nombre, String tipo, int kmCuadrados) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.kmCuadrados = kmCuadrados;
    }

    public PlanetaDTO() {
    }
}
