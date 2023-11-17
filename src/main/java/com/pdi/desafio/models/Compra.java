package com.pdi.desafio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity (name = "compras")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Compra{

    @Id
    @Column(name = "id_compra")
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long idCompra;

    Double valor;

    @Column(name = "numero_conta")
    String numeroConta;

    @Column(name = "data_criacao")
    private Date dataCriacao;
}