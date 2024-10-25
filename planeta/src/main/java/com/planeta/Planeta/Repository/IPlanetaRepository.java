package com.planeta.Planeta.Repository;

import com.planeta.Planeta.Model.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPlanetaRepository extends JpaRepository<Planeta,Long> {
}
