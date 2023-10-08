package co.edu.uniquindio.subasta.model;

import java.io.Serial;
import java.io.Serializable;

public class Anuncio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String nombre;
    private String descripcion;
    private String codigo ;

    public Anuncio() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
