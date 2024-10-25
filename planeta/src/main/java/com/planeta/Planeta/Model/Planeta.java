package com.planeta.Planeta.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
public class Planeta {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "El campo nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;
    @NotBlank(message = "El campo tipo es obligatorio")
    @Size(min = 2, max = 50, message = "El tipo debe tener entre 2 y 50 caracteres")
    private String tipo;

    @NotNull(message = "Los kilómetros cuadrados no pueden ser nulos")
    @Min(value = 1, message = "Los kilómetros cuadrados deben ser al menos 1")
    private int kmCuadrados;


    @OneToMany(mappedBy = "planeta")
    private List<ClientePlanetaPropiedad> propiedades;

    public Planeta(Long id, String nombre, String tipo, int kmCuadrados, List<ClientePlanetaPropiedad> propiedades) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.kmCuadrados = kmCuadrados;
        this.propiedades = propiedades;
    }

    public Planeta() {
    }
}
