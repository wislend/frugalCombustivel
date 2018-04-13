package com.example.deyvi.frugalcombustive.entity;

public class Veiculo {

    private String marca;
    private String moledo;
    private Double volumeTanque;
    private Double quilometragem;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMoledo() {
        return moledo;
    }

    public void setMoledo(String moledo) {
        this.moledo = moledo;
    }

    public double getVolumeTanque() {
        return volumeTanque;
    }

    public void setVolumeTanque(Double volumeTanque) {
        this.volumeTanque = volumeTanque;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(Double quilometragem) {
        this.quilometragem = quilometragem;
    }
}
