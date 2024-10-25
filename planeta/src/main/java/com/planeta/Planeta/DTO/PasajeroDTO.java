package com.planeta.Planeta.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasajeroDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    @JsonIgnore
    private Long reservaId;

    public PasajeroDTO(Long id, String nombre, String apellido, String email, Long reservaId) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.reservaId = reservaId;
    }

    public PasajeroDTO() {
    }
}
