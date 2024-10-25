package com.planeta.Planeta.Repository;

import com.planeta.Planeta.Model.Reserva;
import com.planeta.Planeta.Model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservaRepository extends JpaRepository<Reserva,Long> {
}
