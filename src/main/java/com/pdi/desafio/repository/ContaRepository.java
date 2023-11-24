package com.pdi.desafio.repository;

import com.pdi.desafio.models.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, String> {
    @Query("SELECT MAX(c.numeroConta) FROM contas c")
    Long findMaxNumeroConta();
    Optional<Conta> findByNumeroConta(String numeroConta);
}
