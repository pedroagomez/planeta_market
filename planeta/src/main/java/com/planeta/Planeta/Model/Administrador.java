package com.planeta.Planeta.Model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("ADMIN")

public class Administrador extends Usuario{


    public Administrador(Long id, String nombre, String apellido, String mail, String password) {
        super(id, nombre, apellido, mail, password);
    }
}
