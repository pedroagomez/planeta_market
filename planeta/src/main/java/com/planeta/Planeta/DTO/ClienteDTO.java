package com.planeta.Planeta.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter

public class ClienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private String password;
    private List<ClientePlanetaPropiedadDTO> propiedades;
    private List<ReservaDTO> reservas;

    public ClienteDTO(Long id, String nombre, String apellido, String mail, String password,List<ClientePlanetaPropiedadDTO> propiedades, List<ReservaDTO> reservas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.password=password;
        this.propiedades = propiedades;
        this.reservas = reservas;
    }

    public ClienteDTO() {



    }
}
