package clases;

import java.io.Serializable;

public class Transferencia implements Serializable {
    private String origen;
    private String destino;
    private double cantidad;

    public Transferencia(String origen, String destino, double cantidad) {
        this.origen = origen;
        this.destino = destino;
        this.cantidad = cantidad;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
}
