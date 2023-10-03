package co.edu.uniquindio.subasta.model;

import java.time.LocalDate;

public class Puja {

    private String direccion ;
    private String codigo;
    private float ofertaInicial;
    private LocalDate fechaPuja;

    public Puja() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getOfertaInicial() {
        return ofertaInicial;
    }

    public void setOfertaInicial(float ofertaInicial) {
        this.ofertaInicial = ofertaInicial;
    }

    public LocalDate getFechaPuja() {
        return fechaPuja;
    }

}
