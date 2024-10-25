package com.planeta.Planeta.Repository;

import com.planeta.Planeta.Model.ClientePlanetaPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientePlanetaRepo extends JpaRepository<ClientePlanetaPropiedad,Long> {
}
