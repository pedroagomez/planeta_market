package com.planeta.Planeta.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientePlanetaPropiedadDTO {

    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private Long planetaId;
    private String planetaNombre;
    private Double kilometrosCuadrados;

    public ClientePlanetaPropiedadDTO(Long id, Long clienteId, String clienteNombre, Long planetaId, String planetaNombre, Double kilometrosCuadrados) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.planetaId = planetaId;
        this.planetaNombre = planetaNombre;
        this.kilometrosCuadrados = kilometrosCuadrados;
    }
    public ClientePlanetaPropiedadDTO(Long id, Long planetaId, String planetaNombre, Double kilometrosCuadrados) {
        this.id = id;
        this.planetaId = planetaId;
        this.planetaNombre = planetaNombre;
        this.kilometrosCuadrados = kilometrosCuadrados;
    }


    public ClientePlanetaPropiedadDTO() {
    }
}
