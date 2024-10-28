package com.planeta.Planeta.Repository;

import com.planeta.Planeta.Model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAdminRepository extends JpaRepository<Administrador,Long> {
    Optional<Administrador> findByMail(String mail);
}
