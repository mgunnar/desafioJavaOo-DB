package com.pdi.desafio.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Size;
import java.util.Date;


@Entity (name = "contas")
@SequenceGenerator(name = "conta_sequence", sequenceName = "conta_sequence", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Conta {

    @Id
    @Column(name = "numero_conta", unique = true)
    @Size(min = 5, max = 5, message = "Numero da conta deve ter exatamente 5 digitos.")
    private String numeroConta;
    private Double limite;
    private Double saldo;

    private String cpfCliente;

    @ManyToOne
    @JoinColumn(name = "cpfCliente", insertable = false, updatable = false)
    private Cliente cliente;

    @Column(name = "data_criacao")
    private Date dataCriacao;
}