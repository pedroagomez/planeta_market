package com.planeta.Planeta.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @NotNull(message = "El cliente no puede ser nulo")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planeta_id")
    @NotNull(message = "El planeta no puede ser nulo")
    private Planeta planeta;


    private Double kilometrosCuadrados;

    public Propiedad(Long id, Cliente cliente, Planeta planeta, Double kilometrosCuadrados) {
        this.id = id;
        this.cliente = cliente;
        this.planeta = planeta;
        this.kilometrosCuadrados = kilometrosCuadrados;
    }

    public Propiedad() {
    }

}
