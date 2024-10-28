package com.planeta.Planeta.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministradorDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private String password;

    public AdministradorDTO(Long id, String nombre, String apellido, String mail, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.password = password;
    }

    public AdministradorDTO() {
    }
}
