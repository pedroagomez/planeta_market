package com.planeta.Planeta.Repository;

import com.planeta.Planeta.Model.Pasajero;
import com.planeta.Planeta.Model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPasajeroRepository extends JpaRepository<Pasajero,Long> {
}
