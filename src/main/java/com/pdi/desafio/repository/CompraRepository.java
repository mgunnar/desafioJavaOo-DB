package com.pdi.desafio.repository;

import com.pdi.desafio.models.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CompraRepository extends JpaRepository<Compra, String> {
}
