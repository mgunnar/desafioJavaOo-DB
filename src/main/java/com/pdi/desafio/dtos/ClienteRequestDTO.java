package com.pdi.desafio.dtos;

import com.pdi.desafio.models.enums.TipoCliente;
import io.swagger.annotations.ApiModelProperty;

public record ClienteRequestDTO (

    @ApiModelProperty(value = "Nome do cliente", example = "João")
    String nome,

    @ApiModelProperty(value = "Tipo do cliente", example = "A, B ou C")
    TipoCliente tipoCliente,

    @ApiModelProperty(value = "CPF do cliente", example = "12345678910")
    String cpf){}
