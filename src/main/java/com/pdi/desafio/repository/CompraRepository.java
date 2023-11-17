package com.pdi.desafio.repository;

import com.pdi.desafio.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, String> {
}
