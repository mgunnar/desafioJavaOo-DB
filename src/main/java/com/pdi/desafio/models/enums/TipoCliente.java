package com.pdi.desafio.models.enums;

public enum TipoCliente {

    A( 10000, 5000, 0.1),
    B( 5000, 1000, 0.05),
    C( 1000, 0, 0);
    private double limiteCreditoInicial;
    private final double valorMinimoCompraParaTerDesconto;
    private final double percentualDesconto;

    public double getLimiteCreditoInicial() {
        return limiteCreditoInicial;
    }

    public double getValorMinimoCompraParaTerDesconto() {
        return valorMinimoCompraParaTerDesconto;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    TipoCliente(double limiteCreditoInicial, double valorMinimoCompraParaTerDesconto, double percentualDesconto) {
        this.limiteCreditoInicial = limiteCreditoInicial;
        this.valorMinimoCompraParaTerDesconto = valorMinimoCompraParaTerDesconto;
        this.percentualDesconto = percentualDesconto;
    }

}
