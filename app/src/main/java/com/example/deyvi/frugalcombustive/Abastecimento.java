package com.example.deyvi.frugalcombustive;

import java.util.Date;

public class Abastecimento {

    private Double odometro;
    private Double precoCombustivel;
    private Double valorAbastecimento;
    private Date data;
    private String posto;
    private boolean tanqueCheio;

    public Double getOdometro() {
        return odometro;
    }

    public void setOdometro(Double odometro) {
        this.odometro = odometro;
    }

    public Double getPrecoCombustivel() {
        return precoCombustivel;
    }

    public void setPrecoCombustivel(Double precoCombustivel) {
        this.precoCombustivel = precoCombustivel;
    }

    public Double getValorAbastecimento() {
        return valorAbastecimento;
    }

    public void setValorAbastecimento(Double valorAbastecimento) {
        this.valorAbastecimento = valorAbastecimento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public boolean isTanqueCheio() {
        return tanqueCheio;
    }

    public void setTanqueCheio(boolean tanqueCheio) {
        this.tanqueCheio = tanqueCheio;
    }
}
