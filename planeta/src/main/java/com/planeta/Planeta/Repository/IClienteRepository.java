package com.planeta.Planeta.Repository;

import com.planeta.Planeta.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IClienteRepository extends JpaRepository<Cliente,Long> {
    boolean existsByMail(String mail);
    Optional<Cliente> buscarPorMail(String mail);
}
