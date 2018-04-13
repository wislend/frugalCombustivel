package com.example.deyvi.frugalcombustive.entity;

import java.util.Date;

public class Abastecimento {

    private double odometro;
    private double precoCombustivel;
    private double valorAbastecimento;
    private String combustive;
    private boolean anteriorEmFalta;
    private Date data;
    private String posto;

    public String getCombustive() {
        return combustive;
    }

    public void setCombustive(String combustive) {
        this.combustive = combustive;
    }

    public boolean isAnteriorEmFalta() {
        return anteriorEmFalta;
    }

    public void setAnteriorEmFalta(boolean anteriorEmFalta) {
        this.anteriorEmFalta = anteriorEmFalta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    private String observacoes;
    private boolean tanqueCheio;
    private double valorTotal;

    public double getOdometro() {
        return odometro;
    }

    public void setOdometro(double odometro) {
        this.odometro = odometro;
    }

    public double getPrecoCombustivel() {
        return precoCombustivel;
    }

    public void setPrecoCombustivel(double precoCombustivel) {
        this.precoCombustivel = precoCombustivel;
    }

    public double getValorAbastecimento() {
        return valorAbastecimento;
    }

    public void setValorAbastecimento(double valorAbastecimento) {
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
