package com.planeta.Planeta.Model;


import com.planeta.Planeta.DTO.ReservaDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("CLIENTE")

public class Cliente extends Usuario{

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClientePlanetaPropiedad> propiedades = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    public Cliente(Long id, String nombre, String apellido, String mail, String password, List<ClientePlanetaPropiedad> propiedades, List<Reserva> reservas) {
        super(id, nombre, apellido, mail, password);
        this.propiedades = propiedades;
        this.reservas = reservas;
    }

    public Cliente() {
    }
}

