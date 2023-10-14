package co.edu.uniquindio.subasta.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class Anuncio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Producto producto;
    private String nombre;
    private String descripcion;
    private String foto;
    private String nombreAnunciante;
    private LocalDate fechaPublicacion;
    private LocalDate fechaTerminacion;

    private float valorInicial;
    private int codigo ;

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaTerminacion() {
        return fechaTerminacion;
    }

    public void setFechaTerminacion(LocalDate fechaTerminacion) {
        this.fechaTerminacion = fechaTerminacion;
    }

    public float getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(float valorInicial) {
        this.valorInicial = valorInicial;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", foto='" + foto + '\'' +
                ", nombreAnunciante='" + nombreAnunciante + '\'' +
                ", fechaPublicacion=" + fechaPublicacion +
                ", fechaTerminacion=" + fechaTerminacion +
                ", valorInicial=" + valorInicial +
                ", codigo='" + codigo + '\'' +
                '}';
    }

}
