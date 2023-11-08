package co.edu.uniquindio.subasta.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class Puja implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String direccion ;
    private String codigo;
    private float oferta;
    private LocalDate fechaPuja;
    private Anuncio anuncio;

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

    public float getOferta() {
        return oferta;
    }

    public void setOferta(float oferta) {
        this.oferta = oferta;
    }

    public LocalDate getFechaPuja() {
        return fechaPuja;
    }

    public void setFechaPuja(LocalDate fechaPuja) {
        this.fechaPuja = fechaPuja;
    }

    public co.edu.uniquindio.subasta.model.Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    @Override
    public String toString() {
        return "Puja{" +
                "direccion='" + direccion + '\'' +
                ", codigo='" + codigo + '\'' +
                ", ofertaInicial=" + oferta +
                ", fechaPuja=" + fechaPuja +
                '}';
    }
}
