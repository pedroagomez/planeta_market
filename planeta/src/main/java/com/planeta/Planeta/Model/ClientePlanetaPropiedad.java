package com.planeta.Planeta.Model;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class ClientePlanetaPropiedad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "El cliente no puede ser nulo")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "planeta_id")
    @NotNull(message = "El planeta no puede ser nulo")
    private Planeta planeta;

    @NotNull(message = "Los kilómetros cuadrados no pueden ser nulos")
    @Min(value = 1, message = "Los kilómetros cuadrados deben ser al menos 1")
    private Double kilometrosCuadrados;

    public ClientePlanetaPropiedad(Long id, Cliente cliente, Planeta planeta, Double kilometrosCuadrados) {
        this.id = id;
        this.cliente = cliente;
        this.planeta = planeta;
        this.kilometrosCuadrados = kilometrosCuadrados;
    }


    public ClientePlanetaPropiedad() {
    }
}
