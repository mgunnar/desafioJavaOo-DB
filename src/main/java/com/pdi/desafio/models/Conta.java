package com.pdi.desafio.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
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

    @Column(name = "data_criacao")
    private Date dataCriacao;
}