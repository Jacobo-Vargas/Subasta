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
    private String fechaPuja;
    private String codigoAnuncio;
    private String cedulaComprador;

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

    public String getFechaPuja() {
        return fechaPuja;
    }

    public void setFechaPuja(String fechaPuja) {
        this.fechaPuja = fechaPuja;
    }

    public String getCodigoAnuncio() {
        return codigoAnuncio;
    }

    public void setCodigoAnuncio(String codigoAnuncio) {
        this.codigoAnuncio = codigoAnuncio;
    }

    public String getCedulaComprador() {
        return cedulaComprador;
    }

    public void setCedulaComprador(String cedulaComprador) {
        this.cedulaComprador = cedulaComprador;
    }

    @Override
    public String toString() {
        return "Puja{" +
                "direccion='" + direccion + '\'' +
                ", codigo='" + codigo + '\'' +
                ", oferta=" + oferta +
                ", fechaPuja='" + fechaPuja + '\'' +
                ", codigoAnuncio='" + codigoAnuncio + '\'' +
                '}';
    }
}
