package com.planeta.Planeta.Repository;

import com.planeta.Planeta.Model.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IViajeRepository extends JpaRepository<Viaje,Long> {
}
