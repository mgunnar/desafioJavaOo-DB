package com.pdi.desafio.models.enums;

public enum TipoCliente {

    A( 10000, 2000, 0.1, true, 5000,500),
    B( 5000, 1000, 0.05, false, 0, 0),
    C( 1000, 0, 0, false, 0, 0);
    private double limiteCreditoInicial;
    private final double valorMinimoCompraParaTerDesconto;
    private final double percentualDesconto;
    private final boolean aumentaLimiteLiberado;
    private final double valorGastoParaAumentoLimite;
    private final double valorDeAumentoLimite;

    public double getLimiteCreditoInicial() {
        return limiteCreditoInicial;
    }

    public double getValorMinimoCompraParaTerDesconto() {
        return valorMinimoCompraParaTerDesconto;
    }

    public double getPercentualDesconto() {
        return percentualDesconto;
    }
    public boolean isAumentaLimiteLiberado() {
        return aumentaLimiteLiberado;
    }

    public double getValorGastoParaAumentoLimite() {
        return valorGastoParaAumentoLimite;
    }

    public double getValorDeAumentoLimite() {
        return valorDeAumentoLimite;
    }

    TipoCliente(double limiteCreditoInicial, double valorMinimoCompraParaTerDesconto, double percentualDesconto, boolean aumentaLimiteLiberado, double valorGastoParaAumentoLimite, double valorDeAumentoLimite) {
        this.limiteCreditoInicial = limiteCreditoInicial;
        this.valorMinimoCompraParaTerDesconto = valorMinimoCompraParaTerDesconto;
        this.percentualDesconto = percentualDesconto;
        this.aumentaLimiteLiberado = aumentaLimiteLiberado;
        this.valorGastoParaAumentoLimite = valorGastoParaAumentoLimite;
        this.valorDeAumentoLimite = valorDeAumentoLimite;
    }

}
